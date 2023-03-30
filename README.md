# Javaweb

## CS 和 BS 的异同点

- CS：客户端架构模式
  - 优点：充分利用客户端机器的资源，减轻服务器的负荷(一部分安全要求不高的计算任务存储任务放在客户端执行，不需要把所有的计算和
    存储都在服务器端执行，从而能够减轻服务器的压力，也能够减轻网络负荷)
  - 缺点：需要安装；升级维护成本较高
- BS:浏览器服务器架构模式
  - 优点：客户端不需要安装；维护成本较低
  - 缺点：所有的计算和存储任务都是放在服务器端的，服务器的负荷较重；在服务端计算完成之后把结果再传输给客户端，因此客户端和服务器端会进行非常频繁的数据通信，从而网络负荷较亚重



## Tomcat

- 介绍：是一个 Server 端的 WebContainer
- 目录结构
  - bin 可执行文件目录
  - conf 配置文件目录
  - lib 存放 lib 的目录
  - logs 日志文件目录
  - webapps 项目部署目录
  - work 工作目录
  - temp 临时目录
- 安装和配置
  - 下载：目录不能有空格和中文
  - 配置环境变量：Tomcat 使用 Java 和 C 来写的，需要 JRE ，所以要配置JAVA_HOME
  - 注意：启动 Tomcat ，运行 startup.bat 文件，浏览器访问 [http://localhost:8080/](http://localhost:8080/)（不能关闭 dos 界面）



## idea 部署Javaweb应用程序

- Javaweb 框架的添加

  - 新建 moudule
  - 右键 moudule 找到 **Add Framework Support**

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091628748.png)

  - 勾选 Web Application

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091628344.png)

  - 完成了框架的添加

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648619.png)

- 配置

  - 编辑配置入口

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648620.png)

  - 编辑配置（url 为网页进入的入口，因为后面的 context 设置为 /，所以这里为这个）

    ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648621.png)

    - Application server 的地址配置只需要到 bin 目录的上层

    ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648622.png)

    - Deployment 中添加 Artifact（注意下面的 Application context 是网页入口，一般为/）

    ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648623.png)

    - **注意：**hello.html 的文件需要和 WEB-INF 文件夹同级

    ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648624.png)

- 添加 Tomcat 依赖（使用里面的方法）

  - Project structure
  - Moudules → Dependencies → Add library → Application sever ，加入 Tomcat

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648625.png)

![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648626.png)

