# SensorHub API

# Overview

SensorHub is a RESTful API built using JAX-RS (Jersey) and Apache Tomcat.
It manages Rooms, Sensors, and Sensor Readings with support for CRUD operations, filtering, nested resources, error handling, and logging.

# How to Run:

1. Open project in NetBeans
2. Configure Apache Tomcat
3. Right-click project → Run

 Base URL:http://localhost:8080/SensorHub/api/v1

# Sample cURL Commands:

# Get all rooms
curl http://localhost:8080/SensorHub/api/v1/rooms

# Create a room
curl -X POST http://localhost:8080/SensorHub/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"R3","name":"Office","capacity":60,"sensorIds":[]}'

# Delete a room
curl -X DELETE http://localhost:8080/SensorHub/api/v1/rooms/R2

# Get all sensors
curl http://localhost:8080/SensorHub/api/v1/sensors

# Add sensor reading
curl -X POST http://localhost:8080/SensorHub/api/v1/sensors/S1/readings \
-H "Content-Type: application/json" \
-d '{"id":"SR1","timestamp":1713000000000,"value":420.5}'


# REPORT (Coursework Answers)
 
Part 1
Q1: Resource lifecycle

In JAX-RS, a new resource object is created for each incoming request. This means every request is handled independently, which helps avoid shared data issues. Because of this, I stored my data in static collections (like HashMaps in MockDatabase) so that the data persists across requests. This also means I don’t need to worry about multiple requests interfering with each other at the object level.

Q2: HATEOAS

HATEOAS means including links to related resources inside API responses. In my API, I used a discovery endpoint that returns links to main resources like rooms and sensors. This is useful because clients don’t need to remember all endpoints,they can discover them dynamically. It also makes the API easier to extend in the future.

Part 2
Q3: IDs vs full objects

If only IDs are returned, the response is smaller and faster, which saves bandwidth. However, the client would need to send more requests to get full details. Returning full objects uses more data but makes it easier for the client because everything is already included in one response. In my API, I returned full objects because it simplifies client usage.

Q4: DELETE idempotency

DELETE is idempotent because calling it multiple times does not change the result after the first deletion. For example, if a room is deleted once, calling DELETE again will not change anything, even though the response may be different (like returning 404). The system state remains the same.

 Part 3
Q5: @Consumes behavior

I used @Consumes(MediaType.APPLICATION_JSON) to specify that the API expects JSON input. If a client sends data in another format like XML or plain text, the request will fail and return a 415 Unsupported Media Type error. This ensures that the API only processes valid data formats.

Q6: QueryParam vs path

I used query parameters for filtering sensors (e.g., ?type=CO2). This is better than putting it in the path because filtering is optional and can be combined with other filters. Path parameters are more suitable for identifying specific resources, while query parameters are better for searching and filtering.

Part 4
Q7: Sub-resource locator

I used a sub-resource locator to handle sensor readings under a specific sensor (/sensors/{id}/readings). Instead of handling everything in one class, this approach returns another resource class that manages readings. This keeps the code cleaner and easier to manage.

 Part 5
Q8: HTTP 422 vs 404

I used HTTP 422 when a sensor is created with a room ID that does not exist. This is more accurate than 404 because the request itself is valid, but the data inside it is incorrect. 404 is usually used when a resource is not found in the URL.

Q9: Stack trace risks

If the API returns raw stack traces, it can expose internal details like class names and file structure. This can be a security risk because attackers can use that information to understand the system and find weaknesses. That’s why I used exception mappers to return clean JSON error responses instead.

Q10: Logging filters

I implemented logging using JAX-RS filters instead of putting log statements in every method. This is better because it keeps the code clean and avoids repetition. The filter automatically logs all incoming requests and outgoing responses in one place.
