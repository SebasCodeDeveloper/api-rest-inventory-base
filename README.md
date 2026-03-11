<div align="center">
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg" width="80px" />
  <h1>📦 Inventory & Order Management System  <img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif"></h1>
 <p align="center">
  <img src="https://media.giphy.com/media/QssGEmpkyEOhBCb7e1/giphy.gif" width="100"/>
</p>
  <p><strong>Desarrollo de API REST con Enfoque en Integridad Referencial y QA</strong></p>
 

  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/Hibernate-Data_Integrity-59666C?style=for-the-badge&logo=hibernate&logoColor=white" />
</div>

 <img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

## 📖 Descripción del Proyecto

Este sistema de gestión de inventarios permite el control centralizado de **Usuarios**, **Productos** y **Órdenes de Compra**. El desarrollo destaca por la implementación de reglas de negocio estrictas que impiden la pérdida de datos y aseguran la consistencia de la información mediante validaciones de integridad referencial.

 <img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

## 🛠️ Arquitectura Técnica

El proyecto se basa en una arquitectura de capas diseñada para el desacoplamiento y la eficiencia:

- **🔄 Controller Layer:** Endpoints RESTful para la interacción con el cliente.
- **⚙️ Service Layer:** Lógica de negocio avanzada y orquestación de transacciones.
- **🗄️ Persistence Layer:** Repositorios JPA optimizados para el manejo de UUIDs.
- **⚠️ Exception Handler:** Centralización de errores de negocio para respuestas estandarizadas.

 <img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

## 🛡️ Business Rules & Data Integrity (Especialidad QA)

Como **Automatizador**, he integrado "escudos de integridad" en los métodos críticos de eliminación:

> [!IMPORTANT]
> **Protección contra Borrado Físico:** Se utiliza la lógica `!list.isEmpty()` para validar dependencias antes de cualquier `delete`.

| Entidad | Regla de Protección | Acción ante Conflicto |
| :--- | :--- | :--- |
| **User** | Bloqueo si existen órdenes vinculadas. | `409 Conflict` & Rollback |
| **Product** | Bloqueo si hay registros en detalles de orden. | `409 Conflict` & Rollback |
| **General** | Todas las operaciones son atómicas. | `@Transactional` |

 <img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

## 🧪 Reporte de Calidad (QA Matrix)

Ciclo de validación técnica realizado para asegurar la robustez del sistema:

| ID | Módulo | Caso de Prueba | Resultado Esperado | Estado |
| :--- | :--- | :--- | :--- | :--- |
| **TC-01** | `User` | Delete User with orders | **409 - Transaction Rollback** | ✅ Pass |
| **TC-02** | `User` | Delete User without orders | **204 - Successful Delete** | ✅ Pass |
| **TC-03** | `Product` | Delete Product with sales | **409 - Transaction Rollback** | ✅ Pass |
| **TC-04** | `Global` | UUID Format Persistence | **Valid CHAR(36) Store** | ✅ Pass |
 <img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">
 <!-- dwdcdac-->
<div align="center">
  <h3>
    <img src='https://raw.githubusercontent.com/ShahriarShafin/ShahriarShafin/main/Assets/handshake.gif' width="60px" />
    For More Information, Please Check Out or Connect Me Via
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
  &copy; 2025 Johan Sebastian Peña Ordoñez
</div> 

<div align="center"> 
  <img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExZW91YjQ0eHppM2c5bmluajMyN2VhaW1xeDY5djI0YXMyYm9nYjN0aCZlcD12MV9zdGlja2Vyc19zZWFyY2gmY3Q9cw/CwTvSiWflgCGKgz5eb/giphy.gif" width="10%"/>
	
  <img src="https://raw.githubusercontent.com/bornmay/bornmay/Update/svg/Bottom.svg" alt="Github Stats" />
</div>

<div align="center">
 💡 La mejora continua no es una opción, es parte del código.
</div> 
