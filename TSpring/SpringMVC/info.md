/*
웹 프로그램이란, 인터넷 서비스를 이용해서 서로 다른 구성요소들(PC등)이 통신할 수 있는 프로그램
웹 서버 + 사용자(브라우저, 클라이언트) : Request, Response
프로토콜 : 통신 규약 , IP주소 : 서버에 요청 시 주소를 알아야 '숫자', 도메인 .com mapping DNS
PORT : 여러 프로그램 접속방법 (port, route경로는 종종 생략)
Container 동적 + HTML 정적
*/

###6
DI : 각 클래스 간의 의존관계를 bean 설정 정보 바탕으로
컨테이너 자동연결 : 객체 레퍼런스를 컨테이너로부터 주입받아 실행 동시에 동적 의존관계 생성 

코드 단순, 컴포넌트간 결합도 제거 , new객체(); 더이상없음
클래스 -> 구현클래스 x, 인터페이스를 사용하고
조립기 beans.xml에 의존관계 설정해서 어떤 구현클래스 쓸 것인지 


Spring DI 컨테이너 개념 :  관리 객체 bean, beanFactory
객체의 생성과 런타임 관계 DI관점에서 해당 컨테이너 beanFactory여기에 다
여기에 다른 컨테이너 기능 추가 : ApplicationContext 부름 
둘다 interface, BeanFactory(getBean(), bean 등록 생성 조회 반환)


설정 파일은 src > config 폴더 (프로젝트 ㄴㄴ) 생성후 그 안에
spring bean configuration file : aop x here yet "Beans", 최신 소스만 선택 
beans graph, namespace확인가능 : ref="printer"하자 beans GRaph에서 Hello클래스가 printer로 화살표 

자 이제 test를 해보자 , xml 하위 xml.test 패키지 생성
getBeans하는 방법은 getBeans(" ") 아이디만, 또는 getBeans(" " , XX.class) 아이디와 클래스 
앞의 경우는 아이디만 객체이므로 (클래스명) 으로 형변환 typecast필요
그런데 두 경우 생성된 것이 동일함 즉 Singleton, Not 또다른 생성 possible by Spring IOC

* * *
###7
jUnit : 
TDD Kent Beck + 디자인패턴 Erich Gamma

pom.xml dependdency junit추가 
Run as 도 as Junit Test
assert메서드로 예상,실제값 
@Test @Before @After 등 어노테이션 지원
@Test 각@Test메서드 호출시 새 인스턴스 독립적 실행 
@Before : @Test 실행전, 즉 공통사용 코드
@After : @Test후 실행 릴리즈 
@BeforeClass, @AfterClass 1회만 수행 

Assert메서드
assertEquals(a,b) 객체value, assertSame(a,b)객체ref
assertTrue(a)  asssertNotNull

Spring-test 
각각 테스트 별 객체 생성되어도 싱글톤 ApplicationContext 보장

@Autowired : DI Bean 'context' 자동매핑  - GenericXml 설정 불필요 @RunWith & @Autowired
 @RunWith(SpringJUnit4ClassRunner.class) 
 @ContextConfiguration(locations="classpath:config/beans.xml") //@before mapping here,
 public class HelloBeanJunitSpringTest {

* * *

###8
setter injection <property> 태그 :
setter 메서드 통해 의존관계 Bean 주입시 
ref : Bean 이름 이용 (참조객체) 주입, value : 단순 값 또는 Bean 아닌 객체 

constructor injection 
<constructor-arg>태그 생성자 파라미터, 여러 개 객체 한 번에 주입 가능 
index=" " or name="  " 둘다 가능 

컬렉션타입 주입 : config> new File > value.properties

1) List : <list> <value> , Set : <set> <value>
List<String> names; 
public void setNames(List<String> list) {this.name=list;}

<property name="names"> 
  <list> <value /> <value /> </list>
</property> 

2) Map : key, value
Map<String, Integer> ages;
public void setAges(Map<String, Integer> ages){this.ages = ages;}

<property name="ages>
    <map><entry key="Kim" value="30"/><entry key="Lee" value="26"/></map>
</property>

id값 getBeans( 여기 안 )
xmlBean과 달리 property:DB연결정보 는 개발/테스트/운영 따라 바뀔 수 있으니 
XML이 아닌 간단한 key=value 구성의 properties파일형식으로 저장

<context:property-placeholder   //${} 치환 위해 필요한 tag, namespaces : context beans에 이어 추가 
   location ="classpath:config/database.properties/> 여기선 value.properties
database.properties 안에 driverClass, url, username, pw
<bean id ="datasource" class="   jdbc" 
 < property name="url" value=${db.url}/>
 <property name="username" value=${db.username}/> 이렇게 프로퍼티 치환  

* * *
###9
 XML 단독 : 운영 -모든 bean 명시적 등록, 충돌 위험, setter/constructor필요
 XML, Bean scanning :개발 적합 - 특정 어노테이션 부여,당장은 xml 관리 수고덜어주나 의존관계 엉킬수 있음
 @Component <bean>태그와 동일함 아래 세 개는 Compoennt의 특정 케이스 용
 @Repository: persistence layer like 파일 데이터베이스
 @Service : 서비스 비즈니스 로직
 @Controller : presentation layer, webapp response, Request 

 @Autowired : Type으로 주입, property, setter , 생성자, 일반메서드 적용 가능 
 @Resource : Name으로 주입, (제한적) property, setter메서드에만 적용가능. 필요자원 자동연결 

 @Value("hh") 는 <property .. value="hh"/>와 동일 단순 
 @Qualifier : @Autowired와 함께 사용 (동일 타입의 Bean 객체 중 하나 ) 
 setter메서드 불필요 by @Value() or @Qualifier()

