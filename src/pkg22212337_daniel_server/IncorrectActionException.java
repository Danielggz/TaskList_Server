/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pkg22212337_daniel_server;

/**
 * @author Daniel García
 * 12/10/2024
 */
public class IncorrectActionException extends Exception{
    private String message = "Action selected is not valid. Please select Add, List or Stop";

    public IncorrectActionException() {
    }

    public IncorrectActionException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
