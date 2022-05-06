# JAX-RS REST E-Commerce  API 
### JAX-RS REST API for an E-Commerce

## 📃 Documentation
📧[Postman Docs](https://documenter.getpostman.com/view/20656726/UyxdK9KH)

## 📦 Features
* HATEOAS
* Custom ExceptionMappers

## ⚙ Technologies used
* JAX-RS (Jersey)
* JSON-B
* JAX-B
* Maven
* Tomcat
* Postman

 ## 🛠 Run with Maven
    **Maven**
* Change the configuration of Tomcat in `pom.xml`. 
* Deploy the application using the following maven command:
 ```
mvn clean compile tomcat7:redeploy
```
* REST: change the URL in the Postman collection environment variables to match the port you chose for your Tomcat deployment

**🐬MySQL**
* Create a database schema and provide the username and password in the persistence.xml
* Hibernate will automatically create the tables for you
* Add some data by your self

   
