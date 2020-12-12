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
    //private final static String outStudentsAllColumn = "Id: %d \tName: \t%s, \tage: \t%d \n";


    //ТАБЛИЦА: Goods
    //запросы
    private final static String QUERY_GOOGS_CREATE_TABLE =
            "create table if not exists Goods(" +
                    "good_id integer primary key autoincrement," +
                    "good_name text not null," +
                    "good_price integer not null" +
                    ")";
    private final static String QUERY_GOODS_SELECT_ALL = "select * from Goods";
    private final static String QUERY_GOODS_SELECT_CORRECT_GOOD_NAME = "select * from Goods " +
            "where good_name ='good_name_18'";
    private final static String QUERY_GOODS_SELECT_INCORRECT_GOOD_NAME = "select * from Goods " +
            "where good_name ='good_name_63510'";
    private final static String QUERY_GOODS_SELECT_BETWEEN_PRICE = "select * from Goods " +
            "where good_price between ";
    private final static String QUERY_GOODS_DROP_TABLE = "drop table if exists Goods";
    private final static String QUERY_GOODS_DELETE_ALL = "delete from Goods";
    private final static String QUERY_GOODS_SELECT_COUNT = "select count(good_id) from Goods";
    //Поля таблицы
    private final static String[] colNameGoodsTable = {"good_id", "good_name", "good_price"};
    //вывод
    //private final static String outGoogsAllColumn = "good_id: %d \tgood_name: \t%s, \tgood_price: \t%d \n";


    public static void main(String[] args) {

        //подключение к БД
        connection();

        //очистка таблицы Goods
        DML_Query(QUERY_GOODS_DELETE_ALL, "Данные из таблицы Goods успешно удалены",
                "Ошибка удаления данных из таблицы Goods");

        //удаление таблицы
//        DML_Query(QUERY_GOODS_DROP_TABLE, "Таблица Goods успешно удалена",
//                "Ошибка удаления таблицы Goods :(");


        //CREATE TABLE GOODS
//        DML_Query(QUERY_GOOGS_CREATE_TABLE,"Таблица Goods успешно создана",
//                "Ошибка создания таблицы Goods :(");

        //INSERT ROWS TO GOODS
//      insertValuesToTableGoods(9999);

        //SELECT QUERY
        //STUDENTS
        //select(QUERY_STUDENTS_SELECT_AGE, colNameStudentTable,outStudentsAllColumn);

        //GOODS
        //вывести все столбцы из таблицы Goods
        select(QUERY_GOODS_SELECT_ALL, colNameGoodsTable);

        //запросы по ДЗ
        //4 получить цену товара по его имени или вывести  если его нет
        //товар есть
        //select(QUERY_GOODS_SELECT_CORRECT_GOOD_NAME, colNameGoodsTable);
        //твуого товара нет
        //select(QUERY_GOODS_SELECT_INCORRECT_GOOD_NAME, colNameGoodsTable);


        //5. UPDATE цену товара
        //updateGoodPrice("good_name_18", 18);
        //select(QUERY_GOODS_SELECT_CORRECT_GOOD_NAME, colNameGoodsTable);

        //6. Вывести товары в заданной ценовом диапазоне
        //selectGoodsPriceRange(QUERY_GOODS_SELECT_BETWEEN_PRICE,246, 768);


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


    private static void select(String query, String[] columnTable) {
        resultSet = getResultSetByQuery(query);
        System.out.println(columnTable[0] + "\t\t" + columnTable[1] + "\t\t\t" + columnTable[2]);
        int count = 0;
        while (getOneRowFromResultSet(resultSet)) {
            //вывод содержимого таблицы
            showResultByRows(resultSet, columnTable);
            count++;
        }
        if (count == 0) {
            System.out.printf("Ничего не найдено\n\n");
        } else {
            System.out.printf("Найдено %d строк\n\n", count);
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

    private static void showResultByRows(ResultSet resultSet, String[] columns) {
        try {
            //int id = resultSet.getInt(columns[0]);
            System.out.println(
                    resultSet.getInt(columns[0]) //id
                            + "\t\t\t" + resultSet.getString(columns[1]) //name
                            + "\t\t\t\t" + resultSet.getInt(columns[2]));//age
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
            int k = (int) (Math.random() * 1000);
            try {
                int res = i * k;
                //System.out.println("res=" + res + "i= " + i + " k= " + k);
                String query = "INSERT INTO Goods (\'good_name\',\'good_price\') " +
                        "values(" + "\'good_name_" + i + "\'" + ","
                        + res + ");";
                statement.executeUpdate(query);
            } catch (SQLException throwables) {
                System.out.println("Ошибка вставки данных");
                throwables.printStackTrace();
                return;
            }
        }
        System.out.printf("\nВ таблицу вставлено %d записей\n", i);
    }

    private static void updateGoodPrice(String goodName, int newPrice) {
        try {
            String query = "update Goods " +
                    "set good_price = " + newPrice +
                    " where good_name =\'" + goodName + "\'";

            statement.executeUpdate(query);
            System.out.println("Обновление прошло успешно");
        } catch (SQLException throwables) {
            System.out.println("Ошибка при обновлении");
            throwables.printStackTrace();
        }
    }

    private static void selectGoodsPriceRange(String q, int minPrice, int maxPrice) {
        String query = q + minPrice + " and " + maxPrice;
        select(query, colNameGoodsTable);
        //System.out.println(query);
    }
}