<context:component-scan base-package="myspring.di.annot"> 자동 bean 등록,
 @Autowired 의존관계 주입받는 어노테이션 선언시 xml설정 필요 해당 클래스가 있는 package
 <context:include-filter> <content:exclude-filter>

 원칙적인 POJO 에서 class인 /Hello/, /StringPrinter/, /ConsolePrinter/에 @Component("name")
 또한 StringPrinter에 의존하니 /Hello/에 @Autowired도 추가
 (di.xml 패키지 -> di.annot패키지 통째로 복사)

 Annot Test는 오류가 있네!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 Annot Test도 @RunWith, @ContextConfig( classpath:config/annot.xml)
  public class HelloAnnotTest(
      @Autowired
      ApplicationContext context;
      @Test 
      public void test()
  )

* * *
###10 
: 사용자관리 프로젝트
presentation 계층 : @Controller 
브라우저 상의 웹클라이언트 요청 응답 처리
상위계층(서비스, 데이터액세스계층) 발생 Exception 처리
최종UI에서 표현해야 할 도메인 모델 사용 
최종 UI입력 데이터 대한 유효성검증 Validation 기능 제공
비즈니스 로직, 최종 UI 분리위한 컨트롤러 기능 제공
//UserController 클래스 (UI-서비스 계층 연결)
JSP UserController통해 서비스계층 UserService사용, IoC-@Autowired

service 계층 : 인터페이스 + @Service
애플리케이션 비즈니스 로직 처리, 비즈니스 관련 도메인 모델 적합성 검증
트랜잭션 처리 
프레젠테이션 계층 - 데이터 액세스 계층 사이 연결, 두 계층이 직접 통신 않아
애플리케이션 유연성 증가
다른 계층들과 통신하기 위한 인터페이스를 제공 
//UserService 인터페이스
//UserServiceImpl 클래스 (업무로직)
UserDAO 사용 IoC-@Autowired

data access 계층  : DAO 인터페이스 + @Repository
rdbms 데이터 조작 데이터 액세스 로직 구체화
영구저장소 데이터 CRUD 조회 등록 수정 삭제 
ORM 프레임워크(MyBatis, Hibernate) 주로사용 
//UserDAO 인터페이스
//UserDAOImplJDBC 클래스 
(springJdBC-DataSource 주입)(MyBatis-SQlSession 주입)

3개 계층 공통 : 도메인 모델 '클래스' 계층x
RDBMS의 entity와 유사한 VO(Value Object) 또는 DTO(Data Transfer Object)
private선언 멤버변수 : getter setter 메서드 클래스 지칭

항상  public, private변수, 생성자 2개, public gettersetter
public class AAA {
    private String id;
    private String name;
    public AAA(){}
    public AAA(String id, String name){
        this.id = id;
        this.name = name;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}

***
###11
 DAO (Data Access Object) 패턴 
 비즈니스 로직, 데이터 액세스 로직 분리 원칙 
 Connection Pooling : 미리 정해진 개수만큼 DB 커넥션 Pool준비 
   하나씩 할당 후 돌려받아 pool에 넣는 기법/ Spring Bean으로 지정가능 

JDBC : 모든 자바 데이터액세스 기술 근간, 최신ORM보다 Low level, source바닥
Spring JDBC : 기존 JDBC 단점 극복, 작업 대신 SQL param Object만 지정하면 됨
(JSP강좌 참고) Connection 열고닫기, Statement(PreparedStatement) 열고닫기 해줌
Statement실행 (다양한 형태호출 가능), ReS(result set) loop 처리, 
RuntimeException이어서 try catch 안 써도 됨. / transaction 처리....
테스트환경 : SimpleDriverDataSource 매번 커넥션 새로만듦, pool x 
        : SingleConnectionDriverDataSource(쓰레드용은 x)

오픈소스 : Apache Commons DBCP 제일 평범 , c3p0 JDBC/DataSource Resource Poool

JdbcTemplate Class : insert,update,select,batch
JdbcTemplate template = new JdbcTemplate(dataSource);
DAO DI 의존관계 주입 멀티쓰레드에서도 가능 @Autowired void setDataSource(Datasource)

queryForObject() : 여러 개 칼럼 인데 단일 Row로 반환 시 
T : VO객체 타입에 해당 
RowMapper<T> :  단일 Row 매핑 가능 

pubilc User findUser(String id){
    return this.JdbcTemplate.queryForObject("select * from users where 
    id=?", new Object[] {id},
    new RowMapper<User>(){
        public User mapRow(ResultSet rs, int rowNum) throws 
        SQLException{
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    })
}

query() List<T> return값 

***
###13
OOP여도 해결 안되는 핵심/부가기능 분리 : AOP로 
Asepct 부가기능 애스펙트 정의
Advice 정의코드 + PointCut적용장소 = Asepct
런타임 시 동적 위치 실행 가능 
Target: 핵심기능 모듈, 부가기능 부여 대상
Advice : 타겟제공할 부가기능 모듈 : Singleton
JOin Point : 어드바이스 적용 위치,
 타겟객체가 구현한 인터페이스의 모든 메서드 
Pointcut : 어드바이스 적용 타겟 메서드 선별 정규표현식 
  (메서드 Signature 비교)
Advisor = Advice + Pointcut
Weaving : 포인트컷 의해 결정된 타겟의 조인포인트에 부가기능 삽입
Cross-cutting concern(보안, 인증, 로그) -Advice->Point-cutting
여기까지 Aspect 
이것을 Core에 적용하는 과정이 Weaving 

Spring은 Target 객체 프록시 만들어 제공, 어드바이스 타겟객체 적용 시 런타임
Proxy가 호출 가로챔(전처리 어드바이스) 핵심 로직메서드 호출 후 (후처리 어드바이스)
오직 런타임 시점에만 부가기능(어드바이스) 적용 //(vs AspectJ : 객체생성, 필드값 업뎅트 등 다양 시점)

XML <aop:config>  또는 @Aspect, <aop:aspectj-autoproxy/> 

around 어드바이스 : 전후, After Throwing 어드바이스 : 예외처리 후
trace(ProceedingJoinPoint joinPoint) 파라미터 필수선언 

***
###14
AspectJ 포인트컷 표현식 : execution() : 
execution([접근제한자 패턴] 리턴값타입패턴 [타입패턴.] 
메서드이름패턴(타입패턴|"..",...)[throws 예외패턴])

예시 : execution(* aspects.trace.demo.*.*(..))"
execution(*hello(..)) hello 이름 메서드 선정, 모든 파라미터exec
execution(*myspring.user.srvice.UserServiceImpl.*(..)) 
  -- UserServiceImpl 클래스 직접 지정, 모든 메서드 선택 
execution(*myspring.user.service*.*(..)) service패키지 내 모든 클래스
execution(*myspring.user.service..*.*(..)) service뒤에 .. 더 있어, 서브패키지 모든 클래스까지
execution(* *..Target.*(..)) Target이름 모든 클래스(패키지 무관)

XML POJO : AOP 구현 Advice 클래스 작성 <aop:config> 어드바이스,포인트컷
@Aspect 어노테이션 <aop:aspectj-autoproxy/> 메서드,포인트컷 : 클래스 내부

@Before("pointcut)(joinPoint 파라미터 참조가능), @After("pointcut) : 정상종료, 예외(리턴값 직접 전달x) 
@Around("pointcut") 
@AfterReturning(pointcut="", returning="") returning 리턴값 변수이름 
@AfterThrowing(pointcut="", throwing="") throwing 예외 저장 변수이름

UserService.getUser(..)시작
@Before[getUser]전처리
@Before[getUser]아규먼트
@Before[read] 전처리
@Before[read] 아규먼트
LoadingXML bean def
@After[getUser]메서드 실행완료
@AfterThrowing[getUser]메서드 예외발생
@AfterThrowing[getUser] PreparedStatement Callback
UserService.getUser(..)종료

***
 mybatis 건너뜀
***

###19 
MVC : Business, Presentation logic 분리 위해 : 영향없이 고침
model - 데이터, 비즈니스 로직 view-프레젠테이션 로직, controller- m,v 사이 상호작용고나리
client->C 요청
C ->M 호출, 비즈니스 로직,데이터 처리 : 결과 받음
C ->V 화면 생성 요청: 결과화면 
C -> client 응답 

Model 컴포넌트 : 데이터저장소와 연동, 입출력 데이터 다룸 : CRUD 트랜잭션 
DAO, Service 클래스 해당 

View 컴포넌트 : 모델 처리 데이터 또는 작업결과, 출력화면 만듦
출력은 Browser, HTML, CSS, JS 사용 출력UI만듦 : HTML&JSP 가능

Controller 컴포넌트 : 클라이언트 요청 업무수행 Model 컴포넌트 호출 
적절히 데이터 가공, 또한 모델의 수행결과 화면생성 뷰 전달 
Servlet, JSP로 가능 

[모델 2 아키텍처 순서]
MVC - Java (1. Controller by JSP, 2. Controller by Servlet)
Spring :Servlet 활용(tomcat : url 확인) 
1) Controller Servlet : Model 객체 호출 // 만약 데이터 저장 변경시 VO생성, 
    EJB(Enterprise JavaBeans) 또는 일반 자바객체 POJO Service or DAO object
2) Model 객체 SQL쿼리 to DBMS : 매개변수로 넘어온 VO객체를 DBMS 저장 또는 질의결과를 받아VO객체 생성 반환- JDBC사용 
3) Controller Servlet 이 (VO값 참조된) Model 결과값을 JSP View파일에 넒김 
4) JSP View역시 VO 참조해서 결과화면 만들어 Controller 서블릿 출력화면 제공
5) 서버로부터 받은 응답내용 화면에 출력  

