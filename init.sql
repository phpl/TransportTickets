
CREATE SEQUENCE transport.adres_adres_pk_seq_1;

CREATE TABLE transport.adres (
                adres_pk INTEGER NOT NULL DEFAULT nextval('transport.adres_adres_pk_seq_1'),
                miasto VARCHAR NOT NULL,
                ulica VARCHAR NOT NULL,
                numer_domu VARCHAR NOT NULL,
                CONSTRAINT adres_pk PRIMARY KEY (adres_pk)
);


ALTER SEQUENCE transport.adres_adres_pk_seq_1 OWNED BY transport.adres.adres_pk;

CREATE SEQUENCE transport.kierowca_kierowca_pk_seq;

CREATE TABLE transport.kierowca (
                kierowca_pk INTEGER NOT NULL DEFAULT nextval('transport.kierowca_kierowca_pk_seq'),
                imie VARCHAR NOT NULL,
                nazwisko VARCHAR NOT NULL,
                numer_telefonu INTEGER NOT NULL,
                termin_waznosci_badan DATE NOT NULL,
                termin_waznosci_prawa_jazdy DATE NOT NULL,
                CONSTRAINT kierowca_pk PRIMARY KEY (kierowca_pk)
);


ALTER SEQUENCE transport.kierowca_kierowca_pk_seq OWNED BY transport.kierowca.kierowca_pk;

CREATE UNIQUE INDEX kierowca_idx
 ON transport.kierowca
 ( numer_telefonu );

CREATE SEQUENCE transport.pojazd_pojazd_pk_seq;

CREATE TABLE transport.pojazd (
                pojazd_pk INTEGER NOT NULL DEFAULT nextval('transport.pojazd_pojazd_pk_seq'),
                model VARCHAR NOT NULL,
                numer_rejestracji VARCHAR NOT NULL,
                ilosc_miejsc INTEGER NOT NULL,
                dopuszczalny_bagaz NUMERIC NOT NULL,
                CONSTRAINT pojazd_pk PRIMARY KEY (pojazd_pk)
);


ALTER SEQUENCE transport.pojazd_pojazd_pk_seq OWNED BY transport.pojazd.pojazd_pk;

CREATE UNIQUE INDEX pojazd_idx
 ON transport.pojazd
 ( numer_rejestracji );

CREATE SEQUENCE transport.uzytkownik_uzytkownik_pk_seq;

CREATE TABLE transport.uzytkownik (
                uzytkownik_pk INTEGER NOT NULL DEFAULT nextval('transport.uzytkownik_uzytkownik_pk_seq'),
                login VARCHAR NOT NULL,
                haslo VARCHAR NOT NULL,
                CONSTRAINT uzytkownik_pk PRIMARY KEY (uzytkownik_pk)
);


ALTER SEQUENCE transport.uzytkownik_uzytkownik_pk_seq OWNED BY transport.uzytkownik.uzytkownik_pk;

CREATE UNIQUE INDEX uzytkownik_idx
 ON transport.uzytkownik
 ( login );

CREATE SEQUENCE transport.dane_osobowe_dane_pk_seq;

CREATE TABLE transport.dane_osobowe (
                dane_pk INTEGER NOT NULL DEFAULT nextval('transport.dane_osobowe_dane_pk_seq'),
                uzytkownik_pk INTEGER NOT NULL,
                imie VARCHAR NOT NULL,
                nazwisko VARCHAR NOT NULL,
                numer_telefonu INTEGER NOT NULL,
                adres_pk INTEGER NOT NULL,
                CONSTRAINT dane_osobowe_pk PRIMARY KEY (dane_pk)
);


ALTER SEQUENCE transport.dane_osobowe_dane_pk_seq OWNED BY transport.dane_osobowe.dane_pk;

CREATE UNIQUE INDEX dane_osobowe_idx
 ON transport.dane_osobowe
 ( numer_telefonu );

CREATE SEQUENCE transport.miasto_miasto_pk_seq_1;

CREATE TABLE transport.miasto (
                miasto_pk INTEGER NOT NULL DEFAULT nextval('transport.miasto_miasto_pk_seq_1'),
                nazwa VARCHAR NOT NULL,
                CONSTRAINT miasto_pk PRIMARY KEY (miasto_pk)
);


