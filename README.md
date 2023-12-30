# Customer Data Management Application

This is http basic auth protected Spring-boot Application which exposes Rest services to manage customer resources.

## Tech stack

       * Spring boot: 3.2.1
       * Java       : JDK 21
       * Lombok     : 1.18.30
       * Database   : H2
       * Maven      : 3.8 +

## How to run application locally

        * Download or clone from github repository
        * Open Command Prompt and Change directory (cd) to folder containing pom.xml
        * use command `**mvn clean install**` to build project and then use command `mvn spring-boot:run` your
          application will start at port 8081
        * Alternatively import or clone in Intellij and run main class

## How to run Application using Docker image

1.Pull docker image as below

```
docker pull sandeep745/customer-data-management:latest

```

2.Run docker command

   ```
docker run -p8081:8081 customer-data-management:latest

```

3.Access application swagger file using below url

```
http://localhost:8081/v1/customermanagement/api-docs.html
```

4.Provide basic auth details in Authorize field on swagger page and execute api calls.

## Note: Java 21 and latest maven is required .

## Request Details

        * Attached is postman collection under postman-collection directory download file and import collection in postman 
          incase you dont want to use it please refer below curl command.

1. Create/Add Customer
   ** sample request

```
{
    "customerFirstName": "jack",
    "customerLastName": "ryan",
    "age": 1,
    "customerEmailAddress": "sandeep.pandey6@gmail.com",
    "customerAddress": {
        "houseNumber": "12a",
        "street": "Potassium nitrate",
        "city": "ufo",
        "country": "Nl",
        "postalCode": "1234la"
    }
}
```

**Request

```
curl --location 'http://localhost:8081/v1/customermanagement/customers' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic Tm9uLVBlcnNvbmFsLUFjY291bnQ6S2E9MFo5TDRmODpS' \
--header 'Cookie: JSESSIONID=CB677EF87E3AE3F5DF26F0757DF51C7B' \
--data-raw '{
    "customerFirstName": "jack",
    "customerLastName": "ryan",
    "age": 1,
    "customerEmailAddress": "jack.ryan@dc.com",
    "customerAddress": {
        "houseNumber": "12a",
        "street": "Potassium nitrate",
        "city": "ufo",
        "country": "Nl",
        "postalCode": "1234la"
    }
}'
```

** Response

```
{
    "customerId": "eb4e00aa-3407-48b9-bc02-2d565374a9b3",
    "customerFirstName": "jack",
    "customerLastName": "ryan",
    "age": 1,
    "customerEmailAddress": "jack.ryan@dc.com",
    "customerAddress": {
        "houseNumber": "12a",
        "street": "Potassium nitrate",
        "city": "ufo",
        "country": "Nl",
        "postalCode": "1234la"
    },
    "createdDateTime": "2023-12-27T20:21:45.249236",
    "updatedDateTime": "2023-12-27T20:21:45.249266"
}
```

2 .Get Customers
** sample request

** Request

```
curl --location 'http://localhost:8081/v1/customermanagement/customers' \
--header 'Authorization: Basic Tm9uLVBlcnNvbmFsLUFjY291bnQ6S2E9MFo5TDRmODpS' \
--header 'Cookie: JSESSIONID=CB677EF87E3AE3F5DF26F0757DF51C7B'
```

** Response

```
[
    {
        "customerId": "43737d8a-278a-4a84-b38b-a5194fceb487",
        "customerFirstName": "jack",
        "customerLastName": "ryan",
        "age": 1,
        "customerEmailAddress": "sandeep.pandey6@gmail.com",
        "customerAddress": {
            "houseNumber": "12a",
            "street": "Potassium nitrate",
            "city": "ufo",
            "country": "Nl",
            "postalCode": "1234la"
        },
        "createdDateTime": "2023-12-27T20:18:13.150997",
        "updatedDateTime": "2023-12-27T20:18:13.151176"
    },
    {
        "customerId": "c94382c7-ad5f-4adb-b4e8-df045364cad2",
        "customerFirstName": "jack",
        "customerLastName": "ryan",
        "age": 1,
        "customerEmailAddress": "jack.ryan@dcmovie.com",
        "customerAddress": {
            "houseNumber": "12a",
            "street": "Potassium nitrate",
            "city": "ufo",
            "country": "Nl",
            "postalCode": "1234la"
        },
        "createdDateTime": "2023-12-27T20:20:30.943232",
        "updatedDateTime": "2023-12-27T20:20:30.943263"
    },
    {
        "customerId": "eb4e00aa-3407-48b9-bc02-2d565374a9b3",
        "customerFirstName": "jack",
        "customerLastName": "ryan",
        "age": 1,
        "customerEmailAddress": "jack.ryan@dc.com",
        "customerAddress": {
            "houseNumber": "12a",
            "street": "Potassium nitrate",
            "city": "ufo",
            "country": "Nl",
            "postalCode": "1234la"
        },
        "createdDateTime": "2023-12-27T20:21:45.249236",
        "updatedDateTime": "2023-12-27T20:21:45.249266"
    }
]
```

3. Get Customers by first and last name

```
curl --location 'http://localhost:8081/v1/customermanagement/customers?customerFirstName=jack&customerLastName=ryan' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic Tm9uLVBlcnNvbmFsLUFjY291bnQ6S2E9MFo5TDRmODpS' \
--header 'Cookie: JSESSIONID=310F00D41194F0046502390E7B47E60D' \
--data ''
```

4. Update Customers

```
curl --location --request PUT 'http://localhost:8081/v1/customermanagement/customers/43737d8a-278a-4a84-b38b-a5194fceb487' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic Tm9uLVBlcnNvbmFsLUFjY291bnQ6S2E9MFo5TDRmODpS' \
--header 'Cookie: JSESSIONID=310F00D41194F0046502390E7B47E60D' \
--data-raw '{
        "customerId": "43737d8a-278a-4a84-b38b-a5194fceb487",
        "customerFirstName": "jack",
        "customerLastName": "ryan",
        "age": 1,
        "customerEmailAddress": "jackryan-upadtemail@gmail.com",
        "customerAddress": {
            "houseNumber": "12a",
            "street": "Potassium nitrate1",
            "city": "ufo",
            "country": "Nl",
            "postalCode": "1234la"
        }
    }'
```

## Note:

** For easy testing and validation basic auth is shared in both postman and curl command if that doesnot work then refer
yaml file for credential and select basic auth while calling Api.
** basic http application so certificates not required to call api.