

#### BeanFactory

```java
public interface BeanFactory {
    String FACTORY_BEAN_PREFIX = "&";
```

- 正常getObject()拿到的是对象，"&"拿到的是产生这个Bean的工厂

##### Bean生命周期

- Bean生命周期 完整标准顺序如下
- 源码自带说明书

```java
Bean factory implementations should support the standard bean lifecycle interfaces as far as possible. The full set of initialization methods and their standard order is:
```

```java
* <ol>
* <li>BeanNameAware's {@code setBeanName}
* <li>BeanClassLoaderAware's {@code setBeanClassLoader}
* <li>BeanFactoryAware's {@code setBeanFactory}
* <li>EnvironmentAware's {@code setEnvironment}
* <li>EmbeddedValueResolverAware's {@code setEmbeddedValueResolver}
* <li>ResourceLoaderAware's {@code setResourceLoader}
* (only applicable when running in an application context)
* <li>ApplicationEventPublisherAware's {@code setApplicationEventPublisher}
* (only applicable when running in an application context)
* <li>MessageSourceAware's {@code setMessageSource}
* (only applicable when running in an application context)
* <li>ApplicationContextAware's {@code setApplicationContext}
* (only applicable when running in an application context)
* <li>ServletContextAware's {@code setServletContext}
* (only applicable when running in a web application context)
    
* <li>{@code postProcessBeforeInitialization} methods of BeanPostProcessors

* <li>InitializingBean's {@code afterPropertiesSet}
    
* <li>a custom init-method definition

* <li>{@code postProcessAfterInitialization} methods of BeanPostProcessors
* </ol>
```

```java
<ol>
* <li>{@code postProcessBeforeDestruction} methods of DestructionAwareBeanPostProcessors
* <li>DisposableBean's {@code destroy}
* <li>a custom destroy-method definition
* </ol>
```



#### FactoryBean

- 一个依赖于BeanFactory，生产个性化Bean的接口，因此它也是synchronization同步的

- getObjectType() , getObject() 这两个主要方法，在启动的时候进行暴露，甚至在post-processor启动之前

- ```
  @link #getObjectType()} {@link #getObject()} invocations may arrive early in
  the bootstrap process, even ahead of any post-processor setup.
  ```

- ```
  A bean that implements this interface cannot be used as a normal bean.
  ```

- ```java
  A FactoryBean is defined in a bean style,
  ```



#### Environment

- ```
  ConfigurableEnvironment
  ```

- ```java
  PropertySourcesPlaceholderConfigurer
  ```

- @Profile 不同的环境下使用不同的@Configuration配置

- 组合类 StandardEnvironment