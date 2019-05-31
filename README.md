# Badges

[![Build Status](https://travis-ci.org/nicferretti/WitsRewards_Android.svg?branch=develop)](https://travis-ci.org/nicferretti/WitsRewards_Android)

[![Coverage Status](https://coveralls.io/repos/github/nicferretti/WitsRewards_Android/badge.svg?branch=develop)](https://coveralls.io/github/nicferretti/WitsRewards_Android?branch=develop)

# WitsRewards_Android
An integrated software system that incentivizes students to become more engaging on university campus by rewarding them for being active members within the university ecosystem. The application provides micro-economic stimulation for both student and business whilst improving engagement levels with university facilities and events.

# Welcome to the WitsRewards Wiki!

## Project members:
* Noah Savadier
* Nicholas Ferretti
* Daniel Hewlett
* Daniel Yazbek
* Mark Alence

## Project brief:

Universities across the world are multi-faceted ecosystems that require constant engagement from their students to succeed.

The aim of WitsRewards is to encourage active engagement between students and their university ecosystem. This would include making greater use of facilities on their respective campuses, attending university events, and visiting places of interest to name a few examples.

Through their active participation students can earn rewards which they can use and redeem. Such rewards can include; discounts at various food establishments, free entrance to university events and facilities, and upgrades to a student's 'status'.

A collaborative and goal-orientated university ecosystem is beneficial to all parties involved. Businesses would receive revenue from students who wouldn't ordinarily visit, universities would have a more engaging student body and students would receive benefits both direct (meal discounts for example) and indirect (attending additional informative events/spaces for example).

The WitsRewards team believes in everyone achieving the best they can.

## Continuous Integration:
Throughout the development of the WitsRewards system, GitHub will be/is the main code repository for the project. Through the implementation of TravisCI and Coveralls, every push and change is built, run, analysed and tested to ensure only clean, functional code with sufficient coverage lies within the repository.

## Project Implementation:
### Structure of application:
The WitsRewards system is to be implemented using a distributed Android mobile application created with the Android Studio suite.

### Server/Database
The WitsRewards application will heavily utilize the Firebase support package developed by Google for data analytics, database storage and crash support specifically catered for mobile applications.

## High level documentation:
### Architectural Style: 3-tier

![Architecture](https://user-images.githubusercontent.com/47977629/58589505-5078a300-8262-11e9-87cf-792d0cec8509.png)

The 3-tier architectural style is well suited for the WitsRewards system. Due to the nature of the application, the necessities required include some form of middle-management services (to handle user registration, business service, database communication, etc) and a real-time database for storing application and user data. A 3-tier style therefore is suitable.

Furthermore, the 3-tier style overall tends itself to faster performance, a less complex development environment and easier scalability.


## 4+1 Architectural view model

### Logical view: Class diagram

![Class diagram](https://user-images.githubusercontent.com/47977629/58666485-b7b25800-8333-11e9-8994-98ba4582c1c5.png)


The logical view of the system is concerned with the overall functionality that is provided to the user from a class perspective.

As observed in the diagram above, the student class is at the core of functionality. The rank and statistic classes exist solely due to the existence of the student class. However, the event, quiz, location and code elements are independent of the student class and exist one their own. The rank, event, quiz, location and code classes all have some form of relation to the statistics class due to the fact that when a user completes a task that earns them points, their points will be updated in the statistics class. The event class utilises the location services to ensure a user is present at the event.

A student may have many events associated with them but may only have one rank, one set of statistics and one weekly quiz association.

### Process view: Activity diagram

![Activity Diagram](https://user-images.githubusercontent.com/47977629/58571652-7d18c480-823a-11e9-8f54-9725bfed1ede.png)




The above activity diagram is a graphical representation of process flow, from activity to activity, of the functionality of the WitsRewards android mobile application.

Upon opening the application, the user is intuitively presented with the option of either registering or logging into WitsRewards. If registration is selected the user is prompted to fill in their personal information. If the registration is successful the user is provided with information about how WitsRewards works and is taken to the home screen. Similarly if the user chooses login and logs in successfully, they are taken to the home screen.

Once present at the home screen, the user will have their details/profile statistics and upcoming events visible. There are several options present for the user to select.

If the user decides to view the about section of WitsRewards or chooses to view user statistics, then the respective activities will begin with the respective information displayed. If the user chooses to proceed with ‘Code services’, ‘Location services’ or ‘Event services’, then the user will be required to perform the prompted actions and if successful, the user’s WitsRewards points will be incremented.

### Development view: Component diagram

![Component diagram](https://user-images.githubusercontent.com/47977629/58572463-02e93f80-823c-11e9-8b9f-e434711aa697.png)


The WitsRewards Android mobile application is simplistic in its make-up.

The mobile application contains most of the business logic and computational responsibilities. The application component of the system contains several realizations that contain the core functionality of the application. The application component communicates with the Firebase console component through the Google Firebase API which the console provides.

The Firebase console component is independent in nature and handles all services/analytics provided by Google Firebase. The console component further handles data requests by communicating through a NoSQL interface with the Cloud Firestore database component which is responsible for storing all WitsRewards logic data and user data.

### Physical view: Deployment diagram

![Deployment Diagram](https://user-images.githubusercontent.com/47977629/58571590-58bce800-823a-11e9-930f-3e60fbe11a3d.png)

The deployment diagram observed is comprised of two major devices, the mobile Android device and the Google Firebase server which communicate over a HTTP/SSL connection.

The mobile Android device is host to and responsible for the WitsRewards mobile APK. Within the WitsRewards application there are several components that provide the core functionality of the application and are responsible for communication with Google Firebase.

The Google Firebase server contains several elements. The Firebase console component is essential for managing the services/analytics that Google Firebase provies but it is also crucial for managing data requests from the WItsRewards mobile application. The console communicates with the Cloud Firestore database which has a container for storing all relevant information created by the WitsRewards application as collections.


### Scenario: Use case diagram

![WitsRewards Use Case Diagram](https://user-images.githubusercontent.com/47977629/58572462-0250a900-823c-11e9-91c0-9ba5a6caea66.png)

The scenario view of this project includes two agents. The WitsRewards system and the WitsRewards user.

The user is provided with an array of functionality, beginning with registration and login services. The user is further able to view their profile and user statistics if they choose. When it comes to earning WitsRewards points, several features are provided which include checking into locations, entering/scanning codes and answering daily quizzes. All of which increment the users points. The is also an utility that allows the user to observe upcoming events in which they may earn WitsRewards points.


