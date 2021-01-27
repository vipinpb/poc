## Java GraphQL Federation Server POC With Spring Boot

This works based on three Objects

User
Order
MoneyTransfer

Basically user can order a money transfer.  There are two different microservices

1. Which allows order of money transfer
2. Second allows to check the status of the moneytransfer

Note:  Make sure you have setup the java-home
To test this application

1.  Go to send-order folder and run

gradlew bootRun

2.  Go to track-transfer folder and run

gradlew bootRun

3.  You need to have node install on the machine

go to apollo-federation-js

npm install
node gateway.js

4. Now to test this working

Use the Graph QL client 

http://localhost:4000/graphql

or GraphIQL
http://localhost:4000/graphiql

you should see 3 services

1. me
2. order
3. tracktransfer






