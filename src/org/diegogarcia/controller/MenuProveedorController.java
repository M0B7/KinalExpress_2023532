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
import org.diegogarcia.bean.Proveedores;
import org.diegogarcia.db.Conexion;
import org.diegogarcia.system.main;

// *************************************************************************************************************
public class MenuProveedorController implements Initializable {

    private main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NULL

    }

    private operaciones tipoDeOperaciones = operaciones.NULL;

// *************************************************************************************************************
    
    
    private ObservableList<Proveedores> listaProveedores;

    @FXML
    private TableView<Proveedores> tblProveedores;

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
    private Button btnEmail;
    @FXML
    private Button btnTelefono;

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
    private TextField txtIdProveedor;
    @FXML
    private TextField txtNitProveedor;
    @FXML
    private TextField txtNombreProveedor;
    @FXML
    private TextField txtApellidoProveedor;
    @FXML
    private TextField txtDireccionProveedor;
    @FXML
    private TextField txtRazonSocial;
    @FXML
    private TextField txtContactoPrincipal;
    @FXML
    private TextField txtPaginaWeb;
    @FXML 
    private TextField txtNumeroTelefono;

    @FXML
    private TableColumn colIdProveedores;
    @FXML
    private TableColumn colNitProveedor;
    @FXML
    private TableColumn colNombreProveedor;
    @FXML
    private TableColumn colApellidoProveedor;
    @FXML
    private TableColumn colDireccionProveedor;
    @FXML
    private TableColumn colRazonSocial;
    @FXML
    private TableColumn colContactoPrincipal;
    @FXML
    private TableColumn colPaginaWeb;
    @FXML
    private TableColumn colNumeroTelefono;

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
        tblProveedores.setItems(getProveedor());
        colIdProveedores.setCellValueFactory(new PropertyValueFactory<>("idProveedores"));
        colNitProveedor.setCellValueFactory(new PropertyValueFactory<>("nitProveedor"));
        colNombreProveedor.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        colApellidoProveedor.setCellValueFactory(new PropertyValueFactory<>("apellidosProveedor"));
        colDireccionProveedor.setCellValueFactory(new PropertyValueFactory<>("direccionProveedor"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<>("razonSocial"));
        colContactoPrincipal.setCellValueFactory(new PropertyValueFactory<>("contactoPrincipal"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory<>("paginaWeb"));
        colNumeroTelefono.setCellValueFactory(new PropertyValueFactory<>("numeroTelefono"));
    }

    public void seleccionarElemento() {
        Proveedores proveedorSeleccionado = tblProveedores.getSelectionModel().getSelectedItem();
        if (proveedorSeleccionado != null) {
            txtIdProveedor.setText(String.valueOf(proveedorSeleccionado.getIdProveedores()));
            txtNitProveedor.setText(proveedorSeleccionado.getNitProveedor());
            txtNombreProveedor.setText(proveedorSeleccionado.getNombreProveedor());
            txtApellidoProveedor.setText(proveedorSeleccionado.getApellidosProveedor());
            txtDireccionProveedor.setText(proveedorSeleccionado.getDireccionProveedor());
            txtRazonSocial.setText(proveedorSeleccionado.getRazonSocial());
            txtContactoPrincipal.setText(proveedorSeleccionado.getContactoPrincipal());
            txtPaginaWeb.setText(proveedorSeleccionado.getPaginaWeb());
            txtNumeroTelefono.setText(proveedorSeleccionado.getNumeroTelefono());
        }
    }

// *************************************************************************************************************
    public ObservableList<Proveedores> getProveedor() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Proveedores(
                        resultado.getInt("idProveedores"),
                        resultado.getString("nitProveedor"),
                        resultado.getString("nombreProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb"),
                        resultado.getString("numeroTelefono")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableList(lista);
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
        Proveedores register = new Proveedores();
        register.setIdProveedores(Integer.parseInt(txtIdProveedor.getText()));
        register.setNombreProveedor(txtNombreProveedor.getText());
        register.setApellidosProveedor(txtApellidoProveedor.getText());
        register.setDireccionProveedor(txtDireccionProveedor.getText());
        register.setNitProveedor(txtNitProveedor.getText());
        register.setRazonSocial(txtRazonSocial.getText());
        register.setContactoPrincipal(txtContactoPrincipal.getText());
        register.setPaginaWeb(txtPaginaWeb.getText());
        register.setNumeroTelefono(txtNumeroTelefono.getText());
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{sp_agregarProveedor(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            procedure.setInt(1, register.getIdProveedores());
            procedure.setString(2, register.getNombreProveedor());
            procedure.setString(3, register.getApellidosProveedor());
            procedure.setString(4, register.getDireccionProveedor());
            procedure.setString(5, register.getNitProveedor());
            procedure.setString(6, register.getRazonSocial());
            procedure.setString(7, register.getContactoPrincipal());
            procedure.setString(7, register.getPaginaWeb());
            procedure.setString(8, register.getNumeroTelefono());
            procedure.execute();
            listaProveedores.add(register);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NULL:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/diegogarcia/images/Guardar.png"));
                    imgEliminar.setImage(new Image("/org/diegogarcia/images/Cancelar.png"));
                    activarControles();
                    txtIdProveedor.setEditable(false);
                    tipoDeOperaciones = MenuProveedorController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = MenuProveedorController.operaciones.NULL;
                cargarDatos();

                break;
        }
    }

    public void actualizar() {

        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_editarProveedor(?,?,?,?,?,?,?)}");
            Proveedores register = (Proveedores) tblProveedores.getSelectionModel().getSelectedItem();

            register.setNombreProveedor(txtNombreProveedor.getText());
            register.setNitProveedor(txtNitProveedor.getText());
            register.setApellidosProveedor(txtApellidoProveedor.getText());
            register.setDireccionProveedor(txtDireccionProveedor.getText());
            register.setRazonSocial(txtRazonSocial.getText());
            register.setContactoPrincipal(txtContactoPrincipal.getText());
            register.setPaginaWeb(txtPaginaWeb.getText());
            procedure.setInt(1, register.getIdProveedores());
            procedure.setString(2, register.getNitProveedor());
            procedure.setString(3, register.getNombreProveedor());
            procedure.setString(4, register.getApellidosProveedor());
            procedure.setString(5, register.getDireccionProveedor());
            procedure.setString(6, register.getRazonSocial());
            procedure.setString(7, register.getContactoPrincipal());
            procedure.setString(8, register.getPaginaWeb());
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
                tipoDeOperaciones = MenuProveedorController.operaciones.NULL;
                cancelar();
                break;
            default:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar al proveedor?", "Eliminar Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{sp_eliminarProveedor(?)}");
                            procedure.setInt(1, ((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getIdProveedores());
                            procedure.execute();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un proveedor para eliminar");
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
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperaciones = MenuProveedorController.operaciones.NULL;
        }
    }

// *************************************************************************************************************
    public void desactivarControles() {
        txtIdProveedor.setEditable(false);
        txtNombreProveedor.setEditable(false);
        txtApellidoProveedor.setEditable(false);
        txtNitProveedor.setEditable(false);
        txtDireccionProveedor.setEditable(false);
        txtRazonSocial.setEditable(false);
        txtContactoPrincipal.setEditable(false);
        txtPaginaWeb.setEditable(false);
        txtNumeroTelefono.setEditable(false);
    }

    public void activarControles() {
        txtIdProveedor.setEditable(true);
        txtNombreProveedor.setEditable(true);
        txtApellidoProveedor.setEditable(true);
        txtNitProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtRazonSocial.setEditable(true);
        txtContactoPrincipal.setEditable(true);
        txtPaginaWeb.setEditable(true);
        txtNumeroTelefono.setEditable(true);
    }

    public void limpiarControles() {
        txtIdProveedor.clear();
        txtNombreProveedor.clear();
        txtApellidoProveedor.clear();
        txtNitProveedor.clear();
        txtDireccionProveedor.clear();
        txtRazonSocial.clear();
        txtContactoPrincipal.clear();
        txtPaginaWeb.clear();
        txtNumeroTelefono.clear();
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
