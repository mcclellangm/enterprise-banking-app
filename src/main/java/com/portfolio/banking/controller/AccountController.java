package com.portfolio.banking.controller;

import ch.qos.logback.core.status.Status;
import com.portfolio.banking.model.Account;
import com.portfolio.banking.service.AccountService;
import jdk.jshell.Snippet;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
INTERVIEW QUESTION:

Q: What's the difference between @RestController and @GetMapping in Spring Boot?

A: @RestController is a class-level annotation that tells Spring the class handles
   HTTP requests and that its methods return response data directly, typically serialized as JSON.

   @GetMapping is a method-level annotation that maps HTTP GET requests for a specified
   URL path to a particular Java method.

   -----------------------------
   @RestController combines two annotations: @Controller and @ResponseBody
   - That means Spring automatically writes the method's return value into the HTTP
   response body rather than treating it as the name of a view.

   @RestController
   @RequestMapping("/api/accounts")
    public class AccountController {

    @GetMapping
    public String getAccounts() {
            return "Welcome to Enterprise Banking API";
        }
    }
    Here, @RequestMapping("/api/accounts") establishes the base path for the controller,
    and @GetMapping handles GET requests to that path.

    This is a common pattern in enterprise Spring Boot applications.

    Q: What is @PathVariable ?
    A: --> @PathVariable binds a value from the URL path to a Java method parameter.
    It doesn't technically replace {id}; Spring extracts the value occupying that position.

    Q: What's the difference between returning an Account object directly and returning ResponseEntity<Account>?
    A: When returning a ResponseEntity you are also able to control the status code, headers, and body. of the HTTP call, for example
    set a 404 for account not found.

    Q: Why do we not have to use Annotations over our Java model classes for them to be converted to JSON?
    A: Spring uses Jackson internally to convert POJOs to JSON objects

    **When a controller annotated with @RestController returns an object,
    Spring MVC uses an HTTP message converter backed by Jackson to serialize it into JSON.
    Jackson can discover properties through public getter methods, so JSON annotations on
    the model aren't required.

    Q: What's the difference between serialization and deserialization,
    and which one happens when your REST controller returns an Account object?
    A: Deserialization is turning the recieved HTTP Request into POJOs, while
    Serialization is converting POJO to JSON Objects when sending back the HTTP Response

    Q: What's the difference between @RequestBody and @PathVariable in Spring Boot?
    A: RequestBody refers to a variable found within the RequestBody, while a PathVariable
     will be found in the {} binding within the URL

     **Interview-ready explanation
- @RequestBody tells Spring to deserialize the HTTP request body (typically JSON) into a Java object.

        @PostMapping("/api/accounts")
        public Account createAccount(@RequestBody Account account) {
            // Process the account data
        }

- @PathVariable extracts a value from a placeholder in the URL path and binds it to a Java method parameter.

        @GetMapping("/api/accounts/{id}")
        public Account getAccount(@PathVariable Long id) {
            // Retrieve account by ID
        }


        Q: Why is it better to put the account lookup logic inside AccountService
         instead of directly inside AccountController?
        A: It follows a Layered Architecture Framework (or a three-tier-style application architecture)
        which separates each component layer and combines them with dependency injection. It also makes it
        easier to go and troubleshoot when doing this because you'll have a clearer idea of where the issue
         is whether its an HTTP error (Controller Layer) or returning wrong business logic (Service Layer),
         or returning a database issue (Repository Layer).

         **Your interview-ready answer
"Separating business logic into a service layer follows layered architecture and the separation-of-concerns
principle. Controllers handle HTTP communication, services handle business operations, and repositories handle
database access. This improves maintainability, testability, and code reuse. It also makes debugging easier
 because each layer has a clearly defined responsibility. Spring's dependency injection helps connect these
 layers without tightly coupling their implementations."


    Q: Which annotation would you use to convert that JSON body into a Java Account object?
    A: @RequestBody	Tells Spring to read the HTTP request body and deserialize its JSON into a Java object
    ex:
        @PostMapping("/api/accounts")
        public ResponseEntity<Account> createAccount(@RequestBody Account account) {
            // Account has already been deserialized here
        }

    Q:What HTTP status code should a REST API typically return when a new account is successfully created?
    A: When an API successfully creates a new resource, 201 Created is the standard response.

 */
@RestController
public class AccountController {

    //DEPENDECY INJECTION:
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Your challenge:
    // Create a GET endpoint at "/api/accounts"
    // Return "Welcome to Enterprise Banking API"
    @GetMapping("/api/accounts")
    public String getAccounts() {
        return "Welcome to Enterprise Banking API!";
    }

    @GetMapping("/api/accounts/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        Account account =  accountService.getAccountById(id);

        if (account!= null) {
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/accounts/")
    public ResponseEntity<Account> creatAccount(@RequestBody Account account){
        if(account!=null) {

           Account savedAccount = accountService.createAccount(account);

            if (savedAccount!=null) {

                return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
        //Because you're using @RequestBody, Spring normally rejects a missing request body with 400 Bad Request
        // before your method executes. So your null check is generally unnecessary, but it's fine for
        // understanding conditional response handling.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PutMapping("/api/accounts/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id,@RequestBody Account account) {
        if(account!=null) {

            Account updateAccount = accountService.updateAccount(id,account);

            if (updateAccount!=null) {

                return ResponseEntity.status(HttpStatus.OK).body(updateAccount);

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
        //Because you're using @RequestBody, Spring normally rejects a missing request body with 400 Bad Request
        // before your method executes. So your null check is generally unnecessary, but it's fine for
        // understanding conditional response handling.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }



//    @GetMapping("/api/accounts/{id}")
//    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
//
//        if (id.equals(1L)) {
//            Account account = new Account(1L, "John Doe", 1500.0);
//            return ResponseEntity.ok(account);
//        }
//        return ResponseEntity.notFound().build();
//    }


}
