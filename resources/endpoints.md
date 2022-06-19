| Service | Method | Endpoint              | Response / description                                    | User role            | 
| ------ | ------ | --------------------- | -------------------------------------------------------- |  --------------------- |
| Edge    | POST         | ```/users  ```             | data of the new created user | ADMIN
| Operative    | GET         | ```/new-lead  ```             | data of the new created lead | SALESREPRESENTATIVE
| Operative    | PATCH  | ```/convert/{id}```  | converts lead into an opportunity | SALESREPRESENTATIVE
| Operative   |  GET        | ```/show-leads ```                              | retrieved data with all leads | SALESREPRESENTATIVE
| Operative   | GET  | ```lead/{id}```                        | retrieved data from a specific lead | SALESREPRESENTATIVE
| Operative    | GET  | ```/show-opportunities/{id}```  | retrieved data from a specific user | SALESREPRESENTATIVE
| Operative   | PUT    |  ```/close-lost-opportunity/{id}```           | changes opportunity status to close-lost | SALESREPRESENTATIVE
| Operative   | PUT    |  ```/close-won-opportunity/{id}```             | changes opportunity status to close-won | SALESREPRESENTATIVE
| Operative   | GET    |  ```show-sales```             | retrieved data with all sales | DIRECTOR 
| Operative  | POST    |  ```/new-sales```             | data of the new created sales | DIRECTOR
|    |     |                                 |
|    |     |              |
| Reporting   | GET    | ```/report/closed-won-by-city```           | number of opportunities with closed-won status by city| DIRECTOR
| Reporting   | GET | ```/report/closed-lost-by-city```          | number of opportunities with closed-lost status by city | DIRECTOR
| Reporting   | GET   |  ```/report/open-by-city```              | number of opportunities with open status by city | DIRECTOR 
| Reporting   | GET    |  ```/report/closed-won-by-sales```              | number of opportunities with closed-won status by sales | DIRECTOR
| Reporting   | GET    | ```/report/closed-lost-by-sales```             | number of opportunities with closed-lost status by sales | DIRECTOR
| Reporting   | GET    | ```/report/open-by-sales```             | number of opportunities with open status by sales | DIRECTOR
| Reporting   | GET | ```/report/closed-won-by-product```      | number of opportunities with closed-won status by product type   | DIRECTOR
| Reporting   | GET | ```/report/closed-lost-by-product```     | number of opportunities with closed-lost status by product type       | DIRECTOR
| Reporting   | GET |```/report/open-by-producct```      | number of opportunities with open status by product type         | DIRECTOR
| Reporting   | GET |```/report/closed-won-by-country```      | number of opportunities with closed-won status by country          | DIRECTOR
| Reporting   | GET |```/report/closed-lost-by-country```      | number of opportunities with closed-lost status by country            | DIRECTOR
| Reporting   | GET | ```/report/open-by-country```     | number of opportunities with open status by country         | DIRECTOR
| Reporting   | GET    | ```/report/closed-won-by-industry```              | number of opportunities with closed-won status by industry | DIRECTOR
| Reporting   | GET     |  ```/report/closed-lost-by-industry```              | number of opportunities with closed-lost status by industry | DIRECTOR
| Reporting   | GET   |  ```/report/open-by-industry```              | number of opportunities with open status by industry | DIRECTOR
| Reporting   | GET |```/report/opportunity-by-country```   | number of opportunities by country          | DIRECTOR
| Reporting   | GET | ```/report/opportunity-by-product```    | number of opportunities by  product type            | DIRECTOR
| Reporting   | GET   | ```/report/opportunity-by-industry```            | number of opportunities by industry | DIRECTOR
| Reporting   | GET    | ```/report/opportunity-by-sales```            | number of opportunities by sales | DIRECTOR 
| Reporting   | GET    | ```/report/leads-by-sales```          | number of leads by sales | DIRECTOR 
| Reporting   | GET    | ```/report/median-opps-account```            | median of opportunities per account | DIRECTOR 
| Reporting   | GET    |  ```report/max-opps-account```             | max of opportunities per account | DIRECTOR
| Reporting   | GET    |  ```report/mean-opps-account```             | mean of opportunities per account | DIRECTOR 
| Reporting   | GET   |  ```report/min-opps-account```             | min of opportunities per account  | DIRECTOR 
| Reporting   | GET    |  ```/employee-count/mean```             | mean employee count | DIRECTOR 
| Reporting   | GET    | ```/employee-count/median```             | median employee count | DIRECTOR 
| Reporting   | GET   | ```/employee-count/max```            | max employee count | DIRECTOR 
| Reporting   | GET    | ```/employee-count/min```             | min employee count | DIRECTOR 
| Reporting   | GET   | ```opportunity-quantity/mean```            | mean quantity in opportunities created by a sales representative | SALESREPRESENTATIVE
| Reporting   | GET    | ```opportunity-quantity/median```            | median quantity in opportunities created by a sales representative | SALESREPRESENTATIVE
| Reporting   | GET    | ```opportunity-quantity/max```          | max quantity in opportunities created by a sales representative | SALESREPRESENTATIVE
| Reporting   | GET    | ```opportunity-quantity/min```            | min quantity in opportunities created by a sales representative | SALESREPRESENTATIVE
