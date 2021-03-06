CREATE SCHEMA transport;

CREATE SEQUENCE transport.adres_adres_pk_seq_1;

CREATE TABLE transport.adres (
  adres_pk   INTEGER NOT NULL DEFAULT nextval('transport.adres_adres_pk_seq_1'),
  miasto     VARCHAR NOT NULL,
  ulica      VARCHAR NOT NULL,
  numer_domu VARCHAR NOT NULL,
  CONSTRAINT adres_pk PRIMARY KEY (adres_pk)
);


ALTER SEQUENCE transport.adres_adres_pk_seq_1 OWNED BY transport.adres.adres_pk;

CREATE SEQUENCE transport.kierowca_kierowca_pk_seq;

CREATE TABLE transport.kierowca (
  kierowca_pk    INTEGER NOT NULL DEFAULT nextval('transport.kierowca_kierowca_pk_seq'),
  imie           VARCHAR NOT NULL,
  nazwisko       VARCHAR NOT NULL,
  numer_telefonu INTEGER NOT NULL,
  CONSTRAINT kierowca_pk PRIMARY KEY (kierowca_pk)
);


ALTER SEQUENCE transport.kierowca_kierowca_pk_seq OWNED BY transport.kierowca.kierowca_pk;

CREATE UNIQUE INDEX kierowca_idx
  ON transport.kierowca
  ( numer_telefonu );

CREATE SEQUENCE transport.pojazd_pojazd_pk_seq;

CREATE TABLE transport.pojazd (
  pojazd_pk         INTEGER NOT NULL DEFAULT nextval('transport.pojazd_pojazd_pk_seq'),
  model             VARCHAR NOT NULL,
  numer_rejestracji VARCHAR NOT NULL,
  ilosc_miejsc      INTEGER NOT NULL,
  CONSTRAINT pojazd_pk PRIMARY KEY (pojazd_pk)
);


ALTER SEQUENCE transport.pojazd_pojazd_pk_seq OWNED BY transport.pojazd.pojazd_pk;

CREATE UNIQUE INDEX pojazd_idx
  ON transport.pojazd
  ( numer_rejestracji );

CREATE SEQUENCE transport.uzytkownik_uzytkownik_pk_seq;

CREATE TABLE transport.uzytkownik (
  uzytkownik_pk INTEGER NOT NULL DEFAULT nextval('transport.uzytkownik_uzytkownik_pk_seq'),
  login         VARCHAR NOT NULL,
  haslo         VARCHAR NOT NULL,
  CONSTRAINT uzytkownik_pk PRIMARY KEY (uzytkownik_pk)
);


ALTER SEQUENCE transport.uzytkownik_uzytkownik_pk_seq OWNED BY transport.uzytkownik.uzytkownik_pk;

CREATE UNIQUE INDEX uzytkownik_idx
  ON transport.uzytkownik
  ( login );

CREATE SEQUENCE transport.dane_osobowe_dane_pk_seq;

CREATE TABLE transport.dane_osobowe (
  dane_pk        INTEGER NOT NULL DEFAULT nextval('transport.dane_osobowe_dane_pk_seq'),
  uzytkownik_pk  INTEGER NOT NULL,
  imie           VARCHAR NOT NULL,
  nazwisko       VARCHAR NOT NULL,
  numer_telefonu INTEGER NOT NULL,
  adres_pk       INTEGER NOT NULL,
  CONSTRAINT dane_osobowe_pk PRIMARY KEY (dane_pk)
);


ALTER SEQUENCE transport.dane_osobowe_dane_pk_seq OWNED BY transport.dane_osobowe.dane_pk;

CREATE UNIQUE INDEX dane_osobowe_idx
  ON transport.dane_osobowe
  ( numer_telefonu );

CREATE SEQUENCE transport.trasa_trasa_pk_seq;

