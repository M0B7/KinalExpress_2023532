create database if not exists KinalExpress;
use KinalExpress;
set global time_zone = '-6:00';

-- Nombre: Diego Fernando Garcia Galvez
-- Carnet: 2023532
-- Seccion: IN5BM
-- Fecha de Creacion: 08/05/2024

create table if not exists Clientes(
idCliente int not null,
nitCliente varchar(20),
nombresCliente varchar(40),
apellidosCliente varchar(40),
direccionCliente varchar(60),
telefonoCliente varchar(10),
correoCliente varchar(40),
primary key Pk_idCliente(idCliente)
)engine innodb;

create table if not exists Proveedores(
idProveedores int not null,
nitProveedor varchar(20),
nombreProveedor varchar(40),
apellidosProveedor varchar(40),
direccionProveedor varchar(80),
razonSocial varchar(90),
contactoPrincipal varchar(90),
paginaWeb varchar(90) not null,
primary key PK_idProveedores(idProveedores)
)engine innodb;

create table if not exists CargoEmpleado(
idCargoEmpleado int,
nombreCargo varchar(40),
descripcionCargo varchar(70),
primary key PK_idCargoEmpleado(idCargoEmpleado)
)engine innodb;

create table if not exists Empleados(
idEmpleado int not null,
nombresEmpleado varchar(40),
apellidosEmpleado varchar(40),
sueldo decimal(10,2),
turno varchar(10),
idCargoEmpleado int,
primary key PK_idEmpleado(idEmpleado),     
constraint FK_Empleados_CargoEmpleado foreign key CargoEmpleado(idCargoEmpleado)    
	references CargoEmpleado(idCargoEmpleado) 
)engine innodb;


create table if not exists Compras(
idCompra int not null,
fechaDocumento date,
descripcion varchar(90) ,
totalDocumento decimal(10,2),
primary key PK_idCompra(idCompra)
)engine innodb;

create table if not exists TipoProducto(
idTipoProducto int not null,
descripcion varchar(90),
primary key PK_idTipoProducto(idTipoProducto)
)engine innodb;

create table if not exists Productos(
idProducto int not null,
descripcionProducto varchar(90),
precioUnitario decimal(10,2),
precioDocena decimal(10,2),
precioMayor decimal(10,2),
imagenProducto varchar(45),
existencia int,
idTipoProducto int,
idProveedores int,
primary key PK_idProducto(idProducto),
constraint FK_Productos_TipoProducto foreign key TipoProducto(idTipoProducto)    
references TipoProducto(idTipoProducto),
constraint FK_Productos_Proveedores foreign key Proveedores(idProveedores)
references Proveedores(idProveedores) 
)engine innodb;


create table if not exists TelefonoProveedor(
idTelefonoProveedor int not null,
numeroPrincipal varchar(20),
numeroSecundario varchar(20),
observaciones varchar(90),
idProveedores int not null,
primary key PK_idTelefonoProveedor(idTelefonoProveedor),
constraint FK_TelefonoProveedor_Proveedores foreign key Proveedores(idProveedores)
references Proveedores(idProveedores)
)engine innodb;

create table if not exists EmailProveedor(
idEmailProveedor int not null,
emailProveedor varchar(90),
descripcion varchar(90),
idProveedores int not null,
primary key PK_idEmailProveedor(idEmailProveedor), 
constraint FK_EmailProveedor_Proveedores foreign key Proveedores(idProveedores)
references Proveedores(idProveedores)
)engine innodb;


create table if not exists DetalleCompras(
idDetalleCompra int not null,
precioUnitario decimal(10,2),
cantidad int,
idProducto int,
idCompra int,
primary key PK_idDetalleCompras(idDetalleCompra),
constraint FK_DetalleCompras_Productos foreign key Productos(idProducto)
references Productos(idProducto),
constraint FK_DetalleCompras_Compras foreign key Compras(idCompra)
references Compras(idCompra)
)engine innodb;

