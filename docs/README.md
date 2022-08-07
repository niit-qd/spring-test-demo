# Spring Framework Test 笔记

---

## maven 部分

1. 其它辅助
   ```xml
   <dependencies>
       <dependency>
           <groupId>org.projectlombok</groupId>
           <artifactId>lombok</artifactId>
           <version>1.18.24</version>
           <scope>compile</scope>
       </dependency>

       <dependency>
           <groupId>org.apache.logging.log4j</groupId>
           <artifactId>log4j-core</artifactId>
           <version>2.18.0</version>
       </dependency>
       <dependency>
           <groupId>org.apache.logging.log4j</groupId>
           <artifactId>log4j-jcl</artifactId>
           <version>2.18.0</version>
       </dependency>
       <dependency>
           <groupId>org.apache.logging.log4j</groupId>
           <artifactId>log4j-slf4j-impl</artifactId>
           <version>2.18.0</version>
       </dependency>
   </dependencies>
   ```
2. Spring Framework
   ``` xml
   <dependencyManagement>
       <dependencies>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-framework-bom</artifactId>
               <version>5.2.22.RELEASE</version>
               <type>pom</type>
               <scope>import</scope>
           </dependency>
       </dependencies>
   </dependencyManagement>
   ```
   ```xml
   <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
        </dependency>
   </dependencies>
   ```
2. JUnit
   ```xml
   <dependencies>
       <dependency>
           <groupId>junit</groupId>
           <artifactId>junit</artifactId>
           <version>4.13.2</version>
           <scope>test</scope>
       </dependency>
   </dependencies>
   ```
3.

---

## 注解部分

