### zuul在生产中遇到的三个问题

- token不向后面服务传
- 老项目中路由问题
- 实现动态路由

1. 配置zuul.sensitive-headers:
2. zuul 自定义filter
3. 获取请求的url，设置requestcontext 的 serviceid ，url （路由到一个服务的多个地址）
4. 设置requestcontext routehost 和配置文件定义 （路由到具体地址）

### 网关的职责

- 分发服务
- 身份认证 （鉴权）
- 过滤请求
- 监控
- 动态路由
- 限流

### 网关 约等于 一系列过滤器

- pre  请求路由之前调用， 可利用这种过滤器实现身份认证，选择微服务，限流，记录日志

- route  请求路由到微服务，构建发送给微服务的请求，并用 和HTTP Client 或 ribbon 请求微服务

- post  调用微服务后执行，可用于添加header，记录日志，将响应发给客户端

- error  其他阶段发生错误 ，走此过滤器

  中间若出现异常，都会走 error post 

### 自定义filter

1. 继承 zuulfilter
2. public boolean shouldFilter(); true 执行此过滤器 false 不执行
3. run  过滤器业务逻辑
4. order 数字越小 越先执行

### route 三种filter

1. ribbon
2. simplehost
3. sendforward

### 容错处理

- implment FallbackProvider 接口 进行容错处理


### 过滤器顺序

- sendzuulresponse (false) 只控制不向 route 过滤器执行，
- 加在 shouldfilter 中， 控制后一个filter

### 网关限流

- 令牌桶
- pre
- Ratelimiter
- guva

### 服务限流

- servlet filter
- Ratelimiter