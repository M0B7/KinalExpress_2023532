package org.diegogarcia.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.diegogarcia.system.main;

/**
 * Nombre: Diego Fernando Garcia Galvez 2023532 PE5AM Fecha de creacion:
 * 15/04/2024 Fecha de Modificacion: 17/04, 23/04, 24/04, 30/04, 06/05, 07/05,
 * 8/5
 */
public class MenuProgramadorController implements Initializable {

    private main escenarioPrincipal;

    @FXML
    Button btnRegresar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setEscenarioPrincipal(main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonActionProgramador(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();

        }
    }
}