- 说明
  - lib - artifact：先有 artifact，后来才添加的 mysql.jar。此时，这个 jar 包并没有添加到部署包中那么在 projectSettings 中有一个 Problems 中会有提示的,我们点击 fix 选择 add to... 另外，我们也可以直接把 lib文件夹直接新建在 WEB-INF 下。
  - URL：在部署的时候，修改 application Context。然后再回到 server 选项卡，检查 URL 的值。URL 的值指的是 tomcat 启动完成后自动打开你指定的浏览器，然后默认访问的网址。启动后，报错 404 意味着找不到指定的资源。
  - 默认 URL：如果我们的网址是：[http://localhost:8080/pro01/](http://localhost:8080/pro01/) , 那么表明我们访问的是 index.html .我们可以通过 <welcome-file-list> 标签进行设置欢迎页(在 tomcat 的 web.xml 中设置，或者在自己项目的web.xml中设置)
  - 405：当前请求的方法不支持。比如，我们表单method=post , 那么Servlet必须对应 doPost。否则报405错误。

---

## **Servlet**

![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648627.png)

- 乱码问题

  - post 方式：要设置编码，防止中文乱码。

  ```
  //只需在开头加上
  request.setCharacterEncoding("UTF-8");
  ```

  - get 方式（tomcat8 不需要设置编码）

```
//tomcat8 之前
//需要转换的变量
String name = request.getParameter("name");
//将字符串打散成字节数组
byte[] bytes = name.getBytes("ISO-8859-1");
//将字节数组按照设定的方法重新组装成字符串
name = new String(bytes,"UTF-8");
```

- Servlet的继承关系 - 重点查看的是服务方法（service()）

  - 相关方法

    1. 继承关系
       javax.servlet.Servlet接口
       javax.servlet.GenericServlet抽象类
       javax.servlet.http.HttpServlet抽象子类
    2. 相关方法
       javax.servlet.Servlet接口:
       void init(config) - 初始化方法
       void service(request,response) - 服务方法
       void destory() - 销毁方法
    3. 方法属性

    javax.servlet.GenericServlet抽象类：
    void service(request,response) - 仍然是抽象的

    javax.servlet.http.HttpServlet 抽象子类：
    void service(request,response) - 不是抽象的

    1. 方法实现基本介绍

    ```
        1. String method = req.getMethod(); 获取请求的方式
    
        2. 各种if判断，根据请求方式不同，决定去调用不同的do方法
            if (method.equals("GET")) {
                this.doGet(req,resp);
            } else if (method.equals("HEAD")) {
                this.doHead(req, resp);
            } else if (method.equals("POST")) {
                this.doPost(req, resp);
            } else if (method.equals("PUT")) {
    
       3. 在HttpServlet这个抽象类中，do方法都差不多:
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String protocol = req.getProtocol();
            String msg = lStrings.getString("http.method_get_not_supported");
            if (protocol.endsWith("1.1")) {
                resp.sendError(405, msg);
            } else {
                resp.sendError(400, msg);
            }
        }
    ```

- 小结

  1. 继承关系： HttpServlet -> GenericServlet -> Servlet
  2. 核心方法：init() , service() , destroy()
  3. 服务方法：当有请求过来时，service方法会自动响应（其实是tomcat容器调用的）
     在HttpServlet中我们会去分析请求的方式：到底是get、post、head还是delete等等
     然后再决定调用的是哪个do开头的方法，那么在HttpServlet中这些do方法默认都是405的实现风格-**要我们子类去实现对应的方法，否则默认会报405错误**
  4. 因此，我们在新建Servlet时，我们才会去考虑请求方法，从而决定重写哪个do方法

- Servlet的生命周期

  - 生命周期：从出生到死亡的过程就是生命周期。对应Servlet中的三个方法：init() , service() , destroy()
  - 默认情况下：
    1.第一次接收请求时，这个 Servlet 会进行实例化(调用构造方法)、初始化(调用 init())、然后服务(调用 service())
    2.从第二次请求开始，每一次都是服务
    3.当容器关闭时，其中的所有的 servlet 实例会被销毁，调用销毁方法
  - 通过案例发现：
    1. Servlet 实例 tomcat 只会创建一个，所有的请求都是这个实例去响应。
    2. 默认情况下，第一次请求时，tomcat 才会去实例化，初始化，然后再服务。这样的好处是可以提高系统的启动速度 ，这样的缺点是第一次请求时，耗时较长。
    3. 因此得出结论： 如果需要提高系统的启动速度，当前默认情况就是这样。如果需要提高响应速度，我们应该设置Servlet的初始化时机。而Servlet的初始化时机默认是第一次接收请求时实例化和初始化。我们可以通过<load-on-startup>来设置servlet启动的先后顺序,数字越小，启动越靠前，最小值0
  - Servlet在容器中是：单例的、线程不安全的
    - 单例：所有的请求都是同一个实例去响应
    - 线程不安全：一个线程需要根据这个实例中的某个成员变量值去做逻辑判断。但是在中间某个时机，另一个线程改变了这个成员变量的值，从而导致第一个线程的执行路径发生了变化
    - 我们已经知道了servlet是线程不安全的，给我们的启发是： 尽量的不要在servlet中定义成员变量。如果不得不定义成员变量，那么不要去：①不要去修改成员变量的值 ②不要去根据成员变量的值做一些逻辑判断

---

## **Http 协议**

- 基本内容

  - 超文本传输协议
  - 是无状态的

- Http 请求响应包含请求和响应

  - 请求

    ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648628.png)

    - 请求行：1.请求的方式 2.请求的URL 3.请求主体

    - 请求消息头：

      - 包含了很多客户端需要告诉服务器的信息，比如：我的浏览器型号、版本、我能接收的内容的类型、我给你发的内容的类型、内容的长度等等
      - 比较重要的请求消息头

      | 名称           | 功能                                                 |
      | -------------- | ---------------------------------------------------- |
      | Host           | 服务器的主机地址                                     |
      | Accept         | 声明当前请求能够接受的『媒体类型』                   |
      | Referer        | 当前请求来源页面的地址                               |
      | Content-Length | 请求体内容的长度                                     |
      | Content-Type   | 请求体的内容类型，这一项的具体值是媒体类型中的某一种 |
      | Cookie         | 浏览器访问服务器时携带的Cookie数据                   |

    - 请求体：

      get方式，没有请求体，但是有一个queryString
      post方式，有请求体，form data
      json格式，有请求体，request payload

  - 响应

    - 响应行

      - 1.协议 2.响应状态码(200) 3.响应状态(ok)
      - 相应状态码：以编码的形式告诉浏览器当前请求处理的结果

      | 状态码 | 含义                                                      |
      | ------ | --------------------------------------------------------- |
      | 200    | 服务器成功处理了当前请求，成功返回响应                    |
      | 302    | 重定向                                                    |
      | 400    | [SpringMVC特定环境]请求参数问题                           |
      | 403    | 没有权限                                                  |
      | 404    | 找不到目标资源                                            |
      | 405    | 请求方式和服务器端对应的处理方式不一致                    |
      | 406    | [SpringMVC特定环境]请求扩展名和实际返回的响应体类型不一致 |
      | 50X    | 服务器端内部错误，通常都是服务器端抛异常了                |

    - 响应头：包含了服务器的信息；服务器发送给浏览器的信息（内容的媒体类型、编码、内容长度等）

    - 响应体：响应的实际内容（比如请求add.html页面时，响应的内容就是<html><head><body><form....）

