import java.sql.*;

public class LectionMain {

    //Поля необхоодимые для работы с БД
    //непосредственно само подключение
    private static Connection connection;
    //объект для взаимодействия с БД
    private static Statement statement;
    //возвращаемый запросом результат
    private static ResultSet resultSet;

    //запросы
    private final static String QUERY_STUDENTS_SELECT_ALL = "select * from Students";
    private final static String QUERY_STUDENTS_SELECT_AGE = "select * from Students where age=25";

    //Поля таблицы
    private final static String[] colNameStudentTable = {"id", "name", "age"};


    public static void main(String[] args) {

        //подключение к БД
        connection();

        //методы отправки запросов:
        //execute() - главный метод отправки
        //executeQuery() - SELECT
        //executeUpdate() - UPDATE, DELETE, INSERT - MDL команды
        //SELECT
        select(QUERY_STUDENTS_SELECT_AGE, colNameStudentTable);





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
        while (getOneRowFromResultSet(resultSet)) {
            //вывод содержимого таблицы
            showResultByRows(resultSet, columnTable);
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
            int id = resultSet.getInt(columns[0]);
            System.out.printf("Id: %d \tName: \t%s, \tage: \t%d \n",
                    resultSet.getInt(columns[0]), //id
                    resultSet.getString(columns[1]), //name
                    resultSet.getInt(columns[2]));//age
        } catch (SQLException throwables) {
            System.out.println("Ошибка вывода строки из ResultTest");
            throwables.printStackTrace();
        }

    }
}
