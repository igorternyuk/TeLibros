/**
 * Author:  igor
 * Created: May 31, 2017
 */
create database bd_libros;
use bd_libros;

create table paises(
    id int not null auto_increment,
    nombre varchar(100) not null,
    primary key(id)
);

insert into paises values(default, 'Ucrania'); --1
insert into paises values(default, 'Rusia');  --2
insert into paises values(default, 'España'); --3
insert into paises values(default, 'Alemania'); --4
insert into paises values(default, 'Inglaterra'); --5
insert into paises values(default, 'Francia'); --6
insert into paises values(default, 'Italia'); --7
insert into paises values(default, 'Japón'); --8
insert into paises values(default, 'China'); --9
insert into paises values(default, 'Dinamarca'); --10
insert into paises values(default, 'Canadá'); --11
insert into paises values(default, 'México'); --12
insert into paises values(default, 'EUA'); --13
insert into paises values(default, 'Brasil'); --14
insert into paises values(default, 'Australia'); --15
insert into paises values(default, 'Portugal'); --16
insert into paises values(default, 'Noruega'); --17
insert into paises values(default, 'Bielorrusia'); --18
insert into paises values(default, 'Grecia'); --19

create table generos(
    id int not null auto_increment,
    nombre varchar(100) not null,
    primary key(id)
);

insert into generos values(default, 'prosa'); --1
insert into generos values(default, 'poesía'); --2
insert into generos values(default, 'dramaturgia'); --3
insert into generos values(default, 'ficción'); --4
insert into generos values(default, 'ficción científica'); --4
insert into generos values(default, 'literatura cientìfico-técnica'); --5
insert into generos values(default, 'guía'); --6
insert into generos values(default, 'revista'); --7
insert into generos values(default, 'filosofia'); --8
insert into generos values(default, 'programación'); --9

create table autores(
    id int not null auto_increment,
    nombre varchar(100) not null,
    sexo enum('M', 'F') not null,
    fecha_n date,
    fecha_m date,
    pais int,
    primary key(id),
    foreign key(pais) references paises(id)
);

insert into autores values(default, 'J.K. Rowling', 'F', '1965-07-31','2100-01-01', 13); --1
insert into autores values(default, 'Stephen King', 'M', '1947-09-21','2100-01-01', 13); --2
insert into autores values (default, 'Bjarne Stroustrup', 'M', '1950-12-30','2100-01-01', 10); --3
insert into autores values (default, 'M. Y. Vigodskiy', 'M', '1898-10-02','1965-09-26', 18); --4
insert into autores values (default, 'T. G. Shevchenko', 'M', '1914-03-09','1861-03-10', 1); --5
insert into autores values (default, 'L. N. Tolstoy', 'M', '1928-09-09','1910-11-20', 2); --6
insert into autores values (default, 'A. S. Pushkin', 'M', '1799-06-06','1837-02-10', 2); --7
insert into autores values (default, 'M. Y. Lermontov', 'M', '1814-10-15','1841-07-27', 2); --8
insert into autores values (default, 'I. K. Karpenko-Kariy', 'M', '1845-09-29','1907-09-15', 1); --9
insert into autores values (default, 'Osvald Spengler', 'M', '1880-05-29','1936-05-08', 4); --10
insert into autores values (default, 'Immanuil Kant', 'M', '1724-04-22','1804-02-12', 4); --11

create table libros(
    id int auto_increment,
    nombre varchar(100) not null,
    autor int,
    genero int,
    fecha date,
    paginas int,
    precio double,
    disponible bit,
    primary key(id),
    foreign key(autor) references autores(id),
    foreign key(genero) references generos(id)
);

insert into libros values(
     default,
    'Harry Potter: La piedra filosofal',
     1,
     4,
    '1995-08-19',
    600,
    120.5,
    true
);

insert into libros values(
    default,
    'Harry Potter: El prisionero de Azkaban',
    1,
    4,
    '1999-09-07',
    500,
    200.4,
    true
);

insert into libros values(
     default,
    'C++ programming language',
     3,
     9,
    '2013-06-14',
    1150,
    650.3,
    true
);

insert into libros values(
    default,
    'Spravochnik po visshey matematike',
    4,
    5,
    '2005-09-22',
    950,
    720.9,
    true
);

insert into libros values(
    default,
    'Kobzar',
    5,
    2,
    '1840-01-01',
    600,
    150.8,
    false
);

insert into libros values(
    default,
    'Evgeniy Onegin',
    7,
    2,
    '1820-01-01',
    400,
    190.7,
    true
);

insert into libros values(
    default,
    'Geroy nashego vremeni',
    8,
    1,
    '1830-01-01',
    250,
    400.2,
    false
);

insert into libros values(
    default,
    'Martyn Borulya',
    9,
    3,
    '1886-04-05',
    18,
    120.5,
    true
);

insert into libros values(
    default,
    'Zakat evropy',
    10,
    8,
    '1922-10-11',
    1200,
    1080.5,
    false
);

insert into libros values(
    default,
    'Die Kritik der reihnen Vernunft',
    11,
    8,
    '1781-05-05',
    600,
    160.5,
    true
);

select concat (libros.nombre, ", ", autores.nombre, "(", paises.nombre, ")", ", ", libros.fecha, " - p.", libros.paginas) as 'Name'
FROM libros, autores, generos, paises
where libros.autor = autores.id and libros.genero = generos.id and autores.pais = paises.id;

select libros.id, libros.nombre, generos.nombre, autores.nombre, paises.nombre,
libros.fecha, libros.paginas, libros.precio, libros.disponible
from libros, autores, generos, paises
where libros.autor = autores.id
and libros.genero = generos.id
and autores.pais = paises.id
order by libros.fecha desc;

-- select * from libros;
-- select * from autores;
--
-- select * from libros
-- where nombre like '%Har%'
--  and precio >= 0 and precio <= 300
-- order by libros.fecha desc;
--
--
--  select * from libros where libros.nombre like '%%'  and libros.genero = 4
--  and libros.autor = 1 and (libros.fecha >= 1800-02-01 and libros.fecha <=2015-02-01)
--  and (libros.paginas >= 0 and libros.paginas <=1000) and (libros.precio >= 0.0
--  and libros.precio <=1000.0) and libros.disponible = true;

drop function if exists calc_age;

DELIMITER //

create function calc_age(birthday date)
returns int
begin
return ((DATE_FORMAT(NOW(),'%Y') - DATE_FORMAT(birthday,'%Y') -
       (if(DATE_FORMAT(NOW(),'%m-%d') < DATE_FORMAT(birthday,'%m-%d'), 1, 0))));
end; //

DELIMITER ;

select * from autores where calc_age(autores.fecha_n) < 70;

--drop database bd_libros;
--show databases;