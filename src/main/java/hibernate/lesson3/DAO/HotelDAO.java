package hibernate.lesson3.DAO;

import hibernate.lesson3.Entity.Hotel;

public class HotelDAO extends BaseDAO<Hotel> {
    public HotelDAO() {
        super(Hotel.class);
    }
}
