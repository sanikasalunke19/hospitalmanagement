package hospitalmanagementsystem;
import java.sql.*;
import java.util.*;
public class hospitalmanagementsystem{
    static Connection con;
    static int ch;
    public static void main(String args[]){
       Scanner sc=new Scanner(System.in);
       try{
         Class.forName("com.mysql.cj.jdbc.Driver");
         con=DriverManager.getConnection("jdbc:mysql://localhost:/hospital","root","root");
         patient p=new patient(con,sc);
         doctor d=new doctor(con);

         do{
            System.out.println("HOSPITAL MANANGEMENT APPLICATION");
            System.out.println("1:Add patient");
            System.out.println("2:view patient");
            System.out.println("3:view doctors");
            System.out.println("4:book appointment");
            System.out.println("5:exit");
            System.out.println("ENTER YOUR CHOICE:");
            ch=sc.nextInt(); 
            switch(ch){
                case 1:p.addpatient();
                       System.out.println();
                       break;
                case 2:p.viewpatients();
                       System.out.println();
                       break;
                case 3:d.viewdoctor();
                       System.out.println();
                       break;
                case 4:bookappointment(p,d,con,sc);
                       System.out.println();
                       break;    
                case 5:System.out.println("THANKYOU bye!");
                       System.out.println();
                       break;
                default:System.out.println("Enter valid choice");                                 
            }  

         }while(ch!=5);

       }catch(Exception e){

       }
    }
    public static void bookappointment(patient p,doctor d,Connection con,Scanner sc){
        System.out.println("enter patient id:");
        int pid=sc.nextInt();
        System.out.println("enter doctor id:");
        int did=sc.nextInt();
        System.out.println("enter date(yyyy-mm-dd):");
        String date=sc.next();
        if(p.checkpatients(pid) && d.checkdoctor(did)){
              if(checkdoctoravailability(did,date,con)){
                   String appointment="insert into appointments(pid,did,appointment_date) values(?,?,?)";
                   try{
                      PreparedStatement ps;
                      ps=con.prepareStatement(appointment);
                      ps.setInt(1,pid);
                      ps.setInt(2,did);
                      ps.setString(3,date);
                      int rows=ps.executeUpdate();
                      if(rows>0)
                        System.out.println("Appointmet Booked!");
                      else
                        System.out.println("Sorry could not book the appointment");  
                   }catch(SQLException e){
                      e.printStackTrace();
                   }
              }else{
                  System.out.println("SORRY!Doctor is not available on this date.");
              }
                 
        }else{
              System.out.println("Either patient or doctor doesnt exist.");
        }
          

   }
   public static boolean checkdoctoravailability(int did,String date,Connection con){
        try{
         PreparedStatement ps;
         ps=con.prepareStatement("select count(*) from appointments where did=? and appointment_date=?");
         ps.setInt(1,did);
         ps.setString(2,date);
         ResultSet rs=ps.executeQuery();
         if(rs.next()){
            if(rs.getInt(1)==0)
              return true;
            else 
              return false; 
         }
      
   }catch(SQLException e){
       e.printStackTrace();
   }
   return false;
}
}