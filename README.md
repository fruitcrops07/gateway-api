# How to Spin Up #

set the current directory to the project

```bash
cd gateway-api
```
## Step 1 ##
#### Option 1 ####
if docker and docker-compose is available:

```bash
docker-compose -f spin_mysql_instance.yml -up
```

ensure that mysql workload is already up and running

use:

```bash
./mvnw clean install
```
on linux or:

```bash
mvnw.cmd clean instal
```
on Windows

#### Option 2 ####

if MySQL is installed on machine, import gateway.sql using:

```mysql
mysql -u root -p gateways < gateway.sql
```

use:

```bash
./mvnw clean install
```
on linux or:

```bash
mvnw.cmd clean instal
```
on Windows

## Step 2 ##
start the application using:

```bash
java -jar target/gateway-api.jar
```

## Step 3 ##
access the application's api documentation (SwaggerUI) on:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
