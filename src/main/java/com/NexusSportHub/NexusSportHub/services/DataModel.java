package com.NexusSportHub.NexusSportHub.services;



    import java.util.Date;

public class DataModel {

    private String userId;
    private String apiUrl;
    private String path;
    private String status;
    private Date date;
    private Date paidDate;

    // Constructor vacío (puedes tener otros constructores según tus necesidades)
    public DataModel() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
}



