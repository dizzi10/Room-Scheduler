
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Timestamp;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
public class ReservationQueries {
    
    private static Connection connection;
    private PreparedStatement addReservationEntry;
    private PreparedStatement getAllReservations;
    private PreparedStatement getRoomsReservedByDate;
    private PreparedStatement deleteReservationEntry;
    private PreparedStatement getReservationsByFaculty;
    private PreparedStatement getReservations;
    private PreparedStatement getReservationsByRoom;
    
    
    private ResultSet resultSet;
    
    private ArrayList<RoomEntry> rooms;
    private ArrayList<RoomEntry> availableRooms;
    
    private RoomEntry foundRoom;
    private ReservationEntry entry;
    
    private ArrayList<String> reservedRooms;
    
    
    private RoomEntry bestFitRoom;
    private int requestedSeats;
    
    public RoomQueries roomQueries;
    
    public ReservationQueries(){
        connection = DBConnection.getConnection();
        try
        {
            
            getAllReservations = connection.prepareStatement("select room from reservations");
            addReservationEntry = connection.prepareStatement("insert into reservations (faculty, room, date, seats, timestamp) values (?, ?, ?, ?, ?)");
            getRoomsReservedByDate = connection.prepareStatement("select room from reservations where date = (?)");
            getReservationsByFaculty = connection.prepareStatement("select Faculty, Room, Date, Seats, Timestamp from reservations where faculty = (?)");  
            deleteReservationEntry = connection.prepareStatement("delete from reservations where faculty = (?) and date = (?) and room =(?)");
            getReservations = connection.prepareStatement("select Faculty, Room, Date, Seats, Timestamp from reservations");
            getReservationsByRoom = connection.prepareStatement("select Faculty, Room, Date, Seats, Timestamp from reservations where Room = (?)"); 
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        } 
    }
    
    public RoomEntry addReservationEntry(String faculty, Date date, int requestedSeats, Timestamp timeStamp)
    {
        this.requestedSeats = requestedSeats;
        foundRoom = findRoom(date);
        
        if (foundRoom.getRoom() != ""){
            try
            {
                addReservationEntry.setString(1, faculty);
                addReservationEntry.setString(2, bestFitRoom.getRoom());
                addReservationEntry.setDate(3, date);
                addReservationEntry.setInt(4, bestFitRoom.getSeats());
                addReservationEntry.setTimestamp(5, timeStamp);
                addReservationEntry.executeUpdate();
                
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        
        }
        
        return foundRoom;
    }
    public void deleteReservationEntry(String faculty, Date date, String room)
    {
        try
        {
            deleteReservationEntry.setString(1, faculty);
            deleteReservationEntry.setDate(2, date);
            deleteReservationEntry.setString(3,room);
            
            deleteReservationEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    public ArrayList<String> getRoomsReservedByDate(Date date)
    {
        reservedRooms = new ArrayList<String>();
        try
        {
            getRoomsReservedByDate.setDate(1, date);
            resultSet = getRoomsReservedByDate.executeQuery();
            
            while(resultSet.next())
            {
                reservedRooms.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return reservedRooms;
    } 
    public ArrayList<RoomEntry> getAvailableRooms()
    {
        availableRooms = new ArrayList<RoomEntry>();
        
        for (RoomEntry room : rooms){
            if (reservedRooms.contains(room.getRoom())){    
            }
            else{
                availableRooms.add(room);
            }
        }

        return availableRooms;   
    } 
    private RoomEntry findRoom(Date date){
        
        roomQueries = new RoomQueries();
        rooms = new ArrayList<RoomEntry>();
        rooms = roomQueries.getAllRooms(); 
        
        reservedRooms = getRoomsReservedByDate(date);
        
        availableRooms = getAvailableRooms();

        bestFitRoom = new RoomEntry("", 0);
        
        if (availableRooms.isEmpty()){
            
        }
        else{
            for (RoomEntry room : availableRooms){
                if (room.getSeats() < requestedSeats){
                    continue;
                }
                else{
                    if (reservedRooms.contains(room.getRoom())){
                        continue;
                    }
                    else{
                        if (bestFitRoom.getRoom() == ""){
                            bestFitRoom = room;
                        }
                        else if (bestFitRoom.getSeats() - requestedSeats > room.getSeats() - requestedSeats){ 
                            bestFitRoom = room;
                        }
                    }                
                }    
            }

        
        }
        return bestFitRoom;

    }
    public ReservationEntry getReservationsByFaculty(String faculty){
        try
        {
            getReservationsByFaculty.setString(1, faculty);
            resultSet = getReservationsByFaculty.executeQuery();
            
            
            entry = new ReservationEntry(resultSet.getString("faculty"), resultSet.getDate("date"), resultSet.getString("room"), resultSet.getInt("seats"), resultSet.getTimestamp("timestamp"));
 
        }
    
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return entry;           
   }
    public ArrayList<WaitListEntry> getReservations(){
        
        ArrayList<WaitListEntry> entryList = new ArrayList<>();

        try
        {
            resultSet = getReservations.executeQuery();
            
            while(resultSet.next())
            {
                entryList.add(new ReservationEntry(resultSet.getString("faculty"), resultSet.getDate("date"), resultSet.getString("room"), resultSet.getInt("seats"), resultSet.getTimestamp("timestamp")));
            }
         
        }
    
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return entryList;       
    }
    public ArrayList<ReservationEntry> getReservationsByRoom(String room){
        ArrayList<ReservationEntry> rooms = new ArrayList<>();
        try
        {
            getReservationsByRoom.setString(1, room);
            resultSet = getReservationsByRoom.executeQuery();
            while(resultSet.next())
            {
                rooms.add(new ReservationEntry(resultSet.getString("faculty"), resultSet.getDate("date"), resultSet.getString("room"), resultSet.getInt("seats"), resultSet.getTimestamp("timestamp")));
            }
         
        }
    
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        
        }
        return rooms;
    }
}