---

## **会话**

- HTTP 是无状态的
  - HTTP 无状态 ：服务器无法判断这两次请求是同一个客户端发过来的，还是不同的客户端发过来的
  - 问题：第一次请求是添加商品到购物车，第二次请求是结账；如果这两次请求服务器无法区分是同一个用户的，那么就会导致混乱
  - 解决：会话跟踪技术
- 会话跟踪技术
  - 客户端第一次发请求给服务器，服务器获取session，获取不到，则创建新的，然后响应给客户端。下次客户端给服务器发请求时，会把sessionID带给服务器，那么服务器就能获取到了，那么服务器就判断这一次请求和上次某次请求是同一个客户端，从而能够区分开客户端
  - 常用 API
    - request.getSession() -> 获取当前的会话，没有则创建一个新的会话
    - request.getSession(true) -> 效果和不带参数相同
    - request.getSession(false) -> 获取当前会话，没有则返回null，不会创建新的
    - session.getId() -> 获取sessionID
    - session.isNew() -> 判断当前session是否是新的
    - session.getMaxInactiveInterval() -> session的非激活间隔时长，默认1800秒
    - session.setMaxInactiveInterval()
    - session.invalidate() -> 强制性让会话立即失效
- session 保存作用域

---

## **服务器内部转发 客户端重定向**

- 服务器内部转发

  - request.getRequestDispatcher("...").forward(request,response);
  - 一次请求响应的过程，对于客户端而言，内部经过了多少次转发，客户端是不知道的
  - 地址栏没有变化

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648629.png)

- 客户端重定向

  - response.sendRedirect("....");
  - 两次请求响应的过程。客户端肯定知道请求URL有变化
  - 地址栏有变化

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648630.png)

---

## **Thymeleaf - 视图模板技术**

