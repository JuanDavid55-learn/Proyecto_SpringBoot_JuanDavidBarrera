# LogiTrack S.A. — Sistema de Gestión de Bodegas

## Tabla de contenidos

1. [Descripción del proyecto](#descripción-del-proyecto)
2. [Tecnologías utilizadas](#tecnologías-utilizadas)
3. [Configuración y despliegue](#configuración-y-despliegue)
4. [Base de datos](#base-de-datos)
5. [Autenticación JWT](#autenticación-jwt)
6. [Endpoints REST](#endpoints-rest)
7. [Seguridad y roles](#seguridad-y-roles)
8. [Manejo de excepciones](#manejo-de-excepciones)
9. [Auditoría automática](#auditoría-automática)
10. [Frontend](#frontend)
11. [Documentación Swagger](#documentación-swagger)

---

## Descripción del proyecto

**LogiTrack S.A.** es un sistema backend centralizado desarrollado en **Spring Boot** para la gestión y auditoría de bodegas. La empresa administra múltiples bodegas distribuidas en distintas ciudades, y este sistema reemplaza el control manual en hojas de cálculo por una solución con trazabilidad completa, control de accesos y endpoints REST documentados.

### Objetivos principales

- Controlar todos los movimientos de inventario entre bodegas (entradas, salidas, transferencias).
- Registrar automáticamente los cambios mediante auditorías por `EntityListeners` de JPA.
- Proteger la información con autenticación **JWT + Spring Security**.
- Ofrecer endpoints REST documentados con **Swagger/OpenAPI 3**.
- Exponer reportes auditables de los cambios realizados por cada usuario.

---

## Tecnologías utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje principal |
| Spring Boot | 3.x | Framework backend |
| Spring Security | 3.x | Autenticación y autorización |
| Spring Data JPA | 3.x | Persistencia de datos |
| MySQL | 8.0 | Base de datos relacional |
| JJWT | 0.12.3 | Generación y validación de tokens JWT |
| Lombok | Latest | Reducción de código boilerplate |
| SpringDoc OpenAPI | 2.5.0 | Documentación Swagger |
| Tomcat | Embebido | Servidor de aplicaciones |

---

## Configuración y despliegue

### Requisitos previos

- Java 17+
- Maven 3.8+
- MySQL 8.0+
- IntelliJ IDEA (recomendado)

### 2. Crear la base de datos

Ejecuta el archivo `schema.sql` en MySQL Workbench o desde la terminal:
Luego ejecuta el script completo de `data.sql` para los datos de prueba.

### 3. Configurar `application.properties`

```properties
# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/LogiTrack_SA?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=logitrack_clave_secreta_super_segura_2026
jwt.expiration=28800000

# Excepciones para rutas no encontradas
spring.mvc.throw-exception-if-no-handler-found=true
spring.web.resources.add-mappings=false
```

### 4. Ejecutar el proyecto

Desde IntelliJ IDEA, ejecutar la clase principal `SistemaGABodegasApplication.java`, o desde terminal:

```bash
mvn spring-boot:run
```

El servidor arranca en: `http://localhost:8080`

---

## Base de datos

### Diagrama de tablas

```
persona (id, nombre, documento, edad, correo)
    │
    ├── admin (id→persona, usuario, contrasena)
    └── empleado (id→persona, usuario, contrasena)

bodega (id, nombre, ubicacion, capacidad, encargado→admin)

producto (id, nombre, categoria, stock, precio)

movimiento (id, fecha, tipo, responsable→empleado, bodega_origen→bodega, bodega_destino→bodega)
    └── movimiento_producto (id, movimiento_id, producto_id, cantidad)

auditoria (id, tipo_opc, fecha_hora, responsable→admin)
    └── auditoria_cambio (id, auditoria_id, producto_id, campo,
                          categoria_anterior, categoria_nuevo,
                          stock_anterior, stock_nuevo,
                          precio_anterior, precio_nuevo)
```

### Herencia de Persona

`Admin` y `Empleado` usan la estrategia `JOINED` de JPA, lo que significa que comparten el mismo `id` con `Persona`. Al registrar un Admin, JPA inserta automáticamente en ambas tablas `persona` y `admin` en una sola transacción.

---

## Autenticación JWT

### Flujo de autenticación

```
1. POST /api/auth/login  →  { usuario, contrasena }
2. Servidor valida credenciales con BCrypt
3. Genera token JWT con usuario + rol (ADMIN o EMPLEADO)
4. Devuelve: { token, tipo: "Bearer", usuario, rol }

5. Cada request siguiente incluye el header:
   Authorization: Bearer <token>

6. JwtFiltro intercepta el request, valida el token,
   extrae el rol y lo carga en el SecurityContext
```

### Estructura del token JWT

```json
{
  "sub": "carlos_admin",
  "rol": "ADMIN",
  "iat": 1710500000,
  "exp": 1710528800
}
```

El token expira en **8 horas** (`28800000` ms).

---

## Endpoints REST

### Autenticación — `/api/auth`

| Método | Endpoint | Descripción | Auth |
|---|---|---|---|
| POST | `/api/auth/login` | Iniciar sesión | Si |
| POST | `/api/auth/register` | Registrar usuario | Si |

**Ejemplo login:**
```json
POST /api/auth/login
{
  "usuario": "carlos_admin",
  "contrasena": "654321"
}
```

---

### Admins — `/api/admins`

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/admins` | Crear admin |
| GET | `/api/admins` | Listar todos |
| GET | `/api/admins/{id}` | Obtener por ID |
| GET | `/api/admins/usuario/{usuario}` | Obtener por usuario |
| PUT | `/api/admins/{id}` | Actualizar |
| DELETE | `/api/admins/{id}` | Eliminar |

---

### Empleados — `/api/empleados`

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/empleados` | Crear empleado |
| GET | `/api/empleados` | Listar todos |
| GET | `/api/empleados/{id}` | Obtener por ID |
| GET | `/api/empleados/usuario/{usuario}` | Obtener por usuario |
| PUT | `/api/empleados/{id}` | Actualizar |
| DELETE | `/api/empleados/{id}` | Eliminar |

---

### Bodegas — `/api/bodegas`

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/bodegas` | Crear bodega |
| GET | `/api/bodegas` | Listar todas |
| GET | `/api/bodegas/{id}` | Obtener por ID |
| GET | `/api/bodegas/encargado/{adminId}` | Bodegas por encargado |
| PUT | `/api/bodegas/{id}` | Actualizar |
| DELETE | `/api/bodegas/{id}` | Eliminar |

---

### Productos — `/api/productos`

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/productos` | Crear producto |
| GET | `/api/productos` | Listar todos |
| GET | `/api/productos/{id}` | Obtener por ID |
| GET | `/api/productos/categoria/{categoria}` | Filtrar por categoría |
| GET | `/api/productos/stock-bajo?minimo=10` | Productos con stock bajo |
| PUT | `/api/productos/{id}` | Actualizar |
| DELETE | `/api/productos/{id}` | Eliminar |

---

### Movimientos — `/api/movimientos`

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/movimientos` | Registrar movimiento |
| GET | `/api/movimientos` | Listar todos |
| GET | `/api/movimientos/{id}` | Obtener por ID |
| GET | `/api/movimientos/tipo/{tipo}` | Filtrar por tipo |
| GET | `/api/movimientos/empleado/{id}` | Por empleado |
| GET | `/api/movimientos/bodega-origen/{id}` | Por bodega origen |
| GET | `/api/movimientos/bodega-destino/{id}` | Por bodega destino |
| GET | `/api/movimientos/rango-fechas?desde=...&hasta=...` | Por rango de fechas |

**Ejemplo registro de movimiento:**
```json
POST /api/movimientos
{
  "tipo": "TRANSFERENCIA",
  "responsableId": 3,
  "bodegaOrigenId": 1,
  "bodegaDestinoId": 2,
  "productos": [
    { "productoId": 1, "cantidad": 10 },
    { "productoId": 2, "cantidad": 5 }
  ]
}
```

---

### Auditorías — `/api/auditorias`

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auditorias` | Registrar auditoría manual |
| GET | `/api/auditorias` | Listar todas |
| GET | `/api/auditorias/{id}` | Obtener por ID |
| GET | `/api/auditorias/tipo/{tipoOpc}` | Por tipo de operación |
| GET | `/api/auditorias/admin/{adminId}` | Por admin responsable |
| GET | `/api/auditorias/rango-fechas?desde=...&hasta=...` | Por rango de fechas |

---

### Reportes — `/api/reportes`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/reportes/resumen` | Resumen general del sistema |

**Respuesta de resumen:**
```json
{
  "totalBodegas": 3,
  "totalProductos": 5,
  "totalMovimientos": 4,
  "totalAuditorias": 3,
  "stockPorBodega": [
    {
      "bodegaId": 1,
      "bodegaNombre": "Bodega Central",
      "bodegaUbicacion": "Bogota",
      "totalMovimientos": 2,
      "totalUnidadesMovidas": 30
    }
  ],
  "productosMasMovidos": [
    {
      "productoId": 1,
      "productoNombre": "Laptop Dell",
      "categoria": "Tecnologia",
      "stockActual": 20,
      "totalUnidadesMovidas": 10
    }
  ]
}
```

---

## Seguridad y roles

### Reglas de acceso

| Endpoints | Rol requerido |
|---|---|
| `/api/auth/**` | Público (sin token) |
| `/api/admins/**` | ADMIN |
| `/api/empleados/**` | ADMIN |
| `/api/bodegas/**` | ADMIN |
| `/api/auditorias/**` | ADMIN |
| `/api/productos/**` | ADMIN, EMPLEADO |
| `/api/movimientos/**` | ADMIN, EMPLEADO |
| `/api/reportes/**` | ADMIN, EMPLEADO |
| `/swagger-ui/**` | Público |

### Configuración de CORS

El sistema permite peticiones desde los siguientes orígenes:
- `http://localhost:5500`
- `http://127.0.0.1:5500`
- `http://localhost:63342` (IntelliJ Built-in Server)
- `http://127.0.0.1:63342`

---

## Manejo de excepciones

Todas las excepciones son manejadas globalmente por `GlobalExceptionHandler` y devuelven el siguiente formato JSON:

```json
{
  "timestamp": "2026-03-15T10:30:00",
  "status": 404,
  "message": "Producto no encontrado con ese id",
  "errorCode": "RESOURCE_NOT_FOUND"
}
```

| Excepción | HTTP | errorCode |
|---|---|---|
| `EntityNotFoundException` | 404 | `RESOURCE_NOT_FOUND` |
| `BusinessRuleException` | 400 | `BUSINESS_RULE_VIOLATION` |
| `MethodArgumentNotValidException` | 400 | `VALIDATION_FAILED` |
| `NoHandlerFoundException` | 404 | `ERR_NO_HANDLER_FOUND` |
| `HttpMessageNotReadableException` | 400 | `BAD_REQUEST` |
| `Exception` (genérica) | 500 | `INTERNAL_SERVER_ERROR` |

---

## Auditoría automática

El sistema implementa auditoría automática sobre la entidad `Producto` mediante **JPA EntityListeners**. Cada vez que se crea, actualiza o elimina un producto, se registra automáticamente una entrada en la tabla `auditoria` sin necesidad de llamar al endpoint manualmente.

```java
@Entity
@EntityListeners(ProductoAuditoriaListener.class)
public class Producto { ... }
```

| Evento JPA | Operación registrada |
|---|---|
| `@PostPersist` | `INSERTAR` |
| `@PostUpdate` | `ACTUALIZAR` |
| `@PreRemove` | `ELIMINAR` |

El listener obtiene el admin autenticado en ese momento desde `SecurityContextHolder` para registrarlo como responsable del cambio.

---

## Frontend

El proyecto incluye una interfaz web en un solo archivo `frontend/index.html` que consume la API REST. Para abrirla:

1. Abre el archivo `index.html` desde IntelliJ IDEA.
2. Haz clic en el ícono del navegador en la esquina del editor.
3. Asegúrate de que Spring Boot esté corriendo en `localhost:8080`.

### Funcionalidades del frontend

- Login y registro de usuarios (Admin/Empleado)
- Dashboard con estadísticas generales y alertas de stock bajo
- CRUD completo de Productos, Bodegas, Admins y Empleados
- Registro de movimientos con lista dinámica de productos
- Filtros de movimientos por tipo, empleado, bodega y rango de fechas
- Registro manual de auditorías con detalle de cambios
- Vista de auditorías con cambios anterior/nuevo por campo
- Reporte general con gráficas de barras

---

## Documentación Swagger

Con el proyecto corriendo, accede a la documentación interactiva en:

```
http://localhost:8080/swagger-ui/index.html
```

Para probar endpoints protegidos:

1. Ejecuta `POST /api/auth/login` y copia el campo `token` del response.
2. Haz clic en el botón **Authorize** 🔒 en la parte superior.
3. Escribe `Bearer <tu_token>` y confirma.
4. Todos los endpoints quedarán autenticados automáticamente.

---

## Credenciales de prueba

> Requiere haber ejecutado `data.sql` previamente.

| Usuario | Contraseña | Rol |
|---|---|---|
| `admin_carlos` | `654321` | ADMIN |
| `admin_laura` | `654321` | ADMIN |
| `empleado_juan` | `654321` | EMPLEADO |
| `empleado_ana` | `654321` | EMPLEADO |
| `empleado_pedro` | `654321` | EMPLEADO |

> **Nota:** Las contraseñas en `data.sql` deben estar hasheadas con BCrypt. Si usaste texto plano al insertar, ejecuta el UPDATE provisto en la sección de configuración o registra usuarios nuevos desde el frontend.

---

## Autor: Juan David Barrera Torres            Grupo: S1

Desarrollado como proyecto académico para LogiTrack S.A.  
Stack: **Spring Boot 3 + JWT + MySQL + Swagger + HTML/CSS/JS**