1. @RunWith
   测试运行器。
   [Spring JUnit 4 Runner](https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/testing.html#testcontext-junit4-runner)
   > The Spring TestContext Framework offers full integration with JUnit 4 through a custom runner (supported on JUnit
   4.12 or higher). By annotating test classes with @RunWith(SpringJUnit4ClassRunner.class) or the shorter @RunWith(
   SpringRunner.class) variant, developers can implement standard JUnit 4-based unit and integration tests and
   simultaneously reap the benefits of the TestContext framework, such as support for loading application contexts,
   dependency injection of test instances, transactional test method execution, and so on. If you want to use the Spring
   TestContext Framework with an alternative runner (such as JUnit 4’s Parameterized runner) or third-party runners (
   such as the MockitoJUnitRunner), you can, optionally, use Spring’s support for JUnit rules instead.

   > Spring TestContext 框架通过自定义运行程序（在 JUnit 4.12 或更高版本上受支持）提供与 JUnit 4 的完全集成。 通过使用
   @RunWith(SpringJUnit4ClassRunner.class) 或更短的 @RunWith(SpringRunner.class) 变体注释测试类，开发人员可以实现标准的基于
   JUnit 4 的单元和集成测试，同时获得 TestContext 框架的好处，例如支持 加载应用程序上下文、测试实例的依赖注入、事务性测试方法执行等。
   如果您想将 Spring TestContext 框架与替代运行程序（例如 JUnit 4 的 Parameterized 运行程序）或第三方运行程序（例如
   MockitoJUnitRunner）一起使用，您可以选择使用 Spring 对 JUnit 规则的支持。

   基于`@RunWith(SpringJUnit4ClassRunner.class)`或者其短变形`@RunWith(SpringRunner.class)`
   的注解，可以实现标准的基于`JUnit 4`的单元和集成测试。
   ```java
   @RunWith(SpringRunner.class)
   @TestExecutionListeners({})
   public class SimpleTest {
   
       @Test
       public void testMethod() {
           // test logic...
       }
   }
   ```
2. @ContextConfiguration
   [@ContextConfiguration](https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/testing.html#spring-testing-annotation-contextconfiguration)
   > @ContextConfiguration defines class-level metadata that is used to determine how to load and configure an
   ApplicationContext for integration tests. Specifically, @ContextConfiguration declares the application context
   resource locations or the component classes used to load the context.

   > @ContextConfiguration 定义了类级元数据，用于确定如何为集成测试加载和配置 ApplicationContext。
   具体来说，@ContextConfiguration 声明了应用程序上下文资源位置或用于加载上下文的组件类。

   用于只当上下文资源文件的路径位置。
   ```java
   @ContextConfiguration
   @WebAppConfiguration("classpath:test-web-resources")
   class WebAppTests {
       // class body...
   }
   ```
   指定上下文资源文件后，可以使用`@Autowired`等注解来配置变量。
3. @ContextHierarchy
   [@ContextHierarchy](https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/testing.html#spring-testing-annotation-contexthierarchy)
   > @ContextHierarchy is a class-level annotation that is used to define a hierarchy of ApplicationContext instances
   for integration tests. @ContextHierarchy should be declared with a list of one or more @ContextConfiguration
   instances, each of which defines a level in the context hierarchy.

   > @ContextHierarchy 是一个类级别的注解，用于为集成测试定义 ApplicationContext 实例的层次结构。 @ContextHierarchy
   应该用一个或多个 @ContextConfiguration 实例的列表来声明，每个实例定义上下文层次结构中的一个级别。

   用于指定0或多个层次的上下文资源文件。与`@ContextConfiguration`配合。
   ```java
   @ContextHierarchy({
       @ContextConfiguration("/parent-config.xml"),
       @ContextConfiguration("/child-config.xml")
   })
   class ContextHierarchyTests {
       // class body...
   }
   ```
   注意：和在`@ContextConfiguration`中配置多个上下文资源文件进行区分。`@ContextHierarchy`强调的是层次关系。

   [Context Hierarchies](https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/testing.html#testcontext-ctx-management-ctx-hierarchies)

   [Annotation Type ContextHierarchy](https://docs.spring.io/spring-framework/docs/5.3.22/javadoc-api/org/springframework/test/context/ContextHierarchy.html)

   1. 属性`fvalue`
      是一个数组，元素类型是`ContextConfiguration`。
      > A list of @ContextConfiguration instances, each of which defines a level in the context hierarchy.
      > `@ContextConfiguration` 实例列表，每个实例定义上下文层次结构中的一个级别。

   2. Single Test Class with Context Hierarchy | 具有上下文层次结构的单个测试类
       > ControllerIntegrationTests represents a typical integration testing scenario for a Spring MVC web application
      by declaring a context hierarchy consisting of two levels, one for the root WebApplicationContext (with
      TestAppConfig) and one for the dispatcher servlet WebApplicationContext (with WebConfig). The
      WebApplicationContext that is autowired into the test instance is the one for the child context (i.e., the lowest
      context in the hierarchy).

       > ControllerIntegrationTests 通过声明一个包含两个级别的上下文层次结构来表示 Spring MVC Web 应用程序的典型集成测试场景，一个用于根
      WebApplicationContext（使用 TestAppConfig），一个用于调度程序 servlet WebApplicationContext（使用 WebConfig）。
      自动装配到测试实例中的 WebApplicationContext 是子上下文（即层次结构中最低的上下文）的上下文。

      这里举了一个SpringMVC中的例子，其中，`TestAppConfig`用于表示根上下文`WebApplicationContext`，`WebConfig`
      用于表示`dispatcher servlet`的上下文`WebApplicationContext`。
      注意，这里的2个上下文都是相同类型。
      下面的示例，使用了`classes`属性代替文件路径属性`ContextConfiguration`。
      ```java
      @RunWith(SpringRunner.class)
      @WebAppConfiguration
      @ContextHierarchy({
      @ContextConfiguration(classes = TestAppConfig.class),
      @ContextConfiguration(classes = WebConfig.class)
      })
      public class ControllerIntegrationTests {
       
           @Autowired
           private WebApplicationContext wac;
       
           // ...
      }
      ```
   3. Class Hierarchy with Implicit Parent Context | 具有隐式父上下文的类层次结构
       > The following classes demonstrate the use of named hierarchy levels in order to merge the configuration for
      specific levels in a context hierarchy. BaseTests defines two levels in the hierarchy, parent and child.
      ExtendedTests extends BaseTests and instructs the Spring TestContext Framework to merge the context configuration
      for the child hierarchy level, simply by ensuring that the names declared via ContextConfiguration.name() are
      both "child". The result is that three application contexts will be loaded: one for "/app-config.xml", one for "
      /user-config.xml", and one for {"/user-config.xml", "/order-config.xml"}. As with the previous example, the
      application context loaded from "/app-config.xml" will be set as the parent context for the contexts loaded
      from "/user-config.xml" and {"/user-config.xml", "/order-config.xml"}.

       > 以下类演示了命名层次结构级别的使用，以便合并上下文层次结构中特定级别的配置。 BaseTests 在层次结构中定义了两个级别，父级和子级。
      ExtendedTests 扩展 BaseTests 并指示 Spring TestContext 框架合并子层次结构级别的上下文配置，只需确保通过
      ContextConfiguration.name() 声明的名称都是“子”。 结果是将加载三个应用程序上下文：一个用于“/app-config.xml”，一个用于“/user-config.xml”，一个用于
      {"/user-config.xml", "/order- 配置.xml"}。
      与前面的示例一样，从“/app-config.xml”加载的应用程序上下文将被设置为从“/user-config.xml”和{“/user-config.xml”加载的上下文的父上下文，
      “/order-config.xml”}。

      这里说的是，除了通过上下文数组的索引号来确定上下文层次关系之外，还可以通过类的继承关系来实现上下文的层次关系。
      下面的示例，子类`SoapWebServiceTests`和`RestWebServiceTests`的上下文`"/spring/soap-ws-config.xml"`
      和`"/spring/rest-ws-config.xml"`，其父上下文是其父类的`"file:src/main/webapp/WEB-INF/applicationContext.xml"`。
      ```java
      @RunWith(SpringRunner.class)
      @WebAppConfiguration
      @ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
      public abstract class AbstractWebTests {}
       
      @ContextHierarchy(@ContextConfiguration("/spring/soap-ws-config.xml"))
      public class SoapWebServiceTests extends AbstractWebTests {}
       
      @ContextHierarchy(@ContextConfiguration("/spring/rest-ws-config.xml"))
      public class RestWebServiceTests extends AbstractWebTests {}
      ```
   4. Class Hierarchy with Merged Context Hierarchy Configuration | 具有合并上下文层次结构配置的类层次结构
       > The following classes demonstrate the use of named hierarchy levels in order to merge the configuration for
      specific levels in a context hierarchy. BaseTests defines two levels in the hierarchy, parent and child.
      ExtendedTests extends BaseTests and instructs the Spring TestContext Framework to merge the context configuration
      for the child hierarchy level, simply by ensuring that the names declared via ContextConfiguration.name() are
      both "child". The result is that three application contexts will be loaded: one for "/app-config.xml", one for "
      /user-config.xml", and one for {"/user-config.xml", "/order-config.xml"}. As with the previous example, the
      application context loaded from "/app-config.xml" will be set as the parent context for the contexts loaded
      from "/user-config.xml" and {"/user-config.xml", "/order-config.xml"}.

       > 以下类演示了命名层次结构级别的使用，以便合并上下文层次结构中特定级别的配置。 BaseTests 在层次结构中定义了两个级别，父级和子级。
      ExtendedTests 扩展 BaseTests 并指示 Spring TestContext 框架合并子层次结构级别的上下文配置，只需确保通过
      ContextConfiguration.name() 声明的名称都是“子”。 结果是将加载三个应用程序上下文：一个用于“/app-config.xml”，一个用于“/user-config.xml”，一个用于
      {"/user-config.xml", "/order- 配置.xml"}。
      与前面的示例一样，从“/app-config.xml”加载的应用程序上下文将被设置为从“/user-config.xml”和{“/user-config.xml”加载的上下文的父上下文，
      “/order-config.xml”}。

      ```java
      @RunWith(SpringRunner.class)
      @ContextHierarchy({
          @ContextConfiguration(name = "parent", locations = "/app-config.xml"),
          @ContextConfiguration(name = "child",  locations = "/user-config.xml")
      })
      public class BaseTests {}
       
      @ContextHierarchy(
          @ContextConfiguration(name = "child",  locations = "/order-config.xml")
      )
      public class ExtendedTests extends BaseTests {}
      ```
      示例：如果`employee0`、`employee1`、`employee2`分别定义在`app-config.xml`、`/user-config.xml`、`/order-config.xml`3个上下文中，则通过继承关系，`employee1`和`employee2`都可以注入到上下文`/order-config.xml`中。

   5. Class Hierarchy with Overridden Context Hierarchy Configuration | 具有覆盖的上下文层次结构配置的类层次结构
      > In contrast to the previous example, this example demonstrates how to override the configuration for a given named level in a context hierarchy by setting the ContextConfiguration.inheritLocations() flag to false. Consequently, the application context for ExtendedTests will be loaded only from "/test-user-config.xml" and will have its parent set to the context loaded from "/app-config.xml".

      > 与前面的示例相比，此示例演示了如何通过将 ContextConfiguration.inheritLocations() 标志设置为 false 来覆盖上下文层次结构中给定命名级别的配置。 因此，ExtendedTests 的应用程序上下文将仅从“/test-user-config.xml”加载，并将其父级设置为从“/app-config.xml”加载的上下文。

      与合并上下文对吧，覆盖上下文会覆盖同名上下文中的定义。
      ```java
      @RunWith(SpringRunner.class)
      @ContextHierarchy({
          @ContextConfiguration(name = "parent", locations = "/app-config.xml"),
          @ContextConfiguration(name = "child",  locations = "/user-config.xml")
      })
      public class BaseTests {}
      
      @ContextHierarchy(
          @ContextConfiguration(name = "child",  locations = "/test-user-config.xml", inheritLocations = false)
      )
      public class ExtendedTests extends BaseTests {}
      ```
      示例：如果`employee0`、`employee1`、`employee2`分别定义在`app-config.xml`、`/user-config.xml`、`/order-config.xml`3个上下文中，其中，`/user-config.xml`、`/order-config.xml`的`name`属性相同，则可以设置属性`inheritLocations = false`来禁用`/user-config.xml`中的所有注入。则通过继承关系，`employee2`可以注入到上下文`/order-config.xml`中，但是`employee1`不会。依赖注入时，会对`employee1`提示不满足注入条件，无法注入。

   注：在编写单元测试用例的时候，如果希望将父类和子类放在一个文件中，注意：
   - 将子类设置为public类，因为子类负责测试。
   - 将注解`@RunWith(SpringRunner.class)`放到父类上。


5. d