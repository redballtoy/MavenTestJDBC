//�������� ������� Students
create table if not exists Students(
id integer primary key autoincrement,
name text not null,
age integer not null)

//���������� �������� ���������
insert into Students (name,age) values('����','28');
insert into Students (name,age) values('���','33');
insert into Students (name,age) values('����','25');
insert into Students (name,age) values('�����','27');
insert into Students (name,age) values('���','27');
insert into Students (name,age) values('�����','26');


--���������� �� ���� ���������
select * from Students
select name , age  from Students 
--//where age between 25 and 27


//�������� ��� ���������
drop table Students 

delete from Students 
where id =1

