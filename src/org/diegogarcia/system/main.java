
package org.diegogarcia.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.diegogarcia.controller.MenuClientesController;
import org.diegogarcia.controller.MenuComprasController;
import org.diegogarcia.controller.MenuEmpleadoController;
import org.diegogarcia.controller.MenuPrincipalController;
import org.diegogarcia.controller.MenuProductosController;
import org.diegogarcia.controller.MenuProgramadorController;
import org.diegogarcia.controller.MenuProveedorController;
import org.diegogarcia.controller.MenuTipoProductoController;

/**
 * Nombre: Diego Fernando Garcia Galvez 2023532 PE5AM Fecha de creacion:
 * 15/04/2024
 */
public class main extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;
    
    

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Express");
        menuPrincipalView();

        escenarioPrincipal.getIcons().add(new Image(main.class.getResourceAsStream("/org/diegogarcia/images/Logo.png")));

        escenarioPrincipal.show();
    }
    
    
    

    public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws Exception {
        Initializable resultado;

        FXMLLoader loader = new FXMLLoader();

        InputStream file = main.class.getResourceAsStream("/org/diegogarcia/views/" + fxmlName);

        loader.setBuilderFactory(new JavaFXBuilderFactory());

        loader.setLocation(main.class.getResource("/org/diegogarcia/views/" + fxmlName));

        escena = new Scene((AnchorPane) loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        
        

        resultado = (Initializable) loader.getController();
        
        

        return resultado;
    }

    
    
    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 907, 453);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuClientesView() {
        try {
            MenuClientesController menuClientesView = (MenuClientesController) cambiarEscena("MenuClientesView.fxml", 995, 648);
            menuClientesView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuProgramadorView() {
        try {
            MenuProgramadorController menuProgramadorView = (MenuProgramadorController) cambiarEscena("MenuProgramadorView.fxml", 969, 544);
            menuProgramadorView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuProveedoresView() {
        try {
            MenuProveedorController menuProveedoresView = (MenuProveedorController) cambiarEscena("MenuProveedoresView.fxml", 1228, 648);
            menuProveedoresView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuComprasView() {
        try {
            MenuComprasController menuComprasView = (MenuComprasController) cambiarEscena("MenuCompraView.fxml", 995, 648);
            menuComprasView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuTipoProductoView() {
        try {
            MenuTipoProductoController menuTipoProductoView = (MenuTipoProductoController) cambiarEscena("MenuTipoProductosView.fxml", 995, 648);
            menuTipoProductoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuEmpleadoView() {
        try {
            MenuEmpleadoController menuEmpleadoView = (MenuEmpleadoController) cambiarEscena("MenuEmpleadosView.fxml", 995, 648);
            menuEmpleadoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuProductosView() {
        try {
            MenuProductosController menuProductosView = (MenuProductosController) cambiarEscena("MenuProductosView.fxml", 1128, 650);
            menuProductosView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuTipoProductosView(){
        try{
            MenuTipoProductoController menuTipoProductosView = (MenuTipoProductoController) cambiarEscena("MenuTipoProductoView.fxml", 995,648);
            menuTipoProductosView.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
