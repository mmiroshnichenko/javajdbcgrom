package hibernate.lesson2.hw2;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class ProductDAO {
    private static SessionFactory sessionFactory;

    private static final String findByIdQuery = "SELECT * FROM PRODUCT WHERE ID = :id";
    private static final String findByNameQuery = "SELECT * FROM PRODUCT WHERE NAME = :name";
    private static final String findByContainedNameQuery = "SELECT * FROM PRODUCT WHERE NAME like :name";
    private static final String findByPriceQuery = "SELECT * FROM PRODUCT WHERE PRICE BETWEEN :minPrice AND :maxPrice";
    private static final String findByNameSortedAscQuery = "SELECT * FROM PRODUCT WHERE NAME like :name ORDER BY NAME ASC";
    private static final String findByNameSortedDescQuery = "SELECT * FROM PRODUCT WHERE NAME like :name ORDER BY NAME DESC";
    private static final String findByPriceSortedDescQuery = "SELECT * FROM PRODUCT WHERE PRICE BETWEEN :minPrice AND :maxPrice ORDER BY PRICE DESC";

    public static Product findById(long id) {
        try (Session session = createSessionFactory().openSession()) {
            NativeQuery query = session.createNativeQuery(findByIdQuery);
            query.addEntity(Product.class);
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
            NativeQuery query = session.createNativeQuery(findByNameQuery);
            query.addEntity(Product.class);
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
            NativeQuery query = session.createNativeQuery(findByContainedNameQuery);
            query.addEntity(Product.class);
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
            NativeQuery query = session.createNativeQuery(findByPriceQuery);
            query.addEntity(Product.class);
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
            NativeQuery query = session.createNativeQuery(findByNameSortedAscQuery);
            query.addEntity(Product.class);
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
            NativeQuery query = session.createNativeQuery(findByNameSortedDescQuery);
            query.addEntity(Product.class);
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
            NativeQuery query = session.createNativeQuery(findByPriceSortedDescQuery);
            query.addEntity(Product.class);
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
