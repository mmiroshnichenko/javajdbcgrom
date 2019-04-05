package hibernate.lesson4.DAO;

import hibernate.lesson4.entity.Hotel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HotelDAO extends BaseDAO<Hotel> {
    private static final String findByNameQuery = "FROM Hotel WHERE name = :name";
    private static final String findByCityQuery = "FROM Hotel WHERE city = :city";
    private static final String findAllQuery = "FROM Hotel";
    private static final String findExistHotelQuery = "FROM Hotel WHERE name = :name AND country = :country AND city = :city AND street = :street";

    public HotelDAO() {
        super(Hotel.class);
    }

    public List<Hotel> findByName(String name) {
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(findByNameQuery);
            query.setParameter("name", name);

            return query.list();
        } catch (HibernateException e) {
            System.err.println("Search by name is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public List<Hotel> findByCity(String city) {
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(findByCityQuery);
            query.setParameter("city", city);

            return query.list();
        } catch (HibernateException e) {
            System.err.println("Search by city is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public Hotel findExistHotel(Hotel hotel) {
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(findExistHotelQuery);
            query.setParameter("name", hotel.getName());
            query.setParameter("country", hotel.getCountry());
            query.setParameter("city", hotel.getCity());
            query.setParameter("street", hotel.getStreet());

            return (Hotel) query.uniqueResult();
        } catch (HibernateException e) {
            System.err.println("Search by city is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }
}
