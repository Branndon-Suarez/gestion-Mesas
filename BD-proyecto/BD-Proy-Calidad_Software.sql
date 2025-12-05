CREATE DATABASE BD_CONTROL_MESAS;
USE BD_CONTROL_MESAS;

CREATE TABLE ROL (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE USUARIO (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    id_rol INT NOT NULL,
    CONSTRAINT fk_rol FOREIGN KEY (id_rol) REFERENCES ROL(id_rol)
);

CREATE TABLE MESA (
    id_mesa INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL UNIQUE,
    capacidad INT NOT NULL,
    estado ENUM('Libre', 'Ocupada', 'Sucia', 'Reservada') NOT NULL DEFAULT 'Libre'
);

CREATE TABLE PEDIDO (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora_apertura DATETIME NOT NULL,
    fecha_hora_cierre DATETIME NULL,
    total DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    id_mesa INT NOT NULL,
    CONSTRAINT id_mesa FOREIGN KEY (id_mesa) REFERENCES MESA(id_mesa),
    estado_pago ENUM('Pendiente', 'Pagado', 'Cancelado') NOT NULL DEFAULT 'Pendiente'
);

CREATE TABLE PRODUCTO (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    descripcion VARCHAR(150),
    estado BOOLEAN NOT NULL DEFAULT 1
);

CREATE TABLE DETALLE_PEDIDO (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    CONSTRAINT id_pedido FOREIGN KEY (id_pedido) REFERENCES PEDIDO(id_pedido),
    id_producto INT NOT NULL,
    CONSTRAINT id_producto FOREIGN KEY (id_producto) REFERENCES PRODUCTO(id_producto),
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    nota VARCHAR(255)
);

CREATE TABLE CLIENTE (
	id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    num_documento NUMERIC UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    telefono VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE DETALLE_PAGO (
	id_detalle_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido_fk INT NOT NULL,
    CONSTRAINT id_pedido_fk FOREIGN KEY(id_pedido_fk) REFERENCES PEDIDO(id_pedido),
    id_cliente_fk INT NOT NULL,
    CONSTRAINT id_cliente_fk FOREIGN KEY(id_cliente_fk) REFERENCES CLIENTE(id_cliente),
    fecha_pago DATETIME NOT NULL,
    metodo_pago ENUM("Efectivo", "Tarjeta Crédito", "Tarjeta Débito", "Neki"),
    monto_pago BIGINT NOT NULL
);

INSERT INTO ROL (nombre) VALUES ('ADMIN'), ('EMPLEADO');