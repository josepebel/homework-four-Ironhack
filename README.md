# CRM - API with Microservices

> "A CRM system is a tool for businesses that allows them to manage relationships with customers and potential costumers."

![img](https://github.com/ES-IH-WDPT-JUN21/homework-4-Devs-and-Dragons-2/blob/master/resources/02.png)

For this project, we rebuilt a previous [CRM project](https://github.com/ES-IH-WDPT-JUN21/homework-3-Devs-Dragons
) from the ground up using microservices architecture and creating API routes for every current CLI command.

We decided to split the monolith app into the following microservices:

- [Eureka service](https://github.com/ES-IH-WDPT-JUN21/homework-4-Devs-and-Dragons-2/tree/master/eureka-service): discovery service that contains a registry of all our microservices. It runs on **port :8761**
- [Operative service](https://github.com/ES-IH-WDPT-JUN21/homework-4-Devs-and-Dragons-2/tree/master/operative-service): part of the API that allows the user to create new leads, convert them to opportunities and change their status. It runs on **port :8081**
- [Reporting service](https://github.com/ES-IH-WDPT-JUN21/homework-4-Devs-and-Dragons-2/tree/master/reporting-service): part of the API that allows the user to retrieve data from the database. It runs on **port : 8082**
- [Edge service](https://github.com/ES-IH-WDPT-JUN21/homework-4-Devs-and-Dragons-2/tree/master/edge-service): public part of our API, combines both proxy services, the operative and the reporting service. All endpoints have basic authentication. It runs on **port :8080**.

![img2](https://github.com/ES-IH-WDPT-JUN21/homework-4-Devs-and-Dragons-2/blob/master/resources/01.png)


## Project setup 

### Prerequisites
Don't forget to setup your own SQL server and seed the database with this [SQL script](https://github.com/ES-IH-WDPT-JUN21/homework-4-Devs-and-Dragons-2/tree/master/resources/database.sql). All our microservices will use the same database. 
Make sure you have the same values you'll find in the application.properties file of each microservice or properly change them according to your configuration.

To run this project locally do the following after cloning it.

1. Open each service folder separately.

2. Run ```mvn spring-boot:run ```

❗️ It is important that you start first the eureka service project so the other microservices work

## API documentation

You can check out our API documentation [here](https://psychotic-hat.surge.sh/).

![img3](https://github.com/ES-IH-WDPT-JUN21/homework-4-Devs-and-Dragons-2/blob/master/resources/03.png)

Keep in mind that the edge service uses HTTP basic authentication, so you'll need to pass a user and password with your requests. 
If you seeded the database with the provided sql file you can login into an admin, a sales representative and a director account with these credentials:

```
username: admin
password: 123456
```

```
username: DirectorTest
password: 123456
```

```
username: SalesTest
password: 123456
```

- Users with an ADMIN role have permission to create new users on ```http://localhost:8080/users```

``` 
{
	"name": "username",
	"sharedKey": "user_password",
	"role": "DIRECTOR/SALESREPRESENTATIVE" 
	
}
```

- Users with a DIRECTOR role have permission to create other users with a SALESREPRESENTATIVE role and access the report service endpoints.

- Users with a SALESREPRESENTATIVE role can create new leads, convert them to opportunities, change opportunities status and access to some report service endpoints.


### API Endpoints

All endpoints are available through our edge service on ```localhost:8080```.
A list with all endpoints is provided [here](https://github.com/ES-IH-WDPT-JUN21/homework-4-Devs-and-Dragons-2/blob/master/resources/endpoints.md).

## Diagrams
![diagram1](https://github.com/ES-IH-WDPT-JUN21/homework-4-Devs-and-Dragons-2/blob/master/resources/CRM-Use-Case-diagram.png)
![diagram2](https://github.com/ES-IH-WDPT-JUN21/homework-4-Devs-and-Dragons-2/blob/master/resources/E-R_diagram.jpg)

## Built with 

Spring, Eureka and MySQL


## Made by

- [Bruno Álvarez](https://github.com/brunoalvarezlopez)
- [Pilar María Carranza](https://github.com/pilicarranza)
- [Sergio Mateos](https://github.com/SergioMateosSanz)
- [José Antonio Peño](https://github.com/josepebel)
- [Gema Segarra](https://github.com/gemasegarra)

