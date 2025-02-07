# Temperature BE
## Java version
This project uses Java 21

## Running the application
Run the following command in a terminal at the root of this repository:
```bash
./gradlew bootRun
```
This will start the server at http://localhost:8080/ and fill the database with two buildings and two rooms.

## CURL commands
View all rooms:
```bash
curl http://localhost:8080/rooms
```
Set the current temperature of a room (change the 1 to the id of the room you want to change):
```bash
curl -X PATCH http://localhost:8080/rooms/1/currentTemperature?currentTemperature=21.5
```
Set the target temperature of a room (change the 1 to the id of the room you want to change):
```bash
curl -X PATCH http://localhost:8080/rooms/1/targetTemperature?targetTemperature=24.0
```
Setting the target temperature of a room will increase or decrease its current temperature by 0.1 degrees every second until it reaches the target temperature.

## Running tests
Run the following command in a terminal at the root of this repository:
```bash
./gradlew test
```