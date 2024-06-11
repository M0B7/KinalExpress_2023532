package org.diegogarcia.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.diegogarcia.bean.Clientes;
import org.diegogarcia.db.Conexion;
import org.diegogarcia.reports.GenerarReportes;
import org.diegogarcia.system.main;


// *************************************************************************************************************

public class MenuClientesController implements Initializable {
    
    

    private main escenarioPrincipal;
    
    

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NULL
        
    }
        
    private operaciones tipoDeOperaciones = operaciones.NULL;
    
// *************************************************************************************************************
    

    private ObservableList<Clientes> listaClientes;
    
    
    @FXML
    private TableView tblClientes;

    
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
    private TextField txtNitCliente;
    @FXML
    private TextField txtIdCliente;
    @FXML
    private TextField txtNombreCliente;
    @FXML
    private TextField txtApellidoCliente;
    @FXML
    private TextField txtDireccionCliente;
    @FXML
    private TextField txtTelefonoCliente;
    @FXML
    private TextField txtCorreoCliente;
    
    
    @FXML
    private TableColumn colIdCliente;
    @FXML
    private TableColumn colNombreCliente;
    @FXML
    private TableColumn colApellidoCliente;
    @FXML
    private TableColumn colDireccionCliente;
    @FXML
    private TableColumn colTelefonoCliente;
    @FXML
    private TableColumn colCorreoCliente;
    @FXML
    private TableColumn colNitCliente;
    
    
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
        tblClientes.setItems(getClientes());
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colNitCliente.setCellValueFactory(new PropertyValueFactory<>("nitCliente"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombresCliente"));
        colApellidoCliente.setCellValueFactory(new PropertyValueFactory<>("apellidosCliente"));
        colDireccionCliente.setCellValueFactory(new PropertyValueFactory<>("direccionCliente"));
        colTelefonoCliente.setCellValueFactory(new PropertyValueFactory<>("telefonoCliente"));
        colCorreoCliente.setCellValueFactory(new PropertyValueFactory<>("correoCliente"));
    }

    public void seleccionarElemento() {
            txtIdCliente.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getIdCliente()));
            txtNitCliente.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNitCliente()));
            txtNombreCliente.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNombresCliente()));
            txtApellidoCliente.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getApellidosCliente()));
            txtTelefonoCliente.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente()));
            txtCorreoCliente.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCorreoCliente()));
            txtDireccionCliente.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente()));

    }
    
// *************************************************************************************************************

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(
                        resultado.getInt("idCliente"),
                        resultado.getString("nitCliente"),
                        resultado.getString("nombresCliente"),
                        resultado.getString("apellidosCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaClientes = FXCollections.observableList(lista);
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
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Guadar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/Cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = operaciones.NULL;
        }
    }

    public void guardar() {
        Clientes register = new Clientes();
        register.setIdCliente(Integer.parseInt(txtIdCliente.getText()));
        register.setNombresCliente(txtNombreCliente.getText());
        register.setApellidosCliente(txtApellidoCliente.getText());
        register.setDireccionCliente(txtDireccionCliente.getText());
        register.setNitCliente(txtNitCliente.getText());
        register.setTelefonoCliente(txtTelefonoCliente.getText());
        register.setCorreoCliente(txtCorreoCliente.getText());
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCliente(?, ?, ?, ?, ?, ?, ?)}");
            procedure.setInt(1, register.getIdCliente());
            procedure.setString(2, register.getNombresCliente());
            procedure.setString(3, register.getApellidosCliente());
            procedure.setString(4, register.getDireccionCliente());
            procedure.setString(5, register.getNitCliente());
            procedure.setString(6, register.getTelefonoCliente());
            procedure.setString(7, register.getCorreoCliente());
            procedure.execute();
            listaClientes.add(register);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NULL:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/diegogarcia/images/Guardar.png"));
                    imgEliminar.setImage(new Image("/org/diegogarcia/images/Cancelar.png"));
                    activarControles();
                    txtIdCliente.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/diegogarcia/images/Editar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/Eliminar.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NULL;
                cargarDatos();

                break;
        }
    }

    public void actualizar() {
        // Verificar si se ha seleccionado un cliente
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_editarCliente(?,?,?,?,?,?,?)}");
            Clientes register = (Clientes) tblClientes.getSelectionModel().getSelectedItem();

            register.setNombresCliente(txtNombreCliente.getText());
            register.setNitCliente(txtNitCliente.getText());
            register.setApellidosCliente(txtApellidoCliente.getText());
            register.setTelefonoCliente(txtTelefonoCliente.getText());
            register.setDireccionCliente(txtDireccionCliente.getText());
            register.setCorreoCliente(txtCorreoCliente.getText());
            procedure.setInt(1, register.getIdCliente());
            procedure.setString(2, register.getNitCliente());
            procedure.setString(3, register.getNombresCliente());
            procedure.setString(4, register.getApellidosCliente());
            procedure.setString(5, register.getDireccionCliente());
            procedure.setString(6, register.getTelefonoCliente());
            procedure.setString(7, register.getCorreoCliente());
            procedure.execute();
        } catch (SQLException e) {
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
                tipoDeOperaciones = MenuClientesController.operaciones.NULL;
                break;
            default:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar al cliente?", "Eliminar Cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_borrarCliente(?)}");
                            procedure.setInt(1, ((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getIdCliente());
                            procedure.execute();
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para eliminar");
                }
                break;

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
                tipoDeOperaciones = operaciones.NULL;
                
                break;
        }
    }
    
    
    
     public void reporte() {
        switch (tipoDeOperaciones) {
            case NULL:
                imprimirReporte();
                break;
                
            
            case ACTUALIZAR:
                imgEditar.setImage(new Image("/org/diegogarcia/images/Editar.png"));
                imgReporte.setImage(new Image("/org/diegogarcia/images/Reporteria.png"));
                btnEditar.setText("Editar");
                btnReporte.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NULL;
                break;
              
        }
    }
     
     public void imprimirReporte(){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("idCliente", null);
        parametros.put("IMAGEN_FONDOC", GenerarReportes.class.getResource("/images/FondoReporte.png"));
        GenerarReportes.mostrarReportes("ReporteClientes.jasper", "Reporte de Clientes", parametros);
     }
     
     
     //IReport = commons partes seccionada del Ireport 
     

// *************************************************************************************************************    
    
    public void desactivarControles() {
        txtIdCliente.setEditable(false);
        txtNombreCliente.setEditable(false);
        txtApellidoCliente.setEditable(false);
        txtNitCliente.setEditable(false);
        txtTelefonoCliente.setEditable(false);
        txtDireccionCliente.setEditable(false);
        txtCorreoCliente.setEditable(false);
    }

    public void activarControles() {
        txtIdCliente.setEditable(true);
        txtNombreCliente.setEditable(true);
        txtApellidoCliente.setEditable(true);
        txtNitCliente.setEditable(true);
        txtTelefonoCliente.setEditable(true);
        txtDireccionCliente.setEditable(true);
        txtCorreoCliente.setEditable(true);
    }

    public void limpiarControles() {
        txtIdCliente.clear();
        txtNombreCliente.clear();
        txtApellidoCliente.clear();
        txtNitCliente.clear();
        txtTelefonoCliente.clear();
        txtDireccionCliente.clear();
        txtCorreoCliente.clear();
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
    public void handleButtonActionClientes(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
