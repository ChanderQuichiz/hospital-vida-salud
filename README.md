# Hospital Vida Salud - Plataforma Distribuida de Microservicios Reactivos

Proyecto académico de microservicios reactivos para la gestión administrativa del **Hospital Vida Salud**, construido con **Spring Boot 3.2**, **Spring WebFlux**, **Spring Data R2DBC**, **PostgreSQL**, **Spring Cloud Gateway**, **Eureka Server** y **Reactor RabbitMQ**.

---

## 🏛️ Arquitectura de Microservicios y Puertos

| Módulo | Puerto | Tipo | Responsabilidad |
| :--- | :---: | :---: | :--- |
| **`eureka-server`** | `8761` | Infraestructura | Registro y descubrimiento dinámico de servicios |
| **`gateway`** | `8080` | Infraestructura | Punto de acceso centralizado con enrutamiento reactivo |
| **`pacientes-service`** | `8081` | Negocio (DDD) | Registro, consulta y mantenimiento de pacientes |
| **`citas-service`** | `8082` | Negocio (DDD) | Programación, confirmación, reprogramación y cancelación de citas |
| **`atenciones-service`** | `8083` | Negocio (DDD) | Registro de atenciones de citas confirmadas |
| **`notificaciones-service`** | `8084` | Negocio (DDD) | Consumo asíncrono de eventos para avisos a pacientes |
| **`auditoria-service`** | `8085` | Negocio (DDD) | Consumo asíncrono de eventos para trazabilidad administrativa |

---

## 🛠️ Requisitos Previos y Variables de Entorno

* **JDK 21**
* **Maven 3.9+** o **Maven Wrapper** `./mvnw`
* **PostgreSQL** (con 5 bases de datos vacías: `hospital_pacientes`, `hospital_citas`, `hospital_atenciones`, `hospital_notificaciones`, `hospital_auditoria`)
* **RabbitMQ** (Puerto 5672)

### Variables de Entorno
```env
DB_HOST=localhost
DB_PORT=5432
DB_USER=postgres
DB_PASSWORD=postgres
RABBIT_HOST=localhost
RABBIT_PORT=5672
RABBIT_USER=guest
RABBIT_PASSWORD=guest
EUREKA_URL=http://localhost:8761/eureka/
```

---

## 🚦 Orden de Arranque

1. Iniciar **RabbitMQ** y **PostgreSQL**.
2. **Eureka Discovery Server**:
   ```bash
   ./mvnw spring-boot:run -pl eureka-server
   ```
3. **Microservicios de Negocio**:
   ```bash
   ./mvnw spring-boot:run -pl pacientes-service
   ./mvnw spring-boot:run -pl citas-service
   ./mvnw spring-boot:run -pl atenciones-service
   ./mvnw spring-boot:run -pl notificaciones-service
   ./mvnw spring-boot:run -pl auditoria-service
   ```
4. **API Gateway**:
   ```bash
   ./mvnw spring-boot:run -pl gateway
   ```

---

## 🧪 Ejecutar Pruebas Automatizadas

Las pruebas automatizadas utilizan **H2 R2DBC en memoria** (`r2dbc-h2`) y no requieren PostgreSQL ni RabbitMQ reales instalados:

```bash
./mvnw test
```
O validación completa del ciclo de integración:
```bash
./mvnw --batch-mode verify
```

---

## 📡 Flujo Funcional Completo con `curl` (vía API Gateway - Port 8080)

### Paso 1: Registrar un Paciente
```bash
curl -X POST http://localhost:8080/api/pacientes \
  -H "Content-Type: application/json" \
  -d '{
    "tipoDocumento": "DNI",
    "numeroDocumento": "12345678",
    "nombres": "Juan",
    "apellidos": "Perez",
    "fechaNacimiento": "1990-05-15",
    "telefono": "987654321",
    "correo": "juan.perez@email.com",
    "direccion": "Av. Peru 123"
  }'
```

### Paso 2: Programar una Cita
```bash
curl -X POST http://localhost:8080/api/citas \
  -H "Content-Type: application/json" \
  -d '{
    "pacienteId": 1,
    "especialidad": "Cardiologia",
    "fechaHora": "2026-10-15T10:00:00",
    "profesionalCodigo": "MED-101",
    "motivo": "Consulta preventiva"
  }'
```

### Paso 3: Confirmar la Cita
```bash
curl -X PUT http://localhost:8080/api/citas/1/confirmar
```

### Paso 4: Consultar Notificaciones y Auditoría
```bash
curl http://localhost:8080/api/notificaciones?pacienteId=1
curl "http://localhost:8080/api/auditoria?recurso=cita&recursoId=1"
```

### Paso 5: Registrar la Atención Médica
```bash
curl -X POST http://localhost:8080/api/atenciones \
  -H "Content-Type: application/json" \
  -d '{
    "citaId": 1,
    "area": "Consultorio 302"
  }'
```