create table if not exists Factura(
idFactura int not null,
estado varchar(45),
totalFactura decimal(10,2),
fechaFactura varchar(45),
idEmpleado int,
idCliente int,
primary key PK_idFactura(idFactura),
constraint FK_Factura_Empleados foreign key Empleados(idEmpleado)
references Empleados(idEmpleado),
constraint FK_Factura_Clientes foreign key Clientes(idCliente)
references Clientes(idCliente)
)engine innodb;

create table if not exists DetalleFactura(
idDetalleFactura int not null,
precioUnitario decimal(10,2),
cantidad int,
idFactura int,
idProducto int,
primary key PK_idDetalleFactura(idDetalleFactura),
constraint FK_DetalleFactura_Factura foreign key Factura(idFactura)
references Factura(idFactura),
constraint FK_DetalleFactura_Productos foreign key Productos(idProducto)
references Productos(idProducto)
)engine innodb


-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------
-- PROCEDIMIENTOS ALMACENADOS
-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------
-- CLIENTES
-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------


-- AGREGAR

Delimiter $$
	create procedure sp_agregarCliente (in idCli int, in nitCli varchar(20), in nombresCli varchar(40), in apellidosCli varchar(40), in direccionCli varchar(60), in telefonoCli varchar(10), in correoCli varchar(45))
    begin
		insert into Clientes(idCliente, nitCliente, nombresCliente, apellidosCliente, direccionCliente, telefonoCliente, correoCliente)
			values (idCli, nitCli, nombresCli, apellidosCli, direccionCli, telefonoCli, correoCli);
            
	End $$
Delimiter ;

call sp_AgregarCliente(1, '6755945-1', 'Jordi', 'Wild', '4ta calle sur 1-20', 63548574, 'dgarcia-2023532@kinal.edu');

-- LISTAR

Delimiter $$
	create procedure sp_ListarClientes()
		begin
			select
            C.idCliente,
            C.nitCliente,
            C.nombresCliente,
            C.apellidosCliente,
            C.direccionCliente,
            C.telefonoCliente,
            C.correoCliente
            from Clientes C;
		end $$
Delimiter ;

call sp_ListarClientes ();


-- BUSCAR

Delimiter $$
	create procedure sp_buscarCliente(in idCli int)
		begin
			select
			c.idCliente as 'ID Cliente:',
			c.nitCliente as 'Nit CLiente',
			c.nombresCliente as 'Nombre del Cliente:',
			c.apellidosCliente as 'Apellido del Cliente',
			c.direccionCliente as 'Direccion del Cliente',
			c.telefonoCliente as 'Telefono de Cliente',
			c.correoCliente as 'Correo del Cliente'
			from Clientes c
			where idCliente = idCli;
		end $$
Delimiter ;

call sp_buscarCliente(1);


-- EDITAR


DELIMITER $$
create procedure sp_editarCliente(in idCli int, in nitCli varchar(10), in nombresCli varchar(50), in apellidosCli varchar(50), in direccionCli varchar(150), in telefonoCli varchar(10), in correoCli varchar(40))
begin
	update Clientes set
        nitCliente = nitCli,
        nombresCliente = nombresCli,
        apellidosCliente = apellidosCli,
        direccionCliente = direccionCli,
        telefonoCliente = telefonoCli,
        correoCliente = correoCli
		where idCliente = idCli;
end$$
DELIMITER ;

call sp_editarCliente(1, '6755945-1', 'Jordi', 'Wild', '4ta calle sur 1-20', 63548574, 'dgarcia-2023532@kinal.edu');

-- BORRAR

DELIMITER $$
create procedure sp_borrarCliente(in idCli int)
begin
	delete from Clientes where idCliente = idCli;
end$$
DELIMITER ;

call sp_borrarCliente(1);


-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------
-- PROVEEDORES
-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------

-- AGREGAR 

DELIMITER $$
create procedure sp_agregarProveedor(in idProv int, in nitPro varchar(10), in nombrePro varchar(90), in apellidosPro varchar(90),
in direccionPro varchar(100), in razonSo varchar(100), in contactoPri varchar(100), in paginaW varchar(90))
	begin 
		insert into Proveedores (idProveedores, nitProveedor, nombreProveedor, apellidosProveedor, direccionProveedor,
		razonSocial, contactoPrincipal, paginaWeb)
		values(idProv, nitPro, nombrePro, apellidosPro, direccionPro, razonSo, contactoPri, paginaW);

	end$$
