package hibernate.lesson1.hw;


import org.hibernate.Session;

public class Demo {
    public static void main(String[] args) {
        Product product = new Product();
        product.setId(202);
        product.setName("table");
        product.setDescription("grey & blue");
        product.setPrice(70);

        save(product);
    }

    private static void save(Product product) {
        Session session = new HibernateUtils().createSessionFactory().openSession();

        session.getTransaction().begin();

        session.save(product);

        session.getTransaction().commit();

        System.out.println("Done");

        session.close();
    }
}
