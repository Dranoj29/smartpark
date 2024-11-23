# Smartpark 

"SmartPark" is an intelligent parking management system for
urban areas, aiming to optimize the use of parking spaces and facilitate easy
navigation for drivers.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Build and Run](#build-and-run)
- [Testing](#testing)
- [Reports](#reports)
- [Note](#note)

## Prerequisites
  - Java 17
  - Gradle 8.11
  - Spring Boot 3.3.5

## Getting Started

  ### Clone the repository:
  ```bash
  git clone https://github.com/Dranoj29/smartpark.git
  cd smartpark
  ```

  ### Build the project:
  ```bash
  ./gradlew build
  ```

  ## Build and Run

  To run the application locally:
  ```bash
  ./gradlew bootRun
  ```

  - TO vissualize API using swagger visit visit http://localhost:8080/swagger-ui.html 
  - The service will start on the default port `8080`.
  - To change the port, update the `server.port` property in the `application.properties` file.

  ## Testing

  To run tests, use the following command:
  ```bash
  ./gradlew test
  ```

  ## Reports

  To vissualize and verify test case, use the following command:
  ```bash
  ./gradlew test jacocoTestReport
  ```
  The report will be generated as an HTML template 
  ```bash
  smartpark/ 
  └── build/ 
    └── reports/ 
      └── tests/       
        └── test/
          └── index.html
  ```
  open index.html on any browser

  ## Note
  - This API uses generated ID as an identifier for each data item to process transactions.
  ## Example
  - Before registering a vehicle, retrieve the available vehicle types:
  ```bash
   GET /v1/vehicles/types
  ```
  - Exsample respone:
   ```bash
    [
    {
      "id": 1,
      "name": "Car"
    },
    {
      "id": 2,
      "name": "Motorcycle"
    }
  ]
   ```
  - To register a vehicle, include the ID of the selected vehicle type in the payload:
  ```bash
    POST /v1/vehicles/register
   ```
   - Request Payload
   ```bash
    POST /v1/vehicles/register
    {
      "licensePlate": "ABC100",
      "typeId": 1,
      "ownerName": "Jonnard Baysa"
   }
   ```











          