- 内容

  - 添加thymeleaf的jar包

  - 新建一个Servlet类ViewBaseServlet

  - 在web.xml文件中添加配置（可在类前加上 @WebServlet("/index")从而不配置 xml 文件的匹配类）

    - 配置前缀 view-prefix
    - 配置后缀 view-suffix

  - 使得我们的 Servlet 继承 ViewBaseServlet

    - Servlet 基类，这个类将来使用框架后，这些代码都将被取代。

    ```java
    import org.thymeleaf.TemplateEngine;
    import org.thymeleaf.context.WebContext;
    import org.thymeleaf.templatemode.TemplateMode;
    import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
    import javax.servlet.ServletContext;
    import javax.servlet.ServletException;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.IOException;
    
    public class ViewBaseServlet extends HttpServlet {
        private TemplateEngine templateEngine;
    
    @Override
    public void init() throws ServletException {
    
        // 1.获取ServletContext对象
        ServletContext servletContext = this.getServletContext();
    
        // 2.创建Thymeleaf解析器对象
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
    
        // 3.给解析器对象设置参数
        // ①HTML是默认模式，明确设置是为了代码更容易理解
        templateResolver.setTemplateMode(TemplateMode.HTML);
    
        // ②设置前缀
        String viewPrefix = servletContext.getInitParameter("view-prefix");
    
        templateResolver.setPrefix(viewPrefix);
    
        // ③设置后缀
        String viewSuffix = servletContext.getInitParameter("view-suffix");
    
        templateResolver.setSuffix(viewSuffix);
    
        // ④设置缓存过期时间（毫秒）
        templateResolver.setCacheTTLMs(60000L);
    
        // ⑤设置是否缓存
        templateResolver.setCacheable(true);
    
        // ⑥设置服务器端编码方式
        templateResolver.setCharacterEncoding("utf-8");
    
        // 4.创建模板引擎对象
        templateEngine = new TemplateEngine();
    
        // 5.给模板引擎对象设置模板解析器
        templateEngine.setTemplateResolver(templateResolver);
    
    }
    
    protected void processTemplate(String templateName, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1.设置响应体内容类型和字符集
        resp.setContentType("text/html;charset=UTF-8");
    
        // 2.创建WebContext对象
        WebContext webContext = new WebContext(req, resp, getServletContext());
    
        // 3.处理模板数据
        templateEngine.process(templateName, webContext, resp.getWriter());
    }
    }
    ```

- 根据逻辑视图名称得到物理视图名称
  此处的视图名称是 index
  那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
  逻辑视图名称 ： index
  物理视图名称 ： view-prefix + 逻辑视图名称 + view-suffix
  所以真实的视图名称是： / index .html
  super.processTemplate("index",request,response);

![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648631.png)

- 使用thymeleaf的标签
  th:if , th:unless , th:each , th:text

![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648632.png)

---

## **保存作用域**

- 保存作用域

  - request：一次请求相应范围有效
  - session：一次会话范围有效
  - application：一次应用程序范围有效

  ```
  //1.向application保存作用域保存数据
  //ServletContext : Servlet上下文
  ServletContext application = request.getServletContext();
  application.setAttribute("uname","lili");
  ```

## **水果项目**

- 几个关键步骤的说明

  - 添加链接时要在内层的代码上加上 thymeleaf 代码，如果在外层添加则会用变量覆盖掉内层的所有东西

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648633.png)

  - thymeleaf 代码中 @{}表示的是基于根目录的绝对路径，推荐使用绝对路径，避免找不到页面

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648634.png)

  - 以下两种方式都可以在页面转换的时候获取到下个页面需要用到的信息——fid，推荐使用第二种，**图三**是图一的优化，用||括起来，里面的内容默认是字符串

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648635.png)

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648636.png)

  - th：object 可以指定后面的对象，让代码更加简洁。而 *{} 就相当于指定了{}里面的对象是 object

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648637.png)

  - 使用 type=“hidden” 作为隐藏域，不显示在页面上，但是可以获取到信息

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648638.png)

  - 资源跳转后，如果用第一行，相当于是第二行的服务器内部转发，只是重新访问了原来的 index.html 页面，因为里面的 session 还没有重新获取，展示的内容还是原来的。但是第三行是重新发送请求给 indexServlet 重新获取页面，内容也随之更新了

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648639.png)

  - 如果添加了额外的文件比如 js 而无法正常显示的，则需要检查这个文件夹中有没有部署到新的文件，若没有则删除重新运行项目

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648640.png)

  - 制作查询界面的时候，添加一个隐藏作用域用来区分 post 请求和 get 请求

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648641.png)

  - 之所以要每一次请求都要 keyword 是因为当查询后换页时也要根据 keyword 来呈现相应的内容

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648642.png)