DELIMITER ; 


call sp_agregarProveedor(1, '63548594', ' Primo Manolo', 'Juega FFXV', 'En su casa', 'Razon Social', 'Contacto', 'www.proveedor.com');


-- LISTAR

DELIMITER $$
create procedure sp_listarProveedores()
	begin
		select
        p.idProveedores,
        p.nitProveedor,
        p.nombreProveedor,
        p.apellidosProveedor,
        p.direccionProveedor,
        p.razonSocial,
        p.contactoPrincipal,
        p.paginaWeb
        from Proveedores p;
	end $$
DELIMITER ;


call sp_listarProveedores();


-- EDITAR 

DELIMITER $$
create procedure sp_editarProveedor(
    in idProv int, in nitProv varchar(10), in nombreProv varchar(50),
    in apellidosProv varchar(50), in direccionProv varchar(150), 
    in razonS varchar(100), in contactoPrin varchar(100),
    in paginaW varchar(100)
)
begin
    update Proveedores
    set
        nitProveedor = nitProv,
        nombreProveedor = nombreProv,
        apellidosProveedor = apellidosProv,
        direccionProveedor = direccionProv,
        razonSocial = razonS,
        contactoPrincipal = contactoPrin,
        paginaWeb = paginaW
    where
        idProveedores = idProv;
end$$
DELIMITER ;

        
 call sp_editarProveedor(1, '63548594', ' Primo Manolo', 'Juega FFXV', 'En su casa', 'Razon Social', 'Contacto', 'www.proveedor.com');       
        
    

-- ELIMINAR 


DELIMITER $$

create procedure sp_eliminarProveedor(in idProv int)
begin
		delete from Proveedores where idProveedores = idProv;
end$$
DELIMITER ;

call sp_eliminarProveedor(1);

-- BUSCAR 

DELIMITER $$
create procedure sp_buscarProveedor(in idProv int)
begin
   select
    p.idProveedores,
    p.nitProveedor,
    p.nombreProveedor,
    p.apellidosProveedor,
    p.direccionProveedor,
    p.razonSocial,
    p.contactoPrincipal,
    p.paginaWeb
    from Proveedores p where idProveedores = idProv;
end$$
DELIMITER ;



-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------
-- CARGO EMPLEADO
-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------


-- AGREGAR

DELIMITER $$

create procedure sp_agregarCargoEmpleado(in idCar int, in nombreCar varchar(45), in descripCar varchar(45)
)
begin
    insert into CargoEmpleado (idCargoEmpleado, nombreCargo, descripcionCargo)
    values (idCar, nombreCar, descripCar);
end$$

DELIMITER ;

call sp_agregarCargoEmpleado(1, 'Jefe', 'Hacer nada');


-- LISTAR

DELIMITER $$
create procedure sp_listarCargoEmpleado()
begin
    select ce.idCargoEmpleado, ce.nombreCargo, ce.descripcionCargo  from CargoEmpleado ce;
end$$

DELIMITER ;

call sp_listarCargoEmpleado();


-- BUSCAR

DELIMITER $$
create procedure sp_buscarCargoEmpleado(in idCargo int)
begin
    select ce.idCargoEmpleado, ce.nombreCargo, ce.descripcionCargo  
    from CargoEmpleado ce 
    where idCargoEmpleado = idCargo;
end$$

DELIMITER ;

-- EDITAR

DELIMITER $$
create procedure sp_editarCargoEmpleado(
    in idCargo int, in nombreCar varchar(45), in descripCar varchar(45)
)
begin
    update CargoEmpleado
    set
        nombreCargo = nombreCar,
        descripcionCargo = descripCar
    where
        idCargoEmpleado = idCargo;
end$$

DELIMITER ;

-- ELIMINAR

DELIMITER $$
create procedure sp_eliminarCargoEmpleado(in idCargo int)
begin
    delete from CargoEmpleado where idCargoEmpleado = idCargo;
end$$

DELIMITER ;



-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------
-- COMPRAS
-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------

