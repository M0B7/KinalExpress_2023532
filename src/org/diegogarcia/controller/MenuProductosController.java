package org.diegogarcia.controller;

import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.diegogarcia.bean.Productos;

/**
 *
 * @author informatica
 */
public class MenuProductosController implements Initializable {
    
    private Principal escnearioPrincipal;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Productos> listaProductos;
    //private ObservableList <Proveedores> listaProveedores;
    
    //import productos
    //@FXML private TextField txtIdProducto;
    @FXML private TextField txtDescripcion;
    @FXML private ComboBox cmbIdTipoProducto;
    @FXML private ComboBox cmbIdProveedor;
    @FXML private TableView tblProductos;
    @FXML private TableColumn colPrecioUnirario;
     
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        cargarDatos();
        //cmbIdTipoProducto.setItems(getTipoProducto());
        //colIdProveedor.setItems(getProveedores());
        
    }
    
    public void cargarDatos(){
        //tblProductos.setItems(getProductos());
       // colIdProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("idProducto"));
       // colDescripcionProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("idProducto"));
       // colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Productos, String>("idProducto"));
       // colPrecioDocena.setCellValueFactory(new PropertyValueFactory<Productos, String>("idProducto"));
       /// colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Productos, String>("idProducto"));
        
        
        
        
        
        
    }
    
    
    public void seleccionarElementos(){
        //txtIdProducto.setText(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getIdProducto());
       // txtDescripcionProducto.setText(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        //txtPrecioUnitario.setText(String.valueOf((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario());
        //txtPrecioDocena.setText(String.valueOf((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena());
        //txtPrecioMayor.setText(String.valueOf((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor());
        //txtExistencia
        //cmbIdTipoProducto.getSelectionModel().select(buscarTipoProducto(((Productos)tblProdctos.getSelectionModel().getSelectedItem()).getIdProducto));
        
    }
    
    //public TipoDeProducto buscarTipoProducto(int idTipoProducto){
       // TipoDeProduct resultado = null;
        //try{
           // PreparedStatement procedimiento = Conexion.getInstance() call sp Buscartiopo (?)
          //  procedimiento.setInt(1, idTipoProducto);
          //  ResultSet registro = procedimiento.executeQuery();
           // while(registro.next()){
            //    resultado = new TipoDeProducto(registro.getInt("idTipoProducto"), registro.getString("descripcionProducto"));
            }
            //SQuery solo va a mostrar
           // procedimiento.setString(2, descripcionProducto);
       // }catch(Exception e){
          //  e.printStackTrace();
       // }
    
        
       // return resultado;
        
    //}
    
    
    
   // public ObservableList <Productos> getProductos(){
      //  ArrayList<Productos> lista = new ArrayList <Productos>();
      //  try{
           // PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("fiusdui");
           // ResulSet resultado = procedimiento.executeQuery();
           // while(resultado.next()){
              //  lista.add(new Productos (resultado.getString("idProducto"),
                //                        resultado.getString("descripcionProducto"),
                 //                       resultado.getDouble("precioUnitario"),
                 //                       resultado.getDouble("precioDocena"),
                  //                      resultado.getDouble("precioMayor"),
                   //                     resultado.getInt("existencia"),
                  //                     resultado.getInt("idTipoProducto"),
                   //                     resultado.getInt("idProveedor")
               // ));
                        
         //   }
      //  }catch (Exception e ){
       //     e.printStackTrace();
      //  }
        
       // return listaProductos = FXCollections.observableArrayList(lista);
        
        
   // }
    
    // public ObservableList <Productos> getProveedores(){
      //  ArrayList<Productos> lista = new ArrayList <Productos>();
      //  try{
       //     PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("fiusdui");
        //    ResulSet resultado = procedimiento.executeQuery();
          //  while(resultado.next()){
         //       lista.add(new Productos (resultado.getString("idProveedor"),
           //                             resultado.getString("nitProveedor"),
                        // se llama a proveedores tambien
           //                             resultado.getDouble("precioUnitario"),
           //                             resultado.getDouble("precioDocena"),
          //                              resultado.getDouble("precioMayor"),
           //                             resultado.getInt("existencia"),
           //                             resultado.getInt("idTipoProducto"),
           //                             resultado.getInt("idProveedor")
           //     ));
          //              
         //   }
        //}catch (Exception e ){
       //     e.printStackTrace();
       // }
        
       // return listaProductos = FXCollections.observableArrayList(lista);
        
        
    //}
     
     //public ObservableList <Productos> getTipoProducto(){
      //  ArrayList<Productos> lista = new ArrayList <Productos>();
      //  try{
        //    PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("fiusdui");
        //    ResulSet resultado = procedimiento.executeQuery();
         //   while(resultado.next()){
           //     lista.add(new Productos (resultado.getString("idProveedor"),
             //                           resultado.getString("nitProveedor"),
            //            // se llama a proveedores tambien
                   //                     resultado.getDouble("precioUnitario"),
                   //                     resultado.getDouble("precioDocena"),
                   //                     resultado.getDouble("precioMayor"),
                    //                    resultado.getInt("existencia"),
                     //                   resultado.getInt("idTipoProducto"),
                     //                   resultado.getInt("idProveedor")
              //  ));
                        
           // }
        //}catch (Exception e ){
        //    e.printStackTrace();
     //   }
        
        //return listaTipoProductos = FXCollections.observableArrayList(lista);
        
        
    //}
     
     
    /**public void agregar (){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnagregar.setTextGuardar
                btnElkiminar.setText cancelar
                btn editar.setdisable true
                btnreporte.setdisale true
                tipodeoperacion = operaciones.ACTUALIZAR
             
                        
            CASE ACTUALIZAR
                    guardar();
                    desactivarcontroles
                            limpiarcontroles
                            btnagregar
              
       */           
                
      //  }
        
  //  }
  /** 
    public void guardar(){
        //Parsear
        Producto registro = new Producto();
        registro.setidProducto(txtIdProducto.getText());
        registro.setCodigoProveedor(((Proveedor)cmbIdProveedores.getSelectionModel().getSelectedItem()).getIdProveedor());
        //Se castean las tablas
        // ComboBox funciona igual a una tabla
        registro.setIdTipoProducto(((TipoDeProducto)cmbColTipoProduto.getSelectionModel().getSelectedItem()).getIdTipoProducto());
        registro.setDescripcionProducto(txtDescripcionProducto.getText);
        registro.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
        registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
        try{
            Prepared Statement procedimiento = Conexion.getInstance().getConexion().prepareCall{"{call sp_agregarProducto(?,?,?,?,?)}");
            procedimiento.setString(1, registro.getIdProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setInteger(6, registro.getExistencia());
            procedimiento.setInteger(7, registro.getIdProveedor());
            procedimiento.setInteger(8, registro.getIdTipoProducto());
            procedimiento.execute();
            listaProducto.add
            
        }catch (Exception e){
            e.printStackTrace();
        }
    
    }
    
  */  
   /** 
    public void desactivarControles(){
        txtIdProducto.setEditable(false);
        txtDescripcion.setEditable(false);
        //van todos los txt
        cmbIdTipoProducto.setEditable(false);
        //
    }
    
    public void activarControles(){
        txtIdProducto.setEditable(true);
        txtDescripcion.setEditable(true);
        //van todos los txt
        cmbIdTipoProducto.setEditable(true);
        //
    }
    
    
    public void limpiarControles(){
        txtIdProducto.clear();
        txtDescripcion.clear();
        //van todos los txt
        tblProductos.getSelectionModel().getSelectedItem();
        //Se maneja igual que un tableview
        cmbIdTipoProducto.getSelectionModel().getSelectedItem();
        
        
    }
    
}
*/