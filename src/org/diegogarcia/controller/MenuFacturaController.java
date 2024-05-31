package org.diegogarcia.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.diegogarcia.bean.Clientes;
import org.diegogarcia.bean.Empleados;
import org.diegogarcia.bean.Facturas;
import org.diegogarcia.db.Conexion;
import org.diegogarcia.system.main;


// *************************************************************************************************************

public class MenuFacturaController implements Initializable {
    
    
    private main escenarioPrincipal;
    
    private ObservableList<Facturas> listarFactura;
    private ObservableList<Empleados> listarEmpleados;
    private ObservableList<Clientes> listarClientes;


    private enum operaciones {
        AGREGAR, EDITAR, ACTUALIZAR, ELIMINAR, CANCELAR, NULL
    }

    private operaciones tipoDeOperaciones = operaciones.NULL;
    
// *************************************************************************************************************   
    
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
    private TableView tblFactura;
    
    

    @FXML
    private TableColumn colIdFactura;

    @FXML
    private TableColumn colEstado;

    @FXML
    private TableColumn colTotalFactura;

    @FXML
    private TableColumn colFechaFactura;

    @FXML
    private TableColumn colIdEmpleado;

    @FXML
    private TableColumn colIdCliente;
    
    

    @FXML
    private TextField txtIdFactura;

    @FXML
    private TextField txtEstado;

    @FXML
    private TextField txtTotalFactura;
    
 

    @FXML
    private DatePicker dpFechaFactura;
    

    @FXML
    private ComboBox cbmCliente;

    @FXML
    private ComboBox cbmEmpleado;
    
// *************************************************************************************************************

    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connection conexion = Conexion.getInstance().getConexion();
        if (conexion != null) {
            cargarDatos();
            cbmCliente.setItems(getClientes());
            cbmEmpleado.setItems(getEmpleados());
            cbmCliente.setDisable(true);
            cbmEmpleado.setDisable(true);
        } else {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
        }

    }
    
    public void cargarDatos() {
        desactivarControles();
        tblFactura.setItems(getFactura());
        colIdFactura.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("idFactura"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Facturas, String>("estado"));
        colTotalFactura.setCellValueFactory(new PropertyValueFactory<Facturas, Double>("totalFactura"));
        colFechaFactura.setCellValueFactory(new PropertyValueFactory<Facturas, String>("fechaFactura"));
        colIdCliente.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("idCliente"));
        colIdEmpleado.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("idEmpleado"));
    }
    
    
    public void seleccionarElemento() {
        int codEmpleado = ((Facturas) tblFactura.getSelectionModel().getSelectedItem()).getIdEmpleado();
        
        txtIdFactura.setText(String.valueOf(((Facturas) tblFactura.getSelectionModel().getSelectedItem()).getIdFactura()));
        txtEstado.setText(((Facturas) tblFactura.getSelectionModel().getSelectedItem()).getEstado());
        txtTotalFactura.setText(String.valueOf(((Facturas) tblFactura.getSelectionModel().getSelectedItem()).getTotalFactura()));
        dpFechaFactura.setValue(LocalDate.parse(((Facturas) tblFactura.getSelectionModel().getSelectedItem()).getFechaFactura()));
        cbmCliente.getSelectionModel().select(buscarCliente(((Facturas) tblFactura.getSelectionModel().getSelectedItem()).getIdCliente()));
        cbmEmpleado.getSelectionModel().select(buscarEmpleado(codEmpleado));
    }
    
