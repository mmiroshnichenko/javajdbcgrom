package hibernate.lesson4.service;

import hibernate.lesson4.DAO.OrderDAO;
import hibernate.lesson4.DAO.RoomDAO;
import hibernate.lesson4.entity.Filter;
import hibernate.lesson4.entity.Room;
import hibernate.lesson4.exceptions.BadRequestException;

import java.util.List;

public class RoomService {
    private static RoomDAO roomDAO = new RoomDAO();
    private static OrderDAO orderDAO = new OrderDAO();

    public static Room addRoom(Room room) throws Exception {
        validate(room);

        return roomDAO.save(room);
    }

    public static List<Room> findRooms(Filter filter) {
        return roomDAO.findByFilter(filter);
    }

    public static void deleteRoomById(long roomId) throws Exception {
        Room room = roomDAO.findById(roomId);
        if (room == null) {
            throw new BadRequestException("Error: Room with id:" + roomId + " does not exist in DB");
        }
        if (orderDAO.findByRoom(room).size() > 0) {
            throw new BadRequestException("Error: cannot remove :" + room + ", already exists orders");
        }

        roomDAO.delete(roomId);
    }

    public static List<Room> getAllRooms() {
        return roomDAO.findAll();
    }

    private static void validate(Room room) throws BadRequestException {
        if(room.getNumberOfGuests() <= 0) {
            throw new BadRequestException("Error: incorrect room param: number of guests");
        }

        if(room.getPrice() <= 0) {
            throw new BadRequestException("Error: incorrect room param: price");
        }

        if(room.getDateAvailableFrom() == null) {
            throw new BadRequestException("Error: room param Date Available From is required");
        }

        if(room.getHotel() == null) {
            throw new BadRequestException("Error: room param Hotel is required");
        }
    }
}