CREATE TABLE transport.trasa (
  trasa_pk          INTEGER NOT NULL DEFAULT nextval('transport.trasa_trasa_pk_seq'),
  odleglosc         INTEGER NOT NULL,
  miasto_poczatkowe VARCHAR NOT NULL,
  miasto_koncowe    VARCHAR NOT NULL,
  CONSTRAINT trasa_pk PRIMARY KEY (trasa_pk)
);


ALTER SEQUENCE transport.trasa_trasa_pk_seq OWNED BY transport.trasa.trasa_pk;

CREATE SEQUENCE transport.kurs_kurs_pk_seq_2_1_1;

CREATE TABLE transport.kurs (
  kurs_pk                    INTEGER NOT NULL DEFAULT nextval('transport.kurs_kurs_pk_seq_2_1_1'),
  godzina_odjazdu            TIME    NOT NULL,
  godzina_powrotu            TIME    NOT NULL,
  maks_dostepna_ilosc_miejsc INTEGER NOT NULL,
  trasa_pk                   INTEGER NOT NULL,
  CONSTRAINT kurs_pk PRIMARY KEY (kurs_pk)
);


ALTER SEQUENCE transport.kurs_kurs_pk_seq_2_1_1 OWNED BY transport.kurs.kurs_pk;

CREATE SEQUENCE transport.bilet_bilet_pk_seq_1;

CREATE TABLE transport.bilet (
  bilet_pk      INTEGER NOT NULL DEFAULT nextval('transport.bilet_bilet_pk_seq_1'),
  uzytkownik_pk INTEGER NOT NULL,
  cena          NUMERIC NOT NULL,
  kurs_pk       INTEGER NOT NULL,
  CONSTRAINT bilet_pk PRIMARY KEY (bilet_pk)
);


ALTER SEQUENCE transport.bilet_bilet_pk_seq_1 OWNED BY transport.bilet.bilet_pk;

CREATE SEQUENCE transport.bagaz_bagaz_pk_seq;

CREATE TABLE transport.bagaz (
  bagaz_pk      INTEGER NOT NULL DEFAULT nextval('transport.bagaz_bagaz_pk_seq'),
  bilet_pk      INTEGER NOT NULL,
  waga          NUMERIC,
  uzytkownik_pk INTEGER NOT NULL,
  CONSTRAINT bagaz_pk PRIMARY KEY (bagaz_pk)
);


ALTER SEQUENCE transport.bagaz_bagaz_pk_seq OWNED BY transport.bagaz.bagaz_pk;

CREATE TABLE transport.kurs_pojazd (
  kurs_pk   INTEGER NOT NULL,
  pojazd_pk INTEGER NOT NULL,
  CONSTRAINT kurs_pojazd_pk PRIMARY KEY (kurs_pk, pojazd_pk)
);


