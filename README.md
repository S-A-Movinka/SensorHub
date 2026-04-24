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

In my implementation, JAX-RS creates a new resource instance for each request instead of using a single shared object. This means every request is handled separately, which avoids issues with shared data inside the resource class.
Because of that, I didn’t store data inside the resource classes. Instead, I used a separate MockDatabase with static Maps and Lists so the data is shared and doesn’t get lost between requests.
However, since this data is shared, multiple requests could try to access or modify it at the same time, which can cause race conditions. For example, two requests could try to delete or update the same room at once. In my coursework I didn’t handle this because it’s a simple setup, but in a real system I would use thread-safe collections like ConcurrentHashMap or synchronization to avoid these issues.

Q2: Why is HATEOAS useful?

HATEOAS (Hypermedia as the Engine of Application State) means including links to related resources within API responses. This is considered a key feature of advanced RESTful design because it allows clients to discover available actions dynamically instead of relying on hardcoded URLs.
In my API, I implemented a discovery endpoint that returns links to main resources such as rooms and sensors. This benefits client developers because they can navigate the API easily without needing external documentation. It also makes the API more flexible, since changes to endpoints do not require updates on the client side.

Part 2
Q3: IDs vs full objects

When returning only IDs, the response size is smaller, which reduces network bandwidth usage and improves performance. However, this requires the client to make additional requests to retrieve full room details, increasing client-side processing.
When returning full objects, the response size is larger, but it reduces the number of requests the client needs to make. This simplifies client-side development. In my implementation, I chose to return full objects because it makes the API easier to use and reduces the need for multiple calls.

Q4: Is DELETE idempotent?

Yes, the DELETE operation in my implementation is idempotent. This means that sending the same DELETE request multiple times results in the same final state.
For example, when a room is deleted, it is removed from the MockDatabase. If the client sends the same DELETE request again, the room no longer exists, so the system state does not change further. Although the response might differ (such as returning 404 after deletion), the overall state remains the same, which satisfies the definition of idempotency.

Part 3
Q5: What happens if client sends wrong format (@Consumes)?

I used @Consumes(MediaType.APPLICATION_JSON) to specify that the API expects JSON input. If a client sends data in a different format such as text/plain or application/xml, JAX-RS will not be able to process the request.
As a result, the framework automatically returns an HTTP 415 Unsupported Media Type response. This ensures that the API only accepts valid data formats and prevents incorrect input from being processed.

Q6: QueryParam vs path parameter

In my implementation, I used @QueryParam to filter sensors by type (for example, /sensors?type=CO2). This approach is better for filtering because query parameters are optional and can be combined with other filters.
Using a path like /sensors/type/CO2 is less flexible, as it treats the filter as part of the resource structure rather than an optional condition. Query parameters are more suitable for searching and filtering collections, while path parameters are better for identifying specific resources.

Part 4
Q7: Sub-resource locator benefits

I used a sub-resource locator in the SensorResource class to handle paths like /sensors/{id}/readings. Instead of handling all logic in one class, the method returns a separate SensorReadingResource class.
This improves the architecture because it separates responsibilities, making the code easier to maintain and understand. It also avoids having a large, complex controller class and better represents the hierarchical relationship between sensors and their readings.

 Part 5
Q8: Why HTTP 422 instead of 404?

HTTP 422 is more appropriate when the request structure is valid but contains incorrect data. In my implementation, when a client tries to create a sensor with a room ID that does not exist, the request itself is valid JSON, but the referenced resource is invalid.
Using 404 would suggest that the requested URL is not found, but in this case, the issue is within the request data. Therefore, 422 more accurately describes the problem.

Q9: Risks of exposing stack traces

Exposing internal Java stack traces can reveal sensitive information such as class names, package structure, and implementation details. This information can be used by attackers to understand how the system works and potentially exploit vulnerabilities.
To prevent this, I implemented exception mappers that return clean JSON error responses instead of exposing internal errors, making the API more secure.

Q10: Why use filters for logging?

Using JAX-RS filters for logging is advantageous because it centralizes logging logic in one place. Instead of adding logging statements inside every resource method, the filter automatically logs all incoming requests and outgoing responses.
This reduces code duplication, keeps resource classes clean, and ensures consistent logging across the entire API. It also makes the system easier to maintain and extend.
