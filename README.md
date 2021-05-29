# Covid Updates API
- A simple application so generate Country Wise Covid-19. With this application we can also send custom email to the users.
- The following project is implemented using Java EE/ CDI framework / JAX-RS using jdk 11.

## Getting Started ##
- Clone the project in your system
- Make sure you have docker installed to start the application
- After cloning, open a terminal, go to the root folder of the project and perform the
  following commands.

    1. docker image build -t covidupdates_image .
    2. docker run -p 8080:8080 covidupdates_image

  The first command will build a docker image that consists of jar file of the project. The
  second command will run the image and it can be accessed locally in port 8080. localhost:8080


# APIs #

## Get Countries ##
- This API will provide available countries list. Country ISO2 field will be required to fetch covid-19 report.

### Resource URL ###
```
/v1/covidupdates/countries
```
### Method ####

```HTTP GET```

#### Response ####

```json
{
  "code": "0",
  "message": "SUCCESS",
  "data": [
    {
      "name": "Cyprus",
      "iso2": "CY"
    },
    {
      "name": "Denmark",
      "iso2": "DK"
    },
    {
      "name": "Dominican Republic",
      "iso2": "DO"
    },
    {
      "name": "Nepal",
      "iso2": "NP"
    },
    .....
  ]
}
```

#### Response parameters ####

| Name  |Description |Possible values |
| ------------- | ------------- | -----------|
| code  | 0 if success response  |  |
| message  | SUCCESS |  |
| data  | List of CountryInfo |  |

##### CountryInfo parameters #####
| Name  |Description |Type |
| ------------- | ------------- | -----------|
| name  | Country name  | Text |
| iso2  | Country ISO2 code | Text (2 length) |

## Get day wise covid-19 report of a county ##
- This API will return day wise covid-19 reports for a country from the first recorded case.

### Resource URL ###
```
/v1/covidupdates/country/{countryISO2}
```
### Method ####

```HTTP GET```

### Path parameter ####

  - countryISO2 : Iso2 code of a country from above <b>Get Countries API<b>.

#### Response ####

```json
{
  "code": "0",
  "message": "SUCCESS",
  "data": [
    ....
    {
            "countryName": "Nepal",
            "confirmed": 268948,
            "deaths": 1986,
            "recovered": 263348,
            "active": 3614,
            "confirmedIncrementRate": 0.11,
            "deathsRate": 0.74,
            "recoveredRate": 97.92,
            "activeRate": 1.34,
            "date": "2021-01-22T00:00:00Z"
        },
        {
            "countryName": "Nepal",
            "confirmed": 269180,
            "deaths": 1994,
            "recovered": 263734,
            "active": 3452,
            "confirmedIncrementRate": 0.09,
            "deathsRate": 0.74,
            "recoveredRate": 97.98,
            "activeRate": 1.28,
            "date": "2021-01-23T00:00:00Z"
        },
    ....
  ]
}
```
#### Response parameters ####

| Name  |Description |Possible values |
| ------------- | ------------- | -----------|
| code  | 0 if success response  |  |
| message  | SUCCESS |  |
| data  | List of DailyCovidReportItem |  |

##### DailyCovidReportItem parameters #####
| Name  |Description |Type |
| ------------- | ------------- | -----------|
| countryName  | Country name  | Text |
| iso2  | Country ISO2 code | Text (2 length) |
| confirmed  | Number of confirmed cases | Number |
| deaths  | Number of deaths | Number |
| recovered  | Number of recovered | Number |
| active  | Number of active cases | Number |
| confirmedIncrementRate  | Confirmed cases increment rate from last day | Number |
| deathsRate  | Deaths percentage on day's confirmed case | Number |
| recoveredRate  | Recovered percentage on day's confirmed case | Number |
| activeRate  | Active cases percentage on day's confirmed case | Number |
| date  | Date | Text (ISO-8601 date) |

#### Error codes ####

| code  |message |
| ------------- | ------------- |
| CVD003  | Country iso2 is missing.  |
| CVD003  | Country iso2 is invalid.  |
| CVD002  | Could not get country wise covid info from external service. Or Error message return by external service  |

#### API Description ####

