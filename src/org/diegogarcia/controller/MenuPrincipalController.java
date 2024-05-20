package org.diegogarcia.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import org.diegogarcia.system.main;

/**
 * Nombre: Diego Fernando Garcia Galvez 2023532 PE5AM Fecha de creacion:
 * 15/04/2024 Fecha de Modificacion: 17/04, 23/04, 24/04, 30/04, 06/05, 07/05,
 * 8/5
 */
public class MenuPrincipalController implements Initializable {

    private main escenarioPrincipal;

    @FXML
    MenuItem btnMenuClientes;
    @FXML
    MenuItem btnMenuProgramador;
    @FXML
    MenuItem btnMenuProveedor;
    @FXML
    MenuItem btnMenuCompras;
    @FXML
    MenuItem btnMenuEmpleados;
    @FXML
    MenuItem btnMenuProductos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnMenuClientes) {
            escenarioPrincipal.menuClientesView();
        }
        if (event.getSource() == btnMenuProgramador) {
            escenarioPrincipal.menuProgramadorView();
        }
        if (event.getSource() == btnMenuProveedor) {
            escenarioPrincipal.menuProveedoresView();
        }
        if (event.getSource() == btnMenuCompras) {
            escenarioPrincipal.menuComprasView();
        }
        if (event.getSource() == btnMenuEmpleados) {
            escenarioPrincipal.menuEmpleadoView();
        }
        if (event.getSource() == btnMenuProductos) {
            escenarioPrincipal.menuProductosView();
        }
    }
}
