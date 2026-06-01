CREATE DATABASE IF NOT EXISTS SUNSET_BNB;
USE SUNSET_BNB;

CREATE TABLE admin (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    gender VARCHAR(10),
    nome VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE citta (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    regione VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE camera (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descrizione TEXT,
    extra VARCHAR(255),
    prezzo DECIMAL(10,2) NOT NULL,
    immagini TEXT,
    id_citta INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_citta) REFERENCES citta(id)
);

CREATE TABLE cliente (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ordine (
    id INT NOT NULL AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    data_ordine DATETIME NOT NULL,
    totale DECIMAL(10,2),
    PRIMARY KEY (id),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);

CREATE TABLE prenotazioni (
    id INT NOT NULL AUTO_INCREMENT,
    id_utente INT,
    nome_utente VARCHAR(100),
    cognome_utente VARCHAR(100),
    nome_bnb VARCHAR(100),
    citta VARCHAR(50),
    checkin DATE,
    checkout DATE,
    adulti INT,
    bambini INT,
    camere INT,
    totale DECIMAL(10,2),
    immagine_bnb VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE prodotto (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descrizione TEXT,
    prezzo DECIMAL(10,2) NOT NULL,
    disponibilita INT NOT NULL,
    eliminato TINYINT(1) DEFAULT 0,
    immagine VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE recensione (
    id INT NOT NULL AUTO_INCREMENT,
    id_camera INT NOT NULL,
    nome_utente VARCHAR(100) NOT NULL,
    email_utente VARCHAR(150) NOT NULL,
    stelle INT NOT NULL,
    commento TEXT,
    data_rec DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (id_camera) REFERENCES camera(id)
);

CREATE TABLE utente_google (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    immagine_profilo TEXT,
    tipo_autenticazione ENUM('GOOGLE','MANUALE') NOT NULL DEFAULT 'MANUALE',
    PRIMARY KEY (id)
);

CREATE TABLE utenti (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50),
    cognome VARCHAR(50),
    data_nascita DATE,
    tipo_documento VARCHAR(20),
    numero_documento VARCHAR(20),
    residenza VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    nome_file VARCHAR(255),
    PRIMARY KEY (id)
);
