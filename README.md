# 📱 TecnoStore — Sistema de Venta de Celulares

Sistema de consola desarrollado en Java para gestionar el catálogo de celulares, clientes, ventas y reportes de la tienda TecnoStore. Aplica principios de Programación Orientada a Objetos, patrones de diseño, persistencia con JDBC y procesamiento funcional con Stream API.

# 📱 TecnoStore - prompt

Enlace prompt: https://claude.ai/share/1ebf4d57-d121-41bd-b7cd-a75d3a1930c4
---
# Credenciales db

    private static final String URL = "jdbc:mysql://mysql-8d7963c-maria36508-33ec.b.aivencloud.com:21698/tecnostore_db?ssl-mode=REQUIRED";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_Pfy1WRtez6I7rJpjXaA";
    
## 📋 Descripción del proyecto

TecnoStore es una tienda minorista que necesitaba automatizar su control de ventas, inventario y clientes, reemplazando el manejo manual en hojas de cálculo. Este sistema permite:

- Gestionar el catálogo de celulares por gama (Alta, Media, Baja)
- Registrar y administrar clientes con validaciones
- Procesar ventas con cálculo automático de IVA del 19%
- Generar reportes de stock bajo, celulares más vendidos y ventas por mes
- Exportar un resumen de ventas en archivo de texto

---

## 🗂️ Estructura de clases

```
proyecto-java-tecnostore/
│
├── src/
│   └── tecnostore/
│       ├── model/
│       │   ├── CategoriaGama.java       # Enum: ALTA, MEDIA, BAJA
│       │   ├── Celular.java             # Entidad celular
│       │   ├── Cliente.java             # Entidad cliente
│       │   ├── DetalleVenta.java        # Línea de detalle por venta
│       │   └── Venta.java               # Cabecera de venta con IVA
│       │
│       ├── controller/
│       │   ├── CelularController.java   # Reglas de negocio de celulares
│       │   ├── ClienteController.java   # Reglas de negocio de clientes
│       │   └── VentaController.java     # Reglas de negocio de ventas
│       │
│       ├── dao/
│       │   ├── CelularDAO.java          # CRUD celulares en BD
│       │   ├── ClienteDAO.java          # CRUD clientes en BD
│       │   └── VentaDAO.java            # CRUD ventas y detalles en BD
│       │
│       ├── db/
│       │   └── ConexionDB.java          # Singleton de conexión MySQL
│       │
│       ├── view/
│       │   ├── MenuPrincipal.java       # Menú raíz del sistema
│       │   ├── MenuCelulares.java       # Submenú gestión celulares
│       │   ├── MenuClientes.java        # Submenú gestión clientes
│       │   └── MenuVentas.java          # Submenú ventas y reportes
│       │
│       ├── utils/
│       │   ├── Validador.java           # Validaciones reutilizables
│       │   ├── ArchivoUtils.java        # Generación del archivo .txt
│       │   ├── ReporteUtils.java        # Reportes con Stream API
│       │   └── CelularFactory.java      # Patrón Factory para celulares
│       │
│       ├── reportes/
│       │   └── reporte_ventas.txt       # Generado automáticamente
│       │
│       └── Main.java                    # Punto de entrada
│
└── tecnostore_db.sql                    # Script de creación de BD
```

---

## 🎨 Patrones de diseño implementados

### Factory Method — `CelularFactory`
Centraliza la creación de objetos `Celular` según el tipo ingresado. La vista no necesita conocer los detalles de construcción.

```java
Celular celular = CelularFactory.crearCelular("IOS", 0, "Apple", "iPhone 14", 4200000, 8);
```

### Singleton — `ConexionDB`
Garantiza que solo exista una conexión activa a la base de datos durante toda la ejecución del sistema.

```java
Connection conn = ConexionDB.getInstancia().getConnection();
```

---

## 🧱 Principios SOLID aplicados

