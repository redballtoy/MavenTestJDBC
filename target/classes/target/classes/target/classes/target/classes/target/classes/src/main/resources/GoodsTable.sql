--Создаь таблицу товаров (good_id, good_name, good_price)
create table if not exists Goods(
good_id integer primary key autoincrement,
good_name text not null,
good price integer not null
)

drop table if exists Goods

select * from Goods

select count(good_id) from Goods

select * from Goods 
where good_name ='good_name_1111'

update Goods 
set good_price = 1111
where good_name ='good_name_1111'

