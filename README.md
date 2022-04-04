# OnlineBankingRestAPI  
A Rest API developed with Spring Boot that allows users to perform banking transactions over the internet.

### Technologies  
Java 11, Spring Boot(monolith rest api), Maven(build automation tool), Lombok(eliminates boilerplate code), 
Hibernate(ORM/code-first approach), Swagger(rest api UI and documentation), PostgreSql (DB),
Mockitoo(unit testing/mocking layers), Mockaroo (fake data),  
JWT (jwt json token), Spring Security(Autherization),
MapStruct(dto-entity mapper), Spring HATEOAS(hateoas),Spring JPA(transactions), Docker(containerization),
Kafka(message broker), log4j(logging).  

### Architecture   
https://ibb.co/NmHVL3b    

### Design of the packages, entities, and controllers  
https://ibb.co/2t5Cq3q  
https://ibb.co/LzWWCpz  

### Design Notes
I do not check identity No (citizen id) by algorithm because I want this API to be international.   
There is no update for AccAccount because Accounts do not get updated.   
AccAccount has the method cancel instead of the method delete because Accounts should never be deleted.  