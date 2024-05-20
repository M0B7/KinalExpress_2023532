package org.diegogarcia.controller;

import java.net.URL;
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
import org.diegogarcia.bean.CargoEmpleado;
import org.diegogarcia.db.Conexion;
import org.diegogarcia.system.main;

public class MenuCargoEmpleadoController implements Initializable {

    private ObservableList<CargoEmpleado> listaCargoEmpleado;
    private main escenarioPrincipal;

    private static class colIdCargo {

        private static void setCellValueFactory(PropertyValueFactory<CargoEmpleado, Integer> propertyValueFactory) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public colIdCargo() {
        }
    }

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private TableView<CargoEmpleado> tblCargoEmpleado;
    @FXML
    private TableColumn colIdCargo;
    @FXML
    private TableColumn colCargoEmpleado;
    @FXML
    private TableColumn colDescripcionCargo;
    @FXML
    private TextField txtIdCargo;
    @FXML
    private TextField txtCargo;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgBuscar;
    @FXML
    private ImageView imgEliminar;

    public void menuPrincipalView() {
        escenarioPrincipal.menuPrincipalView();
    }

    public void setEscenarioPrincipal(main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosCE();
    }

    public ObservableList<CargoEmpleado> getCargoEmpleado() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCargoEmpleados()}");
            ResultSet result = procedure.executeQuery();
            {
                while (result.next()) {
                    lista.add(new CargoEmpleado(
                            result.getInt("idCargoEmpleado"),
                            result.getString("nombreCargo"),
                            result.getString("descripcionCargo")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCargoEmpleado = FXCollections.observableList(lista);
    }

    public void cargarDatosCE() {
        tblCargoEmpleado.setItems(getCargoEmpleado());
        colIdCargo.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("idCargoEmpleado"));
        colCargoEmpleado.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("nombreCargo"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));

    }

    public void seleccionarElemento() {

        txtIdCargo.setText(String.valueOf(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getIdCargoEmpleado()));
        txtCargo.setText((((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getNombreCargo()));
        txtDescripcion.setText(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getDescripcionCargo());
    }

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/diegogarcia/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/Cancelar.png"));
                tipoDeOperaciones = MenuCargoEmpleadoController.operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/diegogarcia/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/diegogarcia/images/waste.png"));
                tipoDeOperaciones = MenuCargoEmpleadoController.operaciones.NINGUNO;
        }
    }

    public void guardar() {
        CargoEmpleado register = new CargoEmpleado();
        register.setIdCargoEmpleado(Integer.parseInt(txtIdCargo.getText()));
        register.setNombreCargo(txtCargo.getText());
        register.setDescripcionCargo(txtDescripcion.getText());

        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCargoEmpleado(?, ?, ?)}");
            procedure.setInt(1, register.getIdCargoEmpleado());
            procedure.setString(2, register.getNombreCargo());
            procedure.setString(3, register.getDescripcionCargo());

            procedure.execute();
            listaCargoEmpleado.add(register);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/diegogarcia/images/Editar.png"));
                    imgBuscar.setImage(new Image("/org/diegogarcia/images/Cancelar.png"));
                    activarControles();
                    txtIdCargo.setEditable(false);
                    tipoDeOperaciones = MenuCargoEmpleadoController.operaciones.ACTUALIZAR;
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
                imgEditar.setImage(new Image("/org/diegogarcia/images/editar.png"));
                imgBuscar.setImage(new Image("/org/diegogarcia/images/buscar.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = MenuCargoEmpleadoController.operaciones.NINGUNO;
                cargarDatosCE();

                break;
        }
    }

    public void actualizar() {
        // Verificar si si se ha seleccionado
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_editarCargoEmpleado(?,?)}");
            CargoEmpleado register = (CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem();

            register.setNombreCargo(txtCargo.getText());
            register.setDescripcionCargo(txtDescripcion.getText());
            procedure.setInt(1, register.getIdCargoEmpleado());
            procedure.setString(2, register.getNombreCargo());
            procedure.setString(3, register.getDescripcionCargo());
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
                tipoDeOperaciones = MenuCargoEmpleadoController.operaciones.NINGUNO;
                cancelar();
                break;
            default:
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar este Crago de Empleado?", "Eliminar Cargo de Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCargoEmpleado(?)}");
                            procedure.setInt(1, ((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getIdCargoEmpleado());
                            procedure.execute();
                            listaCargoEmpleado.remove(tblCargoEmpleado.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cargo de empleado para eliminar");
                }
                break;

        }
    }

    public void cancelar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
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
        txtIdCargo.setEditable(false);
        txtCargo.setEditable(false);
        txtDescripcion.setEditable(false);

    }

    public void activarControles() {
        txtIdCargo.setEditable(true);
        txtCargo.setEditable(true);
        txtDescripcion.setEditable(true);
    }

    public void limpiarControles() {
        txtIdCargo.clear();
        txtCargo.clear();
        txtDescripcion.clear();

    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
