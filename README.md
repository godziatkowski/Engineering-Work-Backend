#Room Booking App

Project is build using Spring Boot, and managed by Maven.

###Project Requirements:
1. JDK8
2. Maven
3. MySQL (best version would be 5.5)

#####MySQL database
While installing MySQL you can install it as Service so it can start automacally with your system.
After installing MySQL run script:
```
create database RoomBookingApp char
SET utf8 COLLATE utf8_general_ci;
```

#####Application properties
1. On partition on which project is located (for example **d:\project\RoomBookingApp\backend\**) create folders structure **\opt\roomBookingApp\config\** named opt in partition root (ex. **d:\opt\roomBookingApp\config\**)
2. In project folder **\src\main\resources\externalConfig\** contain two files : **roomBookingApp.properties** and **roomBookingAppLogConfig.xml** - copy them to your **opt\roomBookingApp\config** folder.
3. In copied file **roomBookingApp.properties** find properties **spring.datasource.username=** and **spring.datasource.password=** and fill them with credentils required to connect to your local database.

###Start project
1. Open command line in folder with project
2. Run command **mvn spring-boot:run**
3. After compillation server should start running


###Usefull tools
1. Java IDE (NetBeans, Eclipse, IntellIJ)
2. MySQL Workbench - makes managing database much easier. 
3. SourceTree - graphic tool for git management