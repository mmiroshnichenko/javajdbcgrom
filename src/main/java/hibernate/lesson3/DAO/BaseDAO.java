package hibernate.lesson3.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.ParameterizedType;

public class BaseDAO<T> {
    private static SessionFactory sessionFactory;
    private Class<T> typeOfT;

    public BaseDAO(Class<T> typeOfT) {
        //this.typeOfT = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.typeOfT = typeOfT;
    }

    public T save(T object) {
        try(Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();

            tr.begin();

            session.save(object);

            tr.commit();

            return object;
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public T update(T object) {
        try (Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            session.update(object);

            tr.commit();

            return object;
        } catch (HibernateException e) {
            System.err.println("Update is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public void delete(long objectId) {
        try (Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            session.delete(session.get(this.typeOfT, objectId));

            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Delete is failed");
            System.err.println(e.getMessage());
        }
    }

    public T findById(long objectId) {
        try (Session session = createSessionFactory().openSession()) {

            return session.get(this.typeOfT, objectId);
        } catch (HibernateException e) {
            System.err.println("Search is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    private static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }

        return sessionFactory;
    }
}