-- AGREGAR 

DELIMITER $$

create procedure sp_agregarCompra
(in idCom int, in fechaDoc date, in descrip varchar(60), in totalDoc decimal(10,2))
begin
    insert into Compras (idCompra, fechaDocumento, descripcion, totalDocumento)
    values (idCom, fechaDoc, descrip, totalDoc);
end$$

DELIMITER ;

call sp_agregarCompra(658748, '2024-05-19', 'Compra de suministros', 1000.00);



-- LISTAR 

DELIMITER $$
create procedure sp_listarCompras()
begin
    select co.idCompra, co.fechaDocumento, co.descripcion, co.totalDocumento 
    from Compras co;
end$$

DELIMITER ;

call sp_listarCompras();


-- BUSCAR 

DELIMITER $$
create procedure sp_buscarCompra(in idCom int)
begin
    select co.idCompra, co.fechaDocumento, co.descripcion, co.totalDocumento
    from Compras co
    where idCompra = idCom;
end$$

DELIMITER ;


-- EDITAR

DELIMITER $$

create procedure sp_editarCompra(in idCom int, in fechaDoc date, in descrip varchar(60), in totalDoc decimal(10,2))
begin
    update Compras
    set
        fechaDocumento = fechaDoc,
        descripcion = descrip,
        totalDocumento = totalDoc
    where
        idCompra = idCom;
end$$
DELIMITER ;

-- ELIMINAR

DELIMITER $$

create procedure sp_eliminarCompra(in idCom int)
begin
    delete from Compras where idCompra = idCom;
end$$
DELIMITER ;


-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------
-- TIPO PRODUCTO
-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------

-- AGREGAR

DELIMITER $$
create procedure sp_agregarTipoProducto(in idTipoPro int ,in descri varchar(45))
begin
	insert into TipoProducto(idTipoProducto, descripcion)
    values(idTipoPro, descri);
end $$
DELIMITER ;

call sp_agregarTipoProducto(1, 'Limpieza');

-- LISTAR

DELIMITER $$
create procedure sp_listarTipoProductos()
begin
	select
	tp.idTipoProducto,
    tp.descripcion
    from TipoProducto tp ;
end $$
DELIMITER ;

call sp_listarTipoProductos();

-- BUSCAR

DELIMITER $$
create procedure sp_buscarTipoProducto(in idTipoProducto int)
begin
	select
	tp.idTipoProducto,
    tp.descripcion
    from TipoProducto tp
    where idTipoProducto=idTipoProducto;
end $$
DELIMITER ;

-- ELIMINAR

DELIMITER $$
create procedure sp_eliminarTipoProducto(in idTipoPro int)
begin
	delete from TipoProducto 
    where TipoProducto.idTipoProducto=idTipoPro;
end $$
DELIMITER ;

-- EDITAR

DELIMITER $$
create procedure sp_editarTipoProducto(in idTipoPro int,in descri varchar(45))
begin
	update TipoProducto 
	set 
		TipoProducto.descripcion=descri
    where
		TipoProducto.idTipoProducto=idTipoPro;
end $$
DELIMITER ;


-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------
-- PRODUCTOS
-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------

-- AGREGAR

DELIMITER $$
create procedure sp_agregarProducto(
    in idPro int,
    in descripcionPro varchar(15),
    in precioUni decimal(10,2),
    in precioDoc decimal(10,2),
    in precioMay decimal(10,2),
    in imagenPro varchar(45),
    in exist int,
    in idTipoPro int,
    in idProv int
)
begin
    insert into Productos(idProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, idTipoProducto, idProveedores)
    values(idPro,descripcionPro,precioUni,precioDoc,precioMay,imagenPro,exist,idTipoPro, idProv);
end$$
DELIMITER ;
 
call sp_agregarProducto(1, 'Leche', 10.99, 88.99, 8.99, 'leche.jpg', 200, 1, 1);

-- BUSCAR

DELIMITER $$

create procedure sp_buscarProductoPorCodigo(in codProd varchar(15))
begin
    select p.codigoProducto, p.descripcionProducto, p.precioUnitario, p.precioDocena, p.precioMayor, 
           p.imagenProducto, p.existencia, p.codigoProveedor, p.codigoTipoProducto
    from Productos p 
    where p.codigoProducto = codProd;
