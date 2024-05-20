package org.diegogarcia.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/Eliminar.png"));
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
                if (tblCliente.getSelectionModel().getSelectedItem() != null) {
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
            Clientes register = (Clientes) tblCliente.getSelectionModel().getSelectedItem();

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
                btnEditar.setText("Editar");
                btnEliminar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Agregar.png"));
                imgEditar.setImage(new Image("/org/diegogarcia/images/Editar.png"));
                tipoDeOperaciones = operaciones.NULL;
                cancelar();
                break;
            default:
                if (tblCliente.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar al cliente?", "Eliminar Cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
                            procedure.setInt(1, ((Clientes) tblCliente.getSelectionModel().getSelectedItem()).getIdCliente());
                            procedure.execute();
                            listaClientes.remove(tblCliente.getSelectionModel().getSelectedItem());
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
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Agregar.png"));
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