CREATE TABLE transport.kurs_kierowca (
  kierowca_pk INTEGER NOT NULL,
  kurs_pk     INTEGER NOT NULL,
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

ALTER TABLE transport.kurs ADD CONSTRAINT trasa_kurs_fk
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

INSERT INTO transport.adres (miasto, ulica, numer_domu) VALUES ('Krakow', 'Reymonta', '10');
INSERT INTO transport.uzytkownik (login, haslo) VALUES ('admin', 'admin');
INSERT INTO transport.dane_osobowe (uzytkownik_pk, imie, nazwisko, numer_telefonu, adres_pk) VALUES
  ((SELECT uzytkownik.uzytkownik_pk
    FROM transport.uzytkownik
    WHERE login = 'admin' AND haslo = 'admin'),
   'admin',
   'admin',
   1,
   (SELECT adres.adres_pk
    FROM transport.adres
    WHERE miasto = 'Krakow' AND ulica = 'Reymonta' AND numer_domu = '10'));

CREATE OR REPLACE VIEW transport."trasy_view" AS
  SELECT
    kurs.kurs_pk,
    trasa.trasa_pk,
    kurs.godzina_odjazdu,
    kurs.maks_dostepna_ilosc_miejsc AS wolne_miejsca,
    trasa.odleglosc                 AS km,
    trasa.miasto_poczatkowe,
    trasa.miasto_koncowe,
    (trasa.odleglosc * 2)           AS cena_biletu
  FROM transport.kurs kurs
    JOIN transport.trasa trasa ON kurs.trasa_pk = trasa.trasa_pk
  ORDER BY miasto_poczatkowe, godzina_odjazdu;

CREATE VIEW transport."uzytkownicy_view" AS
  SELECT
    uzytkownik.uzytkownik_pk,
    dane.dane_pk,
    adres.adres_pk,
    uzytkownik.login,
    dane.imie,
    dane.nazwisko,
    dane.numer_telefonu,
    adres.miasto,
    adres.ulica,
    adres.numer_domu
  FROM ((transport.uzytkownik uzytkownik
    JOIN transport.dane_osobowe dane ON ((uzytkownik.uzytkownik_pk = dane.uzytkownik_pk)))
    JOIN transport.adres ON ((dane.adres_pk = adres.adres_pk)))
  ORDER BY uzytkownik.uzytkownik_pk;

CREATE OR REPLACE VIEW transport."pasazerowie_view" AS
  SELECT
    uzytkownik.uzytkownik_pk,
    bilet.bilet_pk,
    bagaz.bagaz_pk,
    kurs.kurs_pk,
    dane_osobowe.imie,
    dane_osobowe.nazwisko,
    dane_osobowe.numer_telefonu,
    bagaz.waga
  FROM transport.uzytkownik
    JOIN transport.dane_osobowe ON uzytkownik.uzytkownik_pk = dane_osobowe.uzytkownik_pk
    JOIN transport.bilet ON uzytkownik.uzytkownik_pk = bilet.uzytkownik_pk
    LEFT JOIN transport.bagaz ON uzytkownik.uzytkownik_pk = bagaz.uzytkownik_pk
    JOIN transport.kurs ON bilet.kurs_pk = kurs.kurs_pk;

CREATE OR REPLACE FUNCTION kup_bilet()
  RETURNS TRIGGER AS $$
DECLARE
  w INTEGER;
BEGIN
  SELECT maks_dostepna_ilosc_miejsc
  INTO w
  FROM transport.kurs
  WHERE kurs_pk = NEW.kurs_pk;
  IF (w = 0)
  THEN RAISE 'Nie mozna kupic biletu! Brak miejsc!';
  ELSE
    UPDATE transport.kurs
    SET maks_dostepna_ilosc_miejsc = maks_dostepna_ilosc_miejsc - 1
    WHERE kurs_pk = NEW.kurs_pk;
    RETURN NEW;
  END IF;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER kup_bilet_trigger
  BEFORE INSERT OR UPDATE
  ON transport.bilet
  FOR EACH ROW EXECUTE PROCEDURE kup_bilet();

CREATE OR REPLACE FUNCTION anuluj_bilet()
  RETURNS TRIGGER AS $$
BEGIN
  UPDATE transport.kurs
  SET maks_dostepna_ilosc_miejsc = maks_dostepna_ilosc_miejsc + 1
  WHERE kurs_pk = OLD.kurs_pk;
  RETURN NULL;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER anuluj_bilet_trigger
  AFTER DELETE
  ON transport.bilet
  FOR EACH ROW EXECUTE PROCEDURE anuluj_bilet();

CREATE OR REPLACE FUNCTION dodaj_kierowca_bus()
  RETURNS TRIGGER AS $$
BEGIN
  UPDATE transport.kurs
  SET maks_dostepna_ilosc_miejsc = maks_dostepna_ilosc_miejsc + (SELECT ilosc_miejsc
                                                                 FROM transport.pojazd
                                                                 WHERE pojazd.pojazd_pk = NEW.pojazd_pk)
  WHERE kurs_pk = NEW.kurs_pk;
  RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER dodaj_kierowca_bus_trigger
  AFTER INSERT
  ON transport.kurs_pojazd
  FOR EACH ROW EXECUTE PROCEDURE dodaj_kierowca_bus();

CREATE OR REPLACE FUNCTION usun_kurs_gdy_pasazer()
  RETURNS TRIGGER AS $$
DECLARE
  w INTEGER;
BEGIN
  SELECT COUNT(*)
  INTO w
  FROM transport.bilet
  WHERE kurs_pk = OLD.kurs_pk;
  IF (w > 0)
  THEN RAISE 'Nie mozna usunac kursu! Istnieje pasazer zapisany na ten kurs!';
  END IF;
  RETURN OLD;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER usun_kurs_gdy_pasazer_trigger
  BEFORE DELETE
  ON transport.kurs
  FOR EACH ROW EXECUTE PROCEDURE usun_kurs_gdy_pasazer();

CREATE OR REPLACE FUNCTION usun_kurs_trasa()
  RETURNS TRIGGER AS $$
BEGIN
  DELETE FROM transport.trasa
  WHERE trasa.trasa_pk = OLD.trasa_pk;
  RETURN OLD;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER usun_kurs_trasa_trigger
  AFTER DELETE
  ON transport.kurs
  FOR EACH ROW EXECUTE PROCEDURE usun_kurs_trasa();

CREATE OR REPLACE FUNCTION usun_admina()
  RETURNS TRIGGER AS $$
BEGIN
  IF OLD.login = 'admin'
  THEN
    RAISE 'Blad! Nie mozna usunac administratora';
  END IF;
  RETURN OLD;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER usun_adminaa_trigger
  BEFORE DELETE
  ON transport.uzytkownik
  FOR EACH ROW EXECUTE PROCEDURE usun_admina();

CREATE OR REPLACE FUNCTION usun_kierowce()
  RETURNS TRIGGER AS $$
DECLARE
  w INTEGER;
BEGIN
  SELECT COUNT(*)
  INTO w
  FROM transport.kurs_kierowca
  WHERE kierowca_pk = OLD.kierowca_pk;
  IF (w > 0)
  THEN RAISE 'Nie mozna usunac kierowcy! Kierowca jest zapisany na kurs!';
  END IF;
  RETURN OLD;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER usun_kierowce_trigger
  BEFORE DELETE
  ON transport.kierowca
  FOR EACH ROW EXECUTE PROCEDURE usun_kierowce();

CREATE OR REPLACE FUNCTION usun_pojazd()
  RETURNS TRIGGER AS $$
DECLARE
  w INTEGER;
BEGIN
  SELECT COUNT(*)
  INTO w
  FROM transport.kurs_pojazd
  WHERE pojazd_pk = OLD.pojazd_pk;
  IF (w > 0)
  THEN RAISE 'Nie mozna usunac pojazdu! Pojazd jest zapisany na kurs!';
  END IF;
  RETURN OLD;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER usun_pojazd_trigger
  BEFORE DELETE
  ON transport.pojazd
  FOR EACH ROW EXECUTE PROCEDURE usun_pojazd();

CREATE OR REPLACE FUNCTION usun_uzytkownika_gdy_kurs()
  RETURNS TRIGGER AS $$
DECLARE
  w INTEGER;
BEGIN
  SELECT COUNT(*)
  INTO w
  FROM transport.bilet
  WHERE bilet.uzytkownik_pk = OLD.uzytkownik_pk;
  IF (w > 0)
  THEN RAISE 'Nie mozna usunac uzytkownika! Uzytkownik jest zapisany na kurs!';
  END IF;
  RETURN OLD;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER usun_uzytkownika_gdy_kurs_trigger
  BEFORE DELETE
  ON transport.uzytkownik
  FOR EACH ROW EXECUTE PROCEDURE usun_uzytkownika_gdy_kurs();
