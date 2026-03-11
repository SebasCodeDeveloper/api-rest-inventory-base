<div align="center">
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg" width="80px" />

  <h1>📦 Advanced Inventory & Order System 
    <img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">
  </h1>
	
 <p>
	 
  <img src="https://media.giphy.com/media/QssGEmpkyEOhBCb7e1/giphy.gif" width="100"/>
</p>


  <p><strong>Backend Architecture with Spring Boot • DTOs • Business Rules • Exception Handling</strong></p>

  <img src="https://img.shields.io/badge/Java-17_/_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/Hibernate-JPA-59666C?style=for-the-badge&logo=hibernate&logoColor=white" />
</div>

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

# 📖 Project Overview

Backend desarrollado con **Spring Boot** para la gestión de **usuarios, productos y órdenes**.

El sistema implementa **arquitectura en capas**, manejo de **DTOs inmutables** y **validaciones de reglas de negocio**.

<div align="center">

| Característica | Implementación |
|------|------|
Arquitectura | Layered Architecture |
Lenguaje | Java 17 / 21 |
Framework | Spring Boot 3 |
Persistencia | Spring Data JPA + Hibernate |
Base de datos | MySQL |
Validaciones | Jakarta Validation |

</div>

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

# 🏗️ Arquitectura del Sistema

