# iqviaCodeTest

### Setup MQ:
1. Install [ActiveMQ](https://activemq.apache.org/components/classic/) v5.16.3.
2. Extract the files in the local system.
3. Replace the apache-activemq-5.16.3\conf\activemq.xml file with the one that is in the root folder of the repository.
4. Navigate to apache-activemq-5.16.3\bin\ directory.
5. Hold down shift key and click the right mouse button on the window.
6. In the menu, find and select Powershell/Command Promt.
7. Enter the following command to start the message broker.
    - ./activemq start


### Start the Spring Boot application:
Build the application using a IDE is one way (or) Try using the packed jar file.
1. In the local clone of the git repository, open up a terminal window.
2. Enter the following command to start up the Spring Boot application.
    - java -jar iqviaCodeTest-0.0.1-SNAPSHOT.jar
3. Once the application is up, one can access the [Swagger UI](http://localhost:4569/swagger-ui/) for API testing.
4. Following are sample valid and invalid requests.
    - If the current time is Tue Oct 12 17:15:40 IST 2021
        - Valid request \
        {"dateTime": "Tue Oct 12 17:16:10 IST 2021", "message": "string"}
        - Invalid request (Time is in past) \
        {"dateTime": "Tue Oct 11 17:16:10 IST 2021", "message": "string"}
        - Also Invalid request (Unrecognizable format) \
        {"dateTime": "Tue Oct 12 17::10 IST 2021", "message": "string"}
