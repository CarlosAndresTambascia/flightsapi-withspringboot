# Spring boot Application:
This is the second variant of the same project. You can see the first variant of this project [here](https://github.com/CarlosAndresTambascia/flightsapi) using Spring MVC instead of SpringBoot.

[![CircleCI](https://circleci.com/gh/CarlosAndresTambascia/flightsapi-withspringboot.svg?style=svg&circle-token=e75326b2701feca13cdd865e220d88c026b9b422)](https://circleci.com/gh/CarlosAndresTambascia/flightsapi-withspringboot)

### The project 

The idea of this application is to develop an api for flights.
Some requirements for this project were the followings:
1. Provide endpoints for the following requirements.
    - Return all the cities and a single city
    - Return the following information related to flights 
    - Get all the flights  on a specific date.
    - Get a certain flight (with the correct identifier)
    - Get a list of flights from a departure on a specific date.
    - Get a list of flights who land off on a destination on a specific date..
    - Get a list of flights for an airline on a specific date.
    - Get all the searches are public, no need for an authenticated user.
    - Add, update and remove a price for a route.
    - Add a flight
    - Get route prices.
2. Create a small user interface to query a list of flights from a departure on a specific date and show the results in a table.
3. Create a database on your favourite relational database : Preferred Postgre or SQL Server.
4. Develop the solution using Spring but you can’t use SpringBoot.
5. Use SpringMVC/JSP , to handle the user interface.
6. Deploy the solution into one of the following servers : Tomcat, Glassfish , JBoss.
7. Use Functional Programming as much as possible.
8. Develop Unit testing and Integration testing. 
9. Dockerize the API in two different containers : 
    - Service 
    -  Database
10. Use JPA / Hibernate or any ORM framework you consider suitable to this.
11. Add JWT authentication for the following endpoints.
    - Add, update and remove a price for a route.
    - Add a flight
    - Get route prices.


### How to run it locally. 
- Have maven and jdk and PostgreSql setup in you machine. 
- Set up the password for PostgreSql as `123456` you can change this from the `db.properties` file. 
- In order to make it easier to run it locally I added the `tomcat7-maven-plugin`. So to start the app locally is as simple as running the command `mvn tomcat7:run`. 
- There's a postman collection you can import to your insomia/postman.
