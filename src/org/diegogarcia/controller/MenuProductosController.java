package org.diegogarcia.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.diegogarcia.bean.Clientes;
import org.diegogarcia.bean.DetalleCompras;
import org.diegogarcia.bean.Productos;
import org.diegogarcia.bean.Proveedores;
import org.diegogarcia.bean.TipoProducto;
import org.diegogarcia.db.Conexion;
import org.diegogarcia.system.main;

// *************************************************************************************************************
public class MenuProductosController implements Initializable {

    private main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NULL

    }

    private operaciones tipoDeOperaciones = operaciones.NULL;

// *************************************************************************************************************
    private ObservableList<Productos> listaProductos;
    private ObservableList<Proveedores> listaProveedores;
    private ObservableList<TipoProducto> listaTipoProducto;

    @FXML
    private TableView<Productos> tblProductos;

    @FXML
    private Button btnTipoProducto;

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
    private TextField txtIdProducto;
    @FXML
    private TextField txtDescripcionProducto;
    @FXML
    private TextField txtPrecioUnitario;
    @FXML
    private TextField txtPrecioDocena;
    @FXML
    private TextField txtPrecioMayor;
    @FXML
    private TextField txtImagenProducto;
    @FXML
    private TextField txtExistencia;

    @FXML
    private ComboBox cmbTipoProducto;
    @FXML
    private ComboBox cmbProveedores;

    @FXML
    private TableColumn colIdProducto;
    @FXML
    private TableColumn colDescripcionProducto;
    @FXML
    private TableColumn colPrecioUnitario;
    @FXML
    private TableColumn colPrecioDocena;
    @FXML
    private TableColumn colPrecioMayor;
    @FXML
    private TableColumn colImagenProducto;
    @FXML
    private TableColumn colExistencia;
    @FXML
    private TableColumn colIdTipoProducto;
    @FXML
    private TableColumn colIdProveedores;

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
        tblProductos.setItems(getProducto());
        colIdProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("idProducto"));
        colDescripcionProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecioDocena.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colImagenProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("imagenProducto"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
        colIdTipoProducto.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("idTipoProducto"));
        colIdProveedores.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("idProveedores"));
    }

    public void seleccionarElemento() {
        try {
            txtIdProducto.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getIdProducto()));
            txtDescripcionProducto.setText((((Productos) tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto()));
            txtPrecioUnitario.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
            txtPrecioDocena.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
            txtPrecioMayor.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
            txtImagenProducto.setText((((Productos) tblProductos.getSelectionModel().getSelectedItem()).getImagenProducto()));
            txtExistencia.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));
            // cmbTipoProducto.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getIdTipoProducto()));
            // cmbProveedores.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getIdProveedores()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor selecciona una fila", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

