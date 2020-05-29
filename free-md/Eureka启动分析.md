1. InstanceInfoFactory
2. com.netflix.discovery.DiscoveryClient 
3. DefaultEurekaServerContext    : Initializing ...
4. cluster.PeerEurekaNodes       : Adding new peer nodes
5. AbstractInstanceRegistry  : Finished initializing remote region registries.
6. DefaultEurekaServerContext    : Initialized
7. EurekaServiceRegistry        : Registering application EUREKASERVER with eureka with status UP
8. EurekaServerBootstrap   : Initialized server context

#### EurekaBootStrap

```java
this.initEurekaEnvironment();
this.initEurekaServerContext();
```

```java
((PeerAwareInstanceRegistry)registry).openForTraffic(applicationInfoManager, registryCount);
```

```java
public void openForTraffic(ApplicationInfoManager applicationInfoManager, int count) {
    this.expectedNumberOfClientsSendingRenews = count;
    this.updateRenewsPerMinThreshold();
```



#### AbstractInstanceRegistry

- <font color="red">**自我保护机制**</font> ，若关闭 ------>  evict 剔除服务。 若开启 --------> 进行服务保护（ 网络抖动或者真的挂掉 ）
- eureka 优化点  <font color="red">**RenewalPercentThreshold());**</font>  续约百分比阈值
- RenewalPercentThreshold 续约百分比阈值 ----默认0.85D


```java
public void evict(long additionalLeaseMs) {
   .....
int registrySize = (int)this.getLocalRegistrySize();
int registrySizeThreshold = (int)((double)registrySize * this.serverConfig.getRenewalPercentThreshold());
int evictionLimit = registrySize - registrySizeThreshold;
int toEvict = Math.min(expiredLeases.size(), evictionLimit);
if (toEvict > 0) {
    logger.info("Evicting {} items (expired={}, evictionLimit={})", new Object[]{toEvict, expiredLeases.size(), evictionLimit});
    Random random = new Random(System.currentTimeMillis());
```
- **RenewalThresholdUpdateIntervalMs**  续约阈值更新间隔 ----默认900000ms
- **ExpectedClientRenewalIntervalSeconds** 期望客户端续约间隔-----默认30s 
- 定时任务 **Timer** 进行剔除没有心跳的服务 
- 缺点：Timer 多线程并行处理任务时运行多个TimerTask，只要其中之一没有捕获抛出的异常，其他任务就会自动终止，建议使用**ScheduleExecutorService**
- 优化点 剔除服务时间间隔毫秒数     <font color="red">**EvictionIntervalTimerInMs**</font>
- 可以进行 <font color="red">**服务的快速下线**</font>
```java
this.evictionTimer.schedule((TimerTask)this.evictionTaskRef.get(), this.serverConfig.getEvictionIntervalTimerInMs(), this.serverConfig.getEvictionIntervalTimerInMs());
```

#### <font color="red">**三级缓存机制**</font>

- **register -->   readWriteCacheMap  -->  readonlyCacheMap**

- 每次注册的时候  更新register ，失效readWriteCacheMap中的相关服务  

- readWriteCacheMap   readonly 之间 ，定时任务Timer 每隔30s 更新一次 （从readWriteCacheMap   中取数据放入readonlyCacheMap 主要提供读，高可用性）。不是强一致性 

  ```java
  eureka.server.response-cache-update-interval-ms=1000
  ```

- 优化点   <font color="red">**response-cache-update-interval-ms**</font>

- 优化点   <font color="red">**eureka.server.use-read-only-response-cache=false**</font>

- 三级缓存 默认为  true ，  false直接从readWriteCacheMap 中取数据，不去readonlyCacheMap


```java
@VisibleForTesting
ResponseCacheImpl.Value getValue(Key key, boolean useReadOnlyCache) {
    ResponseCacheImpl.Value payload = null;

    try {
        if (useReadOnlyCache) {
            ResponseCacheImpl.Value currentPayload = (ResponseCacheImpl.Value)this.readOnlyCacheMap.get(key);
            if (currentPayload != null) {
                payload = currentPayload;
            } else {
                payload = (ResponseCacheImpl.Value)this.readWriteCacheMap.get(key);
                this.readOnlyCacheMap.put(key, payload);
            }
        } else {
            payload = (ResponseCacheImpl.Value)this.readWriteCacheMap.get(key);
        }
    } catch (Throwable var5) {
        logger.error("Cannot get value for key : {}", key, var5);
    }

    return payload;
}
```

- CAP 中的C 一致性无法保证
- **eureka的数据结构**

```java
private final ConcurrentHashMap<String, Map<String, Lease<InstanceInfo>>> registry = new ConcurrentHashMap();
```

##### ApplicationResource

```java
@POST
@Consumes({"application/json", "application/xml"})
public Response addInstance(InstanceInfo info, @HeaderParam("x-netflix-discovery-replication") String isReplication) {
```



##### InstanceRegistry

```java
public void register(final InstanceInfo info, final boolean isReplication) {
    this.handleRegistration(info, this.resolveInstanceLeaseDuration(info), isReplication);
    super.register(info, isReplication);
}
```

- 服务注册 对readWriteCacheMap 中服务 进行失效

```java
public void register(InstanceInfo registrant, int leaseDuration, boolean isReplication) {
    try {
        this.read.lock();
       .......
        Lease<InstanceInfo> existingLease = (Lease)((Map)gMap).get(registrant.getId());
            synchronized(this.lock) {
                if (this.expectedNumberOfClientsSendingRenews > 0) {
                    ++this.expectedNumberOfClientsSendingRenews;
                    this.updateRenewsPerMinThreshold();
                }
            }
-----------------------------------------------------------------------------------------
        Lease<InstanceInfo> lease = new Lease(registrant, leaseDuration);
-----------------------------------------------------------------------------------------      
       ........
-----------------------------------------------------------------------------------------
        this.invalidateCache(registrant.getAppName(), registrant.getVIPAddress(), registrant.getSecureVipAddress());
-----------------------------------------------------------------------------------------
        logger.info("Registered instance {}/{} with status {} (replication={})", new Object[]{registrant.getAppName(), registrant.getId(), registrant.getStatus(), isReplication});
    } finally {
        this.read.unlock();
    }

}
```

#### Lease\<InstanceInfo>，\<T>泛型 。Lease自带各种时间字段

- 服务信息 注入Lease中， 由于每30s心跳后进行服务续约，这个数据结构便于服务续约 （直接调用方法，而不用进行get、set）

- 服务续约 和 服务下线

- ```java
  public void renew() {
      this.lastUpdateTimestamp = System.currentTimeMillis() + this.duration;
  }
  
  public void cancel() {
      if (this.evictionTimestamp <= 0L) {
          this.evictionTimestamp = System.currentTimeMillis();
      }
  ```


##### ResponseCacheImpl

```java
public void invalidate(String appName, @Nullable String vipAddress, @Nullable String secureVipAddress) {
```

#### EurekaServerAutoConfiguration

- ```java
  @Import({EurekaServerInitializerConfiguration.class})
  @ConditionalOnBean({Marker.class})
  ```

#### EurekaServerInitializerConfiguration 

- ```java
  implements SmartLifecycle
  ```

- ```java
  SmartLifecycle extends Lifecycle
  ```

- Lifecycle -------> springframework.context
- EurekaServerInitializerConfiguration 实现了Lifecycle中 start();  