//Создание таблицы Students
create table if not exists Students(
id integer primary key autoincrement,
name text not null,
age integer not null)

//заполнение таблички студентов
insert into Students (name,age) values('Джек','28');
insert into Students (name,age) values('Боб','33');
insert into Students (name,age) values('Иван','25');
insert into Students (name,age) values('Гриша','27');
insert into Students (name,age) values('Сид','27');
insert into Students (name,age) values('Ларри','26');


--посмотреть на этих студентов
select * from Students
select name , age  from Students 
--//where age between 25 and 27


//дропнуть или почистить
drop table Students 

delete from Students 
where id =1

