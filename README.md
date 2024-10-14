# Getting Started

For running the project, you need to have the following installed on your machine:

- docker (https://docs.docker.com/get-docker/)
- docker-compose (https://docs.docker.com/compose/install/)

To run the project, make sure you have docker running, and then you need to execute in your terminal
the following command in the project location path:

```bash
  docker compose up --build -d
```

This command will build the images and start the containers.
The app will be available at http://localhost:8000/view/home.

The application is composed of two services:

- **kitchensink**: The Spring Boot with Java 21 application that serves the frontend and the API.
- **mongodb**: The MongoDB database.

To stop the project, you need to execute the following command:

```bash
  docker compose down
```

In order to run the tests, you need to enable the line 5 in the `Dockerfile` of the `kitchensink` service:

```Dockerfile
  # RUN mvn test
```

and then run the following command:

```bash
  docker compose up --build
```

