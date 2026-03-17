-- personas
INSERT INTO persona (nombre, documento, edad, correo) VALUES
('Carlos Ramirez','1000123456',35,'carlos.ramirez@email.com'),
('Laura Mendoza','1000456789',29,'laura.mendoza@email.com'),
('Juan Torres','1000789123',31,'juan.torres@email.com'),
('Ana Lopez','1000654321',27,'ana.lopez@email.com'),
('Pedro Gomez','1000111222',40,'pedro.gomez@email.com');

-- admins
INSERT INTO admin (id, usuario, contrasena) VALUES
(1,'admin_carlos','$2a$10$M3clpkadzCPwX4dsYEFfD.Ajt9WCFEkfflKh6xGbaKs.iO0gfD/5e'),
(2,'admin_laura','$2a$10$M3clpkadzCPwX4dsYEFfD.Ajt9WCFEkfflKh6xGbaKs.iO0gfD/5e');

-- empleados
INSERT INTO empleado (id, usuario, contrasena) VALUES
(3,'empleado_juan','$2a$10$M3clpkadzCPwX4dsYEFfD.Ajt9WCFEkfflKh6xGbaKs.iO0gfD/5e'),
(4,'empleado_ana','$2a$10$M3clpkadzCPwX4dsYEFfD.Ajt9WCFEkfflKh6xGbaKs.iO0gfD/5e'),
(5,'empleado_pedro','$2a$10$M3clpkadzCPwX4dsYEFfD.Ajt9WCFEkfflKh6xGbaKs.iO0gfD/5e');

-- bodegas
INSERT INTO bodega (nombre, ubicacion, capacidad, encargado) VALUES
('Bodega Central','Bogota',5000,1),
('Bodega Norte','Bogota Norte',3000,2),
('Bodega Sur','Bogota Sur',2000,1);

-- productos
INSERT INTO producto (nombre, categoria, stock, precio) VALUES
('Laptop Dell','Tecnologia',20,3500000),
('Mouse Logitech','Tecnologia',100,80000),
('Teclado Mecánico','Tecnologia',50,250000),
('Monitor Samsung','Tecnologia',30,900000),
('Disco SSD 1TB','Almacenamiento',40,450000);

-- movimientos
INSERT INTO movimiento (tipo, responsable, bodega_origen, bodega_destino) VALUES
('ENTRADA',3,NULL,1),
('SALIDA',4,1,NULL),
('TRANSFERENCIA',5,1,2),
('TRANSFERENCIA',3,2,3);

-- movimiento_productos
INSERT INTO movimiento_producto (movimiento_id, producto_id, cantidad) VALUES
(1,1,10),
(1,2,20),
(2,3,5),
(3,4,8),
(4,5,6);

-- auditorias
INSERT INTO auditoria (tipo_opc, responsable) VALUES
('INSERTAR',1),
('ACTUALIZAR',2),
('ELIMINAR',1);

-- auditoria_cambios
INSERT INTO auditoria_cambio
(auditoria_id, producto_id, campo, categoria_anterior, categoria_nuevo, stock_anterior, stock_nuevo, precio_anterior, precio_nuevo)
VALUES
(1,1,'stock','Tecnologia','Tecnologia','10','20','3500000','3500000'),
(2,2,'precio','Tecnologia','Tecnologia','100','100','70000','80000'),
(3,NULL,'producto_eliminado','Tecnologia',NULL,'5',NULL,'250000',NULL);