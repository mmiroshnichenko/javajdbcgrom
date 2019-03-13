package hibernate.lesson2.hw1;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO {
    private static SessionFactory sessionFactory;

    public static Product findById(long id) {
        try (Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            Query query = session.createQuery("from Product where id = :id");
            query.setParameter("id", id);
            Product product = (Product) query.getSingleResult();

            tr.commit();

            return product;
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static List<Product> findByName(String name) {
        try (Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            Query query = session.createQuery("from Product where name = :name");
            query.setParameter("name", name);
            List<Product> products = query.list();

            tr.commit();

            return products;
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static List<Product> findByContainedName(String name) {
        try (Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            Query query = session.createQuery("from Product where name like :name");
            query.setParameter("name", "%" + name + "%");
            List<Product> products = query.list();

            tr.commit();

            return products;
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static List<Product> findByPrice(int price, int delta) {
        try (Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            Query query = session.createQuery("from Product where price between :minPrice and :maxPrice");
            query.setParameter("minPrice", price - delta);
            query.setParameter("maxPrice", price + delta);
            List<Product> products = query.list();

            tr.commit();

            return products;
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static List<Product> findByNameSortedAsc(String name) {
        try (Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            Query query = session.createQuery("from Product where name like :name ORDER BY name ASC");
            query.setParameter("name", "%" + name + "%");
            List<Product> products = query.list();

            tr.commit();

            return products;
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static List<Product> findByNameSortedDesc(String name) {
        try (Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            Query query = session.createQuery("from Product where name like :name ORDER BY name DESC");
            query.setParameter("name", "%" + name + "%");
            List<Product> products = query.list();

            tr.commit();

            return products;
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static List<Product> findByPriceSortedDesc(int price, int delta) {
        try (Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            Query query = session.createQuery("from Product where price between :minPrice and :maxPrice order by price DESC");
            query.setParameter("minPrice", price - delta);
            query.setParameter("maxPrice", price + delta);
            List<Product> products = query.list();

            tr.commit();

            return products;
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }

        return sessionFactory;
    }
}
