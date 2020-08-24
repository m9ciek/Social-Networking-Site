# Social Networking Site
> A rest backend for a website that allows users to network with each other, 
share posts, upload images and add comments, project is still under development.

Java 11 + Spring Framework + Spring Boot + Spring Security + Spring Data + Hibernate + MySQL + Lombok + JWT

Tests: JUnit 5 + Mockito

## Setup 
* Navigate to project root: `social-networking-site` by default
* Build project with maven: `mvn package install`
* Build docker image with: `docker-compose build`
* Run the app using docker-compose file: `docker-compose up`

## Features
* Register your social account
* Share your favourite images and ideas with others
* Find new friends by adding them to your follow list
* Share your opinions in the comments
#### To Do
* Like/Dislike system
* Embed videos/songs

## Api testing
* The app uses Swagger for documentation - default port 8080: `localhost:8080/swagger-ui.html`

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
* Update user `PUT /users/{id}`

#### Post
* Get all posts: `GET /posts`
* Get post by id: `GET /posts/{id}`
* Delete post by id: `DELETE /posts/{id}`
* Add new post: `POST /posts` - you can include an image as a MultiPart File in request body.
* Update post: `PUT /posts`
* Get posts for user: `GET /posts/user/{userId}`

#### Comment
* Add new comment to certain post: `POST /comments/post/{postId}`
* Get comments for certain post: `GET /comments/post/{postId}`
* Get comment by id: `GET /comments/{id}`

