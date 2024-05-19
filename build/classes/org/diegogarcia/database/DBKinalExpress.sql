create database if not exists KinalExpress;
use KinalExpress;
set global time_zone = '-6:00';

-- Nombre: Diego Fernando Garcia Galvez
-- Carnet: 2023532
-- Seccion: IN5BM
-- Fecha de Creacion: 08/05/2024

create table if not exists Clientes(
idCliente int not null,
nitCliente varchar(20) not null,
nombresCliente varchar(40) not null,
apellidosCliente varchar(40) not null,
direccionCliente varchar(60) not null,
telefonoCliente varchar(10) not null,
correoCliente varchar(40) not null,
primary key Pk_idCliente(idCliente)
)engine innodb;

create table if not exists Proveedores(
idProveedores int not null,
nitProveedor varchar(20) not null,
nombreProveedor varchar(40) not null,
apellidosProveedor varchar(40) not null,
direccionProveedor varchar(80) not null,
razonSocial varchar(40) not null,
contactoPrincipal varchar(40) not null,
paginaWeb varchar(40) not null,
primary key PK_idProveedores(idProveedores)
)engine innodb;

create table if not exists CargoEmpleado(
idCargoEmpleado int not null,
nombreCargo varchar(40) not null,
descripcionCargo varchar(70) not null,
primary key PK_idCargoEmpleado(idCargoEmpleado)
)engine innodb;

create table if not exists Empleados(
idEmpleado int not null,
nombresEmpleado varchar(40) not null,
apellidosEmpleado varchar(40) not null,
sueldo decimal(10,2) not null,
turno varchar(10) not null,
idCargoEmpleado int not null,
primary key PK_idEmpleado(idEmpleado),     
constraint FK_Empleados_CargoEmpleado foreign key (idCargoEmpleado)    
references CargoEmpleado(idCargoEmpleado) 
)engine innodb;


create table if not exists Compras(
idCompra int not null,
fechaDocumento date not null,
descripcion varchar(90) not null,
totalDocumento decimal(10,2) not null,
primary key PK_idCompra(idCompra)
)engine innodb;

create table if not exists TipoProducto(
idTipoProducto int not null,
descripcion varchar(90) not null,
primary key PK_idTipoProducto(idTipoProducto)
)engine innodb;

create table if not exists Productos(
idProducto int not null,
descripcionProducto varchar(90) not null,
precioUnitario decimal(10,2) not null,
precioDocena decimal(10,2) not null,
precioMayor decimal(10,2) not null,
imagenProducto varchar(45) not null,
existencia int not null,
idTipoProducto int not null,
idProveedores int not null,
primary key PK_idProducto(idProducto),
constraint FK_Productos_TipoProducto foreign key (idTipoProducto)    
references TipoProducto(idTipoProducto),
constraint FK_Productos_Proveedores foreign key (idProveedores)
references Proveedores(idProveedores) 
)engine innodb;


create table if not exists TelefonoProveedor(
idTelefonoProveedor int not null,
numeroPrincipal varchar(20) not null,
numeroSecundario varchar(20) not null,
observaciones varchar(90) not null,
idProveedores int not null,
primary key PK_idTelefonoProveedor(idTelefonoProveedor),
constraint FK_TelefonoProveedor_Proveedores foreign key(idProveedores)
references Proveedores(idProveedores)
)engine innodb;

create table if not exists EmailProveedor(
idEmailProveedor int not null,
emailProveedor varchar(20) not null,
descripcion varchar(90) not null,
idProveedores int not null,
primary key PK_idEmailProveedor(idEmailProveedor), 
constraint FK_EmailProveedor_Proveedores foreign key(idProveedores)
references Proveedores(idProveedores)
)engine innodb;


create table if not exists DetalleCompras(
idDetalleCompra int not null,
precioUnitario decimal(10,2) not null,
cantidad int not null,
idProducto int not null,
idCompras int not null,
primary key PK_idDetalleCompras(idDetalleCompra),
constraint FK_DetalleCompras_Productos foreign key (idDetalleCompra)
references Compras(idCompra)
)engine innodb;

create table if not exists Factura(
idFactura int not null,
estado varchar(45) not null,
totalFactura decimal(10,2) not null,
fechaFactura varchar(45) not null,
idEmpleado int not null,
idCliente int not null,
primary key PK_idFactura(idFactura),
constraint FK_Factura_Empleados foreign key (idEmpleado)
references Empleados(idEmpleado),
constraint FK_Factura_Clientes foreign key (idCliente)
references Clientes(idCliente)
)engine innodb;

create table if not exists DetalleFactura(
idDetalleFactura int not null,
precioUnitario decimal(10,2) not null,
cantidad int not null,
idFactura int not null,
idProducto int not null,
primary key PK_idDetalleFactura(idDetalleFactura),
constraint FK_DetalleFactura_Factura foreign key(idFactura)
references Factura(idFactura),
constraint FK_DetalleFactura_Productos foreign key(idProducto)
references Productos(idProducto)
)engine innodb


-- ----------------------------------------------------------------------------------------
-- PROCEDIMIENTOS ALMACENADOS
-- ----------------------------------------------------------------------------------------

-- CLIENTES

Delimiter $$
	create procedure sp_AgregarClientes (in idCliente int, in nitCliente varchar(20), in nombresCliente varchar(40), in apellidosCliente varchar(40), in direccionCliente varchar(60), in telefonoCliente varchar(10), in correoCliente varchar(45))
    begin
		insert into Clientes(idCliente, nitCliente, nombresCliente, apellidosCliente, direccionCliente, telefonoCliente, correoCliente)
			values (idCliente, nitCliente, nombresCliente, apellidosCliente, direccionCliente, telefonoCliente, correoCliente);
            
	End $$
Delimiter ;

call sp_AgregarClientes(1, '6755945-1', 'Jordi', 'Wild', '4ta calle sur 1-20', 63548574, 'dgarcia-2023532@kinal.edu');
call sp_AgregarClientes(2, '8467592-1', 'Dua', 'Lipa', 'Avenida la reforma zona 1', 93625271, 'butifarraloka.com');

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
create procedure sp_buscarClientes()
begin
select
	Clientes.idCliente as 'ID Cliente:',
    Clientes.nitCliente as 'Nit CLiente',
	Clientes.nombresCliente as 'Nombre del Cliente:',
    Clientes.apellidosCliente as 'Apellido del Cliente',
    Clientes.direccionCliente as 'Direccion del Cliente',
    Clientes.telefonoCliente as 'Telefono de Cliente',
    Clientes.correoCliente as 'Correo del Cliente'
    from Clientes
    where idCliente = idCliente;
end $$
Delimiter ;

call sp_buscarClientes();

-- Editar
DELIMITER $$
create procedure sp_editarClientes(in idCliente int, in nitCliente varchar(10), in nombresCliente varchar(50), in apellidosCliente varchar(50), in direccionCliente varchar(150), in telefonoCliente varchar(10), in correoCliente varchar(40))
BEGIN
	update Clientes set
		idCliente = idCliente,
        nitCliente = nitCliente,
        nombresCliente = nombresCliente,
        apellidosCliente = apellidosCliente,
        direccionCliente = direccionCliente,
        telefonoCliente = telefonoCliente,
        correoCliente = correoCliente
			where idCliente = idCliente;
END$$
DELIMITER ;

-- BORRAR

DELIMITER $$
create procedure sp_eliminarClientes(idCliente int)
BEGIN
	delete from Clientes where idCliente = idCliente;
END$$
DELIMITER ;

call sp_eliminarClientes(1);


