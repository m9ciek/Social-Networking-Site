# Social Networking Site
> A rest backend for a website that allows users to network with each other, 
share posts, upload images and add comments.

Java 11, Spring Framework, Spring Boot, Spring Security, Spring Data, Hibernate, MySQL, JWT

Tests: JUnit 5 + Mockito

## Setup 
* Build project with maven: `mvn package install`
* Run the app using docker-compose file: `docker-compose up`

## Api testing
* Api Documentation - default port 8080: `localhost:8080/swagger-ui.html`

### Open Endpoints
Open endpoints require no Authentication.
* Login: `POST /login`
* Register: `POST /register`

### Endpoints that require Authentication

Closed endpoints require a valid JWT Token to be included in the header of the
request. A Token can be acquired from the Login view above.

#### User
* Get all users: `GET /users`
* Get user: `GET /users/{id}`
* Delete user: `DELETE /users/{id}`
* Get users with posts `GET /users/posts`

#### Post
* Get all posts: `GET /posts`
* Get post by id: `GET /posts/{id}`
* Delete post by id: `DELETE /posts/{id}`
* Add new post: `POST /posts`
* Update post: `PUT /posts`
* Get posts for user: `GET /posts/user/{userId}`

#### Comment
* Add new comment to certain post: `POST /comments/post/{postId}`
* Get comments for certain post: `GET /comments/post/{postId}`
* Get comment by id: `GET /comments/{id}`

