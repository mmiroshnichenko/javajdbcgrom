package hibernate.lesson2.lesson;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ProductDAO {
    private static SessionFactory sessionFactory;

    public static Product save(Product product) {
        try(Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();

            tr.begin();

            session.save(product);

            tr.commit();

            return product;
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static Product update(Product product) {
        try (Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            session.update(product);

            tr.commit();

            return product;
        } catch (HibernateException e) {
            System.err.println("Update is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static void delete(Product product) {
        try (Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            session.delete(product);

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Delete is failed");
            System.err.println(e.getMessage());
        }
    }

    public static void saveProducts(List<Product> products) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            for (Product product : products) {
                session.save(product);
            }

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }

        System.out.println("Save is done");
    }

    public static void updateProducts(List<Product> products) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            for (Product product : products) {
                session.update(product);
            }

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Update is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }

        System.out.println("Update is done");
    }

    public static void deleteProducts(List<Product> products) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            for (Product product : products) {
                session.delete(product);
            }

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Delete is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }

        System.out.println("Delete is done");
    }

    public static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }

        return sessionFactory;
    }
}
