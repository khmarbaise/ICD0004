# Automation Project ICD0004

### Participants
- Alekasndr Borovkov
- Alen Siilivask
- Anneli Väli

***
### Tech stack used
- JDK 17
- Maven
- Log4j

***
### Installation

```
git clone https://gitlab.cs.ttu.ee/alsiil/Automation-Project-ICD0004.git
cd Automation-Project-ICD0004
mvn clean install -DskipTests
```

***

### Run the app in the command line

UNIX and Maven Goal executor in IntelliJ
```
mvn exec:java -Dexec.mainClass=main.WeatherMain
```
Windows CMD
```
mvn exec:java -D"exec.mainClass"="main.WeatherMain"
```
- Application will ask to enter a number: 1 for writing city name, 2 for providing full path to file containing city names

#### Example 1:
To insert city name - 1, to insert path to file with cities - 2:
```
1
```
Enter a city name:
```
Tallinn
```
***
#### Example 2:
To insert city name - 1, to insert path to file with cities - 2:
```
2
```
Enter path to file with cities
```
/Users/username/Desktop/automation-project/cities.txt
```
- City names in the file must be each on different line and file format should be .txt.

Example of cities.txt:
```
Tallinn
Riga
```
***

Application creates .json file containing weather report data for each city in the project folder


***
### Run tests
```
mvn test
```
***


### 1. Main details and current weather - 15p
   Note:
- [ ] Application reads input as string parameter and outputs result to stdout.

**ACCEPTANCE CRITERIA**

- [ ] City name can be provided as a string input
- [ ] The output is a weather report with main details and current weather data
- [ ] Main details part has city, coordinates and temperatureUnit properties
- [ ] Coordinates are in the format lat, log. Example: "59.44,24.75"
- [ ] Current weather part has date, temperature, humidity and pressure properties
- [ ] Output is a JSON object
- [ ] At least 3 unit tests exists
- [ ] Mock integration test exists for OWM for the main details data
- [ ] OWM integration is covered by integration tests for the main details data (top-down or bottom-up)
- [ ] OWM integration is covered by integration tests for the current weather data (top-down or bottom-up)

***
### 2. Forecast - 15p
   Note:
- [ ] Application reads input as string parameter and outputs result to stdout part 1 acceptance criteria apply as well

**ACCEPTANCE CRITERIA**

- [ ] City name can be provided as a string input
- [ ] The output is a weather report with main details and current weather data AND forecast report
- [ ] Forecast report part has date, temperature, humidity and pressure for each day
- [ ] Forecast calculates average of temperature, humidity and pressure
- [ ] Forecast is in ascending order (2020-10-01, 2020-10-02, 2020-10-03)
- [ ] At least 3 unit tests exists
- [ ] Mock integration test exists for OWM for the forecast data
- [ ] OWM integration is covered by integration tests for the forecast data (top-down or bottom-up)

***
### 3. Read city name from file and produce a JSON file for given city - 15p
   Note:
- [ ] Application reads input from a file and outputs result to JSON file.

**ACCEPTANCE CRITERIA**

- [ ] Only specific file format is allowed (you choose which: txt, csv, json, plain, docx)
- [ ] Display error message if an unsupported file is provided
- [ ] Display error message when file is missing
- [ ] Write 3 integration tests to test integrations between the weather report application and file reader and writer

***
### 4. Read multiple city names from file and produce a JSON output file for each city - 15p
   Note:
- [ ] Application reads input from a file, where there can be multiple city names, and outputs the result for each city to its own JSON file.
  Refactor your existing tests if need be.

**ACCEPTANCE CRITERIA**

- [ ] Can read multiple cities from file
- [ ] Can create weather report for given cities into separate JSON files
- [ ] Log INFO message when existing weather report file for city is being overwritten
- [ ] When an error occurs for invalid city name(s) then weather reports are created only for valid city names
- [ ] When an error occurs for invalid city name(s) then application logs ERROR message for that city.

***
### 5. Continuous Integration - 10p
   Note:
- [ ] Create a simple CI pipeline for running your regression test suite (all your tests) automatically when changes are pushed to master

**ACCEPTANCE CRITERIA**

- [ ] CI pipeline is run when changes are pushed to remote
- [ ] CI pipeline fails if any test fails
- [ ] CI pipeline passes when all tests have passed
- [ ] CI pipeline produces a log
