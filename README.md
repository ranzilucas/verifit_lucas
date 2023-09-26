# Verifit APP

### Reference Documentation

Verifit is an app that tracks people going to the gym and gives discounts on gym
membership for people who go at least once a week for more than 3 weeks in a row.
1. Set up a SpringBoot application that exposes a REST API with endpoints that provide the
   following functionality:

a. Add a record of attendance.
b. Allow the gym owner to check if a user should get a discount.
c. Allow the gym owner to retrieve a user’s current streak (how many weeks in
a row they have attended the gym).
Endpoints should be accessible localhost:8000 . You do not need to implement
secure authentication. The correct REST verbs should be used for each
endpoint. You should provide appropriate input validation and unit tests for
each endpoint.
Your code should be committed to a GIT repository.
2. Wrap your API in a docker container, that we can run locally to test.


### Guides
There are 5 member in the system, with id 1 to 5 you can test using the swagger ui.

Please follow the commands to run the app:

```shell
mvn clean package

docker build --tag=verifit-lucas-1.0.0:latest .

docker run -d -p 8000:8000 verifit-lucas-1.0.0:latest
```

For tests please execute 
```shell
mvn test
```

## Swagger

Swagger page will be auto-generated for run the commands. 
To open the page please go to `http://localhost:8000/swagger-ui/#`

Another doc infos are in : `http://localhost:8000/v2/api-docs`


## TODO

- Increase the coverage for controller and others classes
- Create endpoint to add new members


## Author
Lucas Ranzi