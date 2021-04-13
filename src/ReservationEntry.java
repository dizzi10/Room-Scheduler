

import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
public class ReservationEntry  extends WaitListEntry  {
    
// Unused Class that was meant for displaying database info to GUI, I decided to go with a TableModel instead.
    
    private String faculty;
    private Date date;
    private String room;
    private int seats;
    private Timestamp currentTimeStamp;

    public ReservationEntry(String faculty, Date date, String room, int seats, Timestamp currentTimeStamp){
        
        super(faculty, date, seats, currentTimeStamp);
        this.room = room;
    } 
    public void setRoom(String room){
        this.room = room;
    }

    public String getRoom(){
        return room;
    }
 
}
