/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.diegogarcia.reports;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.diegogarcia.db.Conexion;

/**
 *
 * @author informatica
 */
public class GenerarReportes {
    
    public static void mostrarReportes(String nombreReporte, String titulo,
            Map parametros){
        InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
        try{
            
            //Llama el reporte
            JasperReport ReporteClientes = (JasperReport)JRLoader.loadObject(reporte);
            //Verifica
            JasperPrint reporteImpreso = JasperFillManager.fillReport(ReporteClientes, parametros, Conexion.getInstance().getConexion());
                
            
            //Vista Previa
            
            JasperViewer visor = new JasperViewer(reporteImpreso, false);
            
            
            visor.setTitle(titulo);
            visor.setVisible(true);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
       
        
    }
    
}

/*
Interface Map

    HashMap es uno de los objetos que implementa un conjunto de key-value.
    Tiene un constructor sin parametros new HashMap() y su finalidad suele referirse 
    para agrupar informacion de varios tipos en un unico objeto

    Tiene cierta similitud con la coleccion de objetos (ArrayList) pero con la
    diferencia que estos no tienen oreden

    Hash hace referencia a una tecnica de organizacion de archivos, hashing (abierto-cerrado)
    en la cual se almacena regstros en una direccion que es generada por una funcion
    se aplica a la llave del registro

    En java el HashMap posee un espacio de memoria y cuando se guarda un objeto en dicho
    espacio se determina su direccion una funcion a la llave que le indiquemos. Cada objeto se 
    identifica mediante algun identificador apropiado


*/
