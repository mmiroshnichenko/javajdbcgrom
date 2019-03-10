package hibernate.lesson2;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ProductDAO {
    private static SessionFactory sessionFactory;

    public static Product save(Product product) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.save(product);

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

    public static Product update(Product product) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.update(product);

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Update is failed");
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

    public static void delete(Product product) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.delete(product);

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Delete is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static void saveProducts(List<Product> products) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
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
        } finally {
            if (session != null) {
                session.close();
            }
        }

        System.out.println("Save is done");
    }

    public static void updateProducts(List<Product> products) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
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
        } finally {
            if (session != null) {
                session.close();
            }
        }

        System.out.println("Update is done");
    }

    public static void deleteProducts(List<Product> products) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
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
        } finally {
            if (session != null) {
                session.close();
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
