package jdbc.lesson3.hw2;

public class Demo {
    public static void main(String[] args) {

        System.out.println("Save performance: " + Solution.testSavePerformance());

        System.out.println("Delete by Id performance: " + Solution.testDeleteByIdPerformance());

        System.out.println("Save performance: " + Solution.testSavePerformance());

        System.out.println("Delete performance: " + Solution.testDeletePerformance());

        System.out.println("Save performance: " + Solution.testSavePerformance());

        System.out.println("Select by Id performance: " + Solution.testSelectByIdPerformance());

        System.out.println("Select performance: " + Solution.testSelectPerformance());
    }
}
