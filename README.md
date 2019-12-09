# Social-Networking-Site
A website where users have the ability to add posts, comments and view other people posts.

## Project under development


### Rest endpoints for testing

**User**
----
 Show all users or register new user.

* **Endpoints**

 See all users -  |`GET`| - /users <br />
 Register new user - |`POST`| - /register
 
 * **Post params:** <br />
  `firstName=[String]` <br />
  `lastName=[String]` <br />
  `email=[String]` <br />
  `password=[String]`

 
**Post**
----
 Add new post while logged in.

* **Endpoints**

 Add new post - |`POST`| - /main/post
 
 * **Data param:** <br />
  `body=[String]`