[Front Controller 패턴 아키텍처] - Spring's DispatcherServlet
클라이언트 보낸 요청 받아 공통작업 먼저 수행 : 세부 Controller 작업위임
인증, 권한 체크 등 공통 로직 : 중앙집중적 관리 : front controller (Servlet)

Spring MVC 특징 :
DI, AOP 기능 외에도 서블릿 기반의 웹 개발 위한 MVC 프레임워크 제공 
모델2아키텍처 + Front Controller 패턴 제공 : Spring의 트랜잭션,AOP 사용

DispatcherServlet web.xml설정 (클라이언트의 모오오든 요청 전달받음)
1) incoming request to FrontController
2) Delegate request 위임 to Controller(개발자)
3) handle Request, Model객체 create
4) Controller to DispatcherServlet, Model (Delegate Rendering of Response 위임)
5) Render Response ; View Template 
6) Return from View Template to  FrontController 

SpringMVC구성요소 
1)DispatcherServlet : 클라이언트 요청 받아 Controller에 전달 , 
 리턴값 View에 전달해 알맞은 응답 생성 
2) HandlerMapping : URL,요청정보 기준 핸들러객체 사용 결정, 
   Dispatcher 하나 이상 사용가능, 이것 사용해 클라이언트 요청 처리할 Controller획득 
3) DispatcherServlet, Controller 객체 이용 클라이언트 요청 처리
   -- Controller : 클라이언트 요청 처리한 뒤, Model호출 결과 DispatcherServlet 알려줌
4) Controller 클라이언트 요청처리결과 & View페이지 정보 담은 ModelAndView 객체 반환 
    -- ModelAndView : Controller처리한 데이터 및 화면 정보 보유 객체 !!!
5) DispatcherServlet ViewResolver로부터 응답결과 생성할 View 객체 구함
    -- View : Controller 처리결과 화면 대한 정보보유 객체
    -- ViewResolver: Controller가 리턴한 뷰 이름 기반 Controller처리결과 생성
6) View는 클라이언트에게 전송할 응답 생성 

[Spring MVC 기반 웹 어플리케이션 작성 절차]
1) 클라이언트의 요청을 받는 DispatcherServlet을 web.xml설정 
   -- <servlet> <servlet-name><param-value> / <servlet-mapping> 
2) 클라이언트의 요청을 처리할 Controller를 작성  
   -- @Controller public class UserController{ @Autowired, @RequestMapping("/getUser.do" url)}
