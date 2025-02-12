# Bank Application

## Description

This project simulates a simple bank account using Java, focusing on functionalities such as transferring and withdrawing money from an account. It supports payments using either debit or credit cards, with a 1% extra charge for transactions made with a credit card. The project also includes API endpoints to interact with the account, ensuring secure access through oauth token based authentication and authorization.

## Bank Application YAML

Within the resources/yaml folder, you'll find the AccountServiceAPI.yaml file, which details all the endpoints provided by the Bank Application.

![image](https://github.com/user-attachments/assets/a956a34f-eeca-4f62-9549-6bb6b42f5fb6)



## Requirements

- Eclipe/Intellij
- Java17
- Maven
- Git
- Docker (Optional)

## Steps to Run the Application

1. **Clone the repository:**
   ```bash
   git clone https://github.com/SakshiMahajan899/bank-application.git
   cd bank-application

2. **Build the project:**
   mvn clean install

3. **SSL Certificate**
To secure the Application endpoints, keystore.p12 is placed in the resources folder of Bank Application and also configuration provided in the application yaml file which helps to Enable HTTPS .

4. **Run the application:**
   mvn spring-boot:run

5. **Access the application:**
   - https://localhost:8080

## Using H2 Database 
 
  This project uses an H2 in-memory database for development and testing purposes. 

  ### Accessing H2 Database Console

   - Go to https://localhost:8080/h2-console.
   - JDBC Url is jdbc:h2:mem:recipedb
   - Provide username and password.
   - Click on "Connect" to access the H2 database console.

## Security - Authentication and Authorization  
   
   - This application uses the authorization server created by using Spring Authorization Server framework. So i create my own OAuth2 Authorization Server application for bank application (repo link mentioned below)
        https://github.com/SakshiMahajan899/oauth-authorization-server.git
   - To access the secure endpoints of Bank Application,  need to provide jwt token.
   - We can generate jwt by using oath-authorization-server applicatio   and send along with request details.
   - Bank Application will act as resource server and owner .
   - Authorization server consulted by Resource servers to authorize requests.
   - Two Clients are onboarded in the **Oauth-authorization-server Application** one is **User** and Other is **Admin** .
RBAC Implementation
   - For **Client-id** - user ,**scopes** defined are **"user.read"** and **"user.write"**.
   - If token is generated using **scope** **"user.read"**, then user is only **authorize** for calling **getTansaction** API.
   - If token is generated using **scope** **"user.write"**  then user is **authorize** for **withdraw** and **transfer** operations.
   - For **Client-id** - admin, **scope** defined is **"admin.audit"** so admin is authorize for **auditing** of transaction of type                 WITHDRAW/TRANSFER
   - Below diagram shows how authentication and authorization works in Bank application
     ![Untitled ss drawio](https://github.com/user-attachments/assets/6d00646a-b50f-4535-ac3b-db87ab1cc783)



## Design Pattern

- Singleton Pattern: for Used the Database.
- Factory Pattern: Used for creating different card types (Debit or Credit) based on user input.
- Observer Pattern: Used for auditing transactions (i.e., when a transaction is made, the audit log is updated).

## Connection Polling
Connection pooling is used to manage database connections . Instead of opening and closing a new database connection every time an application needs to interact with the database (which can be resource-intensive), connection pooling allows the application to reuse existing database connections from a pool. 


 

## API Endpoints
All Request is https enabled and Authorization token is provided in the Header parameters.

###  Available balance in all accounts

- **URL:** `https://localhost:8443/api/accounts/balance`
- **Method:** `POST`
- **Description:** Retrieve balance available in all accounts. 
- **Response:**
  ```json
  {
    "12345": 5000.00,
    "67890": 5000.00
}
![image](https://github.com/user-attachments/assets/3d292d6e-aae9-4662-9368-34fba6f85685)


### Withdraw money

- **URL:** `https://localhost:8443/api/accounts/withdraw`
- **Method:** `POST`
- **Description:** Withdraw money from the account.
- **Request Body:**
  ```json
  {
    "fromAccountNumber": 2,
    "amount": 50,
    "cardType": "DEBIT"
}
- **Response:**
  ```json
  {
    "message": "Withdraw Done",
    "status": "OK",
    "code": 200
}
![image](https://github.com/user-attachments/assets/bbefcf34-998e-43e5-a56a-a7407c6f72d6)



###  Transfer money

- **URL:** `https://localhost:8443/api/accounts/transfer`
- **Method:** `POST`
- **Description:** Transfer Money from one account to another.
- **Request Body:**
  ```json
  {
    "fromAccountNumber": 67890,
    "amount": 500,
    "cardType": "CREDIT",
    "toAccountNumber": 12345
}
- **Response:**
  ```json
 {
    "message": "Transfer Done",
    "status": "OK",
    "code": 200
}
![image](https://github.com/user-attachments/assets/cb8ab3f6-a87e-47eb-8577-a9a4287554ce)



### audit transfers or withdrawals

- **URL:** `https://localhost:8443/api/accounts/audit/logs`
- **Method:** `GET`
- **Description:** Retrieve all transaction logs of type TRANSFER.
- **Query Parameter:** `transactionType=TRANSFER`
- **Response:**
  ```json
 [
    "Timestamp: 1738021921735 |Transferred 500 from e2217d3e4e120c6a3372a1890f03e232b35ad659d71f7a62501a4ee204a3e66d to 5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5",
    "Timestamp: 1738021928672 |Transferred 500 from d4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90da3a666eec13ab35 to 5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5",
    "Timestamp: 1738021942151 |Transferred 37 from 5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5 to d4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90da3a666eec13ab35"
  ]

![image](https://github.com/user-attachments/assets/0d049d9f-1a69-47c3-84d8-214b6190c724)


### Audit for withdrawals

- **URL:** `https://localhost:8443/api/accounts/audit/logs`
- **Method:** `GET`
- **Description:** Retrieve all transaction logs of type WITHDRAW.
- **Query Parameter:** `transactionType=WITHDRAW`
- **Response:**
  ```json
 [
    "Timestamp: 1738021921735 |Withdrawn 500 from e2217d3e4e120c6a3372a1890f03e232b35ad659d71f7a62501a4ee204a3e66d",
    "Timestamp: 1738021928672 |Withdrawn 500 from d4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90da3a666eec13ab35 ",
    "Timestamp: 1738021942151 |Withdrawn 37 from 5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5 "
  ]




## Test Coverage

   ### Unit Tests (UT) & Integration Tests (IT)

1. **Scope**
   - Focuses on individual functions, methods, or classes.
   - Focuses on the interactions between units and their dependencies.

3. **Coverage**
   - Aims to cover all possible code paths, including edge cases.
   - Aims to cover all critical integration points and workflows.

4. **Framework**
   - Mockito
   - Spring

Test Coverage with UT and IT is 87% for Recipe Application.
![image](https://github.com/user-attachments/assets/4a31f36a-6e00-4554-b85e-debb08c9f685)

## Continuous Integration and Deployment

Within the bank-application/.github/workflows/ directory, you'll find the ci-cd.yml file, which is used to build the code & deploy the image to dockerHub.
![image](https://github.com/user-attachments/assets/c179917b-9f5e-4194-958e-a189d408a9c0)



  - Pipeline link -  https://github.com/SakshiMahajan899/bank-application/actions/runs/12998897915/job/36253023235
  - Also refer the below screenprint for the tag creation of the Bank Application in DockerHub
    ![image](https://github.com/user-attachments/assets/71c49f78-fb65-41ae-b0fa-9101eea2ab3c)


