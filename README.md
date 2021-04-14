# property-management

### Assumptions
This is a **property management** prototype, here we have the following roles in the system.
* user
* admin 
* landloard
 
Where **admin** and **landlord** can **create, update, search and approve** 
property an, on the other hand, the **normal user** can use the **search functionality** to find the **approved property**.

If someone accesses the APIs without a valid **api-key** then they will get the **401 (UNAUTHORIZED)** exception.

If a **normal user** tries to access **create, update or approve** APIs then he will get the **403 (FORBIDDEN)** exception.

### Limitations
For a small prototype, we have used **OncePerRequestFilter** to manage the **authentication and role-based access**, instead of the **Spring Security**.

### Start App
Here is the quickest way to start the app:
1. set appropriate database credentials in the application-local.yml file
2. Go to the project root directory and open the terminal
3. Execute the following command

       mvnw spring-boot:run
      
And we are done, now you can open the swagger to access the APIs: 
[Swagger](http://localhost:8081/swagger-ui.html#/)

You can use the following **api-key** to test the **APIs**
* **User:- hdgjshbe123456nnddnd**
* **Admin:- jijne82njddj29nd9nsn**
* **Land-lord:- kjkxskxje44j3djnadke**

<p align="center">
  <b>Thank You :)</b>
</p>
