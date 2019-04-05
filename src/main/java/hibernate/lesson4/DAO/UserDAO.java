package hibernate.lesson4.DAO;

import hibernate.lesson4.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserDAO extends BaseDAO<User> {
    private static final String findByUserNameQuery = "FROM User WHERE userName = :userName";
    private static final String findByUserNameAndPasswordQuery = "FROM User WHERE userName = :userName AND password = :password";

    public UserDAO() {
        super(User.class);
    }

    public User findByUserName(String userName) {
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(findByUserNameQuery);
            query.setParameter("userName", userName);

            return (User) query.uniqueResult();
        } catch (HibernateException e) {
            System.err.println("Search by userName is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public User findByUserNameAndPassword(String userName, String password) {
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(findByUserNameAndPasswordQuery);
            query.setParameter("userName", userName);
            query.setParameter("password", password);

            return (User) query.uniqueResult();
        } catch (HibernateException e) {
            System.err.println("Search by userName and password is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }
}
