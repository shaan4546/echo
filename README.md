<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#setup">Setup</a></li>
        <li><a href="#test">Test</a></li>
        <li><a href="#run">Run</a></li>
      </ul>
    </li>
    <li>
      <a href="#usage">Usage</a>
      <ul>
        <li><a href="#examples">Examples</a></li>
      </ul>
    </li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project
* *Echo* or *server* is a **spring boot java project** to *mock endpoint* with **docker support**. The main purpose of Echo is to serve ephemeral/mock endpoints created with parameters specified by clients
* *Endpoints API*: a set of **secured** endpoints (`GET|POST|PATCH|DELETE /endpoints{/:id}`) designated to manage mock endpoints served by Echo.


<p align="right">(<a href="#readme-top">back to top</a>)</p>



## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* Docker
  ```sh
  https://docs.docker.com/desktop/install/mac-install/
  ```

### Setup

Below is an example of how you can instruct your audience on installing and setting up your app.

1. Clone the repo
   ```sh
   https://github.com/shaan4546/echo.git
   ```
<p align="right">(<a href="#readme-top">back to top</a>)</p>


### Test

To see test coverage of the app, follow the below instructions.

1. Build docker image with echo as tag and test as target configuration.
   ```sh
   docker build -t echo --target test .
   ```
2. Run docker image with echo as tag and exposed port as 8080
   ```sh
   docker run -p 8080:8080 -t echo 
   ```
<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Run

To run the app, follow the below instructions.

1. Build docker image with echo as tag and production as target configuration.
   ```sh
   docker build -t echo --target production .
   ```
2. Run docker image with echo as tag and exposed port as 8080
   ```sh
   docker run -p 8080:8080 -t echo 
   ```
<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Usage

This is an example of how to use the application once its up and running.

### Examples

*All endpoint APIs are secured by http basic auth. User need to give username and password (admin & admin) configured in application.properties file for accessing the APIs.*

<details>
  <summary>Create endpoint</summary>
  <markdown>

#### Request

    curl -u admin:admin --location --request POST 'http://localhost:8080/endpoints' --header 'Content-Type: application/json' --data-raw '{ "type": "endpoints", "attributes": { "verb": "GET", "path": "/greetings", "response": { "code": 200, "headers": {"Content-Type": "application/json"}, "body": "\"{ \"message\": \"Hello, world\" }\"" } } }'

#### Expected response

    HTTP/1.1 201 Created
    Content-Type: application/json

    {
       "id": "1",
       "type": "endpoints",
       "attributes": {
           "verb": "GET",
           "path": "/greetings",
           "response": {
               "code": 200,
               "headers": {
                   "Content-Type": "application/json"
               },
               "body": "\"{ \"message\": \"Hello, world\" }\""
           }
       }
    }
  </markdown>
</details>

<details>
  <summary>List endpoints</summary>
  <markdown>

#### Request

      curl -u admin:admin  --location --request GET 'http://localhost:8080/endpoints' 

#### Expected response

    HTTP/1.1 200 OK
    Content-Type: application/json

    [
       {
           "id": "1",
           "type": "endpoints",
           "attributes": {
               "verb": "GET",
               "path": "/greetings",
               "response": {
                   "code": 200,
                   "headers": {
                       "Content-Type": "application/json"
                   },
                   "body": "\"{ \"message\": \"Hello, world\" }\""
               }
           }
       }
    ]
  </markdown>
</details>
<details>
  <summary>Update endpoint</summary>
  <markdown>

#### Request

    curl -u admin:admin --location --request PATCH 'http://localhost:8080/endpoints/1' --header 'Content-Type: application/json' --data-raw '{ "id": 1, "type": "endpoints", "attributes": { "verb": "POST", "path": "/greetings", "response": { "code": 201, "headers": {}, "body": "\"{ \"message\": \"Hello, everyone\" }\"" } } }'


#### Expected response

    HTTP/1.1 200 OK
    Content-Type: application/json

    {
       "id": "1",
       "type": "endpoints",
       "attributes": {
           "verb": "POST",
           "path": "/greetings",
           "response": {
               "code": 201,
               "headers": {},
               "body": "\"{ \"message\": \"Hello, everyone\" }\""
           }
       }
    }
  </markdown>
</details>

<details>
  <summary>Delete endpoint</summary>
  <markdown>

#### Request

     curl -u admin:admin --location --request DELETE 'http://localhost:8080/endpoints/1'

#### Expected response

    HTTP/1.1 204 No Content
  </markdown>
</details>

<details>
  <summary>Error response</summary>
  <markdown>
In case client makes unexpected response or server encountered an internal problem, Echo should provide proper error response.

#### Request

    curl -u admin:admin --location --request DELETE 'http://localhost:8080/endpoints/1234567890'

#### Expected response

    HTTP/1.1 404 Not found
    Content-Type: application/json

    {
       "errors": [
           {
               "code": "404 NOT_FOUND",
               "details": "Requested Endpoint with ID 1234567890 does not exist"
           }
       ]
    }
  </markdown>
</details>

<details>
  <summary>Unauthorised access to endpoint API</summary>
  <markdown>

#### Request

    curl  --location --request GET 'http://localhost:8080/endpoints'


#### Expected response

    HTTP/1.1 410 Unauthorized
    Content-Type: application/json

    {
      "timestamp": "2023-04-04T12:02:47.051+00:00",
      "status": 401,
      "error": "Unauthorized",
      "path": "/endpoints"
    }
  </markdown>
</details>


<details open>
  <summary>Sample scenario</summary>
  <markdown>

#### 1. Client requests non-existing path

    curl --location --request GET 'http://localhost:8080/hello'

    HTTP/1.1 404 Not found
    Content-Type: application/json

    {
        "errors": [
            {
                "code": "404 NOT_FOUND",
                "details": "Requested page `/hello` does not exist"
            }
        ]
    }

#### 2. Client creates an endpoint

    curl -u admin:admin --location --request POST 'http://localhost:8080/endpoints' --header 'Content-Type: application/json' --data-raw '{ "type": "endpoints", "attributes": { "verb": "GET", "path": "/hello", "response": { "code": 200, "headers": {"Content-Type": "application/json"}, "body": "\"{ \"message\": \"Hello, world\" }\"" } } }'

    HTTP/1.1 201 Created
    Content-Type: application/json

    {
            "id": "1",
            "type": "endpoints",
            "attributes": {
                "verb": "GET",
                "path": "/hello",
                "response": {
                    "code": 200,
                    "headers": {
                        "Content-Type": "application/json"
                    },
                    "body": "\"{ \"message\": \"Hello, world\" }\""
                }
            }
        }
    }

#### 3. Client requests the recently created endpoint

    curl --location --request GET 'http://localhost:8080/hello'

    HTTP/1.1 200 OK
    Content-Type: application/json

    { "message": "Hello, world" }

#### 4. Client requests the endpoint on the same path, but with different HTTP verb

The server responds with HTTP 404 because only `GET /hello` endpoint is defined.

NOTE: if you could imagine different behavior from the server, feel free to propose it in your solution.

    > curl --location --request POST 'http://localhost:8080/hello'

    HTTP/1.1 404 Not found
    Content-Type: application/json

    {
        "errors": [
            {
                "code": "404 NOT_FOUND",
                "details": "Requested page `/hello` does not exist"
            }
        ]
    }

  </markdown>
</details>

<p align="right">(<a href="#readme-top">back to top</a>)</p>
