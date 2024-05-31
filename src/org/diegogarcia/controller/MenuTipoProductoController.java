package org.diegogarcia.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.diegogarcia.bean.Proveedores;
import org.diegogarcia.bean.TipoProducto;
import org.diegogarcia.db.Conexion;
import org.diegogarcia.system.main;


// *************************************************************************************************************


public class MenuTipoProductoController implements Initializable {

    private main escenarioPrincipal;
    
    
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NULL
    }
    private operaciones tipoDeOperaciones = operaciones.NULL;
    
    
// *************************************************************************************************************

    private ObservableList<TipoProducto> listaTipoProducto;
    
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReporte;

    @FXML
    private ImageView imgRegresar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgReporte;
    
    

    @FXML
    private TableView tblTipoProducto;
    

    @FXML
    private TableColumn colIdTipoProducto;

    @FXML
    private TableColumn colDescripcion;
    
    

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtIdTipoProducto;
    
      
// *************************************************************************************************************    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connection conexion = Conexion.getInstance().getConexion();
        if (conexion != null) {
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
        }
    }
    
    
    public void cargarDatos() {
        tblTipoProducto.setItems(getTipoProducto());
        colIdTipoProducto.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("idTipoProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("descripcion"));
    }
    
    
    public void seleccionar() {
        txtIdTipoProducto.setText(String.valueOf(((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getIdTipoProducto()));
        txtDescripcion.setText(String.valueOf(((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getDescripcion()));
    }

// *************************************************************************************************************   
    
    
    public ObservableList<TipoProducto> getTipoProducto() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarTipoProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("idTipoProducto"),
                        resultado.getString("descripcion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTipoProducto = FXCollections.observableList(lista);
    }

// *************************************************************************************************************   
    
    
    
    
    public void agregar() {
        switch (tipoDeOperaciones) {
            case NULL:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage((new Image("/org/diegogarcia/images/Guardar.png")));
                imgEliminar.setImage((new Image("/org/diegogarcia/images/Cancelar.png")));
                tipoDeOperaciones = MenuTipoProductoController.operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/Eliminar.png"));
                cargarDatos();
                tipoDeOperaciones = MenuTipoProductoController.operaciones.NULL;
                break;

        }
    }
    
    public void guardar() {
        TipoProducto registro = new TipoProducto();
        registro.setDescripcion(txtDescripcion.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarTipoProducto(?)}");
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.execute();
            listaTipoProducto.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage((new Image("/org/diegogarcia/images/Agregar.png")));
                imgEliminar.setImage((new Image("/org/diegogarcia/images/Eliminar.png")));
                tipoDeOperaciones = MenuTipoProductoController.operaciones.NULL;
                break;
            default:
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getIdTipoProducto());
                            procedimiento.execute();
                            listaTipoProducto.remove(tblTipoProducto.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para eliminar");
                }
                break;
        }
    }
    
    public void editar() {
        switch (tipoDeOperaciones) {
            case NULL:
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/diegogarcia/images/Guardar.png"));
                    imgReporte.setImage(new Image("/org/diegogarcia/images/Cancelar.png"));
                    activarControles();
                    txtIdTipoProducto.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/diegogarcia/images/Actualizar.png"));
                imgReporte.setImage(new Image("/org/diegogarcia/images/ReportesClientes.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NULL;
                cargarDatos();
                break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarTipoProducto (?, ?)}");
            TipoProducto registro = (TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem();
            //registro.setCodigoTipoProducto(txtCodigoProducto.getText());
            registro.setDescripcion(txtDescripcion.getText());
            procedimiento.setInt(1, registro.getIdTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void cancelar() {
        switch (tipoDeOperaciones) {
            case NULL:
                btnReporte.setDisable(false);
                btnAgregar.setDisable(false);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/Eliminar.png"));
                break;
        }
    }
    
    
    
     public void reporte() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperaciones = MenuTipoProductoController.operaciones.NULL;
        }
    }
     
     
// *************************************************************************************************************  
    
    public void activarControles() {
        txtIdTipoProducto.setEditable(true);
        txtDescripcion.setEditable(true);
    }
    
    public void desactivarControles() {
        txtIdTipoProducto.setEditable(false);
        txtDescripcion.setEditable(false);
    }
    
    public void limpiarControles() {
        txtIdTipoProducto.clear();
        txtDescripcion.clear();
    }
    
// *************************************************************************************************************  
    
    
     public main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public Button getBtnRegresar() {
        return btnRegresar;
    }

    public void setBtnRegresar(Button btnRegresar) {
        this.btnRegresar = btnRegresar;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}