---

## **MVC——Servlet优化**

- 减少 servlet 组件数量，用图二的方式来统一管理 servlet

  - 由于 service 方法可以判断不同的请求，所以可以直接写入 service 方法
  - 图一

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648643.png)

  - 图二

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648644.png)

  - 值的传送

    - post 请求要用 key - value 的形式传送

    ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648645.png)

    - get 请求则可以直接在 URL 传送值

    ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648646.png)

- 运用反射技术减少代码量

  - getDeclaredMethonds() 是获取的全部方法包括 private，由于是类内部调用，所以可以不用爆破

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648647.png)

- Dispatcher Servlet 中央控制器

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648648.png)

  - 通配符来拦截所有请求

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648649.png)

  - xml 文件的配置：在 src 文件夹下

    - 文件说明
      - HTML : 超文本标记语言
        XML : 可扩展的标记语言
        HTML是XML的一个子集
      - XML包含三个部分：
        1. XML声明 ， 而且声明这一行代码必须在XML文件的第一行
        2. DTD 文档类型定义
        3. XML正文
    - 文件配置：这个bean标签的作用是 将来servletpath中涉及的名字对应的是fruit，那么就要FruitController这个类来处理

    ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648650.png)

  - 从 url 获取到 fruit
    如：**[http://localhost:8080/fruit/fruit.do](http://localhost:8080/fruit/fruit.do)**
    获取到 ：/fruit.do 然后获取到 fruit

  ```
  String servletPath = req.getServletPath();
      servletPath = servletPath.substring(1);
      int lastDotIndex = servletPath.lastIndexOf(".do");
      servletPath = servletPath.substring(0,lastDotIndex);
  ```

  - 读取 xml 配置文件并放入 map 中，供 service() 方法反射调用

```
//            读取 xml文件里的 beans
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//            下图的第一个 Document类
            Document document = documentBuilder.parse(inputStream);

//            获取 bean 节点
            NodeList bean = document.getElementsByTagName("bean");
            for (int i = 0; i <bean.getLength(); i++){
                Node beanNode = bean.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE){
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Object beanObj = Class.forName(className).newInstance();
                                    beanMap.put(beanId,beanObj);
```

![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648651.png)

```
try {
    Method method = controllerBeanObj.getClass().getDeclaredMethod(operate, HttpServletRequest.class,
            HttpServletResponse.class);
        method.invoke(this, req,resp);
} catch (NoSuchMethodException e) {
    throw new RuntimeException(e);
} catch (InvocationTargetException e) {
    throw new RuntimeException(e);
} catch (IllegalAccessException e) {
    throw new RuntimeException(e);
}
```

- 提取视图资源通用代码

  - 转发和重定向每个都有，可以提取（相应的只用 req，也无需抛出异常）

    ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648652.png)

    - add：return "redirect:fruit.do";
    - del：return "redirect:fruit.do";
    - edit：return "edit";
    - update：return "redirect:fruit.do";

- 获取 参数 每个方法中都有，可以提取

  - 各个方法都变成了形如下图的简洁方法

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648653.png)

  - 获取到 operate 对应的方法

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648654.png)

  - 获取参数列表用来调用对应的方法

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648655.png)

