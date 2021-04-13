/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
public class Entry {
        
    private String room;
    private int seats;
    
    public Entry(int seats){
        
        setSeats(seats);
    }

    public void setSeats(int seats){
        this.seats = seats;
    }
   
    public int getSeats(){
        return seats;
    }
    
}