3) Spring Bean으로 Controller를 등록
   --bean.xml <context:component-scan base-package= " "> (Component 왜냐면 @Controller)
4) JSP를 이용한 View 영역의 코드를 작성
   -- ${user.userId}, ${user.city} 등 EL문법 VO객체 
5) Browser상에서 JSP를 실행
   -- 해당 url에서 실행하면 화면 나옴 

***
기타 환경설정 메모장
***
###21
EL : <%= request.getParameter("name")%> -> ${param.name}
    <% UserVO user=(UserVO)request.getAttribute("user");
       out.println(user.getName()); %>
    --> ${user.name} request는 생략 가능 , session은 명시 필요 
    name만 EL로 하면, value는 자동 넘겨짐
    <% UserVO user=(UserVO)session.getAttribute("user");
       out.println(user.getName()); %>
    --> ${sessionScope.user.name}

전제조건 : @Controller 했을 그 package conmponent-scan base package로 

JSTL : JSP Standard Tag Library 스크립팅 없이 루프,조건문,데이터관리포맷,XML조작 customizable
request,response,pageContext, application 같은 JSP내장객체 쉽게 접근 가능 

1)core 변수선언, 조건/제어/반복문 
2)formatting 숫자 날짜 시간 국제화 
3)function
4)database 5)xml   

DispatcherServlet *.do 로 설정해놓은 상태 
HelloController 
1 POJO형태 Controller클래스 작성
2 @Controller어노테이션 선언 
3 메서드 @RequestMapping(url)선언 
4 JSP View코드 
5 Browser JSP실행 
---- ui.Model (+addAttribute(name:String, value:Object) Model 
  Controller에서 Service호출한 결과 받아서 view에게 전달하기 위해 전달받은 결과 Model객체 저장
  Model (addAttribute(string Name, Object value) value객체 name이름 저장, view코드 :name지정이름 value사용 

[HelloController]
HelloController.java는 user.controller package하단, 
hello.jsp는 WebContent 아래
beans.xml <component scan 이미 myspring.user.로 되어있으므로 안 건드려도됨)
Browser상에 /hello.do 하면 

[사용자 목록조회 Controller] -ModelAndView 로 바뀜!(스크린샷)
1 POJO UserController클래스 작성
2 @Controller 어노테이션 선언 
3 사용자목록 조회 getUserList()메서드 선언 
  이 메서드에 @RequestMapping 어노테이션 선언 
4 userList.jsp JSP파일에 View코드 작성
5 Browser JSP실행 
 
1)/getUserList.do 포워딩
<%response.sendRedirect("getUserList.do");%>
|index.jsp| ->  @Controller myspring.user.UserController 
               	+ @AutoWired
		+ userService : UserService
	        + @RequestMapping(name="getUserList.do")
           	+ getUserList() :String 

2)@Autowired에 따라 userService 컨트롤러가 서비스 찾으면, getUserList() 메서드 호출
UserController.java : @Autowired UserService userService , 
                      @RequestMapping("/getUserList.do") String getUserList(Model model)
beans.xml       <context:component-scan base-package="myspring.user..../>

UserController -> <<interface>> UserService
                + insertUser(user:UserVO)
		+ getUserList() : List<UserVO>
		+ deleteUser(id:String)
		+ getUser(id:String) :UserVO


3)호출결과 데이터 UserController에 다시쏘면,Model 객체에 저장
UserController -> org.springframework.ui.Model
    		+ addAttribute(String, Object)


4)JSP파일에 포워딩 
userList.jsp   <c:forEach var="user" items="${userList}">
                    <tr><td>${user.userId}</td></tr>
UserController -> userList.jsp

5)JSP파일은 Model에 저장된 객체, 즉 데이터 읽음
userList.jsp -> org.springframework.ui.Model
   ${userList} ${user.userid} ${user.username} loop

***
###22
ViewResolver 설정 : internalResourceViewResolver
  Controller 실행결과 어떤 view에서 보여줄 것인지 결정하는 기능 제공, prefix(/:라우팅처럼) suffix(확장자)
웹 관련 설정이므로 beans-web.xml

[특정 사용자 조회 Controller]
1 getUser(String id)메서드 작성
  @RequestMapping, @RequestParam:HTTP요청 파라미터 참조

AAA.do?XXX=${} 와 (@RequestMapping("/AAA.do") @RequestParam <T> XXX)의 XXX 가 동일해야 함 

userList.jsp :  <c:forEach var="user" items=${userList}>
            	<tr><td><a href="getUser.do?id=${user.userId}>
 			${user.userId} </a>
UserController.java @RequestMapping("/getUser.do")
		    	public ModelAndView getUser(@RequestParam String id){
				UserVO user = userService.getUser(id);
				return new ModelAndView("userInfo", "user", user);

2 userList.jsp 페이지 수정


View에 데이터와 화면정보 전달하는 ModelAndView 클래스 : Service 호출결과 받아 View전달위해 이 객체저장

org.springframework.web.servlet.ModelAndView
+ModelAndView(viewName:String, modelName:String, modelObject:Object)
+ModelAndView(viewName:String)
+addObject(attrName:String, attrValue:Object)//View전달할 데이터 저장하는곳 

+getModel() : Map<String,Object>
+getView() : View
+setView(view:View)
+setViewName(viewName:String)

3 userInfo.jsp 페이지에 View 코드 작성
4 Browser JSP실행 

1)userList중 한 user클릭하면 getUser.do매핑
userList.jsp -> @Controller myspring.user.UserController
		+ @Autowired
  		+ userService : UserService
		+ @RequestMapping(name="getUserList.do")
		+ getUserList():String
		+ @RequestMapping(name="getUser.do")
		+ getUser(@RequestParam String id): ModelAndView

2)UserController, userService서비스의 getUser(id)메서드 호출
UserController -> <<interface>> UserService
		+ insertUser(user:UserVO)
		+ getUserList(): List<UserVO>
		+ deleteUser(id:String)
		+ getUser(id:String):UserVO //요거 호출
		+ updateUser(user:UserVO)

