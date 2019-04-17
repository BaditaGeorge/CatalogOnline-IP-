public class Main {
    public static void main(String[] args) {
        SQL_func test = new SQL_func();
        System.out.println(test.selectFormula());
        System.out.println(test.selectNote());
        test.updateNote(1,12,"L1 5; L2 6; L3 7; L4 8");
    }
}
