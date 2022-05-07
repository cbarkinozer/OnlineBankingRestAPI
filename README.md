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
FindAll methods get active items, but findById methods get both active and passive items.  
The bank's interest rate and allocation fee,kkdv and bsmv tax rates are given as constant,
it can be implemented dynamicaly by pulling data from another API.    
Interest rate can be zero because some countries (there are 11 countries with sharia laws) prefer it that way.  
For accounts, iban no is generated as random numbers(simulated), although stored as string. This generation rules can be changed easily.  