ALTER SEQUENCE transport.miasto_miasto_pk_seq_1 OWNED BY transport.miasto.miasto_pk;

CREATE SEQUENCE transport.trasa_trasa_pk_seq;

CREATE TABLE transport.trasa (
                trasa_pk INTEGER NOT NULL DEFAULT nextval('transport.trasa_trasa_pk_seq'),
                odleglosc INTEGER NOT NULL,
                CONSTRAINT trasa_pk PRIMARY KEY (trasa_pk)
);


ALTER SEQUENCE transport.trasa_trasa_pk_seq OWNED BY transport.trasa.trasa_pk;

CREATE TABLE transport.trasa_miasto (
                trasa_pk INTEGER NOT NULL,
                miasto_pk INTEGER NOT NULL,
                CONSTRAINT trasa_miasto_pk PRIMARY KEY (trasa_pk, miasto_pk)
);


CREATE SEQUENCE transport.kurs_kurs_pk_seq_2_1_1;

CREATE TABLE transport.kurs (
                kurs_pk INTEGER NOT NULL DEFAULT nextval('transport.kurs_kurs_pk_seq_2_1_1'),
                godzina_odjazdu TIME NOT NULL,
                godzina_powrotu TIME NOT NULL,
                maks_dostepna_ilosc_miejsc INTEGER NOT NULL,
                trasa_pk INTEGER NOT NULL,
                CONSTRAINT kurs_pk PRIMARY KEY (kurs_pk)
);


ALTER SEQUENCE transport.kurs_kurs_pk_seq_2_1_1 OWNED BY transport.kurs.kurs_pk;

CREATE SEQUENCE transport.bilet_bilet_pk_seq_1;

CREATE TABLE transport.bilet (
                bilet_pk INTEGER NOT NULL DEFAULT nextval('transport.bilet_bilet_pk_seq_1'),
                uzytkownik_pk INTEGER NOT NULL,
                cena NUMERIC NOT NULL,
                kurs_pk INTEGER NOT NULL,
                CONSTRAINT bilet_pk PRIMARY KEY (bilet_pk)
);


ALTER SEQUENCE transport.bilet_bilet_pk_seq_1 OWNED BY transport.bilet.bilet_pk;

CREATE SEQUENCE transport.bagaz_bagaz_pk_seq;

CREATE TABLE transport.bagaz (
                bagaz_pk INTEGER NOT NULL DEFAULT nextval('transport.bagaz_bagaz_pk_seq'),
                bilet_pk INTEGER NOT NULL,
                waga NUMERIC,
                uzytkownik_pk INTEGER NOT NULL,
                CONSTRAINT bagaz_pk PRIMARY KEY (bagaz_pk)
);


ALTER SEQUENCE transport.bagaz_bagaz_pk_seq OWNED BY transport.bagaz.bagaz_pk;

CREATE TABLE transport.kurs_pojazd (
                kurs_pk INTEGER NOT NULL,
                pojazd_pk INTEGER NOT NULL,
                CONSTRAINT kurs_pojazd_pk PRIMARY KEY (kurs_pk, pojazd_pk)
);


CREATE TABLE transport.kurs_kierowca (
                kierowca_pk INTEGER NOT NULL,
                kurs_pk INTEGER NOT NULL,
                CONSTRAINT kurs_kierowca_pk PRIMARY KEY (kierowca_pk, kurs_pk)
);


