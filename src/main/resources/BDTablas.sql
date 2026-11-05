CREATE DATABASE IF NOT EXISTS tienda_soluciones_tecnologicas;
USE tienda_soluciones_tecnologicas;

CREATE TABLE rol (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    contrasena VARCHAR(255) NOT NULL,
    rol_id INT NOT NULL,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id) REFERENCES rol(id)
);

CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
);

CREATE TABLE producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    existencia INT NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    imagen VARCHAR(255),
    categoria_id INT NOT NULL,
    CONSTRAINT fk_producto_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id),
    CONSTRAINT chk_producto_precio CHECK (precio >= 0),
    CONSTRAINT chk_producto_existencia CHECK (existencia >= 0)
);

CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES usuario(id),
    CONSTRAINT chk_pedido_total CHECK (total >= 0)
);

CREATE TABLE detalle_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detalle_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id),
    CONSTRAINT fk_detalle_producto FOREIGN KEY (producto_id) REFERENCES producto(id),
    CONSTRAINT chk_detalle_cantidad CHECK (cantidad > 0),
    CONSTRAINT chk_detalle_precio CHECK (precio_unitario >= 0)
);

CREATE TABLE dispositivo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_dispositivo VARCHAR(50) NOT NULL,
    marca_modelo VARCHAR(100) NOT NULL,
    cliente_id INT NOT NULL,
    CONSTRAINT fk_dispositivo_cliente FOREIGN KEY (cliente_id) REFERENCES usuario(id)
);

CREATE TABLE reparacion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dispositivo_id INT NOT NULL,
    cliente_id INT NOT NULL,
    tecnico_id INT NULL,
    problema_reportado TEXT NOT NULL,
    diagnostico TEXT NULL,
    resultado_tecnico TEXT NULL,
    costo DECIMAL(10,2) NULL,
    estado VARCHAR(50) NOT NULL,
    fecha_solicitud DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reparacion_dispositivo FOREIGN KEY (dispositivo_id) REFERENCES dispositivo(id),
    CONSTRAINT fk_reparacion_cliente FOREIGN KEY (cliente_id) REFERENCES usuario(id),
    CONSTRAINT fk_reparacion_tecnico FOREIGN KEY (tecnico_id) REFERENCES usuario(id),
    CONSTRAINT chk_reparacion_costo CHECK (costo IS NULL OR costo >= 0)
);

INSERT INTO rol (nombre) VALUES ('Administrador'), ('Cliente'), ('Técnico');