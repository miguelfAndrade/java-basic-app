# Website Title Scraper

Basic Java web app develop with Spring Boot, Maven, MongoDB and Docker.

It's a web app that takes an url and retrives the title of that web page.  
It just serves as a training ground for the tecnologies used.

Attention to the mvnw file, it must have LF line endings.

If you are using Linux, you need to change the `Dockerfile` and `docker-compose.yml`.
Add `RUN chmod u+x ./mvnw` before the line 9 in the `Dockerfile`.
Change the the line 9 of the `docker-compose.yml` to this `sh -c "chmod u+x ./mvnw && ./mvnw spring-boot:run"`

To run the app you need to have Docker and Docker Compose installed. Run `docker-compose up` on the terminal.

You have to create a new database in mongoDB. 
Go to the `http://localhost:8080` and create a new Database with the name **url-site-db**.

Now you can go to the `http://localhost:3000` to test the app.