3)UserVO 객체에 담아 결과값 반환 to UserController 그럼 이를 뿌려줄 viewName 화면과 객체Object 저장
UserController 	@RequestMapping("/getUser.do")
		public ModelAndView getUser(@RequestParam String id){
			UserVO user = userService.getUser(id);
			return new ModelAndView("userInfo", "user",user);

UserController -> org.springframework.web.servlet.ModelAndView
		+ ModelAndView(viewName, modelName, modelObject)

4)JSP파일에 포워딩
${user.userId}, ${user.city} 등 
UserController -> userInfo.jsp

5)JSP파일은 ModelAndView 정보 읽음
userInfo.jsp -> org.springframework.web.servlet.ModelAndView

[사용자 정보 등록 화면 Controller]
1 insertUserForm() 메서드 작성, @RequestMapping 선언
2 userList.jsp 페이지수정 - 등록Form 링크
3 userInsert.jsp 페이지 View 코드 작성
4 Browser상 JSP 실행 

1)userList.jsp의 등록 링크 : UserController의 insertUserForm.do url의 메서드 호출
userList.jsp -> @Controller myspring.user.UserController
		+ @Autowired
		+ userService : UserService
		+ @RequestMapping(name="getUserList.do")
		+ getUserList() : String
		+ @RequestMapping(name="getUser.do")
		+ getUser(@RequestParam String id): ModelAndView
		+ @RequestMapping(name="insertUserForm.do") //여기로
		+ insertUserForm():ModelAndView

2) 등록화면과 Model정보 저장

UserController  @RequestMapping("/insertUserForm.do")
		public ModelAndView insertUserForm(){
		List<String> genderList = new ArrayList<String>();
		genderList.add("m");
		genderList.add("f");
		List<String> cityList = new ArrayList<String>();
		cityList.add("seoul");
		cityList.add("busan");
		cityList.add("daegu");
		Map<String, List<String>> map = new HashMap<>();
		map.put("genderList", genderList);
		map.put("cityList", cityList);
		return new ModelAndView("userInsert", "map", map)
		};
//여기서 viewName: userInsert, modelName: map, with attribute "genderList", "cityList"


UserController -> org.springframework.web.servlet.ModelAndView
		+ ModelAndView(viewName, modelName, modelObject)

3) JSP포워딩 //${model.attr}  !!!!!

userInsert.jsp	<td><c:forEach var="genderName" items="${map.genderList}">
			<input type="radio" name="gender" value=${genderName}">
		</c:forEach></td>
		<td><c:forEach var="cityName" items="${map.cityList}">...

UserController -> userInsert.jsp

4) Model정보 해당 JSP에서 읽음
userInsert.jsp -> org.springframework.web.servlet.ModelAndView


[사용자 정보 등록 Controller]
1 사용자 정보 등록 insertUser(@ModelAttribute UserVO userVO)메서드 작성
  @RequestMapping, @ModelAttribute 선언  

---@ModelAttribute 는 HTTP요청 포함 파라미터, 모델 객체로 바인딩 저장하게 함
   userInsert.jsp의 <input type="text" name="userId"/>,...
              <select name="city"><c:forEach var="cityName option value=${cityName}>
   UserVO.java 의 public class UserVO{ private String userId;(...) private String city;
   매핑할 때 한 번에 UserVO모델 객체로 바인딩해주는 것 
   
   UserController의 @RequestMapping("/insertUser.do") 
		  public String insertUser(@ModelAttribute UserVO user){
  //단 전제 각각의 jsp input name값과 VO 객체의 변수가 동일해야함

  redirect 목록조회페이지
2 userInsert.jsp 페이지 View 코드작성
3 Browser JSP 실행 

1) userInsert.jsp의 등록버튼 클릭,UserController insertUser메서드 호출 

userInsert.jsp form <form method="post" action="insertUser.do">
 			<input type="text" name="userId">...
 			<input type="submit" value="apply"/>
			</form>

userInsert.jsp -> @Controller myspring.user.UserController
		+ @Autowired
		+ userService:UserService
		+ @RequestMapping(name="getUserList.do")
		+ getUserList() : String
		+ @RequestMapping(name="getUser.do")
		+ getUser(@RequestParam String id): ModelAndView
		+ @RequestMapping(name="insertUserForm.do")
		+ insertUserForm(): ModelAndView
		+ @RequestMapping(name="insertUser.do")
		+ insertUser(@ModelAttribute UserVO userVO): String

2) UserController에서 내부적 insertUser(user) 서비스내 메서드 호출


UserController -> <<interface>> UserService
		+ insertUser(user: UserVO)
		+ getUserList(): List<UserVO>
		+ deleteUser(id:String)
		+ getUser(id:String) : UserVO
		+ updateUser(user:UserVO)

3)확인 위해 내부적으로 getUserList() 메서드 다시 호출

UserController  @RequestMapping("/insertUser.do")
		public String insertUser(@ModelAttribute UserVO user){
			if(user!=null)
				userService.insertUser(user);
			return "redirect:/getUserList.do";} //getUserList 목록조회다시

*****한글 입력값 request 데이터 인코딩 Filter클래스 설정
web.xml  <filter-class>org.springframework.web.filter.CharacterEncodingFilter
			<param-name>encoding <param-value>UTF-8  <filter-mapping> *do


4)getUserList()메서드 호출되니 UserController에서 다시 userList.jsp 포워딩 

UserController -> userList.jsp  

***
###23 
[사용자 정보 수정 Controller]
1 사용자 정보 수정 화면 포워딩 updateUserForm
  (@RequestParam String id)메서드 작성 
  @RequestMapping, @RequestParam 어노테이션 선언 
2 userList.jsp 페이지 수정
3 userUpdate.jsp 페이지 View 코드 작성
4 Browser JSP 실행

1) userList.jsp '수정' 버튼, updateUserForm 메서드 호출

userList.jsp 
<td><a href="updateUserForm.do?id=${user.userId}">수정</a></td>


