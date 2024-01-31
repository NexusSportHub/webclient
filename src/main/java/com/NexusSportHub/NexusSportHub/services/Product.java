package com.NexusSportHub.NexusSportHub.services;

import java.util.Date;

public class Product {

    // Creamos las variables que vamos a usar para almacenar los datos y enviarlo a
    // la mongo, es importante que tengan el mismo nombre y el mismo tipo de dato
    // que los definidos en el proyecto externo
    private String userId;
    private String apiUrl;
    private String path;
    private Boolean status;
    private Date date;
    private Date paidDate;
    private String ApiResponse;
    private String paymentMethod;

    // Constructor vacío
    public Product() {

    }

    // Getters y Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public String getApiResponse() {
        return ApiResponse;
    }

    public void setApiResponse(String ApiResponse) {
        this.ApiResponse = ApiResponse;
    }

    public String getpaymentMethod() {
        return paymentMethod;
    }

    public void setpaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
