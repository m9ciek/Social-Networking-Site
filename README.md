# Social-Networking-Site
A website where users have the ability to add posts, comments and view other people posts.

## Project under development


### Rest endpoints for testing

**User**
----
 Show all users or register new user.

* **Endpoints**

 See all users -  |`GET`| - /users <br />
 Show welcome page with logged in user -  |`GET`| - /main <br />
 Register new user - |`POST`| - /register
 
 * **Post params:** <br />
  `firstName=[String]` <br />
  `lastName=[String]` <br />
  `email=[String]` <br />
  `password=[String]`

 
**Post**
----
 Add new post - must have an account and be logged in.

* **Endpoints**

 Add new post - |`POST`| - /main/post
 
 * **Post param:** <br />
  `body=[String]`<br />
  `image=[file/jpg/png]` - optional <br />

