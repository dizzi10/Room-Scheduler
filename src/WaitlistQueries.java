
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class WaitlistQueries {
    
    private static Connection connection;

    
    private PreparedStatement addWaitlistEntry;
    private PreparedStatement deleteWaitlistEntry;
    private PreparedStatement getWaitlistByDate;
    private PreparedStatement getWaitlistByFaculty;
    private PreparedStatement getWaitlist;

    private ResultSet resultSet;
    
    
    
    public WaitlistQueries(){
        connection = DBConnection.getConnection();
        try
        {
            addWaitlistEntry = connection.prepareStatement("insert into waitlist (faculty, date, seats, timestamp) values (?, ?, ?, ?)");
            deleteWaitlistEntry = connection.prepareStatement("delete from waitlist where faculty = (?) and date = (?)");
            getWaitlist = connection.prepareStatement("select Faculty, Date, Seats, Timestamp from waitlist order by timestamp");
            getWaitlistByDate = connection.prepareStatement("select * from waitlist where date = (?)");   
            getWaitlistByFaculty = connection.prepareStatement("select Faculty, Date, Seats, Timestamp from waitlist where faculty = (?)");    
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        } 
    }
    
    public void addWaitlistEntry(String faculty, Date date, int seats, Timestamp timeStamp)
    {
        try
        {
            addWaitlistEntry.setString(1, faculty);
            addWaitlistEntry.setDate(2, date);
            addWaitlistEntry.setInt(3, seats);
            addWaitlistEntry.setTimestamp(4, timeStamp);
            addWaitlistEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    public void addWaitlistEntries(ArrayList<ReservationEntry> entries)
    {
        try
        {
            for (ReservationEntry entry : entries){
                addWaitlistEntry.setString(1, entry.getFaculty());
                addWaitlistEntry.setDate(2, entry.getDate());
                addWaitlistEntry.setInt(3, entry.getSeats());
                addWaitlistEntry.setTimestamp(4, entry.getTimestamp());
                addWaitlistEntry.executeUpdate(); 
            }

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public void deleteWaitlistEntry(String faculty, Date date)
    {
        try
        {
            deleteWaitlistEntry.setString(1, faculty);
            deleteWaitlistEntry.setDate(2, date);

            deleteWaitlistEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    public ArrayList<WaitListEntry> getWaitlist(){
         
        ArrayList<WaitListEntry> waitlistEntries = new ArrayList<WaitListEntry>();
        
        try
        {
            resultSet = getWaitlist.executeQuery();
            while(resultSet.next())
            {
                waitlistEntries.add(new WaitListEntry(resultSet.getString("faculty"), resultSet.getDate("date"), resultSet.getInt("seats"), resultSet.getTimestamp("timestamp")));
            }
         
        }
    
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return waitlistEntries;       
    }
    
    
    
    public WaitListEntry getWaitlistbyFaculty(String faculty){
        WaitListEntry entry = null;
        
        try
        {
            deleteWaitlistEntry.setString(1, faculty);
            resultSet = getWaitlistByFaculty.executeQuery();
            
            
            entry = new WaitListEntry(resultSet.getString("faculty"), resultSet.getDate("date"), resultSet.getInt("seats"), resultSet.getTimestamp("timestamp"));
            
         
        }
    
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return entry;       
    }
    
}

