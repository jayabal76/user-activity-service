# Introduction 
This app provides REST APIs to store the user activity log.

Used Java spring boot, spring rest api, spring framework, jpa and memory h2 db.

Documentation - Integrated with swagger

Tool - Postman for testing the service


# Build executable artifacts
1. Install Maven 3.5.2 or above on the local environment if it's not installed yet.
2. Pull code from the repo
3. Go to the root of the local repo
4. Run 'mvn clean package' to build the executable artificats
5. Executable artifacts are in folder $root/target

# Start REST APIs
1. Run java -jar target/user-activity-service-1.0.0.null-spring-boot.jar
3. The default service port is 8098 if it's not specified.

# Start dababase h2 console
## Get a database table info
```
curl --location --request GET '{host}:{port}/h2-console/'
```
### Credentials
```
username:sa
password:<empty>
```

# Get APIs Info
See the swagger document
```
http://localhost:<port>//swagger-ui.html#
```