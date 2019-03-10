package hibernate.lesson2.hw1;

import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Product product = ProductDAO.findById(6);
        System.out.println(product);

        List<Product> products1 = ProductDAO.findByName("table_new111!!!");
        System.out.println(products1);

        products1 = ProductDAO.findByContainedName("!!!");
        System.out.println(products1);

        products1 = ProductDAO.findByPrice(80, 10);
        System.out.println(products1);

        products1 = ProductDAO.findByNameSortedAsc("!!!");
        System.out.println(products1);

        products1 = ProductDAO.findByNameSortedDesc("!!!");
        System.out.println(products1);

        products1 = ProductDAO.findByPriceSortedDesc(80, 10);
        System.out.println(products1);
    }
}