userList.jsp -> @Controller myspring.user.UserController
		+ @Autowired
		+ userService : UserService
		+ @RequestMapping(name="getUserList.do")
		+ getUserList() : String
		+ @RequestMapping(name="getUser.do")
		+ getUser(@RequestParam String id): ModelAndView
	  	+ @RequestMapping(name="insertUserForm.do")
		+ insertUserForm(): ModelAndView
		+ @RequestMapping(name="insertUser.do")
		+ insertUser(@ModelAttribute UserVO userVO) : String 
		+ @RequestMapping(name="deleteUser.do")
		+ deleteUser(@RequestParam String id): String
		+ @RequestMapping(name="updateUserForm.do")
		+ updateUserForm(@RequestParam String id);
		

2) 데이터베이스에서 요청(param) id로 service에서 getUser(id)호출 

UserController.java 
@RequestMapping("/updateUserForm.do")
public ModelAndView updateUserForm(@RequestParam String id){
	UserVO user userService.getUser(id);
	List<String> genderList = new ArrayList<String>();
	genderList.add("남");..
	Map<String, Object> map = new HashMap<String, OBject>();
	map.put("genderList", genderList);...
	return new ModelAndView("userUpdate", "map", map);

@Controller -> <<interface>> UserService
		+ insertUser(user:UserVO)
		+ getUserList(): List<UserVO>
		+ deleteUser(id:String)
		+ getUser(id:String) : UserVO //요거 
		+ updateUser(user:UserVO)

3)Controller,그 UserVO return해서 userUpdate.jsp 포워딩

userUpdate.jsp
<td> <select name="city">
	<c:forEach items=${map.cityList} var='cityName'> 
		<select name="city> <c:choose>
		<c:when test='${cityName eq map.user.city}'>
			<option value=${cityName} selected>${cityName}</option>
		</c:when> <c:otherwise>
			<option value=${cityNam}>${cityName}</option>
		</c:otherwise></c:choose></c:ForEach></select>

@Controller -> userUpdate.jsp

[사용자 정보 수정 Controller]
1) userUpdate.jsp 수정정보 입력시 updateUser메서드 호출
<input type="hidden"name="userId" value='${map.user.userId}'/> 
<td>${map.user.userId}</td> 수정되면 안됨 but 활용해야

userUpdate.jsp -> @Controller myspring.user.UserController
		+ @Autowired
		+ userService:UserService
		+ @RequestMapping(name="getUserList.do")
		+ getUserList() : String
		+ @RequestMapping(name="getUser.do")
		+ getUser(@RequestParam String id) : ModelAndView
		+ @RequestMapping(name="insertUserForm.do")
		+ insertUserForm() : ModelAndView
		+ @RequestMapping(name="insertUser.do")
		+ insertUser(@ModelAttribute UserVO userVO) : String
		+ @RequestMapping(name="deleteUser.do")
		+ deleteUser(@RequestParam String id) : String
		+ @RequestMapping(name="updateUserForm.do")
		+ updateUserForm(@RequestParam String id)
		+ @RequestMapping(name="updateUser.do")
		+ updateUser(@ModleAttribute UserVO user)

2) Service 상의 updateUser(user)호출, DB 업데이트

@Controller -> <<interface>> UserService
		+ insertUser(user:UserVO)
		+ getUserList(): List<UserVO>
		+ deleteUser(id:String)
		+ getUser(id:String) : UserVO
		+ updateUser(user: UserVO)

3) *업데이트 처리 후 목록 조회 다시, getUserList 메서드 Redirect

UserController.java
@RequestMapping("/updateUser.do")
public String updateUser(@ModelAttribute UserVO user){
		userService.updateUser(user);
		return "redirect:/getUserList.do";

4) userList.jsp 포워딩
@Controller -> userList.jsp

[사용자 정보 삭제 Controller 구현]

1 사용자 정보 삭제 deleteUser(@PathVariable String id)메서드 작성, 
  @RequestMapping, @PathVariable 어노테이션 선언,
  삭제 후 목록조회 redirect
2 userList.jsp 페이지 수정
3 Browser JSP 실행

@PathVariable 어노테이션 : 파라미터URL 형식으로 받을 수 있게 해줌

<a href="deleteUser.do?id=${user.userId}"> & 
	@RequestMapping(value="/deleteUser.do")
<a href="deleteUser.do/${user.userId}"> & 
	@RequestMapping(value="/deleteUser.do/{id}")
	public String deleteUser(@PathVariable String id){  
RESTful API에서 활용함 

이걸 이해서 DispatcherServlet url-pattern바뀌어야 
<servlet-mapping> <url-pattern>  *do -> / 

1) userList.jsp '삭제'버튼 : Controller의 deleteUser메서드 호출 
2) deleteUser메서드 Service의 deleteUser호출
3) 삭제후 Controller getUserList() 목록조회 redirect
4) userList.jsp 포워딩


[Exception MVC 예외처리]
@ExceptionHandler : 예외Type,Message보여줄 JSP페이지

<%@ page isErrorPage = "true" %> 선언해야 함

***
config/ User.xml 확인!!! 18강



* * * 
###24

REST(Representational State Transfer)
HTTP URI + HTTP Method 
HTTP URI 제어 자원 resource 명시, Get,Post,Put,Delete HTTP Method통해 
해당자원 resource 제어 명령 내리는 방식 아키텍처 

POST : Create(insert)	/users
GET : Read(select)  	/users, /users/{id}
PUT : Update/Create	/users
DELETE : Delete 	/users/{id}

QueryString형식 GET/list.do?no=510&name=java 아닌 /java/510
Get,Post만이 아닌 GET/POST/DELETE/PUT CRUD처리

json : 경량의 data-교환 형식
JavaScriptObject Notation : { " " : " ",
                              " " : [" ", " "] } //array,list
JSON library : Jackson 
Browser(JSON) - Backend(JAVA Objects) 
XML : eXtensible Markup Language : Data 저장,전달 tag 자유정의 
jackson-mapper maven & beans-web.xml : <mvc:annotation-driven,
<mvc:default-servlet-handler> : tomcat>server.xml>url-pattern"/"과 충돌문제

