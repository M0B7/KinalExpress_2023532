package org.diegogarcia.controller;


import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import org.diegogarcia.bean.Cliente;
import org.diegogarcia.db.Conexion;
import org.diegogarcia.system.main;


public class MenuClientesController implements Initializable {

    private main escenaPrincipal;

    private ObservableList<Cliente> listaCliente;
    private main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NULL
    }
    private operaciones tipoDeOperaciones = operaciones.NULL;

    @FXML
    private TableView tblCliente;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnReporte;
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
    private ImageView imgAgregar;
    @FXML
    private ImageView imgGuardar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporteria;
    @FXML
    private TableColumn colClienteId;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {

    }

    public void seleccionarElemento() {
        /*
        Parsear a clientes
         */
        txtIdCliente.setText(String.valueOf(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getClienteId()));
        txtNombreCliente.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getNombreCliente());
        txtApellidoCliente.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getApellidoCliente());
        txtNitCliente.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getNitCliente());
        txtTelefonoCliente.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtCorreoCliente.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getCorreoCliente());
        txtDireccionCliente.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getDireccionCliente());

    }

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NULL:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Guardar.png"));
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
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Guardar.png"));
                tipoDeOperaciones = operaciones.NULL;
                break;

        }
    }

    public void guardar() {
        Cliente registro = new Cliente();
        registro.setClienteId(Integer.parseInt(txtIdCliente.getText()));
        registro.setNombreCliente(txtNombreCliente.getText());
        registro.setApellidoCliente(txtApellidoCliente.getText());
        registro.setNitCliente(txtNitCliente.getText());
        registro.setTelefonoCliente(txtTelefonoCliente.getText());
        registro.setDireccionCliente(txtDireccionCliente.getText());
        registro.setCorreoCliente(txtCorreoCliente.getText());
        try {
           // PreparedStatement procedimiento;
           // procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarClientes(?,?,?,?,?,?,?)}");
           // procedimiento.setInt(1,registro.getClienteId());
           // procedimiento.setString(2, registro.getNitCliente());
           // procedimiento.setString(3, registro.getNombreCliente());
            //procedimiento.setString(4, registro.getApellidoCliente());
            //procedimiento.setString(5, registro.getDireccionCliente());
            //procedimiento.setString(6, registro.getTelefonoCliente());
           //procedimiento.setString(7, registro.getCorreoCliente());
           // procedimiento.execute();
            //listaCliente.add(registro);

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
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Guardar.png"));
                tipoDeOperaciones = operaciones.NULL;
                break;
            default:
                if (tblCliente.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Se ha eliminado el registro", "Eliminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                           // PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call_sp_EliminarCliente(?)}");
                           // procedimiento.setInt(1,(Cliente)tblCliente.getSelectionModel().getSelectedItem().getClienteId());
                            //procedimineto.execute();
                            //listaCliente.remove(tblCliente.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para eliminar");
                        
        }
    }
        
        
        public void editar (){
            switch(tipoDeOperaciones){
                case NULL:
                    if(tblCliente.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    //imgEditar.actuaizarpng
                    //imgReporte.cancelar.png
                    activarControles();
                    txtIdCliente.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                    
                }else
                        JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar");
                    break;
                    
                case ACTUALIZAR:
                    actualizar();
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnAgregar.setDisable(false);
                    btnEliminar.setDisable(false);
                    //imageEdiatar.agregarclientes.png
                    //imgreporte.reportecliente.png
                    desactivarControles();
                    limpiarControles();
                    tipoDeOperaciones = operaciones.NULL;
                    cargarDatos();
                    break;
                           
            }
        }
    
    public void actualizar (){
        try{
            //PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call_sp_EliminarCliente(?, ?, ?, ?, ?, ?)}");
            //Cliente registro = (Cliente)tblCliente.getSelectionModel().getSelectedItem();
            //registro.setNombreCliente(txtNombreCliente.getText());
            //.setApellidoCliente(txtApellidoCliente.getText());
            //registro.setNitCliente(txtNitCliente.getText());
            //registro.setTelefonoCliente(txtTelefonoCliente.getText());
            //registro.setDireccionCliente(txtDireccionCliente.getText());
            //registro.setCorreoCliente(txtCorreoCliente.getText());
            // procedimiento.setInt(1,registro.getClienteId());
            //procedimiento.setString(2, registro.getNitCliente());
           // procedimiento.setString(3, registro.getNombreCliente());
            //procedimiento.setString(4, registro.getApellidoCliente());
            //procedimiento.setString(5, registro.getDireccionCliente());
            //procedimiento.setString(6, registro.getTelefonoCliente());
            //procedimiento.setString(7, registro.getCorreoCliente());
            //procedimiento.execute();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void cancelar (){
        switch(tipoDeOperaciones){
                case NULL:
                    desactivarControles();
                    if(tblCliente.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Editar");
                    btnAgregar.setText("Agregar");
                    btnAgregar.setDisable(false);
                    btnEliminar.setDisable(false);
                    imgEditar.setImage(new Image("/org/diegogarcia/images/Editar.png"));
                    imgAgregar.setImage(new Image ("/org/diegogarcia/images/Agregar.png"));
                    }
        }
        
    }

    public void desactivarControles() {
        txtIdCliente.setEditable(false);
        txtNitCliente.setEditable(false);
        txtNombreCliente.setEditable(false);
        txtApellidoCliente.setEditable(false);
        txtDireccionCliente.setEditable(false);
        txtTelefonoCliente.setEditable(false);
        txtCorreoCliente.setEditable(false);
    }

    public void activarControles() {
        txtIdCliente.setEditable(true);
        txtNitCliente.setEditable(true);
        txtNombreCliente.setEditable(true);
        txtApellidoCliente.setEditable(true);
        txtDireccionCliente.setEditable(true);
        txtTelefonoCliente.setEditable(true);
        txtCorreoCliente.setEditable(true);

    }

    public void limpiarControles() {
        txtIdCliente.clear();
        txtNitCliente.clear();
        txtNombreCliente.clear();
        txtApellidoCliente.clear();
        txtDireccionCliente.clear();
        txtTelefonoCliente.clear();
        txtCorreoCliente.clear();
    }

    public void setEscenarioPrincipal(main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonActionClientes(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}


