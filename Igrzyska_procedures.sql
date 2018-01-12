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