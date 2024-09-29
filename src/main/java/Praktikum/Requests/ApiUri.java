package Praktikum.Requests;

import io.restassured.RestAssured;

import static Praktikum.Consants.ApiRequestUri.API_REQUEST_URI;

public class ApiUri {
    public void setUpUri(){
        RestAssured.baseURI = API_REQUEST_URI;
    }
}
