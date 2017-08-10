create memory table ACCOUNT (ID NUMERIC not null primary key, AMOUNT DECIMAL(10,5) not null);

insert into ACCOUNT(ID, AMOUNT) values (1, 100.0);
insert into ACCOUNT(ID, AMOUNT) values (2, 1213.02);
insert into ACCOUNT(ID, AMOUNT) values (3, 11231.0);
insert into ACCOUNT(ID, AMOUNT) values (4, 12312.123);
insert into ACCOUNT(ID, AMOUNT) values (5, 15452.23);
insert into ACCOUNT(ID, AMOUNT) values (6, -2342.32);
insert into ACCOUNT(ID, AMOUNT) values (7, -76454.76);
insert into ACCOUNT(ID, AMOUNT) values (8, 1234.0234);
insert into ACCOUNT(ID, AMOUNT) values (9, 2342.234);
insert into ACCOUNT(ID, AMOUNT) values (10, 543.2346);
insert into ACCOUNT(ID, AMOUNT) values (11, 3453.3452);
insert into ACCOUNT(ID, AMOUNT) values (12, 743.34);
insert into ACCOUNT(ID, AMOUNT) values (13, 1986.65);

commit;

