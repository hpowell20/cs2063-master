# Lab 7 - SQLite Database Storage

In lecture we have explored different storage mechanisms available for app specific data.  One of the more used alternatives is [SQLite database](https://www.sqlite.org/) which allows for more complex structured data to be stored.  In this lab you will build a simple app that uses a SQLite database for managing data.

## Pair Programming

We will again be doing pair programming for this lab.  Details on pair programming can be found at [Pair Programming](../docs/PAIR_PROGRAMMING.md).  You can again work with anybody of your choosing.

**To receive credit for this lab you must pair program.**

**Switch between the roles of navigator and driver every 10 to 15
minutes.**

## Understanding the skeleton code

You are provided with skeleton code which includes three files:
* `MainActivity`
  * Entry point for the app
* `DatabaseHelper`
  * Class defined as a subclass of the `SQLiteOpenHelper` which creates a database table with three columns: ID, item, and number
* `DBHelper`
  * Class used to as the interface between the database and the UI where the database queries are to be added

Once completed the user will be able to use the app for the following:
* Add new rows to the database
* Search for rows that have a particular item
  * Below is a sample screenshot of the app after the user has searched for the item "Cats".

    ![Main Activity](../screenshots/Lab7/Results_found.png)

  * In this case the database had three rows with the item "Cats" with the numbers "3", "5", and "10". (Note that the results have been sorted by number.)

  * In the case that a search item does not match any rows in the database, a message indicating this is displayed, as shown below.

    ![Main Activity](../screenshots/Lab7/No_Results_found.png)


## Resources

The following documentation will be helpful in this lab.

* SQLite databases in Android: https://developer.android.com/training/data-storage/sqlite.html
* SimpleCursorAdapter: https://developer.android.com/reference/android/widget/SimpleCursorAdapter.html
* [SQLite example](https://github.com/hpowell20/cs2063-winter-2020-examples/tree/master/Lecture7/DBDemo) from class


**Tasks**

1. Read the skeleton code and make sure you understand what it's doing
2. Complete the TODO items in the classes

**Lab Completion**

* Show the working app to the instructor or TA using the following scenarios
* Keep a copy of your project work and answers for future reference
