<H1>TastyRecipesApp</H1>

TastyRecipesApp is an Android application designed for users to explore, customize, and enjoy a variety of recipes.

https://github.com/user-attachments/assets/d7034643-df28-4be6-96e7-c408a6c3be8d

<H1>Project Structure</H1>

The app follows a modular architecture pattern with the following main modules:

**core:** The core module contains shared resources and configurations essential for the app. 

**data:** This module is responsible for managing data sources, including local storage, remote APIs, and caching. 

**domain:** The domain layer manages the app’s business logic, including use cases and entities. 

**presentation:** Responsible for the UI, this layer uses Jetpack Compose to render views and manage user interactions. 

<H1>Technologies Used</H1>

**Android**

**Jetpack Compose**

**Dagger Hilt**

**JUnit** 

<H1>Android App with Continuous Integration</H1>

This repository hosts the source code for our Android application, featuring a fully integrated Continuous Integration (CI) pipeline using GitHub Actions. The CI setup ensures automated testing.

<H1>Key Features of the Android App</H1>

**Seamless User Experience:** Designed with modern UI/UX principles, offering an intuitive interface for users using Jetpack Compose.

**High Performance:** Optimized for efficiency and minimal resource usage.

**Modular Architecture:** Developed with scalable and maintainable practices using the MVVM architecture pattern.

<H1>Continuous Integration (CI) Setup</H1>

Our CI pipeline automates the following key tasks to maintain a high-quality codebase.

**Code Validation:** Automatically triggers pull requests and pushes them to the main branch.

**Unit Testing:** Runs all unit tests using JUnit to verify the correctness of individual modules. Reports failures and provides insights into breaking changes.


<H1>Branches</H1>   
This repository contains two main branches, each showcasing a different aspect of the TastyRecipesApp architecture:

**Main Branch:** This branch contains the app with an added Menu feature module.

**Modular Branch:** This branch demonstrates a fully modularized version of the app, where each layer (data, domain, and presentation) is organized into its own module.




**Main Branch Structure Image**

<img src="https://github.com/user-attachments/assets/7a2bbe7c-6369-4eef-a4ff-aa66e4ff7066" width="300">

------------------------------------------------------------------------------------------------------

**Module Branch Structure Image**

<img src="https://github.com/user-attachments/assets/2b63a1d0-74a7-4f08-acb5-27c8754f8051" width="300">

------------------------------------------------------------------------------------------------------



