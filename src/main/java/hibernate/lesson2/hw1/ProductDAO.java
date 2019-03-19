package hibernate.lesson2.hw1;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO {
    private static SessionFactory sessionFactory;

    private static final String findByIdQuery = "FROM Product WHERE id = :id";
    private static final String findByNameQuery = "FROM Product WHERE name = :name";
    private static final String findByContainedNameQuery = "FROM Product WHERE name LIKE :name";
    private static final String findByPriceQuery = "FROM Product WHERE price BETWEEN :minPrice AND :maxPrice";
    private static final String findByNameSortedAscQuery = "FROM Product WHERE name LIKE :name ORDER BY name ASC";
    private static final String findByNameSortedDescQuery = "FROM Product WHERE name LIKE :name ORDER BY name DESC";
    private static final String findByPriceSortedDescQuery = "FROM Product WHERE price BETWEEN :minPrice AND :maxPrice ORDER BY price DESC";


    public static Product findById(long id) {
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(findByIdQuery);
            query.setParameter("id", id);

            return (Product) query.getSingleResult();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static List<Product> findByName(String name) {
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(findByNameQuery);
            query.setParameter("name", name);

            return query.list();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static List<Product> findByContainedName(String name) {
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(findByContainedNameQuery);
            query.setParameter("name", "%" + name + "%");

            return query.list();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static List<Product> findByPrice(int price, int delta) {
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(findByPriceQuery);
            query.setParameter("minPrice", price - delta);
            query.setParameter("maxPrice", price + delta);

            return query.list();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static List<Product> findByNameSortedAsc(String name) {
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(findByNameSortedAscQuery);
            query.setParameter("name", "%" + name + "%");

            return query.list();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static List<Product> findByNameSortedDesc(String name) {
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(findByNameSortedDescQuery);
            query.setParameter("name", "%" + name + "%");

            return query.list();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static List<Product> findByPriceSortedDesc(int price, int delta) {
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(findByPriceSortedDescQuery);
            query.setParameter("minPrice", price - delta);
            query.setParameter("maxPrice", price + delta);

            return query.list();
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