// *************************************************************************************************************
    public ObservableList<Productos> getProducto() {
        ArrayList<Productos> listaP = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaP.add(new Productos(resultado.getInt("idProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getString("imagenProducto"),
                        resultado.getInt("existencia"),
                        resultado.getInt("idTipoProducto"),
                        resultado.getInt("idProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableList(listaP);
    }

// *************************************************************************************************************
    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> listaPro = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_mostrarproveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaPro.add(new Proveedores(resultado.getInt("idProveedor"),
                        resultado.getString("nitProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb"),
                        resultado.getString("telefonoProveedor"),
                        resultado.getString("emailProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableList(listaPro);
    }

// *************************************************************************************************************
    
    
    public ObservableList<TipoProducto> getTipoP() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("CodigoTipoProducto"),
                        resultado.getString("descripcionProducto")
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
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Guadar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/Cancelar.png"));
                tipoDeOperaciones = MenuProductosController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = MenuProductosController.operaciones.NULL;
        }
    }

    public void guardar() {
        Productos registro = new Productos();
        registro.setIdProducto(Integer.parseInt(txtIdProducto.getText()));
        registro.setIdProveedores(((Proveedores) cmbProveedores.getSelectionModel().getSelectedItem()).getIdProveedores());
        registro.setIdTipoProducto(((TipoProducto) cmbTipoProducto.getSelectionModel().getSelectedItem()).getIdTipoProducto());
        registro.setDescripcionProducto(txtDescripcionProducto.getText());
        registro.setPrecioDocena(Double.parseDouble("0.00"));
        registro.setPrecioMayor(Double.parseDouble("0.00"));
        registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
        registro.setImagenProducto(txtImagenProducto.getText());
        registro.setPrecioUnitario(Double.parseDouble("0.00"));

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_agregarProducto(?,?,?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getIdProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getIdTipoProducto());
            procedimiento.setInt(9, registro.getIdProveedores());
            procedimiento.execute();

            listaProductos.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NULL:
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnEliminar.setDisable(true);
                    btnAgregar.setDisable(true);
                    txtPrecioDocena.setDisable(true);
                    txtPrecioMayor.setDisable(true);
                    txtPrecioUnitario.setDisable(true);
                    activarControles();
                    txtIdProducto.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar para editar");
                }
                break;
            case ACTUALIZAR:
                try {
                    actualizar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                btnEditar.setText("Editar");
                btnReporte.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NULL;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_editarProducto(?,?,?,?,?,?,?,?,?)}");
            Productos registro = (Productos) tblProductos.getSelectionModel().getSelectedItem();
            registro.setIdProveedores(((Proveedores) cmbProveedores.getSelectionModel().getSelectedItem()).getIdProveedores());
            registro.setIdTipoProducto((((TipoProducto) cmbTipoProducto.getSelectionModel().getSelectedItem()).getIdTipoProducto()));
            registro.setDescripcionProducto(txtDescripcionProducto.getText());
            registro.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
            registro.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
            registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
            registro.setImagenProducto(txtImagenProducto.getText());
            registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
            procedimiento.setInt(1, registro.getIdProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getIdTipoProducto());
            procedimiento.setInt(9, registro.getIdProveedores());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() throws SQLException {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = operaciones.NULL;
                break;
            default:
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Estas seguro que deseas elimnarlos", "Verificación",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            Productos productoSeleccionado = (Productos) tblProductos.getSelectionModel().getSelectedItem();
                            int idProductos = productoSeleccionado.getIdProducto();

                            PreparedStatement eliminarDetalleCompraStmt = Conexion.getInstance().getConexion()
                                    .prepareCall("{call sp_eliminarDetalleCompraPorProducto(?)}");
                            eliminarDetalleCompraStmt.setInt(1, idProductos);
                            eliminarDetalleCompraStmt.execute();

                            PreparedStatement eliminarProductoStmt = Conexion.getInstance().getConexion()
                                    .prepareCall("{call sp_eliminarProducto(?)}");
                            eliminarProductoStmt.setInt(1, idProductos);
                            eliminarProductoStmt.execute();

                            listaProductos.remove(productoSeleccionado);
                            limpiarControles();
                            cargarDatos();
                            JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente");
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione para poder eliminar");
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
                tipoDeOperaciones = MenuProductosController.operaciones.NULL;
        }
    }


    
    
    public Proveedores buscarProveedor(int codigoProveedor) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProveedor(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Proveedores(registro.getInt("idProveedores"),
                        registro.getString("nitProveedor"),
                        registro.getString("nombreProveedor"),
                        registro.getString("apellidosProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getString("razonSocial"),
                        registro.getString("contactoPrincipal"),
                        registro.getString("paginaWeb"),
                        registro.getString("telefonoProveedor"),
                        registro.getString("emailProveedor")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    
        public TipoProducto buscarTipoProducto(int idTipoProducto) {
        TipoProducto resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarTipoProducto(?)}");
            procedimiento.setInt(1, idTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new TipoProducto(registro.getInt("idTipoProducto"),
                        registro.getString("descripcionProducto")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    
    
// *************************************************************************************************************

    public void desactivarControles() {
        txtIdProducto.setEditable(false);
        txtDescripcionProducto.setEditable(false);
        txtExistencia.setEditable(false);
        txtImagenProducto.setEditable(false);
        txtPrecioDocena.setEditable(false);
        txtPrecioMayor.setEditable(false);
        txtPrecioUnitario.setEditable(false);
        cmbTipoProducto.setDisable(true);
        cmbProveedores.setDisable(true);
    }

    public void activarControles() {
        txtIdProducto.setEditable(true);
        txtDescripcionProducto.setEditable(true);
        txtExistencia.setEditable(true);
        txtImagenProducto.setEditable(true);
        txtPrecioDocena.setEditable(true);
        txtPrecioMayor.setEditable(true);
        txtPrecioUnitario.setEditable(true);
        cmbTipoProducto.setDisable(false);
        cmbProveedores.setDisable(false);
    }

    public void limpiarControles() {
        txtIdProducto.clear();
        txtDescripcionProducto.clear();
        txtExistencia.clear();
        txtImagenProducto.clear();
        txtPrecioDocena.clear();
        txtPrecioMayor.clear();
        txtPrecioUnitario.clear();
        tblProductos.getSelectionModel().getSelectedItem();
        cmbTipoProducto.getSelectionModel().clearSelection();
        cmbProveedores.getSelectionModel().clearSelection();

    }
    
    
// *************************************************************************************************************
    
    
    public main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void handleButtonAction(ActionEvent event) {

        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
        if (event.getSource() == btnTipoProducto) {
            escenarioPrincipal.menuTipoProductoView();
        }
    }

}
