# power_plant

Swagger : http://localhost:8088/swagger-ui.html

Power plant info:
GET : http://localhost:8088/api/power-plants?columnName=name&order=DESC&pageNumber=0&pageSize=10&plantLocationId.in=1%2C2

[
  {
    "id": 3,
    "name": "Tasty",
    "plantOutput": "50",
    "outputUnit": "MWT",
    "type": "INPROGRESS",
    "plantManpowerCapacity": "Table",
    "workingHour": "Facilitator",
    "plantLocationId": 1
  },
  {
    "id": 1,
    "name": "Generic Steel Pizza Realigned",
    "plantOutput": "120",
    "outputUnit": "MWT",
    "type": "INPROGRESS",
    "plantManpowerCapacity": "Credit Card Account Pants",
    "workingHour": "Rhode Island Avon Bacon",
    "plantLocationId": 1
  },
  {
    "id": 2,
    "name": "Argentina application Cambridgeshire",
    "plantOutput": "100",
    "outputUnit": "MWT",
    "type": "INPROGRESS",
    "plantManpowerCapacity": "static payment",
    "workingHour": "neural-net Factors Distributed",
    "plantLocationId": 1
  }
]
Location specific actual with percentage:GET http://localhost:8088/api/power-plants/count-percentage?plantLocationId.in=1
{
  "actualValue": "270",
  "plantOutputPercentage": "28"
}

## Development

To start your application in the dev profile, run:

```
./mvnw
```

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

## Building for production

### Packaging as jar

To build the final jar and optimize the power_plant application for production, run:

```

./mvnw -Pprod clean verify


```

To ensure everything worked, run:

```

java -jar target/*.jar



### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```

./mvnw -Pprod,war clean verify


```

## Testing

To launch your application's tests, run:

```
./mvnw verify
```

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

```
docker-compose -f src/main/docker/postgresql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/postgresql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
./mvnw -Pprod verify jib:dockerBuild
```

Then run:

```
You can fully dockerize and create tar file to deploy on linux:

```
./createDocker.sh
sudo docker save -o power-plant.tar power-plant-0.0.1
 sudo docker load -i power-plant-0.0.1
```
```