end$$

delimiter ;
 
Delimiter $$
create procedure sp_listarProductos()
begin
    select
		p.codigoProducto,
        p.descripcionProducto,
        p.precioUnitario,
        p.precioDocena,
        p.precioMayor,
        p.imagenProducto,
        p.existencia,
        p.codigoTipoProducto,
        p.codigoProveedor
        from
        productos p;
end$$
Delimiter ;
 
call sp_listarProductos();
 
DELIMITER $$
CREATE PROCEDURE sp_actualizarProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_nuevaDescripcionProducto VARCHAR(15),
    IN p_nuevoPrecioUnitario DECIMAL(10,2),
    IN p_nuevoPrecioDocena DECIMAL(10,2),
    IN p_nuevoPrecioMayor DECIMAL(10,2),
    IN p_nuevaImagenProducto VARCHAR(45),
    IN p_nuevaExistencia INT,
    IN p_nuevoCodigoTipoProducto INT,
    IN p_nuevoCodigoProveedor INT
)
BEGIN
    UPDATE Productos
    SET descripcionProducto = p_nuevaDescripcionProducto,
        precioUnitario = p_nuevoPrecioUnitario,
        precioDocena = p_nuevoPrecioDocena,
        precioMayor = p_nuevoPrecioMayor,
        imagenProducto = p_nuevaImagenProducto,
        existencia = p_nuevaExistencia,
        codigoTipoProducto = p_nuevoCodigoTipoProducto,
        codigoProveedor = p_nuevoCodigoProveedor
    WHERE codigoProducto = p_codigoProducto;
END$$
DELIMITER ;
 

 
Delimiter $$
CREATE PROCEDURE sp_eliminarProducto(IN _codigoProducto VARCHAR(15))
BEGIN
    DELETE FROM Productos
    WHERE codigoProducto = _codigoProducto;
END $$
 
DELIMITER ;
 

-- ------------------------------------------ TELEFONO PROVEEDOR------------------------------------------------------

delimiter $$

create procedure sp_agregarTelefonoProveedor(in codTelPro int, in numPrin varchar(8), in numSec varchar(8), in obs varchar(45), in codPro int)
begin
    insert into TelefonoProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor)
    values (codTelPro, numPrin, numSec, obs, codPro);
end$$

delimiter ;

call sp_agregarTelefonoProveedor(1, '5165', '5165', 'sdfg', 1);

delimiter $$

create procedure sp_listarTelefonoProveedor()
begin 
    select t.codigoTelefonoProveedor, t.numeroPrincipal, t.numeroSecundario, t.observaciones, t.codigoProveedor from TelefonoProveedor t;
end$$

delimiter ;

call sp_listarTelefonoProveedor();
delimiter $$

create procedure sp_buscarTelefonoProveedor(in codPro int)
begin
    select t.codigoTelefonoProveedor, t.numeroPrincipal, t.numeroSecundario, t.observaciones, t.codigoProveedor from TelefonoProveedor t where t.codigoProveedor = codPro;
end$$

delimiter ;
delimiter $$

create procedure sp_actualizarTelefonoProveedor(in codTelPro int, in numPrin varchar(8), in numSec varchar(8), in obs varchar(45), in codPro int)
begin
    update TelefonoProveedor set numeroPrincipal = numPrin, numeroSecundario = numSec, observaciones = obs, codigoProveedor = codPro where codigoTelefonoProveedor = codTelPro;
end$$

delimiter ;

delimiter $$

create procedure sp_eliminarTelefonoProveedor(in codTelPro int)
begin
    delete from TelefonoProveedor where codigoTelefonoProveedor = codTelPro;
end$$

delimiter ;

-- --------------------------------------- -----Email Proveedor -------------------------------------------------------------------------------
delimiter $$

create procedure sp_agregarEmailProveedor(
    in codEmailPro int,
    in email varchar(50),
    in descr varchar(100),
    in codPro int
)
begin
    insert into EmailProveedor (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor)
    values (codEmailPro, email, descr, codPro);
