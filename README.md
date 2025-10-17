# OrderNet

## Application

This application has been created for practice purposes,
to build a simple microservice architecture with a focus on ideas from Domain-Driven Design.

Here, concepts like Value Objects were introduced to keep the domain as clean and consistent as possible. This ensures
that interactions between services stay context-bound, so terms always could remain clear and unambiguous.

For example, creating an ID is not just about using a string that stays tied to its context.
It also checks whether the value meets the business rules of the domain, for instance, whether the ID has
a valid pattern.

On top of that, ID's can also be compared with each other to see if their values are equal.

***Note:***

For this project, I did use AI tools like ChatGPT and Claude in situations where I got stuck. I believe that AI is can 
be great, as long as you understand the output of the prompt. 
However, that does not mean that I blindly trusted every output that an AI tool has given me since it is well known 
that the given answers can contain mistakes as well. For this reason, I think it is important to remain secured when it 
comes to coding and that is also something I did all the time by setting up this project.

Also, this project is not perfect and it probably never will. But it does not have to be in my opinion. 
It is still a nice playground where I can practice.

## Author

|                  |                            |
|:-----------------|:---------------------------|
| Name:            | Yannick Coolen             |
| Role:            | Student Software Developer |
| Type of project: | Hobby project              |

## TechStack

- Java Spring Boot
- Docker
- NoSQL
    - MongoDB
    - NEO4J
- Testing
    - Junit
    - Mockito
