# Renan Silva's - Posterr API
## Built with

<a href="https://spring.io/" target="_blank"><img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" target="_blank" ></a>
<a href="https://www.oracle.com/java/technologies/downloads" target="_blank"><img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white" target="_blank" ></a>
<a href="https://github.com/docker" target="_blank"><img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" target="_blank" ></a>
<a href="https://maven.apache.org/" target="_blank"><img src="https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white" target="_blank" ></a>
<a href="https://www.postgresql.org/" target="_blank"><img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" target="_blank" ></a>

# Running locally
### What you will need

* [Maven](https://maven.apache.org/)
* [Java 8](https://www.oracle.com/java/technologies/downloads/) 
* [Docker](https://github.com/docker)
* [docker-compose](https://github.com/docker/compose)

### Building and starting the application:
Navigate to the project's folder open the terminal and execute the commands bellow

After that, you're good to go. Run:
```sh
mvn clean package # Creates JAR file 
docker-compose up --build # Spin up all necessary containers
```
Now the app is up and running, check the [Postman Collection](./docs/postman/Postter%20-%20Collection.json) and the [Swagger/Openapi](./docs/openapi.yaml) to see which requests (with examples) you can perform.
# Critique
## Improvements
***Timeline service*** - Create a service to mount a timeline based on user social networking graph and preferences(This could be a scale factor). <br/>

***Separated User service*** - Create a specific service to handle the User resource/domain (This could be a scale factor). <br/>

***Database schema*** - Improve the database schema to a model more according to the demand and other features (like listing, sorting, searching and do other processing with the posts created). With a full overview of the project, I could define which option can perform better (single table for posts? a specific table for each type of post?)

***Better understanding of requirements*** - This can sound like a joke, or you might not believe it: I didn't have a Twitter account until I received this challenge - I've just signup - so some requirements could be more obvious to someone that had a Twitter account already.

***More kinds of tests from the [Test Pyramid](https://martinfowler.com/articles/practical-test-pyramid.html)*** - Create some integration tests


## Scaling
***If this project were to grow and have many users and posts, which parts do you think would fail first?***

The first thing that would break on high load is the application (stop responding), given the synchronous characteristics. If we reach the limit of available threads the app would start to queue requests until no memory is available. 

***In a real-life situation, what steps would you take to scale this product? What other types of technology and infrastructure might you need to use***

- It depends on the characteristics of the access. Let's suppose we now have a high load of reading in our post listing (read nature), we could use some in memory cache (e.g: Memcached) to store common read data and objects (Timelines, users etc.) .
If the gap is in the writing capacities I would study some async approaches to distribute this API writing process. We could use some Pub/Sub or other async architecture structure to answer the clients when they call the service but do the 'hard computing' operation after. So we could have a topic where all posts can be sent and a subscription that reads this topic and persist asynchronously.
In either situation, the easiest solution would be separate the services and scale them individually
- Isolate services per domain or business function (e.g: Separated user service, timeline service etc.)
- I would create a [BFF](https://medium.com/mobilepeople/backend-for-frontend-pattern-why-you-need-to-know-it-46f94ce420b0) or another aggregation/gateway layer to segregate the contract according to the kind of client that is requesting the resources (Web, Mobile, Desktop) to be more concise about the data that we're returning. For example, with separated services, we could asynchronously aggregate some service calls (let's say Users and Posts) before we give a response.<br/> This kind of architecture approach can help us leverage from cloud horizontally scaling ***AND*** individual service scaling. Let's say we're having high latency on the UserService but not in the PostService: we could easily scale them, separately, besides the high gain in observability.
  Of course this approach has drawbacks too, the first one is that microservices can be very expensive to maintain in a cloud environment  

Made with ❤ by [rpsilva](https://www.linkedin.com/in/renan-silva-06018a104/?locale=en_US)
<div>  
  <a href = "mailto:rpedrodasilva10@gmail.com"><img src="https://img.shields.io/badge/-Gmail-%23333?style=for-the-badge&logo=gmail&logoColor=white" target="_blank"></a>
  <a href="https://www.linkedin.com/in/renan-silva-06018a104/?locale=en_US" target="_blank"><img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" target="_blank"></a>  
</div>
