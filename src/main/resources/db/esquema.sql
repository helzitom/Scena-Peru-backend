-- =========================================================
-- ESQUEMA DE BASE DE DATOS - PLATAFORMA ESCENA MUSICAL PERU
-- PostgreSQL (compatible con Supabase)
-- =========================================================

-- ---------------------------------------------------------
-- 1. UBICACIONES (base para escalar a todo el Peru)
-- ---------------------------------------------------------
CREATE TABLE ciudades (
    id            SERIAL PRIMARY KEY,
    nombre        VARCHAR(80) NOT NULL,      -- Lima, Arequipa, Trujillo...
    departamento  VARCHAR(80) NOT NULL,
    activa        BOOLEAN DEFAULT TRUE,      -- para lanzar por fases
    UNIQUE (nombre, departamento)
);

-- ---------------------------------------------------------
-- 2. USUARIOS (tabla base de autenticacion)
-- ---------------------------------------------------------
CREATE TYPE tipo_usuario AS ENUM ('fan', 'banda', 'organizador', 'admin');

CREATE TABLE usuarios (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email           VARCHAR(150) UNIQUE NOT NULL,
    password_hash   VARCHAR(255) NOT NULL,     -- si usas Supabase Auth/Firebase, esto puede vivir fuera
    tipo            tipo_usuario NOT NULL DEFAULT 'fan',
    ciudad_id       INTEGER REFERENCES ciudades(id),
    nombre_display  VARCHAR(100) NOT NULL,
    foto_perfil_url TEXT,
    verificado      BOOLEAN DEFAULT FALSE,     -- check azul para bandas/organizadores reales
    created_at      TIMESTAMPTZ DEFAULT now(),
    updated_at      TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX idx_usuarios_ciudad ON usuarios(ciudad_id);
CREATE INDEX idx_usuarios_tipo ON usuarios(tipo);

-- ---------------------------------------------------------
-- 3. PERFIL EXTENDIDO DE BANDA
-- ---------------------------------------------------------
CREATE TABLE perfiles_banda (
    usuario_id      UUID PRIMARY KEY REFERENCES usuarios(id) ON DELETE CASCADE,
    nombre_banda    VARCHAR(100) NOT NULL,
    genero_principal VARCHAR(60) NOT NULL,     -- rock subterraneo, cumbia, huayno, indie, metal, urbano...
    generos_secundarios TEXT[],                -- array para filtros combinados
    biografia       TEXT,
    anio_formacion  SMALLINT,
    redes_sociales  JSONB DEFAULT '{}',        -- {"instagram": "...", "spotify": "...", "youtube": "..."}
    integrantes     JSONB DEFAULT '[]'         -- [{"nombre":"...", "rol":"vocalista"}]
);

CREATE INDEX idx_banda_genero ON perfiles_banda(genero_principal);

-- ---------------------------------------------------------
-- 4. PERFIL EXTENDIDO DE ORGANIZADOR (promotor / venue / colectivo)
-- ---------------------------------------------------------
CREATE TYPE tipo_organizador AS ENUM ('promotor', 'venue', 'colectivo');

CREATE TABLE perfiles_organizador (
    usuario_id      UUID PRIMARY KEY REFERENCES usuarios(id) ON DELETE CASCADE,
    nombre_publico  VARCHAR(100) NOT NULL,
    tipo            tipo_organizador NOT NULL,
    biografia       TEXT,
    contacto_whatsapp VARCHAR(20),
    redes_sociales  JSONB DEFAULT '{}'
);

-- ---------------------------------------------------------
-- 5. VENUES / LOCALES (independiente de quien organiza)
-- ---------------------------------------------------------
CREATE TABLE venues (
    id            SERIAL PRIMARY KEY,
    nombre        VARCHAR(120) NOT NULL,
    direccion     VARCHAR(200),
    ciudad_id     INTEGER NOT NULL REFERENCES ciudades(id),
    latitud       DECIMAL(9,6),
    longitud      DECIMAL(9,6),
    capacidad_aprox SMALLINT,
    es_espacio_libre BOOLEAN DEFAULT FALSE   -- true = calle/parque/acustico improvisado, no requiere venue formal
);

CREATE INDEX idx_venues_ciudad ON venues(ciudad_id);

-- ---------------------------------------------------------
-- 6. TOCADAS (eventos) - soporta autogestion Y organizador
-- ---------------------------------------------------------
CREATE TYPE creador_tipo_enum AS ENUM ('banda', 'organizador');
CREATE TYPE estado_tocada_enum AS ENUM ('confirmada', 'tentativa', 'cancelada', 'finalizada');

CREATE TABLE tocadas (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    titulo          VARCHAR(150) NOT NULL,
    descripcion     TEXT,
    ciudad_id       INTEGER NOT NULL REFERENCES ciudades(id),
    venue_id        INTEGER REFERENCES venues(id),        -- null si es espacio libre / calle
    ubicacion_manual VARCHAR(200),                          -- texto libre si no hay venue registrado
    latitud         DECIMAL(9,6),
    longitud        DECIMAL(9,6),
    fecha           DATE NOT NULL,
    hora_inicio     TIME NOT NULL,

    -- quien creo el evento y puede editarlo
    creador_tipo    creador_tipo_enum NOT NULL,
    creador_id      UUID NOT NULL,   -- referencia a perfiles_banda.usuario_id o perfiles_organizador.usuario_id

    estado          estado_tocada_enum NOT NULL DEFAULT 'confirmada',
    precio_entrada  DECIMAL(8,2),          -- null = libre/gratis
    link_entradas   TEXT,                  -- si integras Culqi/Niubiz, este es el link o el id de la transaccion
    imagen_flyer_url TEXT,
    created_at      TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX idx_tocadas_ciudad_fecha ON tocadas(ciudad_id, fecha);
CREATE INDEX idx_tocadas_creador ON tocadas(creador_tipo, creador_id);

-- ---------------------------------------------------------
-- 7. CARTEL DE LA TOCADA (line-up) - horario por banda
-- ---------------------------------------------------------
CREATE TYPE estado_invitacion_enum AS ENUM ('pendiente', 'aceptada', 'rechazada');

CREATE TABLE tocada_lineup (
    id                  SERIAL PRIMARY KEY,
    tocada_id           UUID NOT NULL REFERENCES tocadas(id) ON DELETE CASCADE,
    banda_id            UUID NOT NULL REFERENCES perfiles_banda(usuario_id),
    hora_presentacion   TIME,                -- puede quedar null hasta confirmarse
    orden_aparicion     SMALLINT,            -- 1 = abre, 2 = sigue, etc.
    estado_invitacion   estado_invitacion_enum NOT NULL DEFAULT 'pendiente',
    UNIQUE (tocada_id, banda_id)
);

CREATE INDEX idx_lineup_banda ON tocada_lineup(banda_id);

-- ---------------------------------------------------------
-- 8. LANZAMIENTOS (musica nueva de las bandas)
-- ---------------------------------------------------------
CREATE TYPE tipo_lanzamiento_enum AS ENUM ('single', 'ep', 'album', 'video');

CREATE TABLE lanzamientos (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    banda_id        UUID NOT NULL REFERENCES perfiles_banda(usuario_id) ON DELETE CASCADE,
    titulo          VARCHAR(150) NOT NULL,
    tipo            tipo_lanzamiento_enum NOT NULL,
    url_streaming   TEXT,           -- link a Spotify/SoundCloud/YouTube (embed)
    portada_url     TEXT,
    fecha_lanzamiento DATE,
    created_at      TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX idx_lanzamientos_banda ON lanzamientos(banda_id);

-- ---------------------------------------------------------
-- 9. FEED GENERAL (noticias, texto libre, anuncios)
-- ---------------------------------------------------------
CREATE TYPE tipo_post_enum AS ENUM ('noticia', 'anuncio', 'texto_libre');

CREATE TABLE posts (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    autor_id        UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    tipo            tipo_post_enum NOT NULL DEFAULT 'texto_libre',
    contenido       TEXT NOT NULL,
    imagen_url      TEXT,
    tocada_id       UUID REFERENCES tocadas(id),   -- opcional: post ligado a un evento especifico
    created_at      TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX idx_posts_autor ON posts(autor_id);
CREATE INDEX idx_posts_fecha ON posts(created_at DESC);

-- ---------------------------------------------------------
-- 10. RECUERDOS (fotos de fans/bandas ligadas a una tocada)
-- ---------------------------------------------------------
CREATE TABLE recuerdos_fotos (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tocada_id       UUID NOT NULL REFERENCES tocadas(id) ON DELETE CASCADE,
    usuario_id      UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    foto_url        TEXT NOT NULL,
    caption         VARCHAR(300),
    created_at      TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX idx_recuerdos_tocada ON recuerdos_fotos(tocada_id);

-- ---------------------------------------------------------
-- 11. SEGUIDORES (fan/banda sigue a banda u organizador)
-- ---------------------------------------------------------
CREATE TABLE seguidores (
    id              SERIAL PRIMARY KEY,
    seguidor_id     UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    seguido_id      UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    created_at      TIMESTAMPTZ DEFAULT now(),
    UNIQUE (seguidor_id, seguido_id),
    CHECK (seguidor_id != seguido_id)
);

CREATE INDEX idx_seguidores_seguido ON seguidores(seguido_id);

-- ---------------------------------------------------------
-- 12. NOTIFICACIONES
-- ---------------------------------------------------------
CREATE TYPE tipo_notificacion_enum AS ENUM (
    'nueva_tocada', 'nuevo_lanzamiento', 'invitacion_lineup',
    'nuevo_seguidor', 'recordatorio_tocada'
);

CREATE TABLE notificaciones (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id      UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    tipo            tipo_notificacion_enum NOT NULL,
    referencia_id   UUID,             -- id de la tocada/lanzamiento/etc relacionado
    contenido       VARCHAR(300),
    leido           BOOLEAN DEFAULT FALSE,
    created_at      TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX idx_notif_usuario_no_leidas ON notificaciones(usuario_id, leido);

-- =========================================================
-- NOTAS DE DISEÑO
-- =========================================================
-- 1. "creador_id" en tocadas es polimorfico (banda u organizador). Si prefieres
--    integridad referencial estricta, se puede dividir en dos columnas nullable
--    (creador_banda_id, creador_organizador_id) con un CHECK que exija exactamente
--    una no nula. Aqui se dejo simple para prototipo rapido.
--
-- 2. tocada_lineup permite que una banda "acepte o rechace" ser agregada al cartel
--    de un organizador, evitando que alguien las agregue sin permiso.
--
-- 3. ciudades.activa permite lanzar primero en Lima/Arequipa y activar mas ciudades
--    despues sin tocar el esquema.
--
-- 4. Si usas Supabase, aprovecha su Storage para foto_perfil_url, imagen_flyer_url
--    y foto_url (recuerdos), y Supabase Realtime para notificar nuevas tocadas
--    en el feed sin hacer polling.
