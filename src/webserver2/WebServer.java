package webserver2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class WebServer {

    private OutputStream outputStream;
    private InputStream inputStream;
    private ArrayList<WebServerEventListener> listeners = new ArrayList<>();

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
        System.out.println("Request String: " + request);
        System.out.println("URL: " + getURL(request));

        for (WebServerEventListener listener : listeners)
        {
            listener.handleWebServerEvent(new WebServerEvent(getURL(request), getParams(request), this));
        }
    }

    private String getURL(String request)
    {
        request = request.substring(0, request.indexOf('?'));
        return request;
    }

    private HashMap<String, String> getParams(String request)
    {
        request = request.substring(request.indexOf('?') + 1);
        String[] params = request.split("&");
        HashMap<String, String> parameters = new HashMap<>();
        for (String param : params)
        {
            String key = param.substring(0, param.indexOf("="));
            key = key.trim();
            String value = param.substring(param.indexOf("=") + 1);
            value = value.trim();
            System.out.println("Key:" + key + "--Value:" + value);
            parameters.put(key, value);
        }
        return parameters;
    }

    public void addWebServerEventListenter(WebServerEventListener webServerEventListener, String urlRequest)
    {
        this.listeners.add(webServerEventListener);
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
