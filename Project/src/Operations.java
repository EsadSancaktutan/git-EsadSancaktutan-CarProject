
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Operations {
    //İmport edilecekler
    Connection con = null;
    Statement sta = null;
    PreparedStatement psta=null;
    
    public int carCount(){
        int label = 0;
        String sorgu = " SELECT COUNT(*) FROM  cars_database";
        
        try {
            sta = con.createStatement();
            ResultSet rs = sta.executeQuery(sorgu);
            rs.next();
            label = rs.getInt(1);
            
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            return  0;
        }
        return label;
    }
    
    
    public void carUpdate(int id,String new_brand, String new_sherry, String new_model, String new_year, String new_fuel, String new_gear, String new_km, String new_caseType, String new_motorPower, String new_color, String new_price){
        String sorgu = "Update cars_database SET id=?,car_brand=?,car_sherry=?,car_model?,car_year=?,car_fuel=?,car_gear=?,car_km=?,car_caseType=?,car_motorPower=?,car_color=?,car_price=? WHERE id=?";
        try {
            psta = con.prepareStatement(sorgu);
            psta.setString(1, new_brand);
            psta.setString(2, new_sherry);
            psta.setString(3, new_model);
            psta.setString(4, new_year);
            psta.setString(5, new_fuel);
            psta.setString(6, new_gear);
            psta.setString(7, new_km);
            psta.setString(8, new_caseType);
            psta.setString(9, new_motorPower);
            psta.setString(10, new_color);
            psta.setString(11, new_price);
            psta.setInt(12, id);
            psta.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void carDelete(int id){
        String sorgu = "DELETE FROM cars_database WHERE id=?";
        try {
            psta=con.prepareStatement(sorgu);
            psta.setInt(1, id);
            psta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void carAdd(String brand, String sherry, String model, String year, String fuel, String gear, String km, String caseType, String motorPower, String color, String price){
        String sorgu = "Insert into cars_database( car_brand, car_sherry, car_model, car_year, car_fuel, car_gear, car_km, car_caseType, car_motorPower, car_color, car_price) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            psta = con.prepareStatement(sorgu);
            psta.setString(1, brand);
            psta.setString(2, sherry);
            psta.setString(3, model);
            psta.setString(4, year);
            psta.setString(5, fuel);
            psta.setString(6, gear);
            psta.setString(7, km);
            psta.setString(8, caseType);
            psta.setString(9, motorPower);
            psta.setString(10, color);
            psta.setString(11, price);
            psta.executeUpdate();
      
                    } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Car> CarCome(){
        ArrayList<Car> list = new ArrayList<Car>();
        String sorgu="Select * from cars_database";
        
        try {
            sta =con.createStatement();
            ResultSet rs= sta.executeQuery(sorgu);
            
            while(rs.next()){
                int id = rs.getInt("id");
                String car_brand = rs.getString("car_brand");
                String car_sherry = rs.getString("car_sherry");
                String car_model = rs.getString("car_model");
                String car_year = rs.getString("car_year");
                String car_fuel = rs.getString("car_fuel");
                String car_gear = rs.getString("car_gear");
                String car_km = rs.getString("car_km");
                String car_caseType = rs.getString("car_caseType");
                String car_motorPower = rs.getString("car_motorPower");
                String car_color = rs.getString("car_color");
                String car_price = rs.getString("car_price");
                list.add(new Car(id,car_brand,car_sherry,car_model,car_year,car_fuel,car_gear,car_km,car_caseType,car_motorPower,car_color,car_price));
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public boolean Login (String id, String password){
        String sorgu = "Select * from admin where id= ? and passeord=?";
        try {
            psta = con.prepareStatement(sorgu);
            psta.setString(1,id);
            psta.setString(2,password);
            ResultSet rs = psta.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
       
    }
    
   
   
    public Operations()  {
    
      
        
        String url = "jdbc:mysql://"+Database.host+":"+Database.port+"/"+Database.db_name+"?useUnicode=true&characterEncoding=UTF-8";
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,Database.id,Database.password);
            System.out.println("Veritabanına başarıyla bağlandınız. ");
                    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Driver çalışmadı :/");
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection çalışamadı :/");
        }
   
    }
    
    public static void main(String[] args) {
        Operations op = new Operations();
    }
    
}