- review

  - 最初的做法是： 一个请求对应一个 Servlet ，这样存在的问题是 servlet 太多了
  - 把一些列的请求都对应一个 Servlet , IndexServlet / AddServlet / EditServlet / DelServlet / UpdateServlet -> 合并成FruitServlet,通过一个 operate 的值来决定调用 FruitServlet 中的哪一个方法，使用的是**switch-case**
  - 在上一个版本中，Servlet 中充斥着大量的 switch-case ，试想一下，随着我们的项目的业务规模扩大，那么会有很多的 Servlet ，也就意味着会有很多的 switch-case ，这是一种代码冗余。因此，在 servlet 中使用了反射技术，我们规定 operate 的值和方法名一致，那么接收到 operate 的值是什么就表明我们需要调用对应的方法进行响应，如果找不到对应的方法，则抛异常
  - 在上一个版本中我们使用了反射技术，但是其实还是存在一定的问题：每一个servlet中都有类似的反射技术的代码。因此继续抽取，设计了中央控制器类：DispatcherServlet。 DispatcherServlet 这个类的工作分为两大部分：
    - 根据url定位到能够处理这个请求的controller组件
      - 从 url 中提取 servletPath : /fruit.do -> fruit
      - 根据fruit找到对应的组件:FruitController ， 这个对应的依据我们存储在applicationContext.xml中
        <bean id="fruit" class="com.atguigu.fruit.controllers.FruitController/>
        通过DOM技术我们去解析XML文件，在中央控制器中形成一个beanMap容器，用来存放所有的Controller组件
      - 根据获取到的operate的值定位到我们FruitController中需要调用的方法
    - 调用Controller组件中的方法
      1. 获取参数
         获取即将要调用的方法的参数签名信息: Parameter[] parameters = method.getParameters();
         通过parameter.getName()获取参数的名称；
         准备了Object[] parameterValues 这个数组用来存放对应参数的参数值
         另外，我们需要考虑参数的类型问题，需要做类型转化的工作。通过parameter.getType()获取参数的类型
      2. 执行方法
         Object returnObj = method.invoke(controllerBean , parameterValues);
      3. 视图处理
         String returnStr = (String)returnObj;
         if(returnStr.startWith("redirect:")){
         ....
         }else if.....

---

## **servlet - api**

- Servlet 初始化方法：init() , init(config)

  - init()

    ```
     public void init() throws ServletException{
     }
    ```

  - init（ServletConfig config）

    ```
    public void init(ServletConfig config) throws ServletException {
         this.config = config ;
         init();
       }
    ```

- 如果想要在 Servlet 初始化时做一些准备工作，那么可以重写init方法。可以通过如下步骤去获取初始化设置的数据

  - 获取config对象：ServletConfig config = getServletConfig();
  - 获取初始化参数值： config.getInitParameter(key);

- 注解的方式添加初始信息

  - 方式

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648656.png)

  - 类信息

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648657.png)

