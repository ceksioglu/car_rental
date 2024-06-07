# Vehicle Rental Project

This project encompasses a vehicle rental system consisting of four main modules: Entity, DAO, Business, and View. These modules represent different layers of the project, each fulfilling a specific role.

## Dependencies

This project is designed around POSTGRESQL, because of my personal distaste for the outdated MySQL Workbench so make sure you have the correct JDBC driver for this to work proprely.
Make sure you configure the DB connection properly inside the Database file @ src/core.

## Modules

### Entity Module
- Defines database tables and their corresponding entity objects.
- Includes core entity objects such as user, brand, model, vehicle, and reservation.
- Determines the relationships between these objects.

### DAO (Data Access Object) Module
- Provides an interface for database access and operations.
- Manages the processes of saving, updating, and deleting the objects from the Entity module in the database.
- Executes data retrieval operations from the database.

### Business Module
- Manages business logic and performs core operations within the application.
- Handles business logic operations such as pricing and vehicle rental calculations.
- Interacts with the DAO module for database operations.

### View Module
- Manages the user interface (UI) and ensures interaction with the user.
- Displays user information such as vehicle lists and rental screens.
- Initiates operations by forwarding user input to the business layer.

## Core Requirements of the Project
- Registering all vehicles in the company into the system.
- Keeping license plate information for each vehicle.
- Listing available and suitable vehicles based on specific criteria through the system.
- Handling reservation processes.

Users will be able to check the availability of vehicles within a specified date range. For example, they can search based on criteria such as "Renault Clio" or "Volkswagen Polo" and make reservations from the available vehicles.

## Project Purpose

The purpose of this project is to enable vehicle tracking, view availability statuses, and manage the workplace.

## Layered Architecture

These modules form the layered architecture of the project, ensuring the code is organized, modular, and easy to maintain. Handling entity objects, database access, business logic, and the user interface separately makes the development process more manageable and facilitates the addition of new functions.
