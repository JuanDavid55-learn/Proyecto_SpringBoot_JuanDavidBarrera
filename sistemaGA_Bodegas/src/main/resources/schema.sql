CREATE DATABASE IF NOT EXISTS juandavid;
USE juandavid;

-- ============================================================
-- TABLA: persona
-- ============================================================
CREATE TABLE IF NOT EXISTS persona (
    id        INT        PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nombre    VARCHAR(100)  NOT NULL,
    documento VARCHAR(20)   NOT NULL UNIQUE,
    edad      INT           NOT NULL,
    correo    VARCHAR(150)  NOT NULL UNIQUE
);

-- ============================================================
-- TABLA: admin  (hereda de persona — mismo id)
-- ============================================================
CREATE TABLE IF NOT EXISTS admin (
    id         INT        PRIMARY KEY NOT NULL,
    usuario    VARCHAR(60)  NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    FOREIGN KEY (id) REFERENCES persona (id)
);

-- ============================================================
-- TABLA: empleado  (hereda de persona — mismo id)
-- ============================================================
CREATE TABLE IF NOT EXISTS empleado (
    id         INT        PRIMARY KEY NOT NULL,
    usuario    VARCHAR(60)  NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    FOREIGN KEY (id) REFERENCES persona (id)
);

-- ============================================================
-- TABLA: bodega
-- ============================================================
CREATE TABLE IF NOT EXISTS bodega (
    id         INT        PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nombre     VARCHAR(100) NOT NULL,
    ubicacion  VARCHAR(100) NOT NULL,
    capacidad  INT          NOT NULL,
    encargado  INT       NOT NULL,
    FOREIGN KEY (encargado) REFERENCES admin (id)
);

-- ============================================================
-- TABLA: producto
-- ============================================================
CREATE TABLE IF NOT EXISTS producto (
    id        INT        PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nombre    VARCHAR(100)   NOT NULL,
    categoria VARCHAR(80)    NOT NULL,
    stock     INT            NOT NULL,
    precio    DECIMAL(12, 2) NOT NULL
);

-- ============================================================
-- TABLA: movimiento
-- ============================================================
CREATE TABLE IF NOT EXISTS movimiento (
    id              INT      PRIMARY KEY AUTO_INCREMENT NOT NULL,
    fecha           DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo            ENUM('ENTRADA','SALIDA','TRANSFERENCIA') NOT NULL,
    responsable     INT      NOT NULL,
    bodega_origen   INT      NULL,           -- NULL cuando es ENTRADA pura
    bodega_destino  INT      NULL,           -- NULL cuando es SALIDA pura
    FOREIGN KEY (responsable)    REFERENCES empleado (id),
    FOREIGN KEY (bodega_origen)  REFERENCES bodega (id),
    FOREIGN KEY (bodega_destino) REFERENCES bodega (id)
);

-- ============================================================
-- TABLA: movimiento_producto 
-- ============================================================
CREATE TABLE IF NOT EXISTS movimiento_producto (
    id             INT    PRIMARY KEY AUTO_INCREMENT NOT NULL,
    movimiento_id  INT    NOT NULL,
    producto_id    INT    NOT NULL,
    cantidad       INT    NOT NULL,
    FOREIGN KEY (movimiento_id) REFERENCES movimiento (id),
    FOREIGN KEY (producto_id)   REFERENCES producto (id)
);

-- ============================================================
-- TABLA: auditoria
-- ============================================================
CREATE TABLE IF NOT EXISTS auditoria (
    id           INT      PRIMARY KEY AUTO_INCREMENT NOT NULL,
    tipo_opc     ENUM('INSERTAR', 'ACTUALIZAR', 'ELIMINAR') NOT NULL,
    fecha_hora   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    responsable  INT      NOT NULL,
    FOREIGN KEY (responsable) REFERENCES admin (id)
);

-- ============================================================
-- TABLA: auditoria_cambio
-- ============================================================
CREATE TABLE IF NOT EXISTS auditoria_cambio (
    id              INT      PRIMARY KEY AUTO_INCREMENT NOT NULL,
    auditoria_id    INT      NOT NULL,
    producto_id     INT      NULL,         -- producto afectado (NULL si fue eliminado)
    campo           VARCHAR(60)   NOT NULL,     -- nombre del campo modificado
    categoria_anterior  VARCHAR(255)  NULL,
    categoria_nuevo     VARCHAR(255)  NULL,
    stock_anterior  VARCHAR(255)  NULL,
    stock_nuevo     VARCHAR(255)  NULL,
    precio_anterior  VARCHAR(255)  NULL,
    precio_nuevo     VARCHAR(255)  NULL,
    FOREIGN KEY (auditoria_id) REFERENCES auditoria (id),
    FOREIGN KEY (producto_id)  REFERENCES producto (id)
);