# What is this?

The door, the gateway, the guide.

# How to use this MF?
* In your microservice, use the `springdoc-openapi-starter-webmvc-ui` dependency to generate a documentation for your apis.
* In this gateway, locate the file `servers.json` in the `resources` package and write down there your microservice url.
* Run the gateway.
* Keep your eye on the logs to find out if the operation worked.

# ????????????
![alt text](https://i.kym-cdn.com/photos/images/newsfeed/000/993/875/084.png)
* The  gateway requests the swagger documentations of the servers that are mentioned in the `servers.json` (each documentation is generated during runtime, and each hides behind it a simple JSON file that encapsulates all the data that's then translated to HTML, the infamous swagger-ui. The JSON file can be accessed via `http://serverurl/v3/api-docs` when swagger is enabled on your server).
Those JSON files are collected by the gateway in order for it to map the routes that exist on the microservices, thus, to direct the traffic in a correct way.