end$$

delimiter ;

delimiter $$

create procedure sp_listarEmailProveedor()
begin 
    select e.codigoEmailProveedor, e.emailProveedor, e.descripcion, e.codigoProveedor 
    from EmailProveedor e;
end$$

delimiter ;

delimiter $$

create procedure sp_buscarEmailProveedor(in codPro int)
begin
    select e.codigoEmailProveedor, e.emailProveedor, e.descripcion, e.codigoProveedor 
    from EmailProveedor e 
    where e.codigoProveedor = codPro;
end$$

delimiter ;

delimiter $$

create procedure sp_actualizarEmailProveedor(
    in codEmailPro int,
    in email varchar(50),
    in descr varchar(100),
    in codPro int
)
begin
    update EmailProveedor 
    set emailProveedor = email, descripcion = descr, codigoProveedor = codPro 
    where codigoEmailProveedor = codEmailPro;
end$$

delimiter ;

delimiter $$

create procedure sp_eliminarEmailProveedor(in codEmailPro int)
begin
    delete from EmailProveedor 
    where codigoEmailProveedor = codEmailPro;
end$$

delimiter ;

-- ----------------------------------------------------- Empleados ------------------------------------------------------------
delimiter $$

create procedure sp_agregarEmpleado(
    in codEmp int,
    in nombres varchar(50),
    in apellidos varchar(50),
    in sueldo decimal(10,2),
    in direccion varchar(150),
    in turno varchar(15),
    in codCargoEmp int
)
begin
    insert into Empleados (codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado)
    values (codEmp, nombres, apellidos, sueldo, direccion, turno, codCargoEmp);
end$$

delimiter ;
call sp_agregarEmpleado(1, 'John', 'Doe', 3000.00, '1234 Elm Street', 'Day', 1);


delimiter $$

create procedure sp_listarEmpleados()
begin 
    select e.codigoEmpleado, e.nombresEmpleado, e.apellidosEmpleado, e.sueldo, e.direccion, e.turno, e.codigoCargoEmpleado 
    from Empleados e;
end$$

delimiter ;

delimiter $$

create procedure sp_buscarEmpleado(in codEmpleado int)
begin
    select e.codigoEmpleado, e.nombresEmpleado, e.apellidosEmpleado, e.sueldo, e.direccion, e.turno, e.codigoCargoEmpleado 
    from Empleados e 
    where e.codigoEmpleado = codEmpleado;
end$$

delimiter ;

delimiter $$

create procedure sp_actualizarEmpleado(
    in codEmp int,
    in nombres varchar(50),
    in apellidos varchar(50),
    in sueldo decimal(10,2),
    in direccion varchar(150),
    in turno varchar(15),
    in codCargoEmp int
)
begin
    update Empleados 
    set nombresEmpleado = nombres, apellidosEmpleado = apellidos, sueldo = sueldo, direccion = direccion, turno = turno, codigoCargoEmpleado = codCargoEmp 
    where codigoEmpleado = codEmp;
end$$

delimiter ;

delimiter $$

create procedure sp_eliminarEmpleado(in codEmp int)
begin
    delete from Empleados 
    where codigoEmpleado = codEmp;
end$$

delimiter ;

-- ------------------------------------------- Factura ------------------------------------------------------------------------
delimiter $$

create procedure sp_agregarFactura(
    in numFac int,
    in estadoFac varchar(50),
    in totalFac decimal(10,2),
    in fechaFac varchar(45),
    in codCli int,
    in codEmp int
)
begin
    insert into Factura (numeroFactura, estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleado)
    values (numFac, estadoFac, totalFac, fechaFac, codCli, codEmp);
end$$

delimiter ;

delimiter $$

create procedure sp_listarFacturas()
begin 
    select f.numeroFactura, f.estado, f.totalFactura, f.fechaFactura, f.codigoCliente, f.codigoEmpleado 
    from Factura f;
end$$

delimiter ;

delimiter $$

create procedure sp_buscarFactura(in numFac int)
begin
    select f.numeroFactura, f.estado, f.totalFactura, f.fechaFactura, f.codigoCliente, f.codigoEmpleado 
    from Factura f 
    where f.numeroFactura = numFac;
