# OnlineBankingRestAPI  
A Rest API developed with Spring Boot that allows users to perform banking transactions over the internet.
Time spent on the project : 160+ hours  
Line of code count : 9000+ lines  
Prototype project's line of code count and documentation time is not included.  

### Technologies  
Java 11   
Spring Boot(rest api)   
Maven(build automation tool)  
Lombok(eliminates boilerplate code)    
Hibernate(ORM/code-first approach)    
MapStruct(dto-entity mapper)    
Swagger(rest api UI and documentation)   
PostgreSql (relational database)   
H2 (relational in app database used for testing)   
Mockitoo (unit testing/mocking layers)      
JWT (jwt json token)    
Spring Security(autherization)     
Spring HATEOAS(hateoas)   
Spring JPA(transactions)   
Kafka(message broker)   
Docker(containerization)    
Mockaroo (fake data api)  

### Architecture   
https://ibb.co/NmHVL3b    

### Design of the packages, entities, and controllers    
https://ibb.co/yYDMvD2   
https://ibb.co/phwhSBX  

### To Run  
Pull the project.
Create a database named "online-banking-rest-api" in postgresql.  
Open src>main>resources>"application.properties" file.
Change following:   
```
spring.jpa.hibernate.ddl-auto=create  
spring.datasource.username=[your postgresql username here(probably postgres)]  
spring.datasource.password=[your postgresql password here]  
```
**From here, you can run the application if you will not use kafka messaging feature**
For Kafka, I used a docker container.   
Create a "docker-compose.yml" file and add the following code in it.  
```yml
version: "3"
services:
  zookeeper:
    image: wurstmeister/zookeeper
    ports:
      - "2181:2181"
  kafka:
    image: wurstmeister/kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 101
      KAFKA_ADVERTISED_HOST_NAME: localhost
      KAFKA_ADVERTISED_PORT: 9092
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    depends_on:
      - zookeeper
```
Open cmd and go to that file with "cd path".  
Open docker desktop(or docker daemon) on the background.  
Run command "docker compose up -d".  
Now, a container that has kafka in it is running.  
Run the project.
Open your browser and enter following URL : http://localhost:8080/swagger-ui/index.html

### Documentation    

Detailed documentation link:  https://docdro.id/YaQaBgq

I do not check identity No (citizen id) by algorithm because I want this API to be international.   
There is no update for AccAccount because Accounts do not get updated.   
AccAccount has the method cancel instead of the method delete because Accounts should never be deleted.    
FindAll methods get active items, but findById methods get both active and passive items.  

The bank's interest rate and allocation fee,tax rates (by default 20% because kkdv + bsmv) are given as constant,
it can be implemented dynamicaly by pulling data from another API.    

Interest rate can be zero because some countries (there are 11 countries with sharia laws) prefer it that way.  

For accounts, iban no is generated as random numbers(simulated), although stored as string. This generation rules can be changed easily.
Cvv no is created random but should be created using: primary account number, four-digit expiration date, a pair of DES (Data Encryption Standard) keys and a three-digit service code.  
Credit card no is unique and created random but should be crated according to the Luhn algorithm.  
The probability of recurrence of the credit card no is not checked because very low(1e+16).  

Cut off day of the month is the date where your billing cycle happens, and you select this day when creating your credit card.  

The installment count limit is 30 years because housing loans has the maximum installment count in the domain with 30 years.   

In the sql package there are sql files that has test data in them for main tables created using Mockaroo.   
ACC_ACCOUNT and CRD_CREDIT_CARD table's queries need more work :  
use regular expression datatype to generate random data in custom format. Also use number instead of money for BigDecimals.

There is a simple producer consumer system that runs with Kafka.  
This system imitates the banking apps sending sms to users.  
You can send message to the consumer from the controller.  