package hibernate.lesson4.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class BaseDAO<T> {
    private static SessionFactory sessionFactory;
    private Class<T> typeOfT;

    public BaseDAO(Class<T> typeOfT) {
        this.typeOfT = typeOfT;
    }

    public T save(T object) throws HibernateException {
        try(Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();

            tr.begin();

            session.save(object);

            tr.commit();

            return object;
        } catch (HibernateException e) {
            throw new HibernateException("Save is failed");
        }
    }

    public T update(T object) throws HibernateException {
        try (Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            session.update(object);

            tr.commit();

            return object;
        } catch (HibernateException e) {
            throw new HibernateException("Update is failed");
        }
    }

    public void delete(long objectId) throws HibernateException {
        try (Session session = createSessionFactory().openSession()) {
            Transaction tr = session.getTransaction();
            tr.begin();

            session.delete(session.get(this.typeOfT, objectId));

            tr.commit();
        } catch (HibernateException e) {
            throw new HibernateException("Delete is failed");
        }
    }

    public T findById(long objectId) throws HibernateException {
        try (Session session = createSessionFactory().openSession()) {

            return session.get(this.typeOfT, objectId);
        } catch (HibernateException e) {
            throw new HibernateException("Search is failed");
        }
    }

    protected static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }

        return sessionFactory;
    }
}
