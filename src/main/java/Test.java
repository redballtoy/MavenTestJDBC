public class Test {
    public static void main(String[] args) {

        int newPrice = 15;
        String goodName = "name";

        int i = 10, k = 5;
        String str = "INSERT INTO Goods (\'good_name\',\'good_price\') " +
                "values("+"\'good_name_"+ i +"\'"+","
                + (i * k) + ")";

        String str2 =
                "update Goods " +
                "set good_price = " + newPrice+
                " where good_name =\'"+goodName+"\'";
        System.out.println(str2);
    }
}