- 通过配置文件添加信息

  - 配置文件信息

  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
  
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
  
    <servlet>
        <servlet-name>Demo01</servlet-name>
        <servlet-class>demo</servlet-class>
        <init-param>
            <param-name>hello</param-name>
            <param-value>world</param-value>
        </init-param>
        <init-param>
            <param-name>uname</param-name>
            <param-value>Ice</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>Demo01</servlet-name>
        <url-pattern>/demo</url-pattern>
    </servlet-mapping>
  </web-app>
  ```

  - 代码读取

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648658.png)

  - ServletConfig 对应 <servlet-name></servlet-name>ServletContext 对应 <context-param></context-param>
  - Servlet中的ServletContext和<context-param>
  - 获取ServletContext
    - 在初始化方法中： ServletContxt servletContext = getServletContext()
    - 在服务方法中也可以通过request对象获取：request.getServletContext()
    - 也可以通过session获取：
      session.getServletContext()
  - 获取初始化值：servletContext.getInitParameter();

---

## **MVC 介绍**

- MVC : Model（模型）、View（视图）、Controller（控制器）

  - 视图层：用于做数据展示以及和用户交互的一个界面
  - 控制层：能够接受客户端的请求，具体的业务功能还是需要借助于模型组件来完成
  - 模型层：模型分为很多种：有比较简单的 pojo / vo(value object)，有业务模型组件，有数据访问层组件
    - pojo/vo : 值对象
    - DAO ： 数据访问对象
    - BO ： 业务对象

- 区分业务对象和数据访问对象

  - DAO中的方法都是单精度方法或者称之为细粒度方法。什么叫单精度？一个方法只考虑一个操作，比如添加，那就是insert操作、查询那就是select操作....
  - BO中的方法属于业务方法，也实际的业务是比较复杂的，因此业务方法的粒度是比较粗的
    - 注册这个功能属于业务功能，也就是说注册这个方法属于业务方法。那么这个业务方法中包含了多个DAO方法。也就是说注册这个业务功能需要通过多个DAO方法的组合调用，从而完成注册功能的开发。
    - 检查用户名是否已经被注册 - DAO中的select操作
    - 向用户表新增一条新用户记录 - DAO中的insert操作
    - 向用户积分表新增一条记录（新用户默认初始化积分100分） - DAO中的insert操作
    - 向系统消息表新增一条记录（某某某新用户注册了，需要根据通讯录信息向他的联系人推送消息） - DAO中的insert操作
    - 向系统日志表新增一条记录（某用户在某IP在某年某月某日某时某分某秒某毫秒注册） - DAO中的insert操作
    - ....
  - 在库存系统中添加业务层组件

- ioc 的介绍

  - 耦合/依赖：在软件系统中，层与层之间是存在依赖的。我们也称之为耦合。我们系统架构或者是设计的一个原则是： **高内聚低耦合**。层内部的组成应该是高度聚合的，而层与层之间的关系应该是低耦合的，最理想的情况0耦合（就是没有耦合）

  - IOC - 控制反转

    - 之前在Servlet中，我们创建 service 对象 ， FruitService fruitService = new FruitServiceImpl()，这句话如果出现在servlet中的某个方法内部，那么这个 fruitService 的作用域（生命周期）应该就是这个方法级别；
      如果这句话出现在servlet的类中，也就是说 fruitService 是一个成员变量，那么这个 fruitService 的作用域（生命周期）应该就是这个 servlet 实例级别
    - 之后我们在 applicationContext.xml 中定义了这个 fruitService 。然后通过解析 XML ，产生 fruitService 实例，存放在 beanMap 中，这个 beanMap 在一个 BeanFactory 中
      因此，我们转移（改变）了之前的 service 实例、 dao 实例等等他们的生命周期。控制权从程序员转移到 BeanFactory 。这个现象我们称之为控制反转

  - 依赖注入

    - 之前我们在控制层出现代码：FruitService fruitService = new FruitServiceImpl()；
      那么，控制层和service层存在耦合。

    - 之后，我们将代码修改成FruitService fruitService = null ;
      然后，在配置文件中配置:

        ```
      <bean id="fruit" class="FruitController">
      <property name="fruitService" ref="fruitService"/>
      </bean>
        ```

- ioc 的实现

  - 通常我们在类中使用其他类的对象时需要提前创建一个对象，这就增加了程序之间的耦合。

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648659.png)

  - 更好地方法是在此处置空，后读取 Xml 配置文件中的内容在 init() 方法中运用反射来给此处创建对象

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648660.png)

  - 具体实现

    ```
    <?xml version="1.0" encoding="utf-8"?>
    <beans>
        <bean id="fruitDAO" class="fruit.dao.Impl.FruitDAOImpl"/>
        <bean id="fruitService" class="fruit.service.impl.FruitServiceImpl">
            <property name="fruitDAO" ref="fruitDAO"/>
        </bean>
        <bean id="fruit" class="fruit.controllers.FruitController">
            <property name="fruitService" ref="fruitService"/>
        </bean>
    </beans>
    ```

  - property 标签用来表示属性：name 表示属性名：ref 表示引用其他 bean 的 id 值（此处需用双向标签）

- 第二个 bean 的意思：id 中的名字和下面的类对应，里面的 fruitDAO 需要对应 FruitDAO 类，表示 fruitService 类中需要给 fruitDAO 初始化

- Node 节点扩展：Node 节点 Element 元素节点（sname） Text 文本节点（jim） <sname>jim</sname>

---

## **filter-listener**

- Filter（过滤器）

  - Filter 也属于 Servlet 规范
  - Filter开发步骤：新建类实现 Filter 接口，然后实现其中的三个方法：init、doFilter、destroy
  - 配置 Filter，可以用注解@WebFilter，也可以使用 xml 文件 <filter> <filter-mapping>
  - Filter 在配置时，和 servlet 一样，也可以配置通配符，例如 @WebFilter("*.do")表示拦截所有以 .do 结尾的请求
  - 程序运行时，先运行放行前的代码，然后体跳入放行代码进行 Severlet 的代码的执行，完成后执行放行后的代码

  ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648661.png)

  - 过滤器链

    - 如果采取的是注解的方式进行配置，那么过滤器链的拦截顺序是按照全类名的先后顺序排序的
    - 如果采取的是 xml 的方式进行配置，那么按照配置的先后顺序进行排序

  - 设置编码过滤器

    - 在注解或者 xml 文件中写入

    <img src="https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648662.png" alt="Untitled"  />

    - 然后在初始化方法中读取（为什么要在初始化方法中？因为初始化方法只会调用一次，只需在第一次的时候设置好编码即可）

- 事务问题

  - 简要分析

    - 当前 Service 中包含了三个 DAO 操作，之前的 DAO 的事务管理的基本 API 是右边写的方式。
    - 这样的方式带来的问题是：DAO01 执行成功-提交，DAO02执行失败-回滚，DAO03执行功-提交

    ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648663.png)

    - 此时 service 操作是既有成功也有失败的，不能保证事务的正确性
    - 得出结论：事务官是不能以DAO的单精度方法为单位，而应该以业务会的方法为单

  - 解决

    - 在 filter 层统一开始事务，**业务层操作都包含在放行代码里面**

    ![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648664.png)

    - 难点：**如何向三个 DAO 组件中传递一个 conn 数据库连接对象？**

  - 实现

    - 使用 **ThreadLocal** 来获取当前线程的 conn 数据库连接对象

    - ThreadLocal代码分析- get() , set(obj)

      - ThreadLocal称之为本地线程 。 我们可以通过set方法在当前线程上存储数据、通过get方法在当前线程上获取数据
      - get方法源码分析：

      ```
      public T get() {
      Thread t = Thread.currentThread(); //获取当前的线程
      ThreadLocalMap map = getMap(t);    //获取和这个线程（企业）相关的ThreadLocalMap（也就是工作纽带的集合）
      if (map != null) {
      ThreadLocalMap.Entry e = map.getEntry(this);   //this指的是ThreadLocal对象，通过它才能知道是哪一个工作纽带
      if (e != null) {
      @SuppressWarnings("unchecked")
      T result = (T)e.value;     //entry.value就可以获取到工具箱了
      return result;
      }
      }
      return setInitialValue();
      }
      ```

      - set方法源码分析：

        ```
        public void set(T value) {
        Thread t = Thread.currentThread(); //获取当前的线程
        ThreadLocalMap map = getMap(t);    //每一个线程都维护各自的一个容器（ThreadLocalMap）
        if (map != null)
        map.set(this, value);          //这里的key对应的是ThreadLocal，因为我们的组件中需要传输（共享）的对象可能会有多个（不止Connection）
        else
        createMap(t, value);           //默认情况下map是没有初始化的，那么第一次往其中添加数据时，会去初始化
        }
        ```

        - 在 OpenSessionViewFilter 中处理事务，在放行代码前开启事务，在放行代码后提交或者回滚事务，事务交给放行代码的 Severlet 来执行
        - 注意：不能再 DAO 层try -- catch，因为里层捕获了异常外层就不会捕获了，可以自定义一个 DAOException 类，在 catch 中 抛出自定义的异常，若没有处理则会一直抛出，直到最外层的代码

- Listener（监听器）

  - ServletContextListener - 监听ServletContext对象的创建和销毁的过程
  - HttpSessionListener - 监听HttpSession对象的创建和销毁的过程
  - ServletRequestListener - 监听ServletRequest对象的创建和销毁的过程
  - ServletContextAttributeListener - 监听ServletContext的保存作用域的改动(add,remove,replace)
  - HttpSessionAttributeListener - 监听HttpSession的保存作用域的改动(add,remove,replace)
  - ServletRequestAttributeListener - 监听ServletRequest的保存作用域的改动(add,remove,replace)
  - HttpSessionBindingListener - 监听某个对象在Session域中的创建与移除HttpSessionActivationListener - 监听某个对象在Session域中的序列化和反序列化

---

## **总结**

![Untitled](https://cdn.jsdelivr.net/gh/KingRainIce/typora-pic@main/202302091648665.png)
