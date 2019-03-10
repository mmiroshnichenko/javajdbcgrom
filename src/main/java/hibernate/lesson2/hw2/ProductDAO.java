package hibernate.lesson2.hw2;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO {
    private static SessionFactory sessionFactory;

    public static Product findById(long id) {
        Session session = null;
        Transaction tr = null;
        Product product = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            NativeQuery query = session.createNativeQuery("SELECT * FROM PRODUCT WHERE ID = :id");
            query.addEntity(Product.class);
            query.setParameter("id", id);
            product = (Product) query.getSingleResult();

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return product;
    }

    public static List<Product> findByName(String name) {
        Session session = null;
        Transaction tr = null;
        List<Product> products = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            NativeQuery query = session.createNativeQuery("SELECT * FROM PRODUCT WHERE NAME = :name");
            query.addEntity(Product.class);
            query.setParameter("name", name);
            products = query.list();

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return products;
    }

    public static List<Product> findByContainedName(String name) {
        Session session = null;
        Transaction tr = null;
        List<Product> products = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            NativeQuery query = session.createNativeQuery("SELECT * FROM PRODUCT WHERE NAME like :name");
            query.addEntity(Product.class);
            query.setParameter("name", "%" + name + "%");
            products = query.list();

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return products;
    }

    public static List<Product> findByPrice(int price, int delta) {
        Session session = null;
        Transaction tr = null;
        List<Product> products = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            NativeQuery query = session.createNativeQuery("SELECT * FROM PRODUCT WHERE PRICE BETWEEN :minPrice AND :maxPrice");
            query.addEntity(Product.class);
            query.setParameter("minPrice", price - delta);
            query.setParameter("maxPrice", price + delta);
            products = query.list();

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return products;
    }

    public static List<Product> findByNameSortedAsc(String name) {
        Session session = null;
        Transaction tr = null;
        List<Product> products = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            NativeQuery query = session.createNativeQuery("SELECT * FROM PRODUCT WHERE NAME like :name ORDER BY NAME ASC");
            query.addEntity(Product.class);
            query.setParameter("name", "%" + name + "%");
            products = query.list();

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return products;
    }

    public static List<Product> findByNameSortedDesc(String name) {
        Session session = null;
        Transaction tr = null;
        List<Product> products = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            NativeQuery query = session.createNativeQuery("SELECT * FROM PRODUCT WHERE NAME like :name ORDER BY NAME DESC");
            query.addEntity(Product.class);
            query.setParameter("name", "%" + name + "%");
            products = query.list();

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return products;
    }

    public static List<Product> findByPriceSortedDesc(int price, int delta) {
        Session session = null;
        Transaction tr = null;
        List<Product> products = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            NativeQuery query = session.createNativeQuery("SELECT * FROM PRODUCT WHERE PRICE BETWEEN :minPrice AND :maxPrice ORDER BY PRICE DESC");
            query.addEntity(Product.class);
            query.setParameter("minPrice", price - delta);
            query.setParameter("maxPrice", price + delta);
            products = query.list();

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return products;
    }

    public static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }

        return sessionFactory;
    }
}
