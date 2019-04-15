package hibernate.lesson4.controller;

import hibernate.lesson4.entity.Hotel;
import hibernate.lesson4.service.AuthorizationService;
import hibernate.lesson4.service.HotelService;

import java.util.List;

public class HotelController {
    public static List<Hotel> findHotelByName(String name) throws Exception {
       return HotelService.findHotelByName(name);
    }

    public static List<Hotel> findHotelByCity(String city) throws Exception {
        return HotelService.findHotelByCity(city);
    }

    public static Hotel addHotel(Hotel hotel) throws Exception{
        AuthorizationService.checkAdminPermissions();

        return HotelService.addHotel(hotel);
    }

    public static void deleteHotel(long hotelId) throws Exception {
        AuthorizationService.checkAdminPermissions();

        HotelService.deleteHotel(hotelId);
    }
}
