package org.diegogarcia.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Nombre: Diego Fernando Garcia Galvez
 * 2023532
 * PE5AM
 * Fecha de creacion: 15/04/2024
 * Fecha de Modificacion: 17/04, 23/04, 24/04, 30/04, 06/05, 07/05, 8/5
 */


public class Conexion {
    
    // Realiza la conexion
    private Connection conexion;
    
    //Instancia lo de arriba
    private static Conexion instancia;
    
    
    /*
    Constructor que hace la conexion con la base de datos
    */
    public Conexion () {
		try{
                    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                    conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/KinalExpress?useSSL=false", "YouCanDoIt", "Elmantecasxdpro1");
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
    
    
    /* 
     Istancia de nuestra clase conexion
     */
    
    public static Conexion getInstance(){
        if (instancia == null){
            instancia = new Conexion();           
        }
        return instancia;
    }

    public Conexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Connection getConexion() {
       return conexion;
    }

    public void setConexion(Connection conexion){
        this.conexion = conexion;
    }
}
