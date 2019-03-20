package hibernate.lesson3;

import hibernate.lesson3.DAO.HotelDAO;
import hibernate.lesson3.DAO.RoomDAO;
import hibernate.lesson3.Entity.Hotel;
import hibernate.lesson3.Entity.Room;

import java.util.Date;

public class Demo {
    public static void main(String[] args) {
        HotelDAO hotelDAO = new HotelDAO();

        Hotel hotel1 = new Hotel();
        hotel1.setName("Hilton");
        hotel1.setCountry("Ukraine");
        hotel1.setCity("Kiev");
        hotel1.setStreet("Test street");
        hotel1 = hotelDAO.save(hotel1);
        System.out.println(hotel1);

        RoomDAO roomDAO = new RoomDAO();
        Room room1 = new Room();
        room1.setNumberOfGuests(2);
        room1.setPrice(200);
        room1.setBreakfastIncluded(1);
        room1.setPetsAllowed(0);
        room1.setDateAvailableFrom(new Date());
        room1.setHotel(hotel1);

        room1 = roomDAO.save(room1);
        System.out.println(room1);

        Room room2 = roomDAO.findById(1);
        System.out.println(room2);

        Hotel hotel2 = hotelDAO.findById(3);
        System.out.println(hotel2);

        room1.setNumberOfGuests(3);
        room1 = roomDAO.update(room1);

        System.out.println(room1);
        hotel1.setCity("London");
        hotel1 = hotelDAO.update(hotel1);
        System.out.println(hotel1);

        roomDAO.delete(room1.getId());
        hotelDAO.delete(hotel1.getId());
    }
}
