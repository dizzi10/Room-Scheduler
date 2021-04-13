
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
public class RoomEntry extends Entry {
    
    private String room;
    private int seats;
    
    public RoomEntry(String room, int seats){
        
        super(seats);
        setRoom(room);
       
    }

    public void setRoom(String room){
        this.room = room;
    }
    public String getRoom(){
        return room;
    }
    
    @Override
    public String toString(){
        return "Room " + getRoom() + (" - ") + getSeats() +(" seats.");
        
    }
}
