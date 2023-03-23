package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class WebServer {

    private OutputStream outputStream;
    private InputStream inputStream;
    private HashMap<String, WebServerEventListener> listeners = new HashMap<>();

    public void startServer()
    {
        try (ServerSocket serverSocket = new ServerSocket(80))
        {
            while (true)
            {
                Socket socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                Scanner scan = new Scanner(inputStream);
                String request = scan.nextLine();
                if (validRequest(request))
                {
                    fireWebServerEvent(parseString(request));
                }
                else
                {
                    inputStream.close();
                }
            }

        }
        catch (Exception err)
        {
            System.out.println("Oops!");
            System.out.println(err.getLocalizedMessage());
        }

    }

    public void fireWebServerEvent(String request)
    {
        if (listeners.get(request) != null)
        {
            listeners.get(request).handleWebServerEvent(new WebServerEvent(request, this));
        }
        else
        {
            System.out.println("No Match" + request);
        }
    }

    public void addWebServerEventListenter(WebServerEventListener webServerEventListener, String urlRequest)
    {
        this.listeners.put(urlRequest, webServerEventListener);
    }

    private boolean validRequest(String request)
    {
        return !request.contains("favicon");
    }

    private String parseString(String string)
    {
        string = string.substring(5, string.indexOf("HTTP"));
        string = string.trim();
        return string;

    }

    public void httpResponse(String http)
    {
        try
        {
            outputStream.write(http.getBytes());
            outputStream.close();
            inputStream.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            System.out.println("Yikes!");
            e.printStackTrace();
        }
    }
}
