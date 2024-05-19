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
import org.diegogarcia.bean.Clientes;
import org.diegogarcia.db.Conexion;
import org.diegogarcia.system.main;

public class MenuClientesController implements Initializable {

    private main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NULL
    }
    private operaciones tipoDeOperaciones = operaciones.NULL;
    
    ObservableList<Clientes> listaClientes;

    @FXML
    private TableView<Clientes> tblCliente;
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
    private ImageView imgReporte;
    @FXML
    private TableColumn<Clientes, Integer> colClienteId;
    @FXML
    private TableColumn<Clientes, String> colNombreCliente;
    @FXML
    private TableColumn<Clientes, String> colApellidoCliente;
    @FXML
    private TableColumn<Clientes, String> colDireccionCliente;
    @FXML
    private TableColumn<Clientes, String> colTelefonoCliente;
    @FXML
    private TableColumn<Clientes, String> colCorreoCliente;
    @FXML
    private TableColumn<Clientes, String> colNitCliente;

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
        tblCliente.setItems(getClientes());
        colClienteId.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colNitCliente.setCellValueFactory(new PropertyValueFactory<>("nitCliente"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombresCliente"));
        colApellidoCliente.setCellValueFactory(new PropertyValueFactory<>("apellidosCliente"));
        colDireccionCliente.setCellValueFactory(new PropertyValueFactory<>("direccionCliente"));
        colTelefonoCliente.setCellValueFactory(new PropertyValueFactory<>("telefonoCliente"));
        colCorreoCliente.setCellValueFactory(new PropertyValueFactory<>("correoCliente"));
    }

    public void seleccionarElemento() {
        Clientes clienteSeleccionado = tblCliente.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            txtIdCliente.setText(String.valueOf(clienteSeleccionado.getIdCliente()));
            txtNitCliente.setText(clienteSeleccionado.getNitCliente());
            txtNombreCliente.setText(clienteSeleccionado.getNombresCliente());
            txtApellidoCliente.setText(clienteSeleccionado.getApellidosCliente());
            txtTelefonoCliente.setText(clienteSeleccionado.getTelefonoCliente());
            txtCorreoCliente.setText(clienteSeleccionado.getCorreoCliente());
            txtDireccionCliente.setText(clienteSeleccionado.getDireccionCliente());
        }
    }

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

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NULL:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/Cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                cargarDatos();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/Eliminar.png"));
                tipoDeOperaciones = operaciones.NULL;
                break;
        }
    }

    public void guardar() {
        Clientes registro = new Clientes();
        registro.setIdCliente(Integer.parseInt(txtIdCliente.getText()));
        registro.setNombresCliente(txtNombreCliente.getText());
        registro.setApellidosCliente(txtApellidoCliente.getText());
        registro.setNitCliente(txtNitCliente.getText());
        registro.setTelefonoCliente(txtTelefonoCliente.getText());
        registro.setDireccionCliente(txtDireccionCliente.getText());
        registro.setCorreoCliente(txtCorreoCliente.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCliente(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getIdCliente());
            procedimiento.setString(2, registro.getNitCliente());
            procedimiento.setString(3, registro.getNombresCliente());
            procedimiento.setString(4, registro.getApellidosCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
            listaClientes.add(registro);
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
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/Eliminar.png"));
                tipoDeOperaciones = operaciones.NULL;
                break;
            default:
                if (tblCliente.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Por favor confirma la eliminación del registro", "Eliminar Cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_borrarCliente(?)}");
                            procedimiento.setInt(1, ((Clientes) tblCliente.getSelectionModel().getSelectedItem()).getIdCliente());
                            procedimiento.execute();
                            listaClientes.remove(tblCliente.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para poder eliminarlo.");
                }
                break;
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NULL:
                if (tblCliente.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/diegogarcia/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/diegogarcia/images/Cancelar.png"));
                    activarControles();
                    txtIdCliente.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un cliente para editarlo.");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/diegogarcia/images/Editar.png"));
                imgReporte.setImage(new Image("/org/diegogarcia/images/Reporte.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NULL;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_editarCliente(?, ?, ?, ?, ?, ?, ?)}");
            Clientes registro = tblCliente.getSelectionModel().getSelectedItem();
            registro.setNitCliente(txtNitCliente.getText());
            registro.setNombresCliente(txtNombreCliente.getText());
            registro.setApellidosCliente(txtApellidoCliente.getText());
            registro.setTelefonoCliente(txtTelefonoCliente.getText());
            registro.setDireccionCliente(txtDireccionCliente.getText());
            registro.setCorreoCliente(txtCorreoCliente.getText());
            procedimiento.setInt(1, registro.getIdCliente());
            procedimiento.setString(2, registro.getNitCliente());
            procedimiento.setString(3, registro.getNombresCliente());
            procedimiento.setString(4, registro.getApellidosCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reportes() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/diegogarcia/images/Editar.png"));
                imgReporte.setImage(new Image("/org/diegogarcia/images/Reporte.png"));
                tipoDeOperaciones = operaciones.NULL;
                break;
        }
    }

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
