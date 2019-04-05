package hibernate.lesson4.demo;

import hibernate.lesson4.controller.RoomController;
import hibernate.lesson4.controller.UserController;
import hibernate.lesson4.entity.Filter;
import hibernate.lesson4.entity.Room;

public class DemoRoom extends BaseDemo {
    public static void main(String[] args) {
        try {
            fillData();

            System.out.println("all rooms");
            for (Room elRoom : RoomController.getAllRooms()) {
                System.out.println(elRoom);
            }

            System.out.println("add correct room");
            Room room = new Room();
            room.setNumberOfGuests(2);
            room.setPrice(100);
            room.setBreakfastIncluded(true);
            room.setPetsAllowed(false);
            room.setDateAvailableFrom(dateFormat.parse("20-02-2019"));
            room.setHotel(hotel1);

            RoomController.addRoom(room);

            System.out.println("delete room which does not exist");
            try {
                RoomController.deleteRoom(room24.getId() + 12313);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            System.out.println("delete room. Orders already exists");
            try {
                RoomController.deleteRoom(room3.getId());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            System.out.println("delete room by User");
            UserController.logout();
            UserController.login("User1", "789465");
            try {
                RoomController.deleteRoom(room1.getId());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            UserController.logout();
            UserController.login("Admin", "123456");
            RoomController.deleteRoom(room1.getId());

            for (Room elRoom : RoomController.getAllRooms()) {
                System.out.println(elRoom);
            }

            System.out.println("Find rooms by city");
            Filter filter1 = new Filter();
            filter1.setCity("Lviv");

            for (Room elRoom : RoomController.findRooms(filter1)) {
                System.out.println(elRoom);
            }

            System.out.println("Find rooms by country");
            Filter filter2 = new Filter();
            filter2.setCountry("Ukraine");
            for (Room elRoom : RoomController.findRooms(filter2)) {
                System.out.println(elRoom);
            }

            System.out.println("Find rooms by country, city, number of guests, pets allowed, price");
            Filter filter3 = new Filter();
            filter3.setCountry("Ukraine");
            filter3.setCity("Kiev");
            filter3.setNumberOfGuests(2);
            filter3.setPetsAllowed(true);
            filter3.setPrice(120);
            for (Room elRoom : RoomController.findRooms(filter3)) {
                System.out.println(elRoom);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
