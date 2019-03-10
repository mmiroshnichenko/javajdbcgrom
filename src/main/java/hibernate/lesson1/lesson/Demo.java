package hibernate.lesson1.lesson;

public class Demo {
    public static void main(String[] args) {
//        Session session = new HibernateUtils().createSessionFactory().openSession();
//
//        session.getTransaction().begin();
//
//        Product product = new Product();
//        product.setId(99);
//        product.setName("table");
//        product.setDescription("grey & blue");
//        product.setPrice(70);
//
//        session.save(product);
//
//        session.getTransaction().commit();
//
//        System.out.println("Done");
//
//        session.close();

        Product product1 = new Product();
        product1.setId(201);
        product1.setName("testName1");
        product1.setDescription("testDescription1");
        product1.setPrice(100);

        //ProductRepository.save(product1);

        product1.setPrice(200);
        ProductRepository.update(product1);

        ProductRepository.delete(201);

    }
}
