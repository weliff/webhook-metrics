package br.com.moip.webhook.model;

public class Webhook {

    private String url;
    private String status;

    public Webhook(String url, String status) {
        this.url = url;
        this.status = status;
        
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getStatus() {
        return status;
    }
    
    

}
