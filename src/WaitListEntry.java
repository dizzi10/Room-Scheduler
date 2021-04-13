
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
public class WaitListEntry extends Entry {
    
// Unused Class that was meant for displaying database info to GUI, I decided to go with a TableModel instead.

    private String faculty;
    private Date date;
    private int seats;
    private Timestamp currentTimeStamp;
    

    public WaitListEntry (String faculty, Date date, int seats, Timestamp currentTimeStamp){
        
        super(seats);
        this.faculty = faculty;
        this.date = date;
        this.currentTimeStamp = currentTimeStamp;
    }
 
    public void setFaculty(String faculty){
        this.faculty = faculty;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public void setTimestamp(Timestamp currentTimeStamp){
        this.currentTimeStamp = currentTimeStamp;
    }         
    public String getFaculty(){
        return faculty;
    }
    public Date getDate(){
        return date;
    }
 
    public Timestamp getTimestamp(){
        return currentTimeStamp;
    }
    
    @Override
    public String toString(){
        return getFaculty() + " - " + getDate();
    }
}

