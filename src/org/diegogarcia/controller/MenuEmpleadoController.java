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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.diegogarcia.bean.CargoEmpleado;
import org.diegogarcia.bean.Empleados;
import org.diegogarcia.db.Conexion;
import org.diegogarcia.system.main;


// *************************************************************************************************************



public class MenuEmpleadoController implements Initializable {

    private main escenarioPrincipal;

    private ObservableList<Empleados> listarEmpleados;
    private ObservableList<CargoEmpleado> listarCargoEmpleado;

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
    private TableView tblEmpleados;
    
    

    @FXML
    private TableColumn colIdEmpleado;

    @FXML
    private TableColumn colNombreEmpleado;

    @FXML
    private TableColumn colApellidoEmpleado;

    @FXML
    private TableColumn colSueldo;

    @FXML
    private TableColumn colTurno;

    @FXML
    private TableColumn colIdCargoEmpleado;
    
    
    

    @FXML
    private TextField txtIdEmpleado;

    @FXML
    private TextField txtNombreEmpleado;

    @FXML
    private TextField txtApellidoEmpleado;

    @FXML
    private TextField txtSueldo;

    @FXML
    private TextField txtTurno;
    
    

    @FXML
    private ComboBox cbmCargo;
    
    
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
        desactivarControles();
        tblEmpleados.setItems(getEmpleados());
        colIdEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("idEmpleado"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombreEmpleado"));
        colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidoEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colTurno.setCellValueFactory(new PropertyValueFactory<Empleados, String>("turno"));
        colIdCargoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("idCargoEmpleado"));
    }
    
    
    public void seleccionarElemento() {
        int idCargo = ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getIdCargoEmpleado();

        txtIdEmpleado.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getIdEmpleado()));
        txtNombreEmpleado.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
        txtApellidoEmpleado.setText((((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado()));
        txtSueldo.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        txtTurno.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getTurno());
        cbmCargo.getSelectionModel().select(buscar(idCargo));
    }

// *************************************************************************************************************    
    
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

    public ObservableList<CargoEmpleado> getCargoEmpleado() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCargoEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("idCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarCargoEmpleado = FXCollections.observableList(lista);
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
                txtIdEmpleado.setEditable(false);
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
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/Eliminar.png"));
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                tipoDeOperaciones = operaciones.NULL;
                break;
        }
    }

    public void guardar() {
        Empleados registro = new Empleados();
        registro.setNombresEmpleado(txtNombreEmpleado.getText());
        registro.setApellidosEmpleado(txtApellidoEmpleado.getText());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setTurno(txtTurno.getText());
        registro.setIdCargoEmpleado(((CargoEmpleado) cbmCargo.getSelectionModel().getSelectedItem()).getIdCargoEmpleado());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarEmpleado(?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNombresEmpleado());
            procedimiento.setString(2, registro.getApellidosEmpleado());
            procedimiento.setDouble(3, registro.getSueldo());
            procedimiento.setString(5, registro.getTurno());
            procedimiento.setInt(6, registro.getIdCargoEmpleado());

            procedimiento.execute();
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
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/Eliminar.png"));
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                tipoDeOperaciones = operaciones.NULL;
                break;
            default:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {

                    int resp = JOptionPane.showConfirmDialog(null, "Confirmae para eliminar el registro", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (resp == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarEmpleados(?)}");
                            procedimiento.setInt(1, ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getIdEmpleado());
                            boolean execute = procedimiento.execute();
                            listarEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Seleccione un Empleado para eliminar");
                    }
                    cargarDatos();
                    break;
                }
        }
    }

   

    public void editar() {
        switch (tipoDeOperaciones) {
            case NULL:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    imgReporte.setImage(new Image("/org/diegogarcia/images/Cancelar.png"));
                    imgEditar.setImage(new Image("/org/diegogarcia/images/Guardar.png"));
                    btnReporte.setText("Cancelar");
                    btnEditar.setText("actualizar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    txtIdEmpleado.setDisable(true);
                    activarControles();
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una tupla para editar");
                    break;
                }
            case ACTUALIZAR:
                actualizar();
                imgReporte.setImage(new Image("/org/diegogarcia/images/Reporte.png"));
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

    public void actualizar() {
        Empleados registro = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
        registro.setNombresEmpleado(txtNombreEmpleado.getText());
        registro.setApellidosEmpleado(txtApellidoEmpleado.getText());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setTurno(txtTurno.getText());
        registro.setIdCargoEmpleado(((CargoEmpleado) cbmCargo.getSelectionModel().getSelectedItem()).getIdCargoEmpleado());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_editarEmpleados(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getIdEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getIdCargoEmpleado());
            procedimiento.execute();

            listarEmpleados.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public CargoEmpleado buscar(int idCargo) {
        CargoEmpleado result = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCargoEmpleado(?)}");
            procedimiento.setInt(1, idCargo);

            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                result = new CargoEmpleado(registro.getInt("idCargoEmpleado"),
                        registro.getString("descripcion"),
                        registro.getString("descripcionCargo"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
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
                tipoDeOperaciones = MenuEmpleadoController.operaciones.NULL;
                break;
        }
    }
    
// *************************************************************************************************************   
    
    
    
    public void desactivarControles() {
        txtIdEmpleado.setEditable(false);
        txtNombreEmpleado.setEditable(false);
        txtApellidoEmpleado.setEditable(false);
        txtSueldo.setEditable(false);
        txtTurno.setEditable(false);
        cbmCargo.setDisable(false);
    }

    public void activarControles() {
        txtIdEmpleado.setEditable(true);
        txtNombreEmpleado.setEditable(true);
        txtApellidoEmpleado.setEditable(true);
        txtSueldo.setEditable(true);
        txtTurno.setEditable(true);
        cbmCargo.setDisable(true);
    }

    public void limpiarControles() {
        txtIdEmpleado.clear();
        txtNombreEmpleado.clear();
        txtApellidoEmpleado.clear();
        txtSueldo.clear();
        txtTurno.clear();
        cbmCargo.setValue(null);
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


