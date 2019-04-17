create table studenti (
id_student numeric(5) not null,
nume varchar(20) not null,
prenume varchar(20) not null,
email varchar(50) not null,
primary key (id_student)
);

create table utilizatori(
id_utilizator numeric(5) not null,
username varchar(20) not null,
email varchar(50) not null,
parola varchar(20) not null,
salt_parola varchar(200) not null,
numar_telefon varchar(14),
tip_utilizator varchar2(40),
primary key (id_utilizatori)
);

create table materii(
id_materie numeric(3) not null,
id_student numeric(5) not null,
denumire_materie varchar(50) not null,
valori_note varchar2(1000),
formula_calcul varchar2(1000) not null,
primary key (id_materie),
foreign key (id_student) references studenti(id_student)
);

create table usersAstud(
id_utilizator numeric(5) not null,
id_student numeric(5) not null,
foreign key ( id_utilizator ) references utilizatori(id_utilizator),
foreign key ( id_student ) references studenti(id_student)

);

