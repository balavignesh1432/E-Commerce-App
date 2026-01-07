Use this when no need to create table every time app builds, it updates if already exists
spring.jpa.hibernate.ddl-auto=update
Not useful for case of H2, as it is in memory, need to be created every build.

Use lombok, to avoid getter setter constructor, just mention @Data Annotation and mention @AllArgsConstructor and @NoArgsConstructor, provides default and parameterized constructors. 

To auto generate values for columns, say id. Use @GeneratedValue Annotation and mention the strategy to generate.

Use this to defer running sql file only after database is created during build.
spring.jpa.defer-datasource-initialization=true

By Default controller does not allow requests from Different origin, (Client and Server at different ports)
So use, @CrossOrigin Annotation above the controller.

To convert date format, Use @JsonFormat annotation from jackson, then specify the shape which is string type, and mention the pattern.
Make the M capital
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-MM-yyyy");

To also return status code, return ResponseEntity with type of data returned at the controller level.
ResponseEntity<Product>
and then return new ResponseEntity(product, status=HttpStatus.OK)
When returning any type use ?

For storing image directly in DB, use array of byte and mention large object using @Lob Annotation

@RequestBody considers entire body, but to parse it part by part use @RequestPart Annotation, when sending as multipart formdata from client
When handling with image file use MultipartFile as data type

If needed more than just basic CRUD, declare custom functions in repo. 
JPQL - JPA Query Language, like SQL.
Use @Query Annotation, and write command there inside String.
Build the query using string concatenation, be careful of space when concatenation. Use :keyword to access parameter inside query.

@Query("SELECT p FROM Product p WHERE " +
"LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword,'%'))")


To get access to query parameter from the url, use @RequestParam annotation and match param name for the argument.

Spring Security by default gives login page, and generated password just by adding dependency.

At the core every controller is converted in Servlet which is run on Tomcat server
First before any controller, goes to front controller called the DispatchServlet, before that it goes to Filter Chain.
Filter Chain consists of multiple filters. Spring Security adds filters which enables the default login.
They can control request, as well as response. They are at the forefront, series of filter one after the other.

To get the session id, HttpServletRequest Annotation parameter in the controller. Then access the getSession() method of the parameter.
Then getId() method of it.

To change the default username and password, in properties.
spring.security.user.name=
spring.security.user.password=

Cross Site Request Forgery
Third Party getting access to Session ID and impersonating you.
Spring Security by default prevents it. 
To prevent CSRF, Server should generate CSRF Token, every time you send a request. You use that token, to send request.
Whenever POST, PUT, DELETE, we have to send CSRF token, otherwise we get 401 Unauthorized, even though we send username and password in Authorization section.
Send it in the Headers sections, Add the name, X-CSRF-TOKEN
To get hold csrf token, Use HttpServletRequest and getAttribute() Method, and mention "_csrf". This returns an object, type cast to CsrfToken type.

Alternate, if we generate new Session everytime, we don't need CSRF Token.

Whenever we want to customization, create a config class, and define a bean, which will inject the object.
To mark a class as configuration, use @Configuration Annotation.
Use @EnableWebSecurity, indicates that don't go with default security flow, follow the one mentioned Override.
To mention function as bean, use @Bean Annotation
build() method HttpSecurity Object returns a SecurityFilterChain Object. This has to be returned from the bean.

To disable csrf, use csrf method of HttpSecurity Object, and lambda function, takes customizer argument and call disable on it.
http.csrf(customizer -> customizer.disable);

To make every request to be authenticated, use authorizeHttpRequests() method of HttpSecurity Object.
http.authorizeHttpRequests(request -> request.anyRequest.authenticated());
To make http stateless, that is to generate new session every time, use SessionManagement method of HttpSecurity object.
http.sessionManagement(session -> session.sessionCreationPolicy(sessionCreationPolicy.STATELESS));

Username and Password
Authentication Object which is unauthenticated initially, Authentication provider authenticates it into authenticated Authentication Object.