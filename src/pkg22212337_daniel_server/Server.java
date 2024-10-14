/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pkg22212337_daniel_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Daniel García
 * 12/10/2024
 */
public class Server {
    private static ServerSocket servSock;
    private static final int PORT = 1234;
    private static int clientConnections = 0;
    private static final List<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Server listening at port " + PORT);
        try 
        {
            servSock = new ServerSocket(PORT);      //Step 1.
        }
        catch(IOException e) 
        {
             System.out.println("Unable to attach to port" + PORT);
             System.exit(1);
        }
        do 
        {
             run();
        }while (true);
    }

    private static void run()
    {
        Socket socket = null;
        try 
        {
            socket = servSock.accept();
            clientConnections++;
            String clientName = "Client " + clientConnections;
            Runnable cthread = new ClientThread(clientName, socket);
            Thread t = new Thread(cthread);
            t.start();
            System.out.println("Connections: " + clientConnections);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static List<Task> getTaskList() {
        return taskList;
    }
}
