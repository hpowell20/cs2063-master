# Lab 7 - Database Storage using Room Persistence Library

In lecture we have explored different storage mechanisms available for app specific data.  One of the more used alternatives is to make use of a [SQLite database](https://www.sqlite.org/) which allows for more complex structured data to be stored.  We also learned that there are several issues and shortcomings with using SQLite directly.  As such, Google recommends the use of the Room Persistence Library framework to help solve these issues.  In this lab you will build a simple app that uses a SQLite database and Room for managing the data.

## Pair Programming

We will again be doing pair programming for this lab.  Details on pair programming can be found at [Pair Programming](../docs/PAIR_PROGRAMMING.md).  You can again work with anybody of your choosing.

**To receive credit for this lab you must pair program.**

**Switch between the roles of navigator and driver every 10 to 15
minutes.**

## App Functionality

Once completed the user will be able to use the app for the following:
* Add new rows to the database
* Search for rows that have a particular item
  * Below is a sample screenshot of the app after the user has searched for the item "Cats".

    ![Main Activity](../screenshots/Lab7/RoomPersistence_results_found.png)

  * In this case the database had three rows with the item "Cats" with the numbers "3", "5", and "10". (Note that the results have been sorted by number.)

  * In the case that a search item does not match any rows in the database, a message indicating this is displayed, as shown below.

    ![Main Activity](../screenshots/Lab7/RoomPersistence_no_results.png)


## Resources

The following documentation will be helpful in this lab.

* Android Room Persistence Library: https://developer.android.com/training/data-storage/room
* https://developer.android.com/reference/java/util/concurrent/ExecutorService
* [Room Persistence Library example](https://github.com/hpowell20/cs2063-winter-2020-examples/tree/master/Lecture7/RoomPersistenceLibraryDemo) from class
  * NOTE that this example makes use of Runnables as opposed to AsyncTask



**Task 1**

1. Download the skeleton project for the lab and review the existing code.

  * Start by having a look in the app/build.gradle file
    * There you will notice the following dependency imports which enable us to use the Room Persistence framework
    ```java
    // Room Components
    implementation "androidx.room:room-runtime:$roomVersion"
    annotationProcessor "androidx.room:room-compiler:$roomVersion"
    ```
    ```java
     // Lifecycle components
    implementation "androidx.lifecycle:lifecycle-extensions:$archLifecycleVersion"
    annotationProcessor "androidx.lifecycle:lifecycle-compiler:$archLifecycleVersion"
    ```

  * You are provided the following classes for use:
    * `MainActivity`
      * Entry point for the app
      * This class contains two inner AsyncTask classes which will be used to call the database methods and handle the results
    * `ItemViewModel`
      * A `ViewModel` acts as a communication center between the Repository and the UI
      * In our app, this class is used as the bridge between the `MainActivity` class and the `ItemRepository` class
    * `ItemsAdapter`
      * This class translates the records and updates each row in the `ListView`
    * `AppDatabase`
      * Database holder class; main access point to the data
        * This class must be defined as abstract and extend RoomDatabase
        * You annotate the class to be a Room database with @Database and use the annotation parameters to declare the entities that belong in the database and set the version number
          * Each entity corresponds to a table that will be created in the database
        * A Singleton object is being created to prevent multiple instances of the database being opened at the same time
        * To help with asynchronous access an `ExcecutorService` object is defined with a fixed thread pool
        We've created an ExecutorService with a fixed thread pool that you will use to run database operations asynchronously on a background thread
    * `ItemDao`
      * Interface class which contains the methods for accessing the database
      * The @Dao annotation identifies it as a DAO class for Room
      * This class will need to be updated to include the appropriate queries
    * `Item`
      * Class defined as an @Entity class which represents a SQLite table
    * `ItemRepository`
      * Class used to manages queries and enables the use of multiple backends
        * In our case it is used to provide a clean API for data access for the rest of the app

**Task 2**

After having looked through the code, the next steps are to setup the database classes.

1. Update the `Item` class to define the properties of the entity model
  * HINT: Each public property in the class represents a column in the table
  * You will need to include getters and setters
2. Update the `ItemDao` class to include two methods
  * HINT: One for search and one for insert
3. Update the `ItemRepository` to create the interface between the view model and the database

**Task 3**

With the database code in place you will now need to update the UI to work with the saved data.

1. Update the `ItemsAdapter` class to set the name and number text values.
2. Update the `ItemViewModel` class to create the methods used by the UI classes to work the data
3. Complete the TODO items in the `MainActivity` class

**Lab Completion**

* Show the working app to the instructor or TA using the following scenarios
* Keep a copy of your project work and answers for future reference
