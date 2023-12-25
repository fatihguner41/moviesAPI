# moviesAPI

## Project Overview

I am developing a Java Spring Boot REST API that connects users with the movie world. The primary goal of the project is to gain more experience in related technologies such as Java Spring Boot, Java Spring Security, Data JDBC, JWT, and PostgreSQL.

## Features and Endpoints

### 1. Authentication and Authorization with JWT Token
- Users can sign up or log in with their username and password.
- After authentication, users receive two JWT tokens: an access token and a refresh token.
- Users with different roles, either admin or user, can be assigned to limit access to specific endpoints.

### 2. Movie Endpoints
- Users can search for movies with specific parameters such as category, revenue, IMDb scores, and release date.
- Various searches can be conducted based on different criteria.
- Adding, deleting, and updating movies are restricted to users with admin roles.

### 3. Movie Lists Endpoints
- Each user can create and manage their own movie lists.
- Users have the ability to add and remove movies from their lists.


## Getting Started

To get started with the project, follow these steps:

1. Clone the repository.
2. Navigate to the project directory.
3. Run the application using your preferred development environment.

Feel free to contribute or report issues!

## Technologies Used

- Java Spring Boot
- Java Spring Security
- Data JDBC
- JWT
- PostgreSQL


