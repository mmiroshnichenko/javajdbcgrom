package lesson3;

public class Demo {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        productDAO.delete(10);

        Product product = new Product(10, "test", "test description", 99);

        productDAO.save(product);

        product.setName("name test name");
        productDAO.update(product);

        System.out.println(productDAO.getProducts());

        productDAO.delete(10);

        System.out.println(productDAO.getProducts());
    }
}
