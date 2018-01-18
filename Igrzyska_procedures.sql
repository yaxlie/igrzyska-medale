create or replace function getKrajScore(kraj_id in VARCHAR2) 
   RETURN NUMBER 
   IS
   zl INTEGER;
   sr INTEGER;
   br INTEGER;
   BEGIN 
      SELECT count(*) 
      INTO zl 
      FROM medal 
      WHERE Zawodnik_id_zaw in (select id_zaw from zawodnik where kraj = kraj_id) and kolor like 'Z£OTO';
      
    SELECT count(*) 
      INTO sr 
      FROM medal 
      WHERE Zawodnik_id_zaw in (select id_zaw from zawodnik where kraj = kraj_id) and kolor like 'SREBRO';
      
    SELECT count(*) 
      INTO br 
      FROM medal 
      WHERE Zawodnik_id_zaw in (select id_zaw from zawodnik where kraj = kraj_id) and kolor like 'BR¥Z';
      
      RETURN(zl*7 + sr * 3 + br); 
    END;
/

CREATE OR REPLACE PROCEDURE
dodaj_zawodnik (imie IN VARCHAR2,
nazwisko IN VARCHAR2,
data_ur IN VARCHAR2,
trener IN NUMBER,
kraj IN VARCHAR2,
dyscyplina IN VARCHAR2,
zespol IN VARCHAR2,
ocena in FLOAT) IS

l_exists INTEGER;
BEGIN
  SELECT COUNT(*) INTO l_exists
  from admin.dyscyplina where UPPER(nazwa) like UPPER(dyscyplina)
  AND ROWNUM = 1;
  
IF (l_exists = 0) then
    insert into admin.dyscyplina values(dyscyplina, null, null, null, null);
end if;

  SELECT COUNT(*) INTO l_exists
  from admin.kraj where UPPER(nazwa) like UPPER(kraj)
  AND ROWNUM = 1;
IF (l_exists = 0) then
    insert into admin.kraj values(kraj);
end if;

  SELECT COUNT(*) INTO l_exists
  from admin.zespó³ z where z.kraj_nazwa like kraj 
            and z.dyscyplina_nazwa like dyscyplina
  AND ROWNUM = 1;

IF (zespol is not null and l_exists = 1) then
       INSERT INTO admin.zawodnik VALUES(null, imie, nazwisko, (select numer from admin.zespó³ z where z.kraj_nazwa like kraj 
            and z.dyscyplina_nazwa like dyscyplina), ocena, trener, dyscyplina, kraj, to_date(data_ur, 'dd-mm-yy'));
elsif zespol is not null then
       INSERT INTO admin.zespó³ VALUES(null, kraj, dyscyplina, null, null, null, null);
       INSERT INTO admin.zawodnik VALUES(null, imie, nazwisko, (select numer from admin.zespó³ where kraj_nazwa like kraj), 
            ocena, trener, dyscyplina, kraj, to_date(data_ur, 'dd-mm-yy'));
else
INSERT INTO admin.zawodnik VALUES(null, imie, nazwisko, null, ocena, trener, dyscyplina, kraj, to_date(data_ur, 'dd-mm-yy'));
end if;
END dodaj_zawodnik;
/
commit;
/

create or replace PROCEDURE
dodaj_medal (kolor IN VARCHAR2,
zespol_id IN NUMBER,
zawodnik_id IN NUMBER,
dyscyplina IN VARCHAR2,
data_wr IN VARCHAR2) IS
BEGIN
IF data_wr is not null then
insert into admin.medal values(null, kolor, zespol_id, to_date(data_wr, 'dd-mm-yy'),zawodnik_id, dyscyplina);
else
insert into admin.medal values(null, kolor, zespol_id, (SELECT SYSDATE FROM DUAL),zawodnik_id, dyscyplina);
end if;
END dodaj_medal;
/