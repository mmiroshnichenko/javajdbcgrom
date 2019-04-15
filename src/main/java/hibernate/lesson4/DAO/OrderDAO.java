package hibernate.lesson4.DAO;

import hibernate.lesson4.entity.Hotel;
import hibernate.lesson4.entity.Order;
import hibernate.lesson4.entity.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDAO extends BaseDAO<Order> {
    private static final String findByHotelQuery = "FROM Order WHERE room.hotel = :hotel";
    private static final String findByRoomQuery = "FROM Order WHERE room = :room";

    public OrderDAO() {
        super(Order.class);
    }

    public Order saveOrderAndUpdateRoom(Order order, Room room) throws HibernateException {
        Transaction tr = null;
        try(Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();

            tr.begin();

            session.save(order);
            session.update(room);

            tr.commit();

            return order;
        } catch (HibernateException e) {
            if (tr != null) {
                tr.rollback();
            }
            throw new HibernateException("Save is order or update room failed");
        }
    }

    public void deleteOrderUpdateRoom(Order order, Room room) throws HibernateException {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            session.delete(order);
            session.update(room);

            tr.commit();
        } catch (HibernateException e) {
            if (tr != null) {
                tr.rollback();
            }
            throw new HibernateException("Delete is failed");
        }
    }

    public List<Order> findByHotel(Hotel hotel) {
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(findByHotelQuery);
            query.setParameter("hotel", hotel);

            return query.list();
        } catch (HibernateException e) {
            System.err.println("Search by hotel is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public List<Order> findByRoom(Room room) {
        try (Session session = createSessionFactory().openSession()) {
            Query query = session.createQuery(findByRoomQuery);
            query.setParameter("room", room);

            return query.list();
        } catch (HibernateException e) {
            System.err.println("Search by room is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }
}
