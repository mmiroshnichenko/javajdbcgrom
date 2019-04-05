package hibernate.lesson4.demo;

import hibernate.lesson4.controller.HotelController;
import hibernate.lesson4.controller.UserController;
import hibernate.lesson4.entity.Hotel;

public class DemoHotel extends BaseDemo {
    public static void main(String[] args) {
        try {
            fillData();

            for (Hotel hotel : HotelController.getAllHotels()) {
                System.out.println(hotel);
            }

            System.out.println("add already exist hotel");
            try {
                Hotel hotel = new Hotel();
                hotel.setName("Relax");
                hotel.setCountry("Ukraine");
                hotel.setCity("Lviv");
                hotel.setStreet("Street 6");
                HotelController.addHotel(hotel);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            System.out.println("delete hotel which does not exist");
            try {
                HotelController.deleteHotel(hotel6.getId() + 12454);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            System.out.println("delete hotel. Orders already exists");
            try {
                HotelController.deleteHotel(hotel1.getId());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            System.out.println("delete hotel by User");
            UserController.logout();
            UserController.login("User1", "789465");
            try {
                HotelController.deleteHotel(hotel4.getId());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            UserController.logout();
            UserController.login("Admin", "123456");
            HotelController.deleteHotel(hotel4.getId());

            for (Hotel hotel : HotelController.getAllHotels()) {
                System.out.println(hotel);
            }

            System.out.println("find by name");
            for (Hotel hotel : HotelController.findHotelByName("Hilton")) {
                System.out.println(hotel);
            }

            System.out.println("find by city");
            for (Hotel hotel : HotelController.findHotelByCity("Lviv")) {
                System.out.println(hotel);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
