# ms-pagseg-psql-sqs

## Overview

This project is a Spring Boot microservice designed for managing payments and sales, integrating with PagSeguro's API and AWS SQS for asynchronous processing. It uses PostgreSQL as its main database and follows Clean Architecture principles to ensure maintainability, scalability, and testability.

## Features

- RESTful API for managing clients, products, and sales
- Integration with PagSeguro API for payment processing
- Asynchronous communication using AWS SQS queues
- Database migrations managed by Flyway
- Built with Clean Architecture for clear separation of concerns

## Clean Architecture

The project is structured according to Clean Architecture, which separates the codebase into layers:

- **Domain**: Contains business entities and core logic
- **Application**: Use cases and business rules
- **Adapters**: Controllers, DTOs, and external gateways (e.g., PagSeguro, SQS)
- **Infrastructure**: Implementations for repositories and external services

This separation allows for easy testing, flexibility in changing frameworks or technologies, and a clear distinction between business logic and infrastructure concerns.

## AWS SQS Integration

The service uses AWS Simple Queue Service (SQS) to handle asynchronous events, such as processing sales and payment notifications. This decouples the processing of requests, improves scalability, and increases fault tolerance.

## PagSeguro API Integration

PagSeguro is a popular payment gateway in Brazil. This service integrates with PagSeguro's Orders API to initiate and manage payment transactions. The integration is abstracted through a gateway interface, making it easy to swap or extend payment providers if needed.

## Environment Variables

The application uses the following environment variables, which can be set in your Docker environment or system:

- `PSQL_HOST`: JDBC URL for the PostgreSQL database
- `PSQL_PASSWD`: Password for the PostgreSQL user
- `SQS_URL`: AWS SQS queue URL
- `PAGSEGURO_TOKEN`: PagSeguro API token

## Running with Docker

Build and run the application using Docker:

```sh
# Build the Docker image
$ docker build -t ms-pagseg-psql-sqs .

# Run the container with environment variables
$ docker run -e PSQL_HOST=jdbc:postgresql://host:5432/db \
  -e PSQL_PASSWD=yourpassword \
  -e SQS_URL=https://sqs.your-region.amazonaws.com/queue \
  -e PAGSEGURO_TOKEN=yourtoken \
  -p 8080:8080 ms-pagseg-psql-sqs
```

## License

MIT License
