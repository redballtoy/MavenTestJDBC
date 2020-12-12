public class Test {
    public static void main(String[] args) {

        int i = 10, k = 5;
        String str = "INSERT INTO Goods (\'good_name\',\'good_price\') " +
                "values("+"\'good_name_"+ i +"\'"+","
                + (i * k) + ")";
        System.out.println(str);
    }
}
