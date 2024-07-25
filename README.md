# Estate

## Setup the DDB:
- Download MySQL command line client from your browser
- Set "root" as the password and username
- Copy the absolute path of `data.sql` which is inside `Back\src\main\resources` folder of this project
- In the MySQL CLI, enter: `source ` followed by the absolute path of `data.sql`

## Run the Front:
- Open a terminal inside `Front` folder
- Execute `npm install` to install the dependencies
- Then execute `npm run start` to run the front project

## Run the back:
- Past your keys inside `Back\src\main\resources\application.properties`
- Open a terminal inside `Back` folder
- Execute `mvn spring-boot:run` (If `mvn` is not recognized, follow instructions [here](https://www.baeldung.com/install-maven-on-windows-linux-mac))

You are now ready to go!

## Swagger documentation:
http://localhost:3001/swagger-ui/index.html