# saltedge-connector
Saltedge-connector provides access to SaltEdge API from Java. It is a boilerplate code which simplifies access to SaltEdge API from Java program. To use it, clone the repo, build it and add the maven dependency into your project:
```
<dependency>
    <groupId>org.jrivets.connector</groupId>
	<artifactId>saltedge-connector</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```
For using connector, use builder to create it:
```
...
SaltEdgeClient sec = new SaltEdgeClientBuilder().clientId("__YOUR_SALT_EDGE_CLIENT_ID__")
    .serviceSecret("__YOUR_SALT_EDGE_SERVICE_SECRET__")
    .privateKeyFileName("<path to private key file>")
    .build();
Client clientInfo = sec.getClientInfo();
...
```