// *************************************************************************************************************
    
    
    
    public ObservableList<Facturas> getFactura() {
        ArrayList<Facturas> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarFacturas()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Facturas(resultado.getInt("idFactura"),
                                                        resultado.getString("estado"),
                                                        resultado.getDouble("totalFactura"),
                                                        resultado.getString("fechaFactura"),
                                                        resultado.getInt("idCliente"), 
                                                        resultado.getInt("idEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarFactura = FXCollections.observableList(lista);
    }
    
    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("idEmpleado"),
                                                        resultado.getString("nombreEmpleado"),
                                                        resultado.getString("apellidoEmpleado"),
                                                        resultado.getDouble("sueldo"),
                                                        resultado.getString("turno"),
                                                        resultado.getInt("idCargoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarEmpleados = FXCollections.observableList(lista);
    }
    
    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("idCliente"),
                        resultado.getString("nitCliente"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidosCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarClientes = FXCollections.observableList(lista);
    }
    
// *************************************************************************************************************
    
    
    public void agregar() {
        switch (tipoDeOperaciones) {
            case NULL:
                activarControles();
                btnEliminar.setText("Cancelar");
                btnAgregar.setText("Guardar");
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/Cancelar.png"));
                txtIdFactura.setEditable(false);
                btnReporte.setDisable(true);
                btnEditar.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                cargarDatos();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/diegogarcia/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/eliminar.png"));  
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                tipoDeOperaciones = operaciones.NULL;
                break;
        }
    }
    
    public void guardar(){
        Facturas registro = new Facturas();
        registro.setFechaFactura(dpFechaFactura.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        registro.setIdCliente(((Clientes) cbmCliente.getSelectionModel().getSelectedItem()).getIdCliente());
        registro.setIdEmpleado(((Empleados) cbmEmpleado.getSelectionModel().getSelectedItem()).getIdEmpleado());
        registro.setEstado(txtEstado.getText());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarFactura(?,?,?,?)}");
            procedimiento.setString(1, registro.getEstado());
            procedimiento.setString(2, registro.getFechaFactura());
            procedimiento.setInt(3, registro.getIdCliente());
            procedimiento.setInt(4, registro.getIdEmpleado());
            
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public Clientes buscarCliente(int cod){
        Clientes result=null;
        
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCliente(?)}");
           procedimiento.setInt(1, cod);
           
           ResultSet registro = procedimiento.executeQuery();
           
           while(registro.next()){
               result=new Clientes(registro.getInt("idCliente"),
                        registro.getString("nitCliente"),
                        registro.getString("nombreCliente"),
                        registro.getString("apellidosCliente"),
                        registro.getString("direccionCliente"),
                        registro.getString("telefonoCliente"),
                        registro.getString("correoCliente"));
           }
           
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    public Empleados buscarEmpleado(int cod){
        Empleados result=null;
        
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarEmpleado(?)}");
           procedimiento.setInt(1, cod);
           
           ResultSet registro = procedimiento.executeQuery();
           
           while(registro.next()){
               result=new Empleados(registro.getInt("idEmpleado"),
                                   registro.getString("nombreEmpleado"),
                                 registro.getString("apellidoEmpleado"),
                                          registro.getDouble("sueldo"),
                                           registro.getString("turno"),
                                registro.getInt("idCargoEmpleado"));
           }
           
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/Eliminar.png")); 
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                tipoDeOperaciones = operaciones.NULL;
                break;
            default:
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {

                    int resp = JOptionPane.showConfirmDialog(null, "Desea eliminar la factura?", "Eliminar factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (resp == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarFactura(?)}");
                            procedimiento.setInt(1, ((Facturas) tblFactura.getSelectionModel().getSelectedItem()).getIdFactura());
                            boolean execute = procedimiento.execute();
                            listarFactura.remove(tblFactura.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una factura para eliminar");
                }
                    cargarDatos();
                break;
                }
        }
    }
    
    public void editar(){
        switch (tipoDeOperaciones) {
            case NULL:
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {
                    imgReporte.setImage(new Image("/org/diegogarcia/images/Cancelar.png"));
                    imgEditar.setImage(new Image("/org/diegogarcia/images/Guardar.png"));
                    btnReporte.setText("Cancelar");
                    btnEditar.setText("actualizar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    txtIdFactura.setDisable(true);
                    activarControles();
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para editar");
                    break;
                }
            case ACTUALIZAR:
                actualizar();
                imgReporte.setImage(new Image("/org/diegogarciaa/images/Reporte.png"));
                imgEditar.setImage(new Image("/org/diegogarcia/images/Editar.png"));                
                btnReporte.setText("Reporte");
                btnEditar.setText("Editar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NULL;
                cargarDatos();
        }
    }
    
    public void actualizar(){
        Facturas registro = (Facturas)tblFactura.getSelectionModel().getSelectedItem();
        
        registro.setFechaFactura(dpFechaFactura.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        registro.setIdCliente(((Clientes) cbmCliente.getSelectionModel().getSelectedItem()).getIdCliente());
        registro.setIdEmpleado(((Empleados) cbmEmpleado.getSelectionModel().getSelectedItem()).getIdEmpleado());
        registro.setEstado(txtEstado.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotalFactura.getText()));
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarFactura(?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getIdFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getIdCliente());
            procedimiento.setInt(6, registro.getIdEmpleado());
            
            procedimiento.execute();
            
            listarFactura.add(registro);
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
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/diegogarcia/images/Actualizar.png"));
                imgReporte.setImage(new Image("/org/diegogarcia/images/Reporteria.png"));
                tipoDeOperaciones = MenuFacturaController.operaciones.NULL;
                break;
        }
    }
    
    
    
    // *************************************************************************************************************   
    
    
     public void desactivarControles() {
        txtIdFactura.setEditable(false);
        txtEstado.setEditable(false);
        txtTotalFactura.setEditable(false);
        dpFechaFactura.setEditable(false);
        cbmCliente.setDisable(true);
        cbmEmpleado.setDisable(true);
    }

    public void activarControles() {
        txtIdFactura.setEditable(true);
        txtEstado.setEditable(true);
        dpFechaFactura.setEditable(true);
        cbmCliente.setDisable(false);
        cbmEmpleado.setDisable(false);
    }

    public void limpiarControles() {
        txtIdFactura.clear();
        txtEstado.clear();
        txtTotalFactura.clear();
        dpFechaFactura.setValue(null);
        cbmCliente.setValue(null);
        cbmEmpleado.setValue(null);
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