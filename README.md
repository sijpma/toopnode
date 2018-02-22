# Contents #
TOOP node application

# First of all #
Building the projects is done using:
- JDK 9
- Apache Maven 3x

# Check out #
Use GIT clone to get a local copy of this repo.

# Configuration #
Before running check port-number inside:
./src/main/resources/application.properties

# Building #
Build using:

mvn clean install

# Running #
Run the application inside
./target/

java -jar toopnode.jar

# OR #

mvn spring-boot:run

# Browse to # 

http://localhost:8080/toopnode/consumer/provide?country=NL&id=90001354