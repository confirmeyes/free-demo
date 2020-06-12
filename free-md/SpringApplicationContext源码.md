### ApplicationContext 概览

![](img\ApplicationContext.png)

- 整合的能力：

1. 对于访问应用组件提供BeanFactory
2. 加载通用的资源文件 （资源加载器）
3. 发布事件，注册监听器
4. 解析消息，支持国际化

- 重点方法
- <font color="red">拥有自动装配能力 ， 利用该接口可以连接和填充Bean实例，且不受Spring控制 </font>

```java
AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException;
```



#### ConfigurableApplicationContext

- <font color="red" size=4 face="Comic Sans MS">重点抽象方法 </font>
- As this is a startup method ， Load or refresh  configuration  ， 
- <font color="red" size=4 face="Comic Sans MS">refres() 作为一个启动方法，实现加载或刷新配置，通过XML文件、properties文件、关系数据库schema</font>
- <font color="red" size=4 face="Comic Sans MS">销毁创建失败的单例，释放资源。所有没有实例化的单例 会被实例化</font>
- <font color="red" size=5 face="Comic Sans MS">只有AbstractApplicationContext 重写实现了refresh方法，其他类都是super调用</font>

```java
/**
 * Load or refresh the persistent representation of the configuration,
 * which might an XML file, properties file, or relational database schema.
 * <p>As this is a startup method, it should destroy already created singletons
 * if it fails, to avoid dangling resources. In other words, after invocation
 * of that method, either all or no singletons at all should be instantiated.
 * @throws BeansException if the bean factory could not be initialized
 * @throws IllegalStateException if already initialized and multiple refresh
 * attempts are not supported
 */
void refresh() throws BeansException, IllegalStateException;
```



#### AbstractApplicationContext

```java
abstract class AbstractApplicationContext extends DefaultResourceLoader
      implements ConfigurableApplicationContext {
```

- 抽象类 --> <font color="red">模板方法设计模式，具体的子类来实现抽象方法</font>

- ```
  an ApplicationContext is supposed
  * to detect special beans defined in its internal bean factory:
  * Therefore, this class automatically registers BeanFactoryPostProcessors BeanPostProcessors ApplicationListeners
  ```

- 应用上下文在内部BeanFactory检测特定的Beans，将 <font color="red">BeanFactoryPostProcessors、 BeanPostProcessors 、ApplicationListener</font> 这些Beans注册进上下文中

- <font color="red">MessageSource</font> 也作为Bean 在上下文中，<font color="red">ApplicationEventMulticaste</font>r 也一样作为Bean

- Implements <font color="red">resource loading</font> through extending DefaultResourceLoader  （extends DefaultResourceLoader）
  

---

#### 只有AbstractApplicationContext 重写实现了refresh方法，其他类都是super调用

重点：更新应用上下文

```java
public void refresh() throws BeansException, IllegalStateException {
   synchronized (this.startupShutdownMonitor) {
      // Prepare this context for refreshing.
      prepareRefresh();

      // Tell the subclass to refresh the internal bean factory.
      ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

      // Prepare the bean factory for use in this context.
      prepareBeanFactory(beanFactory);

      try {
         // Allows post-processing of the bean factory in context subclasses.
         postProcessBeanFactory(beanFactory);

         // Invoke factory processors registered as beans in the context.
         invokeBeanFactoryPostProcessors(beanFactory);

         // Register bean processors that intercept bean creation.
         registerBeanPostProcessors(beanFactory);

         // Initialize message source for this context.
         initMessageSource();

         // Initialize event multicaster for this context.
         initApplicationEventMulticaster();

         // Initialize other special beans in specific context subclasses.
         onRefresh();

         // Check for listener beans and register them.
         registerListeners();

         // Instantiate all remaining (non-lazy-init) singletons.
         finishBeanFactoryInitialization(beanFactory);

         // Last step: publish corresponding event.
         finishRefresh();
      }

      catch (BeansException ex) {
         if (logger.isWarnEnabled()) {
            logger.warn("Exception encountered during context initialization - " +
                  "cancelling refresh attempt: " + ex);
         }

         // Destroy already created singletons to avoid dangling resources.
         destroyBeans();

         // Reset 'active' flag.
         cancelRefresh(ex);

         // Propagate exception to caller.
         throw ex;
      }

      finally {
         // Reset common introspection caches in Spring's core, since we
         // might not ever need metadata for singleton beans anymore...
         resetCommonCaches();
      }
```

