package hibernate.lesson4.service;

import hibernate.lesson4.DAO.HotelDAO;
import hibernate.lesson4.DAO.OrderDAO;
import hibernate.lesson4.DAO.RoomDAO;
import hibernate.lesson4.entity.Hotel;
import hibernate.lesson4.exceptions.BadRequestException;

import java.util.List;

public class HotelService {
    private static HotelDAO hotelDAO = new HotelDAO();
    private static RoomDAO roomDAO = new RoomDAO();
    private static OrderDAO orderDAO = new OrderDAO();

    public static Hotel addHotel(Hotel hotel) throws Exception {
        validate(hotel);

        return hotelDAO.save(hotel);
    }

    public static List<Hotel> findHotelByName(String name) throws Exception{
        return hotelDAO.findByName(name);
    }

    public static List<Hotel> findHotelByCity(String city) throws Exception {
        return hotelDAO.findByCity(city);
    }

    public static List<Hotel> getAllHotels() throws Exception {
        return hotelDAO.findAll();
    }

    public static void deleteHotel(long hotelId) throws Exception {
        Hotel hotel = hotelDAO.findById(hotelId);
        if (hotel == null) {
            throw new BadRequestException("Error: Hotel with id:" + hotelId + " does not exist in DB");
        }

        if (OrderService.existOrdersWithHotel(hotel)) {
            throw new BadRequestException("Error: cannot remove Hotel with id:" + hotelId + " for this room already exists orders");
        }

        hotelDAO.delete(hotel.getId());
    }

    private static void validate(Hotel hotel) throws BadRequestException {
        if (hotel.getName() == null || hotel.getName().isEmpty()) {
            throw new BadRequestException("Error: hotel Name is required");
        }

        if (hotel.getCountry() == null || hotel.getCountry().isEmpty()) {
            throw new BadRequestException("Error: hotel Country is required");
        }

        if (hotel.getCity() == null || hotel.getCity().isEmpty()) {
            throw new BadRequestException("Error: hotel City is required");
        }

        if (hotel.getStreet() == null || hotel.getStreet().isEmpty()) {
            throw new BadRequestException("Error: hotel Street is required");
        }

        if (hotelDAO.findExistHotel(hotel) != null) {
            throw new BadRequestException("Error: hotel already exist");
        }
    }
}
