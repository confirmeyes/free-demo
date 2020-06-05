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



#### postProcessBeanDefinitionRegistry

- 在BeanFactoryPostProcessor 之前 添加Bean 定义信息

#### ImportBeanDefinitionRegistrar















