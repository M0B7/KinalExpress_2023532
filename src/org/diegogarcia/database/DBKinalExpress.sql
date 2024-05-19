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
idCompras int,
primary key PK_idDetalleCompras(idDetalleCompra),
constraint FK_DetalleCompras_Productos foreign key Productos(idProducto)
references Prodcutos(idProducto),
constraint FK_DetalleCompras_Compras foreign key Compras(idCompras)
references Compras(idCompras)
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
	create procedure sp_AgregarCliente (in idCli int, in nitCli varchar(20), in nombresCli varchar(40), in apellidosCli varchar(40), in direccionCli varchar(60), in telefonoCli varchar(10), in correoCli varchar(45))
    begin
		insert into Clientes(idCliente, nitCliente, nombresCliente, apellidosCliente, direccionCliente, telefonoCliente, correoCliente)
			values (idCli, nitCli, nombresCli, apellidosCli, direccionCli, telefonoCli, correoCli);
            
	End $$
Delimiter ;

call sp_AgregarCliente(1, '6755945-1', 'Jordi', 'Wild', '4ta calle sur 1-20', 63548574, 'dgarcia-2023532@kinal.edu');
call sp_AgregarCliente(2, '8467592-1', 'Dua', 'Lipa', 'Avenida la reforma zona 1', 93625271, 'butifarraloka.com');

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

call sp_buscarCliente();


-- EDITAR


DELIMITER $$
create procedure sp_editarCliente(in idCliente int, in nitCliente varchar(10), in nombresCliente varchar(50), in apellidosCliente varchar(50), in direccionCliente varchar(150), in telefonoCliente varchar(10), in correoCliente varchar(40))
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

call sp_editarCliente();

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


