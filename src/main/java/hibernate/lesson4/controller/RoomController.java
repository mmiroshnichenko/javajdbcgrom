package hibernate.lesson4.controller;

import hibernate.lesson4.entity.Filter;
import hibernate.lesson4.entity.Room;
import hibernate.lesson4.service.AuthorizationService;
import hibernate.lesson4.service.RoomService;

import java.util.List;

public class RoomController {
    public static List<Room> findRooms(Filter filter) throws Exception {
        return RoomService.findRooms(filter);
    }

    public static Room addRoom(Room room) throws Exception {
         AuthorizationService.checkAdminPermissions();

         return RoomService.addRoom(room);
    }

    public static void deleteRoom(long roomId) throws Exception {
        AuthorizationService.checkAdminPermissions();

        RoomService.deleteRoomById(roomId);
    }

    public static List<Room> getAllRooms() throws Exception {
        return RoomService.getAllRooms();
    }
}
