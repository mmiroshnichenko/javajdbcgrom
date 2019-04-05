package hibernate.lesson4.demo;

import hibernate.lesson4.controller.HotelController;
import hibernate.lesson4.controller.OrderController;
import hibernate.lesson4.controller.RoomController;
import hibernate.lesson4.controller.UserController;
import hibernate.lesson4.entity.Hotel;
import hibernate.lesson4.entity.Room;
import hibernate.lesson4.entity.User;
import hibernate.lesson4.entity.UserType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseDemo {
    protected static SimpleDateFormat dateFormat =  new SimpleDateFormat("dd-MM-yyyy");
    protected static User admin;
    protected static User user1;
    protected static User user2;

    protected static Hotel hotel1;
    protected static Hotel hotel2;
    protected static Hotel hotel3;
    protected static Hotel hotel4;
    protected static Hotel hotel5;
    protected static Hotel hotel6;

    protected static Room room1;
    protected static Room room2;
    protected static Room room3;
    protected static Room room4;
    protected static Room room5;
    protected static Room room6;
    protected static Room room7;
    protected static Room room8;
    protected static Room room9;
    protected static Room room10;
    protected static Room room11;
    protected static Room room12;
    protected static Room room13;
    protected static Room room14;
    protected static Room room15;
    protected static Room room16;
    protected static Room room17;
    protected static Room room18;
    protected static Room room19;
    protected static Room room20;
    protected static Room room21;
    protected static Room room22;
    protected static Room room23;
    protected static Room room24;

    protected static void fillData() throws Exception {
        admin = createUser("Admin", "123456", "Ukraine", UserType.ADMIN);
        user1 = createUser("User1", "789465", "Ukraine", UserType.USER);
        user2 = createUser("User2", "454654", "Ukraine", UserType.USER);

        UserController.login("Admin", "123456");

        hotel1 = createHotel("Hilton", "Ukraine", "Kiev", "Street 1");
        hotel2 = createHotel("Hilton", "USA", "New-York", "Street 2");
        hotel3 = createHotel("Hilton", "Great Britain", "London", "Street 3");
        hotel4 = createHotel("Libid", "Ukraine", "Kiev", "Street 4");
        hotel5 = createHotel("Edem", "Ukraine", "Lviv", "Street 5");
        hotel6 = createHotel("Relax", "Ukraine", "Lviv", "Street 6");

        room1 = createRoom(2, 100, true, false, dateFormat.parse("20-02-2019"), hotel1);
        room2 = createRoom(2, 120, true, true, dateFormat.parse("31-03-2019"), hotel1);
        room3 = createRoom(4, 200, true, true, dateFormat.parse("15-03-2019"), hotel1);
        room4 = createRoom(3, 150, false, true, dateFormat.parse("01-03-2019"), hotel1);

        room5 = createRoom(1, 50, true, false, dateFormat.parse("01-03-2019"), hotel2);
        room6 = createRoom(2, 110, true, false, dateFormat.parse("06-03-2019"), hotel2);
        room7 = createRoom(2, 120, true, true, dateFormat.parse("15-03-2019"), hotel2);
        room8 = createRoom(4, 180, true, false, dateFormat.parse("20-03-2019"), hotel2);

        room9 = createRoom(2, 80, true, false, dateFormat.parse("20-03-2019"), hotel3);
        room10 = createRoom(2, 100, true, true, dateFormat.parse("10-03-2019"), hotel3);
        room11 = createRoom(3, 110, false, false, dateFormat.parse("15-03-2019"), hotel3);
        room12 = createRoom(3, 150, true, true, dateFormat.parse("21-03-2019"), hotel3);

        room13 = createRoom(2, 40, true, false, dateFormat.parse("19-03-2019"), hotel4);
        room14 = createRoom(2, 60, true, true, dateFormat.parse("16-03-2019"), hotel4);
        room15 = createRoom(3, 80, true, false, dateFormat.parse("18-03-2019"), hotel4);
        room16 = createRoom(3, 120, true, true, dateFormat.parse("25-03-2019"), hotel4);

        room17 = createRoom(1, 40, false, false, dateFormat.parse("02-03-2019"), hotel5);
        room18 = createRoom(2, 60, true, false, dateFormat.parse("07-03-2019"), hotel5);
        room19 = createRoom(2, 70, true, true, dateFormat.parse("12-03-2019"), hotel5);
        room20 = createRoom(3, 100, true, false, dateFormat.parse("14-03-2019"), hotel5);

        room21 = createRoom(2, 80, true, false, dateFormat.parse("11-03-2019"), hotel6);
        room22 = createRoom(2, 90, true, true, dateFormat.parse("10-03-2019"), hotel6);
        room23 = createRoom(3, 110, true, true, dateFormat.parse("14-03-2019"), hotel6);
        room24 = createRoom(4, 150, true, false, dateFormat.parse("12-03-2019"), hotel6);

        OrderController.bookRoom(room3.getId(), user1.getId(), dateFormat.parse("16-03-2019"), dateFormat.parse("20-03-2019"));
    }
    
    private static User createUser(
            String userName,
            String password,
            String country,
            UserType userType) throws Exception {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setCountry(country);
        user.setUserType(userType);

        return UserController.registerUser(user);
    }
    
    private static Hotel createHotel(
            String name,
            String country,
            String city,
            String street) throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setCountry(country);
        hotel.setCity(city);
        hotel.setStreet(street);
        
        return HotelController.addHotel(hotel);
    }

    private static Room createRoom(
            int numberOfGuests,
            double price,
            boolean breakfastIncluded,
            boolean petsAllowed,
            Date dateAvailableFrom,
            Hotel hotel) throws Exception {
        Room room = new Room();
        room.setNumberOfGuests(numberOfGuests);
        room.setPrice(price);
        room.setBreakfastIncluded(breakfastIncluded);
        room.setPetsAllowed(petsAllowed);
        room.setDateAvailableFrom(dateAvailableFrom);
        room.setHotel(hotel);
        
        return RoomController.addRoom(room);
    }
}
