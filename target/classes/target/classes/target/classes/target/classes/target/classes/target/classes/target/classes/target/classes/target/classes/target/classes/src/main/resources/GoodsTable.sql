--Создаь таблицу товаров (good_id, good_name, good_price)
create table if not exists Goods(
good_id integer primary key autoincrement,
good_name text not null,
good price integer not null
)

drop table Goods