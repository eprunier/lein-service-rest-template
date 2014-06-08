# lein-webapp-template

A Leiningen template for REST API based on Liberator.

## Usage with Leiningen 2

Create a new project using this template:

    lein new lein-api-rest-template my-api
    cd my-api

Then launch the new Web app by issuing one of the following commands:

```shell
SERVER_HOST=localhost SERVER_PORT=8080 lein run
```

You can generate a standalone jar and run it:

```shell   
lein uberjar
java -Dserver.host=localhost -Dserver.port=8080 -jar target/my-api-0.1.0-SNAPSHOT-standalone.jar
```

You can also generate a war to deploy on a server like Tomcat, Jboss...

```shell
lein ring uberwar
```

## License

Copyright © 2012-2013 Eric Prunier

Distributed under the Eclipse Public License, the same as Clojure.
