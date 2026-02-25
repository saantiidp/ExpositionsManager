# 📚 ExpoManager — Sistema de Gestión de Exposiciones

## 🧠 Descripción General

**ExpoManager** es una aplicación software diseñada para la **gestión integral de centros de exposiciones**, desarrollada como proyecto académico en la asignatura *Proyecto de Análisis y Diseño de Software* del **Grado en Ingeniería Informática de la Universidad Autónoma de Madrid (UAM)**.

El sistema permite administrar exposiciones, salas, obras, empleados, clientes, ventas de entradas, sorteos, notificaciones y contabilidad, siguiendo principios de **ingeniería del software, orientación a objetos, calidad, mantenibilidad y escalabilidad**.

El proyecto cubre **todo el ciclo de vida del software**:
- Captura y análisis de requisitos
- Diseño de arquitectura y modelo de dominio
- Implementación orientada a objetos en Java
- Pruebas unitarias, de integración, sistema y aceptación
- Documentación técnica y funcional

> 🎓 Calificación obtenida: **8/10**  
> 🎓 Titulación: **Grado en Ingeniería Informática – Universidad Autónoma de Madrid (UAM)**

---

## 🎯 Objetivos del Proyecto

- Diseñar y construir una aplicación **robusta, segura y mantenible**.
- Aplicar **buenas prácticas de Ingeniería del Software**.
- Trabajar con **arquitectura orientada a objetos**.
- Implementar un sistema realista con:
  - Múltiples roles de usuario
  - Reglas de negocio complejas
  - Gestión de estado y flujos de trabajo
- Asegurar la calidad mediante **pruebas y validaciones**.

---

## 🏗️ Arquitectura y Diseño

El sistema está basado en:

- **Modelo de dominio orientado a objetos**
- Separación de responsabilidades:
  - Lógica de negocio
  - Entidades del dominio (Exposición, Sala, Obra, Usuario, etc.)
  - Servicios del sistema (Sistema, PasarelaPago, GeneradorEntradasPDF, etc.)
- Uso de:
  - Herencia y polimorfismo (`Obra`, `Sala`, `Sorteo`, `Descuento`, etc.)
  - Patrones de diseño (por ejemplo, `Sistema` como **Singleton**)
  - Enumeraciones de estado (`EstadoObra`, `EstadoExposición`)
  - Filtros para búsqueda avanzada de exposiciones

El diseño completo está documentado mediante:
- Documento de Requisitos
- Diagrama de Clases UML
- Casos de Uso detallados
- Maquetas de interfaz

---

## 👥 Roles del Sistema

### 👔 Gestor
- Crear, modificar, cancelar y prorrogar exposiciones
- Crear salas y subsalas
- Dar de alta empleados y obras
- Configurar descuentos y sorteos
- Consultar contabilidad
- Cerrar y reabrir exposiciones
- Gestionar horarios del centro

### 🧑‍💼 Empleado
- Vender entradas en taquilla (si tiene permiso)
- Configurar temperatura y humedad de salas
- Enviar notificaciones a clientes
- Modificar sus datos personales

### 🧑 Cliente Registrado
- Buscar y filtrar exposiciones
- Comprar entradas online
- Consultar sus entradas y notificaciones
- Participar en sorteos
- Elegir si desea recibir publicidad

### 👤 Cliente No Registrado
- Ver exposiciones y obras
- Registrarse en el sistema
- Comprar entradas únicamente en taquilla

---

## 🧩 Funcionalidades Principales

- 📅 Gestión completa de exposiciones (alta, cancelación, cierre temporal, prórroga)
- 🏛️ Gestión de salas y subsalas con control de aforo y condiciones ambientales
- 🖼️ Gestión de obras (cuadros, esculturas, fotos, audiovisuales)
- 🎟️ Venta de entradas:
  - Online con pasarela de pago
  - En taquilla por empleados
- 📄 Generación de entradas en PDF
- 🎁 Sistema de sorteos con distintos tipos de uso
- 🔔 Sistema de notificaciones a clientes
- 💰 Módulo de contabilidad con filtros por fecha y exposición
- 🔍 Búsqueda avanzada con filtros (fecha, tipo de exposición, tipo de obra)
- 🧪 Validaciones de reglas de negocio y control de errores

---

## 🧪 Calidad y Pruebas

El proyecto contempla:

- ✅ Pruebas unitarias con JUnit
- 🔁 Pruebas de regresión
- 🔗 Pruebas de integración
- 🧪 Pruebas de sistema y aceptación
- 📏 Validación de requisitos funcionales y no funcionales:
  - Rendimiento
  - Usabilidad
  - Consistencia
  - Robustez ante errores

---

## 🛠️ Tecnologías y Enfoque

- **Lenguaje:** Java  
- **Paradigma:** Orientación a Objetos  
- **Pruebas:** JUnit  
- **Diseño:** UML, Casos de Uso, Modelo de Dominio  
- **Metodología:** Enfoque de Ingeniería del Software con ciclo completo de vida  
- **Principios aplicados:**
  - Alta cohesión y bajo acoplamiento
  - Responsabilidad única
  - Abstracción y polimorfismo
  - Diseño orientado a mantenibilidad y extensibilidad

---

## 📚 Competencias Desarrolladas

- Análisis y especificación de requisitos
- Diseño de arquitectura software
- Modelado orientado a objetos
- Implementación de sistemas complejos
- Aplicación de buenas prácticas de ingeniería del software
- Trabajo con pruebas y control de calidad
- Documentación técnica y funcional
- Gestión de un proyecto software de tamaño medio

---

## 🚀 Relevancia Profesional

Este proyecto es especialmente relevante para roles de:

- Backend Engineer (Java / Kotlin)
- Software Engineer
- Ingeniero de Software orientado a arquitectura y dominio

Demuestra experiencia en:
- Sistemas con reglas de negocio complejas
- Diseño orientado a dominio
- Calidad, pruebas y mantenibilidad
- Enfoque de ingeniería y no solo implementación
---

## 📎 Documentación Incluida

- Documento de Análisis de Requisitos
- Documento de Diseño (UML, modelo de clases)
- Maquetas de interfaz
- Casos de uso detallados
