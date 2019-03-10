package hibernate.lesson2;

import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Product product = new Product();

        product.setName("table new");
        product.setDescription("grey & blue");
        product.setPrice(70);

        ProductDAO.save(product);

        product.setName("table new!!!");
        ProductDAO.update(product);
        ProductDAO.delete(product);

        Product product1 = new Product();

        product1.setName("table new111");
        product1.setDescription("grey & blue");
        product1.setPrice(70);

        Product product2 = new Product();

        product2.setName("table new222");
        product2.setDescription("grey & blue");
        product2.setPrice(80);

        Product product3 = new Product();

        product3.setName("table new333");
        product3.setDescription("grey & blue");
        product3.setPrice(90);

        List<Product> products = Arrays.asList(product1, product2, product3);
        ProductDAO.saveProducts(products);

        product1.setName("table_new111!!!");
        product2.setName("table_new222!!!");
        product3.setName("table_new333!!!");

        ProductDAO.updateProducts(products);
        ProductDAO.deleteProducts(products);

    }
}
