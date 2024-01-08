package hospitalmanagementsystem;
import java.sql.*;
import java.util.*;

public class patient{
    private Connection con;
    private Scanner sc; 
    private ResultSet rs;
    public patient(Connection con,Scanner sc){
        this.con=con;
        this.sc=sc;
    }  
    public void addpatient(){
       System.out.println("ENTER PATIENT NAME:");
       String name=sc.next();
       System.out.println("ENTER PATIENT AGE:");
       int age=sc.nextInt();
       System.out.println("ENTER PATIENT GENDER:");
       String gender=sc.next();
       try{
        
         PreparedStatement ps;
         ps=con.prepareStatement("insert into patients(pname ,age ,gender) values(?, ?, ?)");
         ps.setString(1,name);
         ps.setInt(2,age);
         ps.setString(3,gender);
         int affectedrows=ps.executeUpdate();
         if(affectedrows>0)
            System.out.println("patients added successfully!");
         else
            System.out.println("failed to add patients!");   
       }catch(SQLException e){
          e.printStackTrace(); 
       } 
    }
    public void viewpatients(){
       try{
          PreparedStatement ps;
          ps=con.prepareStatement("select * from patients");  
          rs=ps.executeQuery();
          if(rs.isBeforeFirst()){
              while(rs.next()){
                System.out.println("ID:"+rs.getInt(1)+"\nNAME:"+rs.getString(2)+"\nAGE:"+rs.getInt(3)+"\nGENDER:"+rs.getString(4));  
              }
          }
          else
              System.out.println("table is empty"); 
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean checkpatients(int id){
      
        try{
        PreparedStatement ps;
        ps=con.prepareStatement("select * from patients where id=?");
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