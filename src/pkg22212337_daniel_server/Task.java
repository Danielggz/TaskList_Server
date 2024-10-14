/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pkg22212337_daniel_server;

import java.text.SimpleDateFormat;

/**
 * @author Daniel García
 * 12/10/2024
 */
public class Task {
    private String name, description;
    private SimpleDateFormat date;

    public Task(String name, String description, SimpleDateFormat date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }
    
    
}
