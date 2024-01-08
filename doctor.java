package hospitalmanagementsystem;
import java.util.*;
import java.sql.*;

public class doctor{
    private Connection con;
    private ResultSet rs;
    public doctor(Connection con){
        this.con=con;
      
    }  
    public void viewdoctor(){
       
        try{
           PreparedStatement ps; 
           ps=con.prepareStatement("select * from doctor");  
           rs=ps.executeQuery();
           if(rs.isBeforeFirst()){
              while(rs.next()){
                System.out.println("ID:"+rs.getInt(1)+"\nNAME:"+rs.getString(2)+"SPECIALIZATION:"+rs.getString(3)); 
              }
          }
          else
              System.out.println("table is empty"); 
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean checkdoctor(int id){
        try{
        PreparedStatement ps;    
        ps=con.prepareStatement("select * from doctor where did=?");
        ps.setInt(1,id);    
        rs=ps.executeQuery();
        if(rs.next())
           return true;
        else 
           return false;   
        
    }catch(SQLException e){
         e.printStackTrace();
    }
    return false;
}

}