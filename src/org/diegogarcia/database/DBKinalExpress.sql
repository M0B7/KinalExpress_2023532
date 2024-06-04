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
create procedure sp_eliminarCliente(in idCli int)
begin
	delete from Clientes where idCliente = idCli;
end$$
DELIMITER ;

call sp_eliminarCliente(1);


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

INSERT INTO Proveedores (idProveedores, nitProveedor, nombreProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
VALUES (1, '123456789', 'Proveedor', 'Apellidos', 'Direccion', 'Razon Social', 'Contacto Principal', 'www.proveedor.com')
ON DUPLICATE KEY UPDATE idProveedores = idProveedores;
 
call sp_agregarProducto(1, 'Leche', 10.99, 88.99, 8.99, 'leche.jpg', 200, 1, 1);

-- BUSCAR

DELIMITER $$

create procedure sp_buscarProducto(in idProd varchar(15))
begin
    select p.idProducto, p.descripcionProducto, p.precioUnitario, p.precioDocena, p.precioMayor, 
           p.imagenProducto, p.existencia, p.idProveedores, p.idTipoProducto
    from Productos p 
    where p.idProducto = idProd;
end$$
DELIMITER ;
 
 -- LISTAR
 
Delimiter $$
create procedure sp_listarProductos()
begin
    select
		p.idProducto,
        p.descripcionProducto,
        p.precioUnitario,
        p.precioDocena,
        p.precioMayor,
        p.imagenProducto,
        p.existencia,
        p.idTipoProducto,
        p.idProveedores
        from
		Productos p;
end$$
Delimiter ;
 
call sp_listarProductos();

-- EDITAR
 
DELIMITER $$
create procedure sp_editarProducto(
	in idPro int,
    in descripcionProd varchar(15),
    in precioUnit decimal(10,2),
    in precioDoce decimal(10,2),
    in precioMayo decimal(10,2),
    in imagenProduc varchar(45),
    in existen int,
    in idTipoProduc int,
    in idProveed int
)
begin
    update Productos
    set descripcionProducto = descripcionProd,
        precioUnitario = precioUnit,
        precioDocena = precioDoce,
        precioMayor = precioMayo,
        imagenProducto = imagenProduc,
        existencia = existen,
        idTipoProducto = idTipoProduc,
        idProveedores = idProveed
    where idProducto = idPro;
end$$
DELIMITER ;
 
-- ELIMINAR
 
DELIMITER $$
create procedure sp_eliminarProducto(in  idProduc int)
begin
    delete from Productos
    where idProducto = idProduc;
end $$
 
DELIMITER ;
 

-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------
-- TELEFONO PROVEEDOR
-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------

-- AGREGAR

DELIMITER $$

create procedure sp_agregarTelefonoProveedor
(in idTelPro int, in numeroPrin varchar(12), in numeroSec varchar(12), in observa varchar(90), in idProvee int)
begin
    insert into TelefonoProveedor(idTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, idProveedores)
    values (idTelPro, numeroPrin, numeroSec, observa, idProvee);
end$$
DELIMITER ;

call sp_agregarTelefonoProveedor(1, '82536475', '0273546', 'El numero principal es de la secretaria', 1);

-- LISTAR

DELIMITER $$

create procedure sp_listarTelefonoProveedor()
begin 
    select t.idTelefonoProveedor, t.numeroPrincipal, t.numeroSecundario, t.observaciones, t.idProveedores
    from TelefonoProveedor t;
end$$
DELIMITER ;

call sp_listarTelefonoProveedor();

-- BUSCAR

delimiter $$

create procedure sp_buscarTelefonoProveedor(in idPro int)
begin
    select t.idTelefonoProveedor, t.numeroPrincipal, t.numeroSecundario, t.observaciones, t.idProveedores
    from TelefonoProveedor t 
    where t.idProveedores = idPro;
end$$
DELIMITER ;

-- EDITAR

DELIMITER $$

create procedure sp_editarTelefonoProveedor(in idTelPro int, in numPrin varchar(10),
 in numSec varchar(10), in obs varchar(90), in idPro int)
begin
    update TelefonoProveedor 
    set
    numeroPrincipal = numPrin,
    numeroSecundario = numSec, 
    observaciones = obs, 
    idProveedores = idPro where idTelefonoProveedor = idTelPro;
end$$
DELIMITER ;

-- ELIMINAR

DELIMITER $$

create procedure sp_eliminarTelefonoProveedor(in idTelPro int)
begin
    delete from TelefonoProveedor 
    where idTelefonoProveedor = idTelPro;
end$$
DELIMITER ;

-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------
-- EMAIL PROVEEDOR
-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------

DELIMITER $$

create procedure sp_agregarEmailProveedor(
    in idEmailPro int,
    in email varchar(50),
    in descr varchar(100),
    in idProv int
)
begin
    insert into EmailProveedor (idEmailProveedor, emailProveedor, descripcion, idProveedores)
    values (idEmailPro, email, descr, idProv);
end$$
DELIMITER ;

call sp_agregarEmailProveedor(1, 'hdfjebrf@gmail.com', 'No respondo los domingos', 1);

-- LISTAR

DELIMITER $$

create procedure sp_listarEmailProveedor()
begin 
    select e.idEmailProveedor,
    e.emailProveedor,
    e.descripcion, 
    e.idProveedores 
    from EmailProveedor e;
end$$
DELIMITER ;

call sp_listarEmailProveedor();

-- BUSCAR

DELIMITER $$

create procedure sp_buscarEmailProveedor(in idPro int)
begin
    select e.idEmailProveedor, 
    e.emailProveedor, 
    e.descripcion,
    e.idProveedores 
    from EmailProveedor e 
    where e.idProveedores = idPro;
end$$
DELIMITER ;

-- EDITAR

delimiter $$

create procedure sp_editarEmailProveedor(
    in idEmailPro int,
    in email varchar(50),
    in descri varchar(100),
    in idPro int
)
begin
    update EmailProveedor 
    set emailProveedor = email,
    descripcion = descri,
    idProveedores = idPro 
    where idEmailProveedor = idEmailPro;
end$$
DELIMITER ;

-- ELIMINAR

DELIMITER $$

create procedure sp_eliminarEmailProveedor(in idEmailPro int)
begin
    delete from EmailProveedor 
    where idEmailProveedor = idEmailPro;
end$$
DELIMITER ;

-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------
-- EMAIL EMPLEADOS
-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------


-- AGREGAR

DELIMITER $$

create procedure sp_agregarEmpleado(
    in idEmp int,
    in nombres varchar(50),
    in apellidos varchar(50),
    in sueldo decimal(10,2),
    in turno varchar(15),
    in idCargoEmp int
)
begin
    insert into Empleados (idEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, turno, idCargoEmpleado)
    values (idEmp, nombres, apellidos, sueldo, turno, idCargoEmp);
end$$

DELIMITER ;

call sp_agregarEmpleado(1, 'Neto', 'Bran', 1.00, 'Nocturno', 1);

-- LISTAR

DELIMITER $$

create procedure sp_listarEmpleados()
begin 
    select e.idEmpleado, e.nombresEmpleado, e.apellidosEmpleado, e.sueldo, e.turno, e.idCargoEmpleado 
    from Empleados e;
end$$
DELIMITER ;

call sp_listarEmpleados();

-- BUSCAR

DELIMITER $$

create procedure sp_buscarEmpleado(in idEmpleado int)
begin
    select e.idEmpleado, e.nombresEmpleado, e.apellidosEmpleado, e.sueldo, e.turno, e.idCargoEmpleado 
    from Empleados e 
    where e.idEmpleado = idEmpleado;
end$$

DELIMITER ;

-- EDITAR

DELIMITER $$

create procedure sp_editarEmpleado(
    in idEmp int,
    in nombres varchar(50),
    in apellidos varchar(50),
    in sueldo decimal(10,2),
    in turno varchar(15),
    in idCargoEmp int
)
begin
    update Empleados 
    set nombresEmpleado = nombres,
    apellidosEmpleado = apellidos, 
    sueldo = sueldo,
    direccion = direccion,
    turno = turno,
    idCargoEmpleado = idCargoEmp 
    where idEmpleado = idEmp;
end$$
DELIMITER ;

-- ELIMINAR

DELIMITER $$

create procedure sp_eliminarEmpleado(in idEmp int)
begin
    delete from Empleados 
    where idEmpleado = idEmp;
end$$

DELIMITER ;

-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------
-- FACTURA
-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------

-- AGREGAR

DELIMITER $$

create procedure sp_agregarFactura(
    in idFac int,
    in estadoFac varchar(50),
    in totalFac decimal(10,2),
    in fechaFac varchar(45),
    in idCli int,
    in idEmp int
)
begin
    insert into Factura (idFactura, estado, totalFactura, fechaFactura, idCliente, idEmpleado)
    values (idFac, estadoFac, totalFac, fechaFac, idCli, idEmp);
end$$
DELIMITER ;


-- LISTAR

DELIMITER $$

create procedure sp_listarFacturas()
begin 
    select f.idFactura, f.estado, f.totalFactura, f.fechaFactura, f.idCliente, f.idEmpleado 
    from Factura f;
end$$

DELIMITER ;

call sp_listarFacturas();

-- BUSCAR 

DELIMITER $$

create procedure sp_buscarFactura(in idFac int)
begin
    select f.idFactura, f.estado, f.totalFactura, f.fechaFactura, f.idCliente, f.idEmpleado 
    from Factura f 
    where f.idFactura = idFac;
end$$
DELIMITER ;

-- ACTUALIZAR 

DELIMITER $$

create procedure sp_actualizarFactura(
    in idFac int,
    in estadoFac varchar(50),
    in totalFac decimal(10,2),
    in fechaFac varchar(45),
    in idCli int,
    in idEmp int
)
begin
    update Factura 
    set estado = estadoFac,
    totalFactura = totalFac, 
    fechaFactura = fechaFac,
    idCliente = idCli, 
    idEmpleado = idEmp 
    where idFactura = idFac;
end$$	
DELIMITER ;

-- ELIMINAR

DELIMITER $$

create procedure sp_eliminarFactura(in idFac int)
begin
    delete from Factura 
    where idFactura = idFac;
end$$

DELIMITER ;


-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------
-- DETALLE FACTURA
-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------

-- AGREGAR

DELIMITER $$

create procedure sp_agregarDetalleFactura(
    in idDetFac int,
    in precioUnit decimal(10,2),
    in cant int,
    in idFac int,
    in idProd int
)
begin
    insert into DetalleFactura (idDetalleFactura, precioUnitario, cantidad, idFactura, idProducto)
    values (idDetFac, precioUnit, cant, idFac, idProd);
end$$

DELIMITER ;


-- LISTAR

DELIMITER $$

create procedure sp_listarDetalleFactura()
begin 
    select d.idDetalleFactura, d.precioUnitario, d.cantidad, d.idFactura, d.idProducto
    from DetalleFactura d;
end$$

DELIMITER ;


DELIMITER $$

create procedure sp_buscarDetalleFactura(in codDetFac int)
begin
    select d.codigoDetalleFactura, d.precioUnitario, d.cantidad, d.numeroFactura, d.codigoProducto
    from DetalleFactura d 
    where d.codigoDetalleFactura = codDetFac;
end$$

DELIMITER ;


-- EDITAR

DELIMITER $$

create procedure sp_editarDetalleFactura(
    in idDetFac int,
    in precioUnit decimal(10,2),
    in cant int,
    in idFac int,
    in idProd int
)
begin
    update DetalleFactura 
    set precioUnitario = precioUnit, 
    cantidad = cant, 
    idFactura = idFac,
    idProducto = idProd 
    where idDetalleFactura = idDetFac;
end$$

DELIMITER ;

-- ELIMINAR 

DELIMITER $$

create procedure sp_eliminarDetalleFactura(in idDetFac int)
begin
    delete from DetalleFactura 
    where idDetalleFactura = idDetFac;
end$$
DELIMITER ;

-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------
-- DETALLE FACTURA
-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------

-- AGREGAR 

DELIMITER $$

create procedure sp_agregarDetalleCompra(
    in idDetComp int,
    in costoUnit decimal(10,2),
    in cant int,
    in idProd varchar(15),
    in idCom int
)
begin
    insert into DetalleCompra (idDetalleCompra, costoUnitario, cantidad, idProducto, idCompra)
    values (idDetComp, costoUnit, cant, idProd, idCom);
end$$

DELIMITER ;

-- LISTAR

DELIMITER $$

create procedure sp_listarDetalleCompras()
begin 
    select d.idDetalleCompra, d.costoUnitario, d.cantidad, d.idProducto, d.idCompra
    from DetalleCompra d;
end$$

DELIMITER ;

-- BUSCAR

DELIMITER $$

create procedure sp_buscarDetalleCompraPorCodigo(in idDetComp int)
begin
    select d.idDetalleCompra, d.costoUnitario, d.cantidad, d.idProducto, d.idCompra
    from DetalleCompra d 
    where d.idDetalleCompra = idDetComp;
end$$

DELIMITER ;

-- EDITAR

DELIMITER $$

create procedure sp_actualizarDetalleCompra(
    in idDetComp int,
    in costoUnit decimal(10,2),
    in cant int,
    in idProd varchar(15),
    in idCom int
)
begin
    update DetalleCompra 
    set costoUnitario = costoUnit,
    cantidad = cant, 
    idProducto = idProd, 
    idCompra = idCom 
    where idDetalleCompra = idDetComp;
end$$
DELIMITER ;

-- ELIMINAR

DELIMITER $$

create procedure sp_eliminarDetalleCompra(in idDetComp int)
begin
    delete from DetalleCompra 
    where idDetalleCompra = idDetComp;
end$$
DELIMITER ;

-- ------------------------------------------------------------------------------------------ ----------------------------------------------------------------------------------------
-- TRIGGERS
-- ------

-- Actualiza el total de la factura al eliminar un DetalleFactura
DELIMITER $$
create trigger ActualizarTotal after delete on DetalleCompras
for each row
begin
    update Compras
    set totalDocumento = totalDocumento - OLD.precioUnitario * OLD.cantidad
    where idCompra = OLD.idCompra;
end $$
DELIMITER ;



-- Actualiza el  total de la factura al actualizar su detalle
/*DELIMITER $$
create trigger ActualizarTotalFactura after update on DetalleFactura
for each row
begin
    update Factura
    set totalFactura = totalFactura + (new.precioUnitario * new.cantidad) - (OLD.precioUnitario * OLD.cantidad)
    where numeroDeFactura = new.numeroDeFactura;
end $$
DELIMITER ;
*/

DELIMITER ;

-- Actualiza el total de la factura al insertar
DELIMITER $$
create trigger ActualizarTotalInsert after insert on DetalleCompras
for each row
begin
    update Compras
    set totalDocumento = totalDocumento + new.precioUnitario * new.cantidad
    where idCompra = new.idCompra;
end
$$
DELIMITER ;


/*
-- Actualiza el total de Compras al actualizar su detalle
DELIMITER $$
create trigger ActualizarTotalCompra after insert on DetalleCompras
for each row 
begin
    update Compras
    set totalDocumento = totalDocumento + (new.precioUnitario * new.cantidad) - (OLD.precioUnitario * OLD.cantidad)
    where idCompra = new.idCompra;
end $$
DELIMITER ;

*/

-- actualiza la cantidad en productos disponibles luego de inserta en detalle factura

/*DELIMITER $$
create trigger ActualizarStockInsert after insert on DetalleFactura
for each row 
begin
    call ActualizarStockInsert(new.idProducto, new.cantidad);
end;
$$
DELIMITER ;

*/

/*
-- actualiza el stock al eliminar algo en datelleFactura
DELIMITER $$
create trigger ActualizarStockDelete after delete on DetalleFactura
for each row
begin
    call ActualizarStockDelete(OLD.idProducto, OLD.cantidad);
end;
$$
DELIMITER ;
*/

DELIMITER $$

create trigger AfterInsertDetalleCompra after insert on DetalleCompra
for each row
begin
    declare precioUnitario decimal(10,2);
    declare precioDocena decimal(10,2);
    declare precioMayor decimal(10,2);

    -- Calcula los precios
    set precioUnitario = new.precioUnitario * 1.40;
    set precioDocena = precioUnitario * 1.35;
    set precioMayor = precioUnitario * 1.25;

 -- Coloca los precios ya calculados
    update Productos
    set precioUnitario = new.precioUnitario,
        precioDocena = precioDocena,
        precioMayor = precioMayor
    where idProducto = new.idProducto;
    
end $$

DELIMITER ;