- Input Data is validated in the beginning.
- Fetch the day wise covid info from [external public API](https://api.covid19api.com/dayone/country/{countryCode})
- Calculate additional report fields like rates.
- Build and send the daily covid report in response.

## Send email to system users ##
- This API will send email to system users who have valid emails.

### Resource URL ###
```
/v1/users/email
```
### Method ####

```HTTP POST```

#### Request body ####

```json
{
    "subject":"Greeting",
    "content":"Hi there, \nHow are you doing? \nThank you",
    "count":50
}
```
#### Request parameters ####

| Name  |Description | Type | Default |
| ------------- | ------------- | -----------| -----------|
| subject  | Email subject  | text (998 max length) | |
| content  | Email body content | text (4000 max length)  | |
| count (Optional)  | Number of users to send emails | number (max 50)  | 50  |


#### Response ####

```json
{
  "code": "0",
  "message": "SUCCESS",
  "data": {
    "usersWithValidEmail": [
      {
        "name": "user1169",
        "email": "test169@test.com"
      },
      {
        "name": "user1168",
        "email": "test168@test.com"
      }
    ],
    "usersWithInvalidEmail": [
      {
        "name": "user1172",
        "email": "test@172@test.com"
      }
    ]
  }
}
```

#### Response parameters ####

| Name  |Description |Possible values |
| ------------- | ------------- | -----------|
| code  | 0 if success response  |  |
| message  | SUCCESS |  |
| data.usersWithValidEmail  | List of UserBasicInfo for whom we send email successfully |  |
| data.usersWithInvalidEmail  | List of UserBasicInfo who have invalid emails |  |

##### UserBasicInfo parameters #####
| Name  |Description |Type |
| ------------- | ------------- | -----------|
| name  | Name of an user  | Text |
| email  | Email address of an user | Text |

#### Error codes ####

| code  |message |
| ------------- | ------------- |
| URS001  | Email subject is missing.  |
| URS002  | Email body content is missing.  |
| URS009  | Count exceeded max limit.  |
| URS010  | Email subject exceeded max length limit.  |
| URS011  | Message exceeded max length limit.  |
| URS004  | Could not found users. |
| URS003  | Could not send email. |

## Get Users ##
- This API will provide all users registered in system.

### Resource URL ###
```
/v1/users
```
### Method ####

```HTTP GET```

#### Response ####

```json
{
  "code": "0",
  "message": "SUCCESS",
  "data": [
    {
      "id": 70,
      "name": "user1172",
      "email": "test@172@test.com",
      "isEmailValid": false
    },
    {
      "id": 69,
      "name": "user1169",
      "email": "test169@test.com",
      "isEmailValid": true
    },
    ...
  ]
}
```
#### Response parameters ####

| Name  |Description |Possible values |
| ------------- | ------------- | -----------|
| code  | 0 if success response  |  |
| message  | SUCCESS |  |
| data  | List of UserInfo | |

##### UserInfo parameters #####
| Name  |Description |Type |
| ------------- | ------------- | -----------|
| id  | Id of an user  | Long |
| name  | Name of an user  | Text |
| email  | Email address of an user | Text |
| isEmailValid  | Flag to indicate email is valid or not | Boolean |

## Add a user to system ##
- This API will add a user to system.

### Resource URL ###
```
/v1/users/
```
### Method ####

```HTTP POST```

#### Request body ####

```json
{
  "name":"Sunil",
  "email":"sunil15poudel@gmail.com"
}
```
#### Request parameters ####

| Name  |Description | Type | Validation Regex |
| ------------- | ------------- | -----------| -----------|
| name  | Name of a user  | text | |
| email  | Email of a user | text | ^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$ |


#### Response ####

```json
{
    "code": "0",
    "message": "SUCCESS",
    "data": {
        "id": 1622281558701,
        "name": "Sunil",
        "email": "sunil15poudel@gmail.com",
        "isEmailValid": true
    }
}
```
#### Response parameters ####

| Name  |Description |Possible values |
| ------------- | ------------- | -----------|
| code  | 0 if success response  |  |
| message  | SUCCESS |  |
| data  | UserInfo | |

##### UserInfo parameters #####
| Name  |Description |Type |
| ------------- | ------------- | -----------|
| id  | Id of an user  | Long |
| name  | Name of an user  | Text |
| email  | Email address of an user | Text |
| isEmailValid  | Flag to indicate email is valid or not | Boolean |

#### Error codes ####

| code  |message |
| ------------- | ------------- |
| URS005  | Email is missing.  |
| URS006  | Email is invalid.  |
| URS007  | Name is missing.  |
| URS008  | Could not add user.  |


## Description ##

- Input Data is validated in the beginning.
- Used HttpClient from JavaEE to perform external API call.
- Used JavaMail API for sending emails.
- Used JUnit and Mockito for unit test.
- 100% test coverage is maintained for all the code that consists of business logic. 

## Packages ##

There are four high level packages
  1. rest:  Package that contains end point of api
  2. lib: Library files
  3. repositories: Main business logic of the project
      1. impl: Main business logic implementation.
      2. dtos: Object files related to the project.  
      3. constants: Files that contains the app constants.
      4. services.covid19: Contains game services external api call logic implementation,
      4. services.mailclient.impl: App specific implementation of HttpClient.
      5. dao: App database related operation files.
      6. dao.entites: Java Object that will match to the database table schema.


## Code Description

- RestApplication | PingResource | CovidUpdatesResource | UserResource
  -  Rest endpoint is located in these files.
- CovidUpdatesRepository | UserRepository
  - This file contains main usecase of our file
- Covid19APIService
  - This file contains a function to get countries list and country wise covid19 info from [external public API](https://api.covid19api.com/dayone/country/{countryCode}).
- MailSender
  - This file contains utils functions for HTTP API call.
- UserDao
  - This file contains functions to perform read and write to the user database.