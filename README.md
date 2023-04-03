<h1 align="center">Sample Book Store Management API Demo 📚</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
  <a href="https://github.com/MoamenZakaria/search-api" target="_blank">
    <img alt="Documentation" src="https://img.shields.io/badge/documentation-yes-brightgreen.svg" />
  </a>
  <a href="#" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg" />
  </a>
  </p>
  <p>
  <a href="#" target="_blank">
<img src="https://img.shields.io/badge/spring%20-%236DB33F.svg?&style=for-the-badge&logo=spring&logoColor=white"/>
  </a> 
   <a href="#" target="_blank">
<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>  </a>
   <a href="#" target="_blank">
<img src="https://img.shields.io/badge/H2-DB-blue?&style=for-the-badge&logo=mysql&logoColor=white"/> </a>   <a href="#" target="_blank">
<img src="https://img.shields.io/badge/Flyway-Migration-red?&style=for-the-badge&logo=mysql&logoColor=white"/> </a>


<a href="#" target="_blank">
<img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white"/>
</a> 
   <a href="#" target="_blank">
<img src="https://img.shields.io/badge/docker%20-%230db7ed.svg?&style=for-the-badge&logo=docker&logoColor=white"/>
</a>

   <a href="#" target="_blank">
<img src="https://img.shields.io/badge/Red%20Hat%20Open%20Shift-E00?logo=redhatopenshift&logoColor=fff&style=for-the-badge&logoColor=white"/>
</a>
</p>

# Sample Book Store Management API Demo 📚

This API enables users to manage books (CRUD), update books details, and checkout order with ability to apply promotions
on the applicable items.
## 🚀 Live API Demo @OpenShift
- **Health Check** : http://book-store-route-due2023-dev.apps.sandbox-m3.1530.p1.openshiftapps.com/book-store-service/actuator/health
- **Docs** : http://book-store-route-due2023-dev.apps.sandbox-m3.1530.p1.openshiftapps.com/book-store-service/swagger-ui.html
- **ListBook API** : http://book-store-route-due2023-dev.apps.sandbox-m3.1530.p1.openshiftapps.com/book-store-service/v1/books?page=1&size5

## ✨ Features

- Books Management
- Order Checkout
- Promo Code Redemption

## 🗒️ Tech Stack

Book Store API uses a number of open source projects to work properly:

* OpenJDK 17
* [Spring Boot 3](https://spring.io)
* Redhat OpenShift
* Docker 🐳
* Docker-compose 🐳
* Gradle
* H2 In-Mem DB (MySQL Mode)
* Flyway (schema & test data seeding)

# 🧾 TO-DO Checklist
- [x] Books CRUD Operation 
- [x] Checkout API with promo code support based on book type
- [x] Docker / Docker compose support & Onboard the app on OpenShift 
- [ ] Use JWT for APIs security

# 🧪 Testing

### Using Gradle

```sh
$ cd book-store-api
$ ./gradlew test
```

### Using Docker

```sh
PS : tests will be excueted automatcally as part of the deployment 
```

## ⚒️ Deployment

### Installation

Book Store API requires [JDK V17](https://openjdk.org/projects/jdk/17/) to run.

### Using Gradle

```sh
$ cd book-store-api
$ ./gradlew bootRun
```

### Using Docker 🐳🐳

Search-Api is very easy to install and deploy in a Docker container with all dependencies using docker compose or Docker
compose as follow.

```sh
cd book-store-api
docker build -t book-store-api 
docker run -p 9999:9999  book-store-api
```

Using Docker Compose

```sh
cd book-store-api
docker-compose up
```

### Verify Service Health using spring actuator.

```sh
http://127.0.0.1:9999/book-store-service/actuator/health
```
## Sample Usage

------------------------------------------------------------------------------------------

#### Checkout Order

<details>
 <summary><code>POST</code> <code><b>/v1/orders/checkout</b></code> </summary>

##### Request

```json
{
  "orderItems": [
    {
      "bookId":2,
      "quantity" : 1
    },
    {
      "bookId":1,
      "quantity" : 1
    }
  ],
  "promotionCode": "SPRING2023"
}
```

##### Responses

```json
{
  "code": "1",
  "message": "Success",
  "data": {
    "orderId": 1680540281037,
    "totalPrice": 27.98,
    "totalPriceAfterDiscount": 25.18,
    "discount": 2.80,
    "consumedPromotion": {
      "promotionCode": "SPRING2023",
      "discountPercentage": 10.0,
      "applicableOnBookType": "Fiction",
      "applicableOnBooksIds": [
        1,
        2
      ]
    }
  }
}
```
##### Example Checkout cURL

```bash
curl --location 'http://127.0.0.1:9999/book-store-service/v1/orders/checkout' \
  --header 'Content-Type: application/json' \
  --data '{
  "orderItems": [
    {
      "bookId": 2,
      "quantity": 1
    },
    {
      "bookId": 1,
      "quantity": 1
    }
  ],
  "promotionCode": "SPRING2023"
}'

```

</details>

------------------------------------------------------------------------------------------

## 📄 API Documentation

Once the Application is up and running started you can check Swagger docs using below URL.

```sh
 http://127.0.0.1:9999/book-store-service/swagger-ui.html
```

## Author

👤 **Moamen Zakaria**

* Github: [@MoamenZakaria](https://github.com/MoamenZakaria)
* LinkedIn: [@moamen1](https://linkedin.com/in/moamen1)

## Show your support

Give a ⭐️ if this example helped you!

## 🪪 License

Copyright © 2023 [Moamen Zakaria](https://github.com/MoamenZakaria)

This project is [MIT](https://opensource.org/licenses/MIT) licensed.
