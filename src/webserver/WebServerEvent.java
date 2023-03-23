package webserver;

public class WebServerEvent {
    private String url;
    private WebServer source;

    public WebServerEvent(String url, WebServer server) {
        this.url = url;
        this.source = server;
    }

    public String getUrl()
    {
        return url;
    }

    public WebServer getSource()
    {
        return source;
    }

}
