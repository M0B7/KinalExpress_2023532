package org.diegogarcia.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import org.diegogarcia.system.main;

public class MenuTelefonoController implements Initializable {

    private main escenarioPrincipal;
    @FXML
    private Button btnRegresar;

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    public main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
