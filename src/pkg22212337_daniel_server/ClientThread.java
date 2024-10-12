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
import java.util.ArrayList;

/**
 * @author Daniel García
 * 12/10/2024
 */
public class ClientThread implements Runnable{
    String name;
    Socket link = null;
    private ArrayList<Task> taskList = new ArrayList<Task>();
    
    public ClientThread(String name, Socket link){
        this.name = name;
        this.link = link;
    }
    
    @Override
    public void run(){
        String message = "";
        try 
        {
            while(!message.equalsIgnoreCase("STOP")){
                BufferedReader in = new BufferedReader( new InputStreamReader(link.getInputStream()));
            
                //Receive message from the client
                message = in.readLine();

                //Message is add
                addMethod(message);
                //Message is list
            }
         }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally 
        {
           try {
                System.out.println("\n* Closing connection... *");
                link.close();
            }
           catch(IOException e)
           {
                System.out.println("Unable to disconnect!");
                System.exit(1);
           }
        }
    }
    
    public void addMethod(String message){
        try{
            PrintWriter out = new PrintWriter(link.getOutputStream(),true);
            //Receives the message
            System.out.println("The message in server: " + message);
            out.println("Echo Message for client: " + message); 
        }catch(IOException e){
            System.out.println("Error when printing message: " + e.getMessage());
        }
        
    }
}
