# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

Click [here](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmeqBJzBYbjObqYCMhbLCNQbx1A1TJXGoMh+XyNXoKFmTiYO189Q+qpelD1NA+BAIBMU+4tumqWogVXot3sgY87nae1t+7GWoKDgcTXS7QD71D+et0fj4PohQ+PUY4Cn+Kz5t7keC5er9cnvUexE7+4wp6l7FovFqXtYJ+cLtn6pavIaSpLPU+wgheertBAdZoFByyXAmlDtimGD1OEThOFmEwQZ8MDQcCyxwfECFISh+xXOgHCmF4vgBNA7CMjEIpwBG0hwAoMAADIQFkhRYcwTrUP6zRtF0vQGOo+RoFmipzGsvz-BwVygYKQH+iMykoKp+h-Ds0KPMB4lUEiMAIEJ4oYoJwkEkSYCkm+hi7jS+4MkyU76XOnl3kuwowGKEpujKcplu8So3gFDpJr6zpdhu7pbsO8WVMuMAAGomkgWjoilMBNEZ6mYCqwYam6RgQGoaAAOTMFaaKxbygUWVZAByvb9m5IHVP60i1aonUQGAUZolGMZxoUWkJfAyCpjA6aZqMYw5qoebzNBRYlq6RGGBww0wGgY0wM1OQNvRrULgK81uftm7yNu7lUre-L1IecgoM+8Tnpe17pYumXBY+AYA1ufXzTppYOeKGSqABmAw-1ElgYR5afIsqxjBRVH1pCmkDfCyaLdhMC4fha36XsON44hBO0Y2DGeN4fj+F4KDoDEcSJJz3MOb4WCiYKoH1A00gRvxEbtBG3Q9HJqgKcM9NIeh8KVDD9QUdASAAF55AU9QADyq+g5TI2ZGEdS61lCUL-3wQzaCuUlgpA6OMCMmAv2O5Rzv+W1GVCvUoVPhD8iyvKZuFBV6rGpe+NoDA4pUHlDEe6jlm2z2fYvVn-oAJJoGnyAcFNKCxgp6uYWTYBphmBEbVtBZjLt0D1MXpf5VdTaZ-dSX1L9r5ux5Qf0jIKDcMel43fu97BdIU9Mj9l5RwnTtq1DH5W7D9ungjSMowPaMvETaO1yU9cU3hWZ0U2rPMf4KLrv42Dihq-FojAADiSoaCLCykkf4y3lvYJUKtE7OxrjvWE-odZQH1obNAJsY4W2PolTs9RkA5D-jmP2SdXadndu9Ce3tfYx0DrdBeodxThxfNodeMdyqqg1DHFOJd05z3apg7OyVc69VHkA0sXd04VyrvGOapMr4N1WtmfkLcdrFg7jAURZde4Z1IXdG2yVh6Q1Hm9OKE8cFgDwWoAhAduHByyuQvsZjVAYiIXw9sWsBJonsTAQ+CBAK7wLmBZY4CcwFgaOMQJKBC7SALAARnCMEQIIJNjxF1CgN0nJaYgmSKANUqTILYxBGEzqSpsYXBgJ0c+iZpFLUplmAJ-9gmhKVBE6JsT4nLEScknJWMxjfEySAbJB10m1LmIUuYxTSn3xZkxdmHAADsbgnAoCcDECMwQ4BcQAGzwAnIYMxMAih11FsTcWrQOhgIgdMKBSEswFKVOU62mtd7a0vLrA2M1UGXPNmsEYQyUAjO2t0mApk4Eaw7Hwz62yzEYjgBCpUTk1AuXzmPW63kfaXgsUhKh88gq0IlHoyOkVmFxzYR85OqcuH9x0d2HqiLhGuguXqF5KBy7RkrjNGBlTybpiiTU9aCj8xKL2gqel8RGW7EBczKxi5KUbyvPo4hSKvIwC+uiSFZjAZaJoQGNcv8lQj3lbAr89RoVHhQGYrxPjgV+JeD8pp9QYlxPFVIyookcK31GDayJdqWniuuo-dmlgp42U2DzJACQwABr7BAYNAApCA4odVzBiL0tU+yr6HNPo0JozIZI9DCZAze6AszYAQMAANUA4AQBslANYYSIl3JBa4hBSC3kwFNiS8oXz1glrLRWqtewADqLBC6yx6AAIX4goOAABpb4tbPUwHtYEcVGDQVWQAFZxrQJC2N4ozUoEJPCpxg5DHj09uQtFlDJV3RBji+hsr8XRxJSwyqMqk4cO7poox2jeFWQETS3h-pmTCtFeItlTqFoyOWk4bl7reW5n5YWZRpYfDAcQXrJlSwJlXqzlZCiernEKpHCiyFmLAo3pCnQhNhhIpmIidhk+YKva6rlQRk9yKFTYAKqapUjj6PkeZJx76VH160ekP+h4wL6g7q3bC-83jLaWoY7petl8qlupGFhv1AQvClpDWGnT8pEDBlgMAbAxbCDIL2YAgDpYJZSxlnLXoxgYEPMkwtYzCgzPIBAMguFOQFNfnTYxozeBPPmZ82BgxMgtFGu4HgN0fGQ6T2noYQMCAUr4ePdFr9sWPOz37uRpeKWAx9nBgw56UM2OKpC1AMxiWspFZXiV9Laq0oaoY1ZMgPh9zidcTVsL3nfNyYtYFpTYEVPzRdTfKmGmJVAA) for the Chess Server Design:

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
