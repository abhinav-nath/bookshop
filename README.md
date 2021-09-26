<p>
    <a alt="Java">
        <img src="https://img.shields.io/badge/Java-v1.8-orange.svg" />
    </a>
    <a alt="Spring Boot">
        <img src="https://img.shields.io/badge/Spring%20Boot-v2.5.4-brightgreen.svg" />
    </a>
</p>

# Bookshop [![CircleCI](https://circleci.com/gh/abhinav-nath/bookshop/tree/master.svg?style=svg)](https://circleci.com/gh/abhinav-nath/bookshop/tree/master)

This app is a simple CRUD application made using Spring Boot and Java.

Below are the key features of this app:

1. Focus on TDD and Unit Tests
2. CI/CD pipeline using CircleCI and Heroku
3. JPA relationships like `@OneToMany`, `@ManyToOne` and `@OneToOne`
4. Flyway DB Migrations
5. Clean Coding practices
6. AOP based Error Handling
7. Validation of input fields
8. Security using BasicAuth
9. Role based access to resources
10. Integration with PostgreSQL

---
## Useful Commands

#### Run PostgreSQL as a Docker container in local

```shell
$ docker run --name postgresdb -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres
```


Build the docker image:

```shell
$ docker build --build-arg JAR_FILE="build/libs/bookshop-1.0.0.jar" -t bookshop -f Dockerfile .
```


Run the container in detached mode:

```shell
$ docker run -d --name bookshop -p 8080:8081 bookshop
```