### refresh 流程

#### prepareRefresh() 刷新context

##### AnnotationConfigServletWebServerApplicationContext

```
this.scanner.clearCache();
```

- 清理本地元数据缓存 和 所有 缓存元数据类 ，
-  共享缓存 重置为 本地缓存 （new了一个LinkedHashMap ， 初始值256 ， 扩容因子0.75）
- local cache , 默认最大值 256的 Map

##### AbstractApplicationContext

```java
// Initialize any placeholder property sources in the context environment
initPropertySources();

// Validate that all properties marked as required are resolvable
// see ConfigurablePropertyResolver#setRequiredProperties
getEnvironment().validateRequiredProperties();

earlyApplicationEvents = new LinkedHashSet<>();
```



#### 告诉子类去刷新 内部工厂 DefaultListableBeanFactory

```
// Tell the subclass to refresh the internal bean factory.
ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
```

实际是ConfigurableListableBeanFactory的实现类  DefaultListableBeanFactory 

##### GenericApplicationContext

![](img\GenericApplicationContext.png)



- this.beanFactory 是 DefaultListableBeanFactory

![](img\DefaultListableBeanFactory.png)

- GenericApplicationContext   ， DefaultListableBeanFactory   都实现了 BeanDefinitionRegistry
-  可以对Bean信息进行注册 registerBeanDefinition

```
this.beanFactory.setSerializationId(getId());
```

- serializableFactories 最大容量 8

- ```java
  private static final Map<String, Reference<DefaultListableBeanFactory>> serializableFactories =
        new ConcurrentHashMap<>(8);
  ```
  
- 创建 DefaultListableBeanFactory 弱引用 ， 放在 serializableFactories  ConcurrentHashMap中 

- debug时 serializationId 序列化ID为  application
```
if (serializationId != null) {
      serializableFactories.put(serializationId, new WeakReference<>(this));
   }
   else if (this.serializationId != null) {
      serializableFactories.remove(this.serializationId);
   }
   this.serializationId = serializationId;
}
```

- 返回 DefaultListableBeanFactory

#### 在上下文中准备 BeanFactory

- 为 BeanFactory 配置基本特征 比如 类加载器、后置处理器  ClassLoader and post-processors.
- 使用context的 类加载器
- 配置context 与 BeanFactory的回调 ， 为BeanFactory 添加ApplicationContextAwareProcessor

- 忽略一些 Aware 组件 ， 注册一些 资源解析功能

- ```java
  // BeanFactory interface not registered as resolvable type in a plain factory.
  // MessageSource registered (and found for autowiring) as a bean.
  beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
  beanFactory.registerResolvableDependency(ResourceLoader.class, this);
  beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
  beanFactory.registerResolvableDependency(ApplicationContext.class, this);
  
  // Register early post-processor for detecting inner beans as ApplicationListeners.
  beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));
  ```

- early post-processor 处理，注册 应用监听器的Bean

- ```java
  // Register early post-processor for detecting inner beans as ApplicationListeners.
  beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));
  ```

- Register default environment beans.







- allowBeanDefinitionOverriding
- allowCircularReferences

```java
protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
   if (this.allowBeanDefinitionOverriding != null) {
      beanFactory.setAllowBeanDefinitionOverriding(this.allowBeanDefinitionOverriding);
   }
   if (this.allowCircularReferences != null) {
      beanFactory.setAllowCircularReferences(this.allowCircularReferences);
   }
}
```

##### postProcessBeanDefinitionRegistry

- 在BeanFactoryPostProcessor 之前 添加Bean 定义信息

##### ImportBeanDefinitionRegistrar















