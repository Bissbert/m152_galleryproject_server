# Gallery Project BE Server

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Bissbert_m152_galleryproject_server&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Bissbert_m152_galleryproject_server)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Bissbert_m152_galleryproject_server&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=Bissbert_m152_galleryproject_server)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Bissbert_m152_galleryproject_server&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=Bissbert_m152_galleryproject_serverr)


This project is the BE for the [gallery project in the module 152](https://github.com/Bissbert/m152_galleryproject_FE)

It is used to save and fetch images and the belonging data from and to a database.

There are implementations all CRUD operations for the image as well as a all metadata endpoint.
The list of images doesn't contain the images themself, these have to be fetched seperatatelly to optimize the loading speeds

## Example Usage Of Endpoints

```
### basic loading of images data
GET http://localhost:8080/images?page=0&size=9

### load with sort
GET http://localhost:8080/images?page=0&size=9&sort=height-desc

### load images data by id
GET http://localhost:8080/images?page=0&size=3&idList=1,2,3,4

### loading of image preview by id
GET http://localhost:8080/images/preview/1

### loading the full image by id
GET http://localhost:8080/images/1
```
