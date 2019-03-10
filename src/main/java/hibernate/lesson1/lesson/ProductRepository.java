package hibernate.lesson1.lesson;

import org.hibernate.Session;

public class ProductRepository {
    public static void save(Product product) {
        try (Session session = new HibernateUtils().createSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.save(product);

            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error: Product(id: " + product.getId() + " has not saved");
            e.printStackTrace();
        }
    }

    public static void delete(long id) {
        try (Session session = new HibernateUtils().createSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.delete(session.get(Product.class, id));

            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error: Product(id: " + id + " has not deleted");
            e.printStackTrace();
        }
    }

    public static void update(Product product) {
        try (Session session = new HibernateUtils().createSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.update(product);

            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error: Product(id: " + product.getId() + " has not updated");
            e.printStackTrace();
        }
    }
}
