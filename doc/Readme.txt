This RESTful application can calculate distance. API has 3 endpoint:
1)List of all cities in the DB. Fields:
	ID
	Name
Output:
JSON data. If city not found return 204 code.
Example:
http://localhost:8080/DistanceCalculator/city/all GET method without parameters
2) Calculate distance
Input:
Calculation Type: <Crowflight, Distance Matrix, All>
From City: city's name 
To City: city's name
Output:
JSON data. If data not found return null value.
Example:
http://localhost:8080/DistanceCalculator/calculate?type=all&cityfrom=Samara&cityto=Chapaevsk GET method

3) Upload data to the DB. Uploads XML file with cities and distances into the application. Application parses it and stores it into the database.
Input:
	Multipart/form-data
Output:
HTTP response code 200 without body. If file invalid or too big(>100kb) return 409 code.
Example:
http://localhost:8080/DistanceCalculator/upload POST method with file.
Example of xml file you can find in this folder.

To run liquibase you need to execute 
/liquibase/liquibase.bat --url=jdbc:mysql://localhost:3306/distance-calculator --driver=com.mysql.jdbc.Driver --username=root --password=root --changeLogFile=liquibase/cha
ngelog-master.xml --classpath=liquibase/lib/mysql-connector-java-5.1.39-bin.jar update

Note:
Examples are valid if the application deployed on localhost on port 8080.