[Spring MVC 기반 RESTful 웹서비스 구현 절차]
1 RESTful 웹서비스 처리 RestfulController클래스 작성 및 Spring Bean 등록 
2 요청 처리할 메서드 @RequestMapping, 
@RequestBody(XML,JSON->JAVA Obj) 
@ResponseBody(JAVA obj->JSON)어노테이션 선언 

without @ResponseBody(REST 전 강의)
즉 getByIdInHTML메서드 : ViewResolver의 /user.jsp 포워딩, UserModel객체참조

	@Controller
	@RequestMapping("/user")
	public class UserController{
		@RequestMapping(value="/html/{id}", method=RequestMethod.GET)
		public String getByIdInHTML(@PathVariable String id, ModelMap model){
			UserModel user = new UserModel();
			user.setId(id);
			user.setName("al");
			model.addAttribute("user", user);
			return "user";
 		}
	}
		

with @ResponseBody
즉 getByIdInJSON메서드 : MappingJacksonHttpMessageConverter,
리턴값 UserModel JAVA객체 -> JSON 변환

	@Controller
	@RequestMapping("/user")
	public class UserController{
		@RequestMapping(value="/json/{id}", method=RequestMethod.GET)
		@ResponseBody
		public UserModel getByIdInJSON(@PathVariable String id){
			UserModel user = new UserModel();
			user.setId(id);
			user.setName("al");
			return user;
		}

3 Postman(REST Client Tool) 메서드 각각 테스트 
4 Ajax 통신으로 RESTful 웹서비스 호출 HTML페이지 작성

insert는 select와 달리 json값이 들어옴 : Headers, insertUser 파라미터 @Body
	@RequestMapping(value="/users", 
			method=RequestMethod.POST (GET아님), 
                	headers={"Content-type=application/json"})
	@ResponseBody 
	public Map insertUser(@RequestBody UserVO user){
		그리고 if(user!= null) 해줘야 함
이것은 Postman에서도 Headers(Content-Type, application/json;charset=UTF-8)입력!! 
     Post// Body/raw JSON(application/json)  

수정(method="PUT") 그 외 Headers(Content-type, application/json;charset=UTF-9)
     Put//  Body/raw JSON(application/json) 

PUT,POST 둘다 result:TRUE만 나오고, 다시 userList GET할 때 업데이트현황 확인가능

삭제(method="DELETE", value="/users/{id}")
DELETE, users/{id} Postman

* * * 
###26
Ajax (Asynchronous Javascript and XML)
웹 2.0 실현 핵심기능 (HTML, CSS, JS, XML, XMLHttpRequest객체)
비동기적 : 서버 데이터가 로드되는 동안 계속 사용 가능 Ajax 이벤트 일부만 수정
- 예 1 : 라이브 검색(자동완성, 검색어 입력동시에 검색결과 호출)
- 예 2 : 사용자 정보 표시 (회원가입 시 입력 동시에 중복 메시지, 온라인 쇼핑몰 물품 추가시 전체 페이지 새로고침X)

XMLHttpRequest객체 : XHR 

1) XMLHttpRequest객체 생성: Request 보낼 준비
chrome,firefox,ie10 
var xhr = new XMLHttpRequest();

2) Callback함수 생성: 서버 Response 오면 실행
xhr.onreadystatechange = function(){
	if(xhr.readyState==4){
		var myDiv= document.getElementById('myDiv');
		myDiv.innerHTML=xhr.responseText;
		}
}

3) Request Open : HTTP method, 호출 Server url 정보 전달
xhr.open("GET", "user.do");

4) Request Send
xhr.send();

이것을 jQuery로 하면 크로스브라우징, 간단한 코드 사용 가능, 강력한 CSS셀렉터, PlugIn,
HTML element동적 manipulation, attribute 읽고쓰기, 선택(Selector)
(CDN : content delivery network host or 로컬 저장)
 jQuery 꼭 document ready해야 사용 가능
<script> $(document).ready(function(){});</script>
**********
26강 jQuery 예시 다시 
https://offbyone.tistory.com/368
*********

###27

[클라이언트 프로그램]
1)RESTful 웹서비스 호출 : Ajax 통신
2)Ajax 복잡코드 간결 작성 위해 jQuery의 $.ajax()함수 사용
3)서버로부터 받은 데이터 이용, 동적 테이블 Row 증가 
4)사용자 등록, 수정시 입력한 데이터 JSON포맷 변경, 서버 전송
5)화면 스타일 코드 Bootstrap 

//url:" ", type:' '

userList_json.html
<script type="text/javascript">
	$(function(){
		userList();
		userSelect();
		userDelete();
		userInsert();
		userUpdate();
		init();
  	}); jQuery document ready해야 사용 가능!
//초기화
	function init(){	
		$('#btnInit').on('click',function(){
			$('#form1').each(function(){
				this.reset();
			});
		});
	} // init함수

//목록조회	
	function userList(){
		$.ajax({
			url:'users',type:'GET',
			contentType:'application/json;charset=utf-8', dataType:'json', 
			error:function(xhr,status,msg){
				alert(status+msg);},
			success:userListResult
		});
	}//server단의 RFUController에서 url:value, type:method:GET, error/success표현 확인

