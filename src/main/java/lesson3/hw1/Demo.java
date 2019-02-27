package lesson3.hw1;

public class Demo {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        System.out.println(productDAO.findProductsByPrice(300, 100));

        try {
            System.out.println(productDAO.findProductsByName("car"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println(productDAO.findProductsWithEmptyDescription());

    }
}
