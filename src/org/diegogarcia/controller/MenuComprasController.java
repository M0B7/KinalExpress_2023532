package org.diegogarcia.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.diegogarcia.system.main;


public class MenuComprasController implements Initializable{
    
    private main escenarioPrincipal;
    
    @FXML private Button btnRegresar;
    
   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setEscenarioPrincipal(main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void handleButtonActionCompras (ActionEvent event){
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        } 
    }
}

        
        




    

