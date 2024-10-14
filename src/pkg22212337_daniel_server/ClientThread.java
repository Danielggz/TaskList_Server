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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author Daniel García
 * 12/10/2024
 */
public class ClientThread implements Runnable{
    String name;
    Socket socket = null;
    private ArrayList<Task> taskList = new ArrayList<Task>();
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
                            add(strSplit[1], strSplit[2], strSplit[3]); 
                            break;
                        case "LIST":
                            list(message);
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
                System.out.println("\n Closing connection for " + name);
                socket.close();
            }
           catch(IOException e)
           {
                System.out.println("Unable to disconnect!");
                System.exit(1);
           }
        }
    }
    
    public void add(String tName, String tDescription, String tDate){
        synchronized(taskList){
            //Create new task and add it to the list
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try{
                sdf.parse(tDate);
            }catch(ParseException pe){
                System.out.println(tDate + " is not a valid date");
            }
            taskList.add(new Task(tName, tDescription, sdf));
            System.out.println("New Task added by " + name);
            
            //Retrieve tasks on that day
            out.println(getTasksByDate(sdf));
        }
        
    }
    
    public void list(String message){
        synchronized(taskList){
            //Receives the message
            System.out.println("The message from " + name + " in server: " + message);
            out.println("Echo Message for client: " + message); 
        }
    }
    
    public void stop(){
        try{
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            //Send stop request
            out.println("Closing..." + name); 
        }catch(IOException e){
            System.out.println("Error when printing message: " + e.getMessage());
        }
    }
    
    public String getTasksByDate(SimpleDateFormat date){
        String message = date + ": ";
        synchronized(taskList){
            for (int i = 0; i<taskList.size(); i++) {
                Task t = taskList.get(i);
                if(t.getDate().equals(date)){
                    //Check if its last position to add separator
                    if(i == taskList.size()-1){
                        message += t.getDescription();
                    }else{
                        message += t.getDescription() + "; ";
                    }
                }
            }
        }
        return message;
    }
}
