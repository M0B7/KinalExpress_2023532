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
import org.diegogarcia.bean.Compras;
import org.diegogarcia.db.Conexion;
import org.diegogarcia.system.main;



// *************************************************************************************************************



public class MenuComprasController implements Initializable {

    private main escenarioPrincipal;

    private ObservableList<Compras> listaCompras;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NULL
    }
    private operaciones tipoDeOperaciones = operaciones.NULL;
    
    
    
 // *************************************************************************************************************

    @FXML
    private TableView tblCompras;
    

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
    private TableColumn colIdCompras;

    @FXML
    private TableColumn colFechaDocumento;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colTotalDocumento;
    
    
    

    @FXML
    private TextField txtIdCompra;

    @FXML
    private TextField txtFechaDocumento;

    @FXML
    private TextField txtTotalDocumento;

    @FXML
    private TextField txtDescripcion;
    
 
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
        tblCompras.setItems(getCompras());
        colIdCompras.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("idCompra"));
        colFechaDocumento.setCellValueFactory(new PropertyValueFactory<Compras, String>("fechaDocumento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotalDocumento.setCellValueFactory(new PropertyValueFactory<Compras, String>("totalDocumento"));
    }

    public void seleccionarElemento() {
        txtIdCompra.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getIdCompra()));
        txtFechaDocumento.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getFechaDocumento()));
        txtDescripcion.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion()));
        txtTotalDocumento.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));
    }
    
    
// *************************************************************************************************************
    

    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCompras ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compras(resultado.getInt("idCompra"),
                        resultado.getString("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getInt("totalDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCompras = FXCollections.observableList(lista);
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

                tipoDeOperaciones = MenuComprasController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = MenuComprasController.operaciones.NULL;
                break;

        }
    }

    public void guardar() {
        Compras registro = new Compras();
        registro.setIdCompra(Integer.parseInt(txtIdCompra.getText()));
        txtIdCompra.setDisable(true);
        registro.setFechaDocumento(txtFechaDocumento.getText());
        registro.setDescripcion(txtDescripcion.getText());
        registro.setTotalDocumento(Integer.parseInt(txtTotalDocumento.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCompra( ?, ?, ?)}");
            txtIdCompra.setDisable(true);
            procedimiento.setInt(1, registro.getIdCompra());
            procedimiento.setString(1, registro.getFechaDocumento());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.setDouble(3, registro.getTotalDocumento());
            procedimiento.execute();
            listaCompras.add(registro);
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
                imgAgregar.setImage((new Image("/org/diegogarcoa/images/Agregar.png")));
                imgEliminar.setImage((new Image("/org/diegogarcia/images/Eliminar.png")));
                tipoDeOperaciones = MenuComprasController.operaciones.NULL;
                break;
            default:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCompra(?)}");
                            procedimiento.setInt(1, ((Compras) tblCompras.getSelectionModel().getSelectedItem()).getIdCompra());
                            procedimiento.execute();
                            listaCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar una compra para eliminar");
                }
                break;
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NULL:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/diegogarcia/images/Guardar.png"));
                    imgReporte.setImage(new Image("/org/diegogarcia/images/Cancelar.png"));
                    activarControles();
                    txtIdCompra.setEditable(false);
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

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_editarCompra (?, ?, ?, ?)}");
            Compras registro = (Compras) tblCompras.getSelectionModel().getSelectedItem();
            registro.setFechaDocumento(txtFechaDocumento.getText());
            registro.setDescripcion(txtDescripcion.getText());
            registro.setTotalDocumento(Integer.parseInt(txtTotalDocumento.getText()));
            procedimiento.setInt(1, registro.getIdCompra());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, (int) registro.getTotalDocumento());
            procedimiento.execute();
        } catch (Exception e) {
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
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/diegogarcia/images/Actualizar.png"));
                imgReporte.setImage(new Image("/org/diegogarcia/images/Reporteria.png"));
                tipoDeOperaciones = operaciones.NULL;
                break;
        }
    }
    
    
// *************************************************************************************************************
    
    

    public void activarControles() {
        txtIdCompra.setEditable(true);
        txtFechaDocumento.setEditable(true);
        txtDescripcion.setEditable(true);
        txtTotalDocumento.setEditable(true);
    }

    public void desactivarControles() {
        txtIdCompra.setEditable(false);
        txtFechaDocumento.setEditable(false);
        txtDescripcion.setEditable(false);
        txtTotalDocumento.setEditable(false);
    }

    public void limpiarControles() {
        txtIdCompra.clear();
        txtFechaDocumento.clear();
        txtDescripcion.clear();
        txtTotalDocumento.clear();
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
