/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pkg22212337_daniel_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Daniel García
 * 12/10/2024
 */
public class ClientThread implements Runnable{
    String name;
    Socket socket = null;
    PrintWriter out;
    
    public ClientThread(String name, Socket socket){
        this.name = name;
        this.socket = socket;
        //Create global printwriter for the app
        try{
            out = new PrintWriter(socket.getOutputStream(),true);
        }catch(IOException e){
            System.out.println("Error in PrintWriter: " + e.getMessage());
        }
    }
    
    @Override
    public void run(){
        String message = "";
        try
        {
            //keep communications server-client until STOP request
            while(!message.equalsIgnoreCase("STOP")){
                BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
                message = in.readLine();
                //Split the string in parts (separated by commas)
                String[] strSplit = message.split(",");
                if(strSplit.length > 1){
                    //Check if message contains parts, if not it means is STOP
                    switch(strSplit[0].toUpperCase()){
                        case "ADD":
                            //Position 1 of string is description and 2 is date
                            add(strSplit[1], strSplit[2]); 
                            break;
                        case "LIST":
                            list(strSplit[1]);
                            break;
                    }
                }else{
                    out.println("Closing: " + name);
                }
            }
         }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally 
        {
           try {
                System.out.println("Closing connection for " + name);
                socket.close();
            }
           catch(IOException e)
           {
                System.out.println("Unable to disconnect!");
                System.exit(1);
           }
        }
    }
    
    public void add(String tDescription, String tDate){
        //Use of synchronization for the server list
        synchronized(Server.getTaskList()){
            //Create new task and add it to the list
            Server.getTaskList().add(new Task(tDescription, tDate));
            System.out.println("New Task added by " + name);
            
            //Retrieve tasks on that day
            list(tDate);
        }
        
    }
    
    public void list(String tDate){
        //Convert date to String to add it to the message
        String message = tDate + ": ";
        synchronized(Server.getTaskList()){
            boolean dateFound = false;
            message += "Elements in list: " + Server.getTaskList().size();
            for (int i = 0; i<Server.getTaskList().size(); i++) {
                Task t = Server.getTaskList().get(i);
                if(t.getDate().equals(tDate)){
                    dateFound = true;
                    //Check if its last position to add separator
                    if(i == Server.getTaskList().size()-1){
                        message += t.getDescription();
                    }else{
                        message += t.getDescription() + "; ";
                    }
                }
            }
            if(!dateFound){
                message = "No tasks found for that date";
            }
        }
        out.println(message);
    }
}
