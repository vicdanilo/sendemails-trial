# SEND-EMAILS

This is an API which provides an email sending service using two different email service providers which are SendGrid and Mailgun. The reason why two providers were used lies in the fact that in case one goes down the second will warranty the service is provided anyways. 

### Prerequisites

In order to run the API you need [Gradle](https://gradle.org/install/) and [Java JDK or JRE](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

### Running

Use gradle command to run the application 

```
gradle bootRun
```

And if you need to install the dependencies, then the following code can be used

```
gradle clean
```

## Important

Please change the respective key string  in both: **SendE_SG.java** and **SendE_MG.java**, and domain string in **SendE_MG.java**

You could also use the following to access your key

```
System.getenv("SENDGRID_API_KEY")
```

# Quick Start

## Send Request

You have to send hhtp post request to "http://localhost:8080/api/sendemail", the server handles json format, with the following json structure
```
{
    "from":"test@example.com",
    "to":"to1@example.com to2@example.com to3@example.com",
    "subject":"some text here",
    "text":"some text here",
    "cc":"cc1@example.com cc2@example.com",
    "bcc":"bcc1@example bcc2@example.com"
}
```

## Built With

* [Spring Boot](https://spring.io/) - The web framework used
* [Gradle](https://gradle.org/) - Dependency Management


## Error Handling

We expect that when sending the request, a valid structure with valid arguments is used. If you want to receive the exact error message you could uncomment the part in **SendEmailController.java**.