ALTER TABLE transport.dane_osobowe ADD CONSTRAINT dane_osobowe_adres_fk
FOREIGN KEY (adres_pk)
REFERENCES transport.adres (adres_pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE transport.kurs_kierowca ADD CONSTRAINT kierowca_kurs_kierowca_fk
FOREIGN KEY (kierowca_pk)
REFERENCES transport.kierowca (kierowca_pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE transport.kurs_pojazd ADD CONSTRAINT pojazd_kurs_pojazd_fk
FOREIGN KEY (pojazd_pk)
REFERENCES transport.pojazd (pojazd_pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE transport.dane_osobowe ADD CONSTRAINT uzytkownik_dane_osobowe_fk
FOREIGN KEY (uzytkownik_pk)
REFERENCES transport.uzytkownik (uzytkownik_pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE transport.bilet ADD CONSTRAINT uzytkownik_bilet_fk
FOREIGN KEY (uzytkownik_pk)
REFERENCES transport.uzytkownik (uzytkownik_pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE transport.bagaz ADD CONSTRAINT bagaz_uzytkownik_fk
FOREIGN KEY (uzytkownik_pk)
REFERENCES transport.uzytkownik (uzytkownik_pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE transport.trasa_miasto ADD CONSTRAINT miasto_trasa_miasto_fk
FOREIGN KEY (miasto_pk)
REFERENCES transport.miasto (miasto_pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE transport.kurs ADD CONSTRAINT trasa_kurs_fk
FOREIGN KEY (trasa_pk)
REFERENCES transport.trasa (trasa_pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE transport.trasa_miasto ADD CONSTRAINT trasa_trasa_miasto_fk
FOREIGN KEY (trasa_pk)
REFERENCES transport.trasa (trasa_pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE transport.kurs_kierowca ADD CONSTRAINT kurs_kurs_kierowca_fk
FOREIGN KEY (kurs_pk)
REFERENCES transport.kurs (kurs_pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE transport.kurs_pojazd ADD CONSTRAINT kurs_kurs_pojazd_fk
FOREIGN KEY (kurs_pk)
REFERENCES transport.kurs (kurs_pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE transport.bilet ADD CONSTRAINT kurs_bilet_fk
FOREIGN KEY (kurs_pk)
REFERENCES transport.kurs (kurs_pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE transport.bagaz ADD CONSTRAINT bilet_bagaz_fk
FOREIGN KEY (bilet_pk)
REFERENCES transport.bilet (bilet_pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

CREATE OR REPLACE VIEW transport."trasy_view" AS
  SELECT
    kurs.kurs_pk,
    trasa.trasa_pk,
    miasto.miasto_pk,
    kurs.godzina_odjazdu,
    kurs.maks_dostepna_ilosc_miejsc AS wolne_miejsca,
    trasa.odleglosc                 AS km,
    miasto.nazwa                    AS miasto,
    (trasa.odleglosc * 2)           AS cena_biletu
  FROM transport.kurs kurs
    JOIN transport.trasa trasa ON kurs.trasa_pk = trasa.trasa_pk
    JOIN transport.trasa_miasto ON trasa.trasa_pk = trasa_miasto.trasa_pk
    JOIN transport.miasto ON trasa_miasto.miasto_pk = miasto.miasto_pk
  ORDER BY godzina_odjazdu;

CREATE OR REPLACE VIEW transport."uzytkownicy_view" AS
    SELECT
        uzytkownik.uzytkownik_pk AS "id",
        dane.imie,
        dane.nazwisko,
        dane.numer_telefonu      AS "numer telefonu",
        adres.miasto,
        adres.ulica,
        adres.numer_domu         AS "numer domu"
    FROM transport.uzytkownik uzytkownik
    JOIN transport.dane_osobowe dane ON uzytkownik.uzytkownik_pk = dane.uzytkownik_pk
    JOIN transport.adres ON dane.adres_pk = adres.adres_pk
    ORDER BY id;

CREATE OR REPLACE VIEW transport."pasazerowie_view" AS
  SELECT
    dane.imie,
    dane.nazwisko,
    bilet.cena
  FROM transport.dane_osobowe dane
    JOIN transport.uzytkownik ON dane.uzytkownik_pk = uzytkownik.uzytkownik_pk
    JOIN transport.bilet ON uzytkownik.uzytkownik_pk = bilet.uzytkownik_pk
    JOIN transport.kurs ON bilet.kurs_pk = kurs.kurs_pk
  ORDER BY dane.nazwisko;

 CREATE OR REPLACE VIEW transport."kierowca_pojazd_view" AS
  SELECT
    kierowca.imie,
    kierowca.nazwisko,
    kierowca.numer_telefonu AS "numer telefonu kierowcy",
    numer_rejestracji       AS "rejestracja pojazdu"
  FROM transport.kierowca kierowca
    JOIN transport.kurs_kierowca ON kierowca.kierowca_pk = kurs_kierowca.kierowca_pk
    JOIN transport.kurs ON kurs_kierowca.kurs_pk = kurs.kurs_pk
    JOIN transport.kurs_pojazd ON kurs.kurs_pk = kurs_pojazd.kurs_pk
    JOIN transport.pojazd ON kurs_pojazd.pojazd_pk = pojazd.pojazd_pk;