end$$

delimiter ;

delimiter $$

create procedure sp_actualizarFactura(
    in numFac int,
    in estadoFac varchar(50),
    in totalFac decimal(10,2),
    in fechaFac varchar(45),
    in codCli int,
    in codEmp int
)
begin
    update Factura 
    set estado = estadoFac, totalFactura = totalFac, fechaFactura = fechaFac, codigoCliente = codCli, codigoEmpleado = codEmp 
    where numeroFactura = numFac;
end$$

delimiter ;

delimiter $$

create procedure sp_eliminarFactura(in numFac int)
begin
    delete from Factura 
    where numeroFactura = numFac;
end$$

delimiter ;

-- ------------------------------------------------ Detalle Factura-----------------------------------------------------
delimiter $$

create procedure sp_agregarDetalleFactura(
    in codDetFac int,
    in precioUnit decimal(10,2),
    in cant int,
    in numFac int,
    in codProd varchar(15)
)
begin
    insert into DetalleFactura (codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto)
    values (codDetFac, precioUnit, cant, numFac, codProd);
end$$

delimiter ;

delimiter $$

create procedure sp_listarDetalleFacturas()
begin 
    select d.codigoDetalleFactura, d.precioUnitario, d.cantidad, d.numeroFactura, d.codigoProducto
    from DetalleFactura d;
end$$

delimiter ;

delimiter $$

create procedure sp_buscarDetalleFactura(in codDetFac int)
begin
    select d.codigoDetalleFactura, d.precioUnitario, d.cantidad, d.numeroFactura, d.codigoProducto
    from DetalleFactura d 
    where d.codigoDetalleFactura = codDetFac;
end$$

delimiter ;

delimiter $$

create procedure sp_actualizarDetalleFactura(
    in codDetFac int,
    in precioUnit decimal(10,2),
    in cant int,
    in numFac int,
    in codProd varchar(15)
)
begin
    update DetalleFactura 
    set precioUnitario = precioUnit, cantidad = cant, numeroFactura = numFac, codigoProducto = codProd 
    where codigoDetalleFactura = codDetFac;
end$$

delimiter ;

delimiter $$

create procedure sp_eliminarDetalleFactura(in codDetFac int)
begin
    delete from DetalleFactura 
    where codigoDetalleFactura = codDetFac;
end$$

delimiter ;

-- ------------------------------------------------- Detalle Compra ---------------------------------------------------
delimiter $$

create procedure sp_agregarDetalleCompra(
    in codDetComp int,
    in costoUnit decimal(10,2),
    in cant int,
    in codProd varchar(15),
    in numDoc int
)
begin
    insert into DetalleCompra (codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento)
    values (codDetComp, costoUnit, cant, codProd, numDoc);
end$$

delimiter ;

delimiter $$

create procedure sp_listarDetalleCompras()
begin 
    select d.codigoDetalleCompra, d.costoUnitario, d.cantidad, d.codigoProducto, d.numeroDocumento
    from DetalleCompra d;
end$$

delimiter ;

delimiter $$

create procedure sp_buscarDetalleCompraPorCodigo(in codDetComp int)
begin
    select d.codigoDetalleCompra, d.costoUnitario, d.cantidad, d.codigoProducto, d.numeroDocumento
    from DetalleCompra d 
    where d.codigoDetalleCompra = codDetComp;
end$$

delimiter ;

delimiter $$

create procedure sp_actualizarDetalleCompra(
    in codDetComp int,
    in costoUnit decimal(10,2),
    in cant int,
    in codProd varchar(15),
    in numDoc int
)
begin
    update DetalleCompra 
    set costoUnitario = costoUnit, cantidad = cant, codigoProducto = codProd, numeroDocumento = numDoc 
    where codigoDetalleCompra = codDetComp;
end$$

delimiter ;

delimiter $$

create procedure sp_eliminarDetalleCompra(in codDetComp int)
begin
    delete from DetalleCompra 
    where codigoDetalleCompra = codDetComp;
end$$

delimiter ;

