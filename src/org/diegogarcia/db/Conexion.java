/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.diegogarcia.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author informatica
 */
public class Conexion {
    private Connection conexion;
    private static Conexion instancia;
    
    public Conexion () {
		try{
                    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                    conexion = DriverManager.getConnection("dbc:mysql://localhost:3306:/DBKinalExpressIN5BM?useSSL=false", "root", "Elmantecasxdpro1");
                }catch(ClassNotFoundException e){
                    e.printStackTrace();
                }catch(InstantiationException e){
                    e.printStackTrace();
                }catch(IllegalAccessException e){
                    e.printStackTrace();               
                }catch(SQLException e){
                    e.printStackTrace();
                }
    }
    
    public static Conexion getInstance(){
        if (instancia == null){
            instancia = new Conexion();           
        }
        return instancia;
    }

    public static Conexion getInstancia() {
        return instancia;
    }

    public static void setInstancia(Conexion instancia) {
        Conexion.instancia = instancia;
    }

    public Object getConexion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
                                                
    
}
