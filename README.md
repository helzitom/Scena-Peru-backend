# Escena Peru - Backend

Monolito modular en Spring Boot (Java 17) para la plataforma de escena musical
peruana. Cada modulo aplica clean architecture (domain -> application ->
infrastructure) y se comunica con los demas SOLO mediante eventos de dominio,
para poder extraerse a microservicio propio sin reescritura.

## Requisitos
- Java 17+
- Maven 3.9+
- PostgreSQL 15+ (o Supabase)

## Configuracion
Variables de entorno (con defaults locales en `application.yml`):

```
DB_HOST=localhost
DB_PORT=5432
DB_NAME=escena_peru
DB_USER=postgres
DB_PASSWORD=postgres
```

1. Crea la base de datos y ejecuta `src/main/resources/db/esquema.sql`
   contra Postgres/Supabase (el `ddl-auto` esta en `validate`, no en `update`,
   para que el esquema real siempre viva en ese .sql versionado).
2. `mvn spring-boot:run`
3. API disponible en `http://localhost:8080`

## Endpoints incluidos en este primer corte

| Metodo | Ruta                              | Descripcion |
|--------|------------------------------------|-------------|
| POST   | /api/usuarios/registro             | Crea usuario (fan/banda/organizador) |
| POST   | /api/tocadas                       | Crea una tocada (autogestionada u organizada) |
| POST   | /api/tocadas/{id}/lineup            | Invita una banda al cartel de una tocada |
| GET    | /api/tocadas?ciudadId=1             | Lista tocadas confirmadas por ciudad (para el mapa) |
| WS     | /ws (STOMP+SockJS), topic /topic/ciudad/{id} | Notificaciones en tiempo real de nuevas tocadas |

## Estructura de modulos

```
usuarios/         auth, roles, ciudad de residencia          -> COMPLETO (dominio+app+infra)
tocadas/          eventos, lineup, autogestion vs organizador -> COMPLETO (dominio+app+infra)
notificaciones/   listener del evento TocadaCreadaEvent + WS  -> COMPLETO (dominio+app+infra)
bandas/           perfil extendido de banda                  -> dominio de referencia (agregar app+infra siguiendo el mismo patron)
organizadores/    perfil extendido de organizador/venue       -> dominio de referencia
ubicaciones/      ciudades (para escalar Lima -> Arequipa -> Trujillo...) -> dominio de referencia
feed/             lanzamientos y posts                        -> dominio de referencia
recuerdos/        fotos de fans ligadas a una tocada           -> dominio de referencia
shared/events/    bus de eventos interno (DomainEvent, EventPublisherPort) -> nucleo compartido
```

Los modulos marcados "dominio de referencia" ya tienen su modelo de dominio
escrito siguiendo el mismo esquema SQL; falta agregarles application/
(casos de uso) e infrastructure/ (JPA + controller) replicando exactamente
el patron ya completo en `usuarios/` y `tocadas/`.

## Como se extrae un modulo a microservicio mas adelante
1. El modulo ya no depende de clases de otros modulos (regla seguida desde
   el inicio: solo eventos de dominio como contrato).
2. Se mueve su paquete completo a un proyecto Spring Boot nuevo.
3. Se reemplaza `SpringEventPublisherAdapter` por un productor de
   Kafka/RabbitMQ, y el modulo extraido agrega un consumidor del mismo topico.
4. El resto del monolito no cambia una linea.

## Siguientes pasos sugeridos
- Integrar Spring Security + JWT en `usuarios` (login, roles en el token).
- Completar application/infrastructure de bandas, organizadores, feed y recuerdos.
- Conectar Supabase Storage para `foto_perfil_url`, `imagen_flyer_url`, `foto_url`.
- Agregar `AceptarInvitacionLineupUseCase` para que la banda confirme su
  presencia en el cartel de un organizador.
