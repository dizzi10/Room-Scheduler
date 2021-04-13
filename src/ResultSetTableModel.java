/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
import java.sql.Connection; 
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement; 
import java.sql.ResultSet; 
import java.sql.ResultSetMetaData; 
import java.sql.SQLException;  
import javax.swing.table.AbstractTableModel; 

public class ResultSetTableModel extends AbstractTableModel {
    
//Taken from the textbook
   
    private static Connection connection; 
    private Statement getAllData; 
    private PreparedStatement getSelectedData;
    private ResultSet resultSet; 
    private ResultSetMetaData metaData;
    private int numberOfRows;
    private boolean connectedToDatabase = false; 
     
    public ResultSetTableModel(String query) throws SQLException {
    
        connection = DBConnection.getConnection();
        getAllData = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        connectedToDatabase = true; 
        setQuery(query); 
        } 
   
    public ResultSetTableModel(String query, Date date) throws SQLException {
    
        connection = DBConnection.getConnection();
        getSelectedData = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        connectedToDatabase = true; 
        setQuery(query, date); 
        }
    
    public ResultSetTableModel(String query, String faculty) throws SQLException {
    
        connection = DBConnection.getConnection();
        getSelectedData = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        connectedToDatabase = true; 
        setQuery(query, faculty); 
        }
    
    public Class getColumnClass(int column) throws IllegalStateException{
    
        if (!connectedToDatabase) 
            throw new IllegalStateException("Not connected to Database");

        try {
            String className = metaData.getColumnClassName(column + 1);
            return Class.forName(className);

        }
        catch (Exception exception)
        {
            exception.printStackTrace(); 
        }
        return Object.class;

    }


    public int getColumnCount() throws IllegalStateException 
    {  
        if (!connectedToDatabase) 
        throw new IllegalStateException("Not Connected to Database"); 

        try
        {
            return metaData.getColumnCount() ;
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace(); 
        }
        return 0;
    }

    @Override
    public String getColumnName(int column) throws IllegalStateException 
    {
     
        if (!connectedToDatabase) 
        throw new IllegalStateException("Not Connected to Database"); 

        try
        {
            return metaData.getColumnName(column + 1);
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace(); 
        }
        
        return "";
    }
    @Override
    public int getRowCount() throws IllegalStateException 
    {
     
        if (!connectedToDatabase) 
        throw new IllegalStateException("Not Connected to Database");
        
        return numberOfRows;
    }

    @Override
    public Object getValueAt(int row, int column) throws IllegalStateException
    {
        if (!connectedToDatabase) 
            throw new IllegalStateException("Not Connected to Database");
    
        try{
            resultSet.absolute(row + 1);
            return resultSet.getObject(column + 1);
        }  
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace(); 
        }
        return "";
    }
    
    public void setQuery(String query) throws SQLException, IllegalStateException
    {
        if (!connectedToDatabase) 
            throw new IllegalStateException("Not Connected to Database");
    
        resultSet = getAllData.executeQuery(query); 
        metaData = resultSet.getMetaData(); 
        resultSet.last(); 
        numberOfRows = resultSet.getRow(); 
        fireTableStructureChanged(); 
    }
    
    public void setQuery(String query, Date date) throws SQLException, IllegalStateException
    {
        if (!connectedToDatabase) 
            throw new IllegalStateException("Not Connected to Database");
        
        getSelectedData = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        getSelectedData.setDate(1, date);
        
        resultSet = getSelectedData.executeQuery() ; 
        metaData = resultSet.getMetaData(); 
        resultSet.last(); 
        numberOfRows = resultSet.getRow(); 
        fireTableStructureChanged(); 
    }
    public void setQuery(String query, String faculty) throws SQLException, IllegalStateException
    {
        if (!connectedToDatabase) 
            throw new IllegalStateException("Not Connected to Database");
        
        getSelectedData = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        getSelectedData.setString(1, faculty);
        
        resultSet = getSelectedData.executeQuery() ; 
        metaData = resultSet.getMetaData(); 
        resultSet.last(); 
        numberOfRows = resultSet.getRow(); 
        fireTableStructureChanged(); 
    }

    public void disconnectFromDatabase() 
    {
    
        if (connectedToDatabase) 
            try
            {
                resultSet.close(); 
                getAllData.close();
                getSelectedData.close(); 
                connection.close();
            }

            catch (SQLException sqlException) 
            {
                sqlException.printStackTrace(); 
            }
            finally
            {
                connectedToDatabase = false;
            }
    }
}

    




