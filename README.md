<p>
    <a alt="Java">
        <img src="https://img.shields.io/badge/Java-v1.8-orange.svg" />
    </a>
    <a alt="Spring Boot">
        <img src="https://img.shields.io/badge/Spring%20Boot-v2.5.4-brightgreen.svg" />
    </a>
</p>

# Bookshop [![CircleCI](https://circleci.com/gh/abhinav-nath/bookshop/tree/master.svg?style=svg)](https://circleci.com/gh/abhinav-nath/bookshop/tree/master)

#### Run PostgreSQL as a Docker container in local

```
$ docker run --name postgresdb -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres
```


Build the docker image:

```shell
docker build --build-arg JAR_FILE="build/libs/bookshop-1.0.0.jar" -t bookshop -f Dockerfile .
```


Run the container in detached mode:

```shell
docker run -d --name bookshop -p 8080:8081 bookshop
```