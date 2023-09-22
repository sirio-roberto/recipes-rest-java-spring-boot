# Recipes Application

The `Recipes Application` is a RESTful web-based API that allows users to create, manage, and share their favorite recipes. It provides endpoints for adding, editing, and searching for recipes, as well as user registration and authentication.

## Table of Contents

* [Features](#features)
* [Technologies Used](#technologies-used)
* [Getting Started](#getting-started)
* [Usage](#usage)
* [Contributing](#contributing)

## Features

The Recipes Application includes the following features:

*  User registration and authentication.
*  Creation and management of recipes through RESTful endpoints, including name, category, description, ingredients, and directions.
*  Search for recipes by name or category.
*  Viewing, editing, and deleting individual recipes.
*  Batch creation of multiple recipes.
*  User management for administrators.

## Technologies Used

The project is built using the following technologies and frameworks:

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* MySQL (or your preferred database)
* RESTful API for recipe management

## Getting Started

To get started with the Recipes Application, follow these steps:

1. Clone the repository to your local machine:
    ```
    git clone https://github.com/your-username/recipes-application.git
    ```

2. Set up your database. You can configure the database connection in the `application.properties` file.

3. Build the project using your favorite Java IDE or with Maven:
    ```
    mvn clean install
    ```

4. Run the application:
    ```
    mvn spring-boot:run
    ```
The application will be accessible at `http://localhost:8080`.

## Usage

Interact with the Recipes Application by making HTTP requests to the provided API endpoints. You can use tools like Postman, curl, or any HTTP client to send requests to the API.

### User Registration

To register a new user, send a POST request to /api/register with the user's email address and password in the request body.

Example request body:
```json
{
    "email": "user@example.com",
    "password": "secretpassword"
}
```

### Recipe Management

**Manage recipes using the following API endpoints:

* Create a recipe: Send a POST request to `/api/recipe/new` with the recipe details in the request body.
* View a recipe: Send a GET request to `/api/recipe/{id}` to retrieve a specific recipe by its ID.
* Edit a recipe: Send a PUT request to `/api/recipe/{id}` with updated recipe details in the request body.
* Delete a recipe: Send a DELETE request to `/api/recipe/{id}` to remove a recipe by its ID.
* Batch create recipes: Send a POST request to `/api/recipe/newBatch` with an array of recipe objects in the request body.

### Recipe Search

* Search by name: Send a GET request to `/api/recipe/search?name={name}` to find recipes by name.
* Search by category: Send a GET request to `/api/recipe/search?category={category}` to find recipes by category.

### User Management (Admin Only)

Administrators can manage users using the following API endpoints:

* View all users: Send a GET request to `/api/allUsers` to retrieve a list of all registered users.

## Contributing

Contributions to the Recipes Application are welcome. You can contribute by:

* Reporting issues or suggesting improvements by creating GitHub issues.
* Submitting pull requests to address issues or implement new features.