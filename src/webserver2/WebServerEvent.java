package webserver2;

import java.util.HashMap;

public class WebServerEvent {
    private String url;
    private HashMap<String, String> parameters = new HashMap<>();
    private WebServer source;

    public WebServerEvent(String url, HashMap<String, String> parameters, WebServer server) {
        this.url = url;
        this.parameters = parameters;
        this.source = server;
    }

    public HashMap<String, String> getParameters()
    {
        return parameters;
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
