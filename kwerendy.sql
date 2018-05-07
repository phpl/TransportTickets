INSERT INTO transport.adres (miasto, ulica, numer_domu) VALUES (?, ?, ?);
INSERT INTO transport.kurs (godzina_odjazdu, godzina_powrotu, maks_dostepna_ilosc_miejsc, trasa_pk) VALUES (?, ?, ?, ?);
INSERT INTO transport.kurs_kierowca (kierowca_pk, kurs_pk) VALUES (?, ?);
INSERT INTO transport.kurs_pojazd (kurs_pk, pojazd_pk) VALUES (?,?);
INSERT INTO transport.kierowca (imie, nazwisko, numer_telefonu) VALUES (?, ?, ?);
INSERT INTO transport.bagaz (bilet_pk, waga, uzytkownik_pk) VALUES (?, ?, ?);
INSERT INTO transport.dane_osobowe (uzytkownik_pk, imie, nazwisko, numer_telefonu, adres_pk) VALUES (?, ?, ?, ?, ?);
INSERT INTO transport.trasa (odleglosc, miasto_poczatkowe, miasto_koncowe) VALUES (?,?,?);
INSERT INTO transport.bilet (uzytkownik_pk, cena, kurs_pk) VALUES (?, ?, ?);
INSERT INTO transport.uzytkownik (login, haslo) VALUES (?, ?);
INSERT INTO transport.pojazd (model, numer_rejestracji, ilosc_miejsc) VALUES (?, ?, ?);

SELECT adres.adres_pk FROM transport.adres WHERE miasto = ? AND ulica = ? AND numer_domu = ?;
SELECT * FROM transport.trasy_view;
SELECT kurs.kurs_pk FROM transport.kurs WHERE kurs.godzina_odjazdu = ? AND kurs.godzina_powrotu = ? AND maks_dostepna_ilosc_miejsc = ? AND trasa_pk = ?;
SELECT * FROM transport.kurs WHERE kurs_pk = ?;
SELECT kierowca.kierowca_pk FROM transport.kierowca WHERE numer_telefonu = ?;
SELECT * FROM transport.kierowca LEFT JOIN transport.kurs_kierowca ON kierowca.kierowca_pk = kurs_kierowca.kierowca_pk;
SELECT trasa.trasa_pk FROM transport.trasa WHERE trasa.odleglosc = ? AND trasa.miasto_poczatkowe = ? AND trasa.miasto_koncowe = ?;
SELECT bilet.bilet_pk FROM transport.bile WHERE uzytkownik_pk = ? AND kurs_pk = ?;
SELECT  * FROM transport.pasazerowie_view WHERE kurs_pk = ?;
SELECT uzytkownik.uzytkownik_pk FROM transport.uzytkownik WHERE login = ?;
SELECT * FROM transport.uzytkownicy_view;
SELECT uzytkownik.uzytkownik_pk FROM transport.uzytkownik WHERE login = ? AND haslo = ?;
SELECT pojazd.ilosc_miejsc FROM transport.pojazd WHERE numer_rejestracji = ?;
SELECT pojazd.pojazd_pk FROM transport.pojazd WHERE numer_rejestracji = ?;
SELECT * FROM transport.pojazd LEFT JOIN transport.kurs_pojazd ON pojazd.pojazd_pk = kurs_pojazd.pojazd_pk;

DELETE FROM transport.adres WHERE adres_pk = ?;
DELETE FROM transport.kurs WHERE kurs_pk = ?
DELETE FROM transport.kurs_kierowca WHERE kierowca_pk = ?;
DELETE FROM transport.kurs_kierowca WHERE kurs_pk = ?;
DELETE FROM transport.kurs_pojazd WHERE pojazd_pk = ?;
DELETE FROM transport.kurs_pojazd WHERE kurs_pk = ?;
DELETE FROM transport.kierowca WHERE kierowca_pk = ?;
DELETE FROM transport.bagaz WHERE uzytkownik_pk = ? AND bilet_pk = ?;
DELETE FROM transport.dane_osobowe WHERE uzytkownik_pk = ? AND adres_pk = ?;
DELETE FROM transport.trasa WHERE trasa_pk = ?
DELETE FROM transport.bilet WHERE uzytkownik_pk = ? AND kurs_pk = ?
DELETE FROM transport.uzytkownik WHERE uzytkownik_pk = ?;
DELETE FROM transport.pojazd WHERE pojazd_pk = ?;