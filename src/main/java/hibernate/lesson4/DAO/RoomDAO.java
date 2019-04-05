package hibernate.lesson4.DAO;

import hibernate.lesson4.entity.Filter;
import hibernate.lesson4.entity.Hotel;
import hibernate.lesson4.entity.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO extends BaseDAO<Room> {
    public RoomDAO() {
        super(Room.class);
    }

    public List<Room> findByFilter(Filter filter) {
        try (Session session = createSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Room> criteriaQuery = builder.createQuery(Room.class);
            Root<Room> root = criteriaQuery.from(Room.class);
            List<Predicate> predicates = new ArrayList<>();
            if (filter.getNumberOfGuests() > 0) {
                predicates.add(builder.equal(root.get("numberOfGuests"), filter.getNumberOfGuests()));
            }
            if (filter.getPrice() > 0) {
                predicates.add(builder.equal(root.get("price"), filter.getPrice()));
            }
            if (filter.getBreakfastIncluded() != null) {
                predicates.add(builder.equal(root.get("breakfastIncluded"), filter.getBreakfastIncluded() ? 1 : 0));
            }
            if (filter.getPetsAllowed() != null) {
                predicates.add(builder.equal(root.get("petsAllowed"), filter.getPetsAllowed() ? 1 : 0));
            }
            if (filter.getDateAvailableFrom() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("dateAvailableFrom"), filter.getDateAvailableFrom()));
            }
            if (filter.getCountry() != null || filter.getCity() != null) {
                Join<Room, Hotel> hotelJoin = root.join("hotel");
                if (filter.getCountry() != null) {
                    predicates.add(builder.equal(hotelJoin.get("country"), filter.getCountry()));
                }
                if (filter.getCity() != null) {
                    predicates.add(builder.equal(hotelJoin.get("city"), filter.getCity()));
                }
            }

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
            Query<Room> query = session.createQuery(criteriaQuery);

            return query.getResultList();
        } catch (HibernateException e) {
            System.err.println("Search by name is failed");
            System.err.println(e.getMessage());
        }

        return null;
    }
}