| Principio | Dónde |
|---|---|
| Single Responsibility | Cada clase tiene una única responsabilidad (model, dao, controller, view separados) |
| Open/Closed | `CelularFactory` puede extenderse con nuevos tipos sin modificar el código existente |
| Dependency Inversion | Los controllers dependen de DAOs, no de la BD directamente |

---

## 🗄️ Base de datos

### Tablas

```sql
celulares     (id, marca, modelo, sistema_operativo, gama, precio, stock)
clientes      (id, nombre, identificacion, correo, telefono)
ventas        (id, id_cliente, fecha, total)
detalle_ventas(id, id_venta, id_celular, cantidad, subtotal)
```

### Relaciones

- `ventas.id_cliente` → `clientes.id` (RESTRICT al eliminar, CASCADE al actualizar)
- `detalle_ventas.id_venta` → `ventas.id` (CASCADE al eliminar y actualizar)
- `detalle_ventas.id_celular` → `celulares.id` (RESTRICT al eliminar, CASCADE al actualizar)

---

## ⚙️ Configuración de conexión MySQL

Abre `src/tecnostore/db/ConexionDB.java` y ajusta los siguientes valores:

```java
private static final String URL = "jdbc:mysql://localhost:3306/tecnostore_db";
private static final String USER = "root";
private static final String PASSWORD = "tu_contraseña";
```

Si usas una base de datos en la nube como Aiven:

```java
private static final String URL = "jdbc:mysql://host:puerto/tecnostore_db?ssl-mode=REQUIRED";
private static final String USER = "usuario";
private static final String PASSWORD = "contraseña";
```

---

## ▶️ Ejemplo de ejecución

```
========================================
        BIENVENIDO A TECNOSTORE
========================================
1. Gestion de Celulares
2. Gestion de Clientes
3. Gestion de Ventas
0. Salir
========================================
Opcion: 1

=== MENU CELULARES ===
1. Agregar celular
2. Listar celulares
3. Buscar celular por ID
4. Actualizar celular
5. Eliminar celular
0. Volver
Opcion: 2

1 | Samsung Galaxy S23 | ALTA | SO: Android | $3500000.0 | Stock: 10
2 | Apple iPhone 14 | ALTA | SO: iOS | $4200000.0 | Stock: 8
3 | Xiaomi Redmi Note 12 | MEDIA | SO: Android | $1200000.0 | Stock: 15
```

---

## 📄 Reporte generado

Al seleccionar la opción **Generar reporte en archivo** desde el menú de ventas, se crea o actualiza el archivo `src/reportes/reporte_ventas.txt`:

```
========================================
        REPORTE DE VENTAS TECNOSTORE
========================================
Venta ID    : 1
Cliente     : Carlos Pérez
Fecha       : 2026-06-05T10:30:00
----------------------------------------
  Celular   : Samsung Galaxy S23
  Cantidad  : 2
  Subtotal  : $7000000.0
Total (IVA) : $8330000.0
========================================
```

---

## 📄 Reporte generado global del examen

Al seleccionar la opción **Generar reporte en archivo** desde el menú de ventas, se crea o actualiza el archivo `src/reportes/reporte_ventas.txt`:

```
--- REPORTE GLOBAL TECNOCELL ---
Total de ventas: $12.450.000
Celulares vendidos por modelo:
- Samsung A55: 10 unidades
- iPhone 13: 6 unidades
- Xiaomi Note 13: 8 unidades

Clientes con créditos pendientes:
- Laura Gómez → $250,000
- Carlos Ríos → $120,000

Stock actual:
- Samsung A55: 3 unidades (ALERTA: bajo stock)
- iPhone 13: 12 unidades
- Xiaomi Note 13: 2 unidades (ALERTA: bajo stock)

Reporte guardado en: reporte_global.txt
```

---

## 🛠️ Requisitos

- Java 17 o superior
- NetBeans IDE
- MySQL 8.x o conexión a BD en la nube
- Conector JDBC: `mysql-connector-j-x.x.x.jar` agregado en Libraries del proyecto

---

## 👩‍💻 Desarrollado por

**Maria Garcia**  
Proyecto final — Curso Java Junior  
2026
