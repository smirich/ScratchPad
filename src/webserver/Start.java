package webserver;

public class Start {
    public static void main(String[] args)
    {
        WebServer server = new WebServer();
        server.addWebServerEventListenter(new WebServerEventListener() {

            @Override
            public void handleWebServerEvent(WebServerEvent webServerEvent)
            {
                System.out.println("Handled!");
                webServerEvent.getSource().httpResponse("Yay!");
            }

        }, "test");

        server.startServer();
        System.out.println("Done!");
    }
}

/*
 * 
 * I think maybe get rid of the url distinction...just have the server parse the url into url and params and fire all that information in the event object as a hashmap...or have separate addlisteners maybe...like addWebServerOrderCookieEventListener and addWebServerDisplayOrdersEventListener etc  - probably the first one
 * 
 */