//목록조회응답(위에서 success일 떄)
	function userListResult(xhr){
		console.log(xhr.data);
		$("body").empty();
		$.each(xhr.data, function(idx, item){
			$('<tr>')
				.append($('<td>').html(item.userId)			
				.append($('<td>').html(item.gender)..
				.append($('<td>').html('<button id=￦'btnSelect￦')조회(</button>'))
				.append($('<td>').html('<button id=￦'btnDelete￦')삭제(</button>'))
				.append($('<input type=￦'hidden￦' id=￦'hidden_userId￦'>').val(item.userId))
				.appendTo('tbody');
		}); //each 
	//server단 result.put("data",userList);라서 xhr.data 로, 그리고 비동기적 html <tr><td></td></tr> : html <tbody></tbody>에 넣음

//사용자(특정)조회 요청: 그 전에 우선 불러와야 함
	function userSelect(){
		$('body').on('click', '#btnSelect', function(){
			var userId= $(this).closest('tr').find('#hidden_userId').val(); //위에 id hidden_userId, val(item.userId)

		$.ajax({
			url:'users/'+userId, type:'GET', //특정사용자조회 users/{id}였으니까
			contentType:'application/json;charset='utf-8', dataType='json',
			error:function(xhr,status,msg){
				alert(status+msg);},
			success:userSelectResult
		});
	}//server단의 RFUController에서 url:value, type:method:GET, error/success 

//사용자 조회 응답(위에서 success일 때)
	function userSelectResult(xhr){
		var user= xhr.data;
		$('input:text[name="userId"]').val(user.userId);
		$('input:text[name="name"]').val(user.name);
		$('input:radio[name="gender"][value=""+user.gender+""]').prop('checked',true);
		//elem.checked VS $( elem ).prop( "checked" )
		$('select[name="city"]').val(user.city).attr("selected", "selected");

//사용자 등록 요청
	function userInsert(){
		$('#btnInsert').on('click', function(){
		var userId = $('input:text[name="userId"]).val();
		var name = $('input:text[name="name"]).val();
		var gender = $('input:radio[name="gender"]:checked').val(); //radio
		var city = $('select[name="city"]).val();
		$.ajax({ 
			url:"users", type:'POST', dataType:'json',
			data:JSON.stringify({userId:userId, name:name, gender:gender, city:city}), //UserVO 객체 to json
			contentType:'application/json',
			mimeType:'application/json', // Multipurpose Internet Mail Extensions,여러 확장자 
			success:function(response){
				if(response.result==true){
					userList();}}, // server controller에서 result.put("result", Boolean.TRUE); 
			error:function(xhr,status,message){alert(status+msg);}
			});
		}); //html form btnInsert id인 버튼 클릭
	}

//사용자 수정 요청
	function userUpdate(){
		$('#btnUpdate').on('click', function(){
		var userId = $('input:text[name="userId"]').val();
		var name = $('input:text[name="name"]').val();
		var gender = $('input:radio[name="gender"]:checked').val();
		var city = $('select[name="city"]').val();
		$.ajax({
			url:"users", type:'PUT', dataType:'json',
			data:JSON.stringify({userId:userId, name:name, gender:gender, city:city});//UserVO->json
			contentType:'application/json',
			mimeType:'application/json',
			success:function(data?result?){
				userList();},
			error:function(xhr,status,message){ alert(status+message);} 
		}); //ajax
	}); //html form btnUpdate id인 버튼 클릭 
}

//사용자 삭제 요청 **조심 form이 아닌 List상에서 삭제
	function userDelete(){
		$('body').on('click','#btnDelete',function(){ 
		var userId = $(this).closest('tr').find('#hidden_userId').val(); //마지막 리턴값 id <tr>의 <btnDelete> 
		var result = confirm(userId + '사용자 정말 삭제하시겠습니까?');
		if (result){		result=true, 즉 confirm값 OK이면 삭제 실행
			$.ajax({
				url:"users/"+userId, type:'DELETE', //  server Controller 상 users/{id}
				contentType:'application/jsonlcharset=utf-8', dataType:'json',
				error:function(xhr,status,msg){
					console.log(status+msg);},
				success:function(xhr){
					console.log(xhr.result);
					userList();
				}
		}); }//if	
	}); //userList btnDelete 버튼 클릭
}

* * * 
###28

JAXB(Java Architecture for XML Binding) 
jackson과 달리 Java SE에 포함된, Java Object->XML(직렬화, Marshalling) 및 
XML-> Java OBject(역직렬화, Unmarsalling)해주는 API
@XmlRootElement : 클래스 XML Root, @XmlElement : 변수 :XML element

[JAXB 사용 RESTful웹서비스 구현절차] 
1 Java 객체 XML 변환 @XMLRootElement와 @XMLElement 어노테이션 선언 UserVOXML클래스선언
2 RestfulController클래스 사용자목록 조회 getUserListXml()메서드 작성
  @RequestMapping, @ResponseBody 어노테이션 선언
3 Postman 메서드 테스트
4 jQuery기반 Ajax 통신 userList_Xml.html 작성

	@XmlRootElement(name="users")
	public class UserVOXML{
		private String status;
		private List<UserVO> userList;

	@XmlElement
	public void setStatus(String status) {this.status = status;}

	@XmlElement
	public void setUserList(List<UserVO> userList) {this.userList= userList;} 

이것이 만드는 것은 
<users>
	<status>success</status>
	<user>
		<city>jeju</city>
		<gender>m</gender>
		<name>dooly</name>
		<userId>dolly</userId>
	</user>
	<user>...</user>
</users>

그럼 RestfulUserController도
		@RequestMapping(value="/usersXml", method=RequestMethod.GET)
		@ResponseBody
		public UserVOXML getUserListXml(){
			List<UserVO> list = userService.getUserList();
			UserVOXML xml = new UserVOXML("success", list);
			return xml;

Postman : GET : /usersXml 
userList_Xml.html 도 Xml반영 url, contentType,dataType 

//Ajax요청
$(function(){
	$.ajax({
		type:'get', url:'usersXml',
		contentType:'application/xml;charset=utf-8', dataType:'xml',
		error:function(xhr,status,msg){alert(status+msg);},
		success:showResult
	});
});

//Ajax응답
	function showResult(xhr){
		console.log(xhr);
		if($(xhr).find("status").text()=='success'){ //status 태그먼저나옴
			$(xhr).find("user").each(function(idx, user){
				$('<tr>')
				.append($('<td>').html($(user).find("userId").text()))
				.append($('<td>').html($(user).find("name").text()))...
				.appendTo('tbody');
			}); //user태그 each loop	 }} //showResult

