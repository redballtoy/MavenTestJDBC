import java.sql.*;
import java.util.Random;
/*
1. Подключиться к БД
2. Создаь таблицу товаров (good_id, good_name, good_price) запрсом из Java пиложения.
3. При запуске приложения очистить таблицу и заполнить 10000 товаров
4. Написать консольное приложение, которое позволяет узнать цену товара по его имени,
    либо вывести сообщение «Такого товара нет», если товар не обнаружен в базе.
5. Добавить возможность изменения цены товара. Указываем имя товара и новую цену.
6. Вывести товары в заданном ценовом диапазоне.
Вам понадобятся: SELECT, DELETE, WHERE, UPDATE, LIKE, BETWEEN
*/

public class HW_03_02_SQL {

    //Поля необхоодимые для работы с БД
    //непосредственно само подключение
    private static Connection connection;
    //объект для взаимодействия с БД
    private static Statement statement;
    //возвращаемый запросом результат
    private static ResultSet resultSet;


    //ТАБЛИЦА: Students
    //запросы
    private final static String QUERY_STUDENTS_SELECT_ALL = "select * from Students";
    private final static String QUERY_STUDENTS_SELECT_AGE = "select * from Students where age=25";
    //Поля таблицы
    private final static String[] colNameStudentTable = {"id", "name", "age"};
    //вывод
    private final static String outStudentsAllColumn = "Id: %d \tName: \t%s, \tage: \t%d \n";


    //ТАБЛИЦА: Goods
    //запросы
    private final static String QUERY_GOOGS_CREATE_TABLE =
            "create table if not exists Goods(" +
                    "good_id integer primary key autoincrement," +
                    "good_name text not null," +
                    "good_price integer not null" +
                    ")";
    private final static String QUERY_GOODS_SELECT_ALL = "select * from Goods";
    private final static String QUERY_GOODS_DROP_TABLE = "drop table if exists Goods";
    //Поля таблицы
    private final static String[] colNameGoodsTable = {"good_id", "good_name", "good_price"};
    //вывод
    private final static String outGoogsAllColumn = "good_id: %d \tgood_name: \t%s, \tgood_price: \t%d \n";


    public static void main(String[] args) {

        //подключение к БД
        connection();


        //удаление таблицы

//        DML_Query(QUERY_GOODS_DROP_TABLE, "Таблица Goods успешно удалена",
//                "Ошибка удаления таблицы Goods :(");


        //CREATE TABLE GOODS
//        DML_Query(QUERY_GOOGS_CREATE_TABLE,"Таблица Goods успешно создана",
//                "Ошибка создания таблицы Goods :(");

        //INSERT ROWS TO GOODS
        //insertValuesToTableGoods(10);


        //SELECT QUERY
        //select(QUERY_STUDENTS_SELECT_AGE, colNameStudentTable,outStudentsAllColumn);

        //вывести все столбцы из таблицы Goods
        select(QUERY_GOODS_SELECT_ALL, colNameGoodsTable, outGoogsAllColumn);


        //отключение от БД
        disconnect();


    }


    private static void connection() {
        //необходимо подключиться к JDBC
        try {
            Class.forName("org.sqlite.JDBC");

        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер JDBC не найден");
            e.printStackTrace();
        }

        //подключение к базе, для SQLite нет необходимости указывать пользователя и пароль
        try {
            connection = DriverManager
                    .getConnection("jdbc:sqlite:src/main/resources/hw_03_01_test.db");
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
            e.printStackTrace();
        }

        //объект для постоянного взаимодействия с БД, через него отправлять запросы
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Ошибка создания объекта для запросов");
            e.printStackTrace();

        }
    }

    private static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка закрытия базы");
            e.printStackTrace();
        }
    }


    private static void select(String query, String[] columnTable, String out) {
        resultSet = getResultSetByQuery(query);
        while (getOneRowFromResultSet(resultSet)) {
            //вывод содержимого таблицы
            showResultByRows(resultSet, columnTable, out);
        }
    }

    private static ResultSet getResultSetByQuery(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException throwables) {
            System.out.println("Ошибка выполнения запроса!");
            throwables.printStackTrace();
        }
        return null;
    }

    private static boolean getOneRowFromResultSet(ResultSet resultSet) {
        try {
            if (!resultSet.next()) return false;
        } catch (SQLException throwables) {
            System.out.println("Ошибка чтения строки из таблицы БД");
            throwables.printStackTrace();
        }
        return true;
    }

    private static void showResultByRows(ResultSet resultSet, String[] columns, String output) {
        try {
            int id = resultSet.getInt(columns[0]);
            System.out.printf(output,
                    resultSet.getInt(columns[0]), //id
                    resultSet.getString(columns[1]), //name
                    resultSet.getInt(columns[2]));//age
        } catch (SQLException throwables) {
            System.out.println("Ошибка вывода строки из ResultTest");
            throwables.printStackTrace();
        }

    }

    private static void DML_Query(String query, String sucess, String fault) {
        try {
            statement.executeUpdate(query);
            System.out.println(sucess);
        } catch (SQLException throwables) {
            System.out.println(fault);
            throwables.printStackTrace();
        }

    }

    private static void dropTable() {
    }

    private static void insertValuesToTableGoods(int countGoods) {
        int i = 0;
        for (i = 0; i <= countGoods; i++) {
            int k = new Random(1000).nextInt() + 100;
            try {
                String query = "INSERT INTO Goods (\'good_name\',\'good_price\') values(" + i + ","
                        + (i * k) + ")";
                statement.executeUpdate(query);
            } catch (SQLException throwables) {
                System.out.println("Ошибка вставки данных");
                throwables.printStackTrace();
                return;
            }
        }
        System.out.printf("\nВ таблицу вставлено %d записей",i);
    }
}
