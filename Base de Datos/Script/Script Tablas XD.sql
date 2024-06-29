-- Tabla Arma
CREATE TABLE Arma (
    codigo_Arma VARCHAR(20) PRIMARY KEY,
    tipo_arma VARCHAR(50),
    nombre_arma VARCHAR(100)
);

-- Tabla VictimasHomicidio
CREATE TABLE VictimasHomicidio (
    anioVictimas INT PRIMARY KEY,
    anio INT,
    femicidio INT,
    sicariato INT,
    homicidio INT,
    asesinato INT
);

-- Tabla Tasas_Porcentaje
CREATE TABLE Tasas_Porcentaje (
    anioTasas INT PRIMARY KEY,
    anio INT,
    tasa_PoblacionE DECIMAL(5,2),
    tasa_Inmigrantes DECIMAL(5,2),
    tasa_Desempleo DECIMAL(5,2),
    tasa_Crimen DECIMAL(5,2),
    tasa_Desnutricion DECIMAL(5,2)
);

-- Tabla Nacionalidad
-- CREATE TABLE Nacionalidad (
--    siglas VARCHAR(10) PRIMARY KEY,
--    nombre VARCHAR(100)
-- );

-- Tabla Detenido
CREATE TABLE Detenido (
    identificador VARCHAR(20) PRIMARY KEY,
    nacionalidad VARCHAR(10),
    edad INT,
    sexo CHAR(1),
    autoidentificacion_etnica VARCHAR(50),
    estatus_migratorio VARCHAR(50),
    numero_detenciones INT,
    estado_civil VARCHAR(20),
    nivel_instruccion VARCHAR(50),
    genero VARCHAR(20)
    -- FOREIGN KEY (siglas) REFERENCES Nacionalidad(siglas)
);

-- Tabla Zona
CREATE TABLE Zona (
    nombre_Zona VARCHAR(100),
    nombre_Subzona VARCHAR(100),
    PRIMARY KEY (nombre_Zona, nombre_Subzona),
    UNIQUE KEY (nombre_Subzona)
);

-- Tabla Provincia
CREATE TABLE Provincia (
    codigo_Provincia VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100)
);

-- Tabla Canton
CREATE TABLE Canton (
    codigo_canton VARCHAR(20) PRIMARY KEY,
    nombre_Subzona VARCHAR(100),
    nombre VARCHAR(100),
    codigo_provincia VARCHAR(20),
    FOREIGN KEY (nombre_Subzona) REFERENCES Zona(nombre_Subzona),
    FOREIGN KEY (codigo_provincia) REFERENCES Provincia(codigo_Provincia)
);

-- Tabla Parroquia
CREATE TABLE Parroquia (
    codigo_parroquia VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100),
    codigo_canton VARCHAR(20),
    FOREIGN KEY (codigo_canton) REFERENCES Canton(codigo_canton)
);

CREATE TABLE Distrito (
    codigo_Distrito VARCHAR(20) PRIMARY KEY,
    nombre_Zona VARCHAR(100),
    nombre VARCHAR(100),
    FOREIGN KEY (nombre_Zona) REFERENCES Zona(nombre_Zona)
);

-- Tabla Circuito
CREATE TABLE Circuito (
    codigo_Circuito VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100),
    codigo_Distrito VARCHAR(20),
    FOREIGN KEY (codigo_Distrito) REFERENCES Distrito(codigo_Distrito)
);

-- Tabla Subcircuito
CREATE TABLE Subcircuito (
    codigo_Subcircuito VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100),
    codigo_Circuito VARCHAR(20),
    FOREIGN KEY (codigo_Circuito) REFERENCES Circuito(codigo_Circuito)
);

-- Tabla Crimen
CREATE TABLE Crimen (
    anio INT,
    id INT AUTO_INCREMENT,
    codigo_Arma VARCHAR(20),
    codigo_ascci VARCHAR(20),
    lugar VARCHAR(100),
    presunta_subinfraccion VARCHAR(100),
    presunta_infraccion VARCHAR(100),
    hora_detencion TIME,
    presunta_flagrancia BOOLEAN,
    condicion VARCHAR(50),
    movilizacion VARCHAR(50),
    zona VARCHAR(100),
    subzona VARCHAR(100),
    PRIMARY KEY (anio, id),
    UNIQUE KEY (id),
    FOREIGN KEY (codigo_Arma) REFERENCES Arma(codigo_Arma),
    FOREIGN KEY (subzona) REFERENCES Zona(nombre_Subzona)
);
-- Tabla DetencionXCrimen
CREATE TABLE DetencionXCrimen (
    anio INT,
    identificador VARCHAR(20),
    PRIMARY KEY (anio, identificador),
    FOREIGN KEY (anio) REFERENCES Crimen(anio),
    FOREIGN KEY (identificador) REFERENCES Detenido(identificador)
);