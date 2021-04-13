
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class RoomQueries {
    
    private static Connection connection;
    private PreparedStatement addRoom;
    private PreparedStatement dropRoom;
    private PreparedStatement getAllRooms;
    private ResultSet resultSet;
    

    public RoomQueries(){
        connection = DBConnection.getConnection();
        
        try
        {
            addRoom = connection.prepareStatement("insert into rooms (name, seats) values (?, ?)");
            dropRoom = connection.prepareStatement("DELETE FROM ROOMS WHERE name = ?");
            getAllRooms = connection.prepareStatement("select name, seats from rooms order by name");
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    public void addRoom(String room, int seats)
    {
        try
        {
            addRoom.setString(1, room);
            addRoom.setInt(2, seats);
            addRoom.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }   
    }
    public void dropRoom(String room)
    {
        try
        {   
            dropRoom.setString(1, room);
            dropRoom.executeUpdate();  
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    public ArrayList<RoomEntry> getAllRooms()
    {
        ArrayList<RoomEntry> rooms = new ArrayList<RoomEntry>();
        
        try
        {
            resultSet = getAllRooms.executeQuery();
            while(resultSet.next())
            {
                rooms.add(new RoomEntry(resultSet.getString(1), resultSet.getInt(2)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return rooms;  
    }
}
