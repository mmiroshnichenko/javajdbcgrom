package hibernate.lesson4.service;

import hibernate.lesson4.DAO.OrderDAO;
import hibernate.lesson4.DAO.RoomDAO;
import hibernate.lesson4.DAO.UserDAO;
import hibernate.lesson4.entity.Hotel;
import hibernate.lesson4.entity.Order;
import hibernate.lesson4.entity.Room;
import hibernate.lesson4.entity.User;
import hibernate.lesson4.exceptions.BadRequestException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrderService {
    private static OrderDAO orderDAO = new OrderDAO();
    private static RoomDAO roomDAO = new RoomDAO();
    private static UserDAO userDAO = new UserDAO();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static Order bookRoom(long roomId, long userId, Date dateFrom, Date dateTo) throws Exception {
        Room room = roomDAO.findById(roomId);
        User user = userDAO.findById(userId);
        validateBookRoomParams(room, roomId, user, userId, dateFrom);

        long countDays = TimeUnit.DAYS.convert(dateTo.getTime() - dateFrom.getTime(), TimeUnit.MILLISECONDS);
        double moneyPaid = countDays * room.getPrice();

        Order order = new Order();
        order.setUserOrdered(user);
        order.setRoom(room);
        order.setDateFrom(dateFrom);
        order.setDateTo(dateTo);
        order.setMoneyPaid(moneyPaid);

        room.setDateAvailableFrom(dateTo);

        return orderDAO.saveOrderAndUpdateRoom(order, room);
    }

    public static void cancelReservation(long orderId) throws Exception {
        Order order = orderDAO.findById(orderId);
        validateCancelReservationParams(order);

        Date dateFrom = order.getDateFrom();
        Room room = order.getRoom();
        //я понимаю, что просто установить доступность комнаты в dateFrom от удаленного заказа - это не совсем верно
        //но тут возможно очень много вариантов, которые тоже будут отличаться от того как это сделано на реальных проектах
        //поэтому сделал этот простой вариант
        room.setDateAvailableFrom(dateFrom);

        orderDAO.deleteOrderUpdateRoom(order, room);
    }

    public static boolean existOrdersWithHotel(Hotel hotel) throws Exception {
        return orderDAO.findByHotel(hotel).size() > 0;
    }

    private static void validateBookRoomParams(Room room, long roomId, User user, long userId, Date dateFrom) throws BadRequestException {
        if (room == null) {
            throw new BadRequestException("Error: room with id:" + roomId + " does not exist");
        }

        if (dateFrom.compareTo(room.getDateAvailableFrom()) < 0) {
            throw new BadRequestException("Error: " + room + " is available from " + dateFormat.format(room.getDateAvailableFrom()));
        }

        if (user == null) {
            throw new BadRequestException("Error: user with id:" + userId + " does not exist");
        }
    }

    private static void validateCancelReservationParams(Order order) throws BadRequestException {
        if (order == null) {
            throw new BadRequestException("Error: order does not exist");
        }

        Date currentDate = new Date();
        if (currentDate.compareTo(order.getDateFrom()) >= 0) {
            throw new BadRequestException("Error: cancel time already expired for order " + order);
        }
    }
}
