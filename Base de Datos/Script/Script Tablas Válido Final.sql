CREATE DATABASE detenidos;
USE detenidos;

CREATE TABLE Provincia (
    codigo_provincia VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(50)
);

CREATE TABLE Zona (
    nombre_zona VARCHAR(10),
    nombre_subzona VARCHAR(30),
    PRIMARY KEY (nombre_zona, nombre_subzona),
    INDEX idx_nombre_zona (nombre_zona),
    INDEX idx_nombre_subzona (nombre_subzona)
);

CREATE TABLE Canton (
    codigo_canton VARCHAR(20) PRIMARY KEY,
    nombre_subzona VARCHAR(30),
    codigo_provincia VARCHAR(20), -- le faltaba este atributo
    nombre_canton VARCHAR(50),
    FOREIGN KEY (nombre_subzona) REFERENCES Zona(nombre_subzona),
    FOREIGN KEY (codigo_provincia) REFERENCES Provincia(codigo_provincia)
);

CREATE TABLE Parroquia (
    codigo_parroquia VARCHAR(20) PRIMARY KEY,
    codigo_canton VARCHAR(20),
    nombre VARCHAR(50),
    FOREIGN KEY (codigo_canton) REFERENCES Canton(codigo_canton)
);

CREATE TABLE Distrito (
    codigo_distrito VARCHAR(20) PRIMARY KEY,
    nombre_subzona VARCHAR(30),
    nombre VARCHAR(50),
    FOREIGN KEY (nombre_subzona) REFERENCES Zona(nombre_subzona)
);

CREATE TABLE Circuito (
    codigo_circuito VARCHAR(20) PRIMARY KEY,
    codigo_distrito VARCHAR(20),
    nombre VARCHAR(50),
    FOREIGN KEY (codigo_distrito) REFERENCES Distrito(codigo_distrito)
);

CREATE TABLE Subcircuito (
    codigo_subcircuito VARCHAR(20) PRIMARY KEY,
    codigo_circuito VARCHAR(20),
    nombre VARCHAR(50),
    FOREIGN KEY (codigo_circuito) REFERENCES Circuito(codigo_circuito)
);

CREATE TABLE Detencion (
    anio INT,
    id_detencion INT,
    zona VARCHAR(10),
    subzona VARCHAR(30),
    tipo_arma VARCHAR(50),
    nombre_arma VARCHAR(100),
    tipo VARCHAR(20),
    estado_civil VARCHAR(20),
    estatus_migratorio VARCHAR(20),
    edad INT,
    sexo VARCHAR(20),
    genero VARCHAR(20),
    nacionalidad VARCHAR(10),
    autoidentificacion_etnica VARCHAR(50),
    numero_detenciones INT,
    nivel_instruccion VARCHAR(50),
    condicion VARCHAR(100),
    movilizacion VARCHAR(100),
    fecha_detencion_aprehension DATE,
    hora_detencion VARCHAR(20),
    lugar VARCHAR(100),
    tipo_lugar VARCHAR(100),
    presunta_flagrancia VARCHAR(100),
    PRIMARY KEY (anio, id_detencion),
    FOREIGN KEY (zona, subzona) REFERENCES Zona(nombre_zona, nombre_subzona),
    INDEX idx_anio (anio),
    INDEX idx_id_detencion (id_detencion)
);

CREATE TABLE VictimasHomicidio (
    anioVictimas INT PRIMARY KEY,
    feminicidio INT,
    sicaritio INT,
    homicidio INT,
    asesinato INT,
    FOREIGN KEY (anioVictimas) REFERENCES Detencion(anio)
);

CREATE TABLE Tasas_Porcentaje (
    anioTasas INT PRIMARY KEY,
    tasa_PoblacionE DOUBLE,
    tasa_Inmigrantes DOUBLE,
    tasa_Desempleo DOUBLE,
    tasa_Crimen DOUBLE,
    tasa_Desnutricion DOUBLE,
    FOREIGN KEY (anioTasas) REFERENCES Detencion(anio)
);

CREATE TABLE Crimen (
    codigo_iccs VARCHAR(20),
    presunta_infraccion VARCHAR(100),
    presunta_subinfraccion VARCHAR(100),
    presunta_modalidad VARCHAR(100),
    PRIMARY KEY (codigo_iccs, presunta_subinfraccion)
);

CREATE TABLE DetencionXCrimen (
    anio INT,
    id_detencion INT,
    codigo_iccs VARCHAR(20),
    presunta_subinfraccion VARCHAR(100),
    FOREIGN KEY (anio, id_detencion) REFERENCES Detencion(anio, id_detencion),
    FOREIGN KEY (codigo_iccs, presunta_subinfraccion) REFERENCES Crimen(codigo_iccs, presunta_subinfraccion)
);

    -- en esta tabla no deben haber claves primarias por que solo e sun "punte"
    -- PRIMARY KEY (anio, id_detencion, codigo_iccs),
    -- FOREIGN KEY (anio) REFERENCES Detencion(anio),
    -- FOREIGN KEY (id_detencion) REFERENCES Detencion(id_detencion),
    -- en esto no estoy del todo segura si funciona así, 
    -- pero le dejé la FK como clave compuesta, en caso de que no, 
    -- estan comentadas las anteriores lineas.