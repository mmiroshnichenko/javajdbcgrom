package hibernate.lesson3.DAO;

import hibernate.lesson3.Entity.Room;

public class RoomDAO extends BaseDAO<Room> {
    public RoomDAO() {
        super(Room.class);
    }
}