```
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

<div align="center">

| Capa | Responsabilidad |
|------|------|
Controller | Exposición de endpoints REST |
Service | Lógica de negocio |
Repository | Acceso a datos |
DTO | Transferencia de datos |
Exception | Manejo centralizado de errores |

</div>

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

# 🧩 Componentes del Sistema

## 🔄 Controllers

<div align="center">

| Controller | Responsabilidad |
|------|------|
UserController | Gestión de usuarios |
ProductController | Gestión de productos |
OrderController | Creación y gestión de órdenes |
OrderDetailController | Reportes de órdenes |

</div>

---

## 🧠 Services

<div align="center">

| Servicio | Función |
|------|------|
UserService | Gestión de usuarios |
ProductService | Gestión de productos |
OrderService | Lógica de órdenes |
OrderDetailService | Consultas y reportes |

</div>

---

## 📦 DTOs

Uso de **Java Records** para garantizar **inmutabilidad**.

<div align="center">

| Tipo | DTO |
|------|------|
Request | OrderRq |
Request | ProductRq |
Request | UserRq |
Request | OrderDetailRq |
Query | GetOrderByEmailRq |
Response | OrderRs |
Response | OrderReportRs |
Response | OrderDetailReportRs |

</div>

---

## 🗄️ Entities

<div align="center">

| Entidad | Descripción |
|------|------|
User | Información del usuario |
Product | Catálogo de productos |
Order | Orden de compra |
OrderDetail | Productos dentro de una orden |

</div>

Relaciones principales

```
User 1 --- * Order
Order 1 --- * OrderDetail
Product 1 --- * OrderDetail
```

---

## 🗂️ Repositories

<div align="center">

| Repository | Función |
|------|------|
UserRepository | CRUD usuarios |
ProductRepository | CRUD productos |
OrderRepository | Persistencia de órdenes |
OrderDetailRepository | Detalles de órdenes |

</div>

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

# ⚠️ Manejo de Excepciones

Sistema centralizado de errores.

<div align="center">

| Componente | Función |
|------|------|
BaseBusinessException | Excepción base |
BusinessErrorType | Enum de errores |
GlobalExceptionHandler | Manejo global |

</div>

Excepciones específicas

<div align="center">

| Excepción | Descripción |
|------|------|
UserException | Errores de usuario |
ProductException | Errores de producto |
OrderException | Errores de orden |
OrderDetailException | Errores de detalle |

</div>

```
@RestControllerAdvice
```

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

# 🛡️ Business Rules

<div align="center">

| Entidad | Regla |
|------|------|
User | No se puede eliminar si tiene órdenes |
Product | No se puede eliminar si está en una orden |
Order | Debe tener al menos un detalle |
OrderDetail | Debe tener producto válido |

</div>

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

# 🔄 Flujo de Creación de Orden

<div align="center">

| Paso | Acción |
|------|------|
1 | Validar usuario |
2 | Validar productos |
3 | Calcular subtotales |
4 | Calcular total |
5 | Guardar orden |
6 | Guardar detalles |

</div>

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

# 🧪 QA Matrix

<div align="center">

| ID | Escenario | Resultado Esperado |
|----|-----------|--------------------|
TC-01 | Eliminación de usuario con órdenes | Error 409 |
TC-02 | Consulta de órdenes por email | Lista de órdenes |
TC-03 | Validación de estado de orden | Estado consistente |
TC-04 | Persistencia en base de datos | Datos guardados correctamente |

</div>

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

# 🛠️ Tecnologías

<div align="center">

| Tecnología | Uso |
|------|------|
Java 17 / 21 | Lenguaje principal |
Spring Boot | Framework backend |
Spring Data JPA | Persistencia |
Hibernate | ORM |
MySQL | Base de datos |
Lombok | Reducción de boilerplate |
Jakarta Validation | Validación |

</div>

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

# 💻 Ejemplo de Endpoint

### Obtener órdenes por email

```
POST /api/details
```

Request

```json
{
  "email":"cliente@email.com"
}
```

Response

```json
[
 {
   "orderId":"123",
   "email":"cliente@email.com",
   "productName":"Laptop",
   "quantity":1,
   "subtotal":2000,
   "status":"COMPLETED"
 }
]
```


<div align="center">
  <h3>
    <img src='https://raw.githubusercontent.com/ShahriarShafin/ShahriarShafin/main/Assets/handshake.gif' width="60px" />
   Hablemos de Arquitectura y Calidad de Software
  </h3>
</div>
<br>
</div>
<p align="center">
  <a href="mailto:sebatianpena950@gmail.com"  target="_blank">
    <img align="center" alt="TienHuynh-TN | Gmail" width="26px" src="https://github.com/SebasCodeDeveloper/SebasCodeDeveloper/blob/main/gmail.gif" />
  </a> &nbsp;&nbsp;
  
  <a href="https://www.linkedin.com/in/sebastian-penna-dev/" target="_blank">
    <img align="center" alt="TienHuynh-TN | Linkedin" width="43px" src="https://media3.giphy.com/media/a9eTxCdJhDU98Jp79g/giphy.gif" />
  </a> &nbsp;&nbsp;
  
  <a href="https://www.facebook.com/sebastian.pena.507464/" target="_blank">
      <img align="center"  width="44px" src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExNHMwbHBtODN4c3R2cTBpMGl3MmF4d3E0ZHM0emF5NWs4YzF2MWE1dSZlcD12MV9zdGlja2Vyc19zZWFyY2gmY3Q9cw/pUAgNUnRUqxyx5PsHe/giphy.gif" />
  </a> &nbsp;&nbsp;
  
  <a href="https://www.instagram.com/sebas.720.pdc/" target="_blank">
    <img align="center" alt="TienHuynh-TN | Instagram" width="35px" src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExM2s5ZG1qYmV1a2sybHV0eGt1ejhsNXhkc2t1OThyamozOWFzd29vMSZlcD12MV9zdGlja2Vyc19zZWFyY2gmY3Q9cw/rZAStCy2giIh7le1Gs/giphy.gif" />
  </a> &nbsp;&nbsp;
  
  <a href="https://github.com/SebasCodeDeveloper" target="_blank">
    <img align="center" alt="TienHuynh-TN | GitHub" width="26px" src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExZW91YjQ0eHppM2c5bmluajMyN2VhaW1xeDY5djI0YXMyYm9nYjN0aCZlcD12MV9zdGlja2Vyc19zZWFyY2gmY3Q9cw/OFEabGCcVqsckIGn8G/giphy.gif" />
  </a> &nbsp;&nbsp;  
<br><br>

<div align="center">
  :heart_eyes: Thanks for watching my profile! Have a nice day! :wink: <br/>
  &copy; 2026 Johan Sebastian Peña Ordoñez
</div> 

<div align="center"> 
  <img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExZW91YjQ0eHppM2c5bmluajMyN2VhaW1xeDY5djI0YXMyYm9nYjN0aCZlcD12MV9zdGlja2Vyc19zZWFyY2gmY3Q9cw/CwTvSiWflgCGKgz5eb/giphy.gif" width="10%"/>
	
  <img src="https://raw.githubusercontent.com/bornmay/bornmay/Update/svg/Bottom.svg" alt="Github Stats" />
</div>
