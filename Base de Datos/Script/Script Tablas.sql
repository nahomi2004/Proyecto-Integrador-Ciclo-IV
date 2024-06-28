CREATE TABLE Subcircuito (
    codigo_Subcircuito VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100)
);

CREATE TABLE Circuito (
    codigo_Circuito VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100)
);

CREATE TABLE Zona (
    codigo_Zona VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100),
    nombre_subzona VARCHAR(100)
);

CREATE TABLE Parroquia (
    codigo_Parroquia VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100)
);

CREATE TABLE Provincia (
    codigo_Provincia VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100)
);

CREATE TABLE Canton (
    codigo_Canton VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100)
);

CREATE TABLE Distrito (
    codigo_Distrito VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100)
);

CREATE TABLE Ubicacion (
    codigo_Ubi VARCHAR(20) PRIMARY KEY,
    codigo_circuito VARCHAR(20),
    codigo_subcircuito VARCHAR(20),
    codigo_zona VARCHAR(20),
    codigo_parroquia VARCHAR(20),
    codigo_provincia VARCHAR(20),
    codigo_canton VARCHAR(20),
    codigo_distrito VARCHAR(20),
    FOREIGN KEY (codigo_circuito) REFERENCES Circuito(codigo_Circuito),
    FOREIGN KEY (codigo_subcircuito) REFERENCES Subcircuito(codigo_Subcircuito),
    FOREIGN KEY (codigo_zona) REFERENCES Zona(codigo_Zona),
    FOREIGN KEY (codigo_parroquia) REFERENCES Parroquia(codigo_Parroquia),
    FOREIGN KEY (codigo_provincia) REFERENCES Provincia(codigo_Provincia),
    FOREIGN KEY (codigo_canton) REFERENCES Canton(codigo_Canton),
    FOREIGN KEY (codigo_distrito) REFERENCES Distrito(codigo_Distrito)
);

CREATE TABLE VictimasHomicidio (
    anio INT,
    tipo VARCHAR(20),
    PRIMARY KEY (anio, tipo)
);

CREATE TABLE Tasas_Porcentaje (
    anio INT PRIMARY KEY,
    tasa_PoblacionE DECIMAL(5,2),
    tasa_Inmigrantes DECIMAL(5,2),
    tasa_Desempleo DECIMAL(5,2),
    tasa_Crimen DECIMAL(5,2)
);

CREATE TABLE Crimen (
    anio INT,
    codigo_Ubi VARCHAR(20),
    codigo_asoci VARCHAR(20),
    lugar VARCHAR(100),
    presunta_subinfraccion VARCHAR(100),
    presunta_infraccion VARCHAR(100),
    hora_detencion TIME,
    presunta_flagrancia BOOLEAN,
    condicion VARCHAR(50),
    movilizacion VARCHAR(50),
    PRIMARY KEY (anio, codigo_Ubi),
    FOREIGN KEY (codigo_Ubi) REFERENCES Ubicacion(codigo_Ubi)
);

CREATE TABLE Detenido (
    identificador VARCHAR(20) PRIMARY KEY,
    siglas VARCHAR(10),
    edad INT,
    sexo CHAR(1),
    autoidentificacion_etnica VARCHAR(50),
    estatus_migratorio VARCHAR(50),
    numero_detenciones INT,
    estado_civil VARCHAR(20),
    nivel_instruccion VARCHAR(50),
    genero VARCHAR(20)
);

CREATE TABLE DetencionXCrimen (
    anio_Crimen INT,
    identificador VARCHAR(20),
    PRIMARY KEY (anio_Crimen, identificador),
    FOREIGN KEY (anio_Crimen) REFERENCES Crimen(anio),
    FOREIGN KEY (identificador) REFERENCES Detenido(identificador)
);

CREATE TABLE Nacionalidad (
    siglas VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(100)
);

CREATE TABLE Arma (
    codigo_Arma VARCHAR(20) PRIMARY KEY,
    anio INT,
    tipo_arma VARCHAR(50),
    nombre_arma VARCHAR(100)
);

ALTER TABLE Detenido
ADD FOREIGN KEY (siglas) REFERENCES Nacionalidad(siglas);