package hibernate.lesson4.controller;

import hibernate.lesson4.entity.Order;
import hibernate.lesson4.service.AuthorizationService;
import hibernate.lesson4.service.OrderService;

import java.util.Date;
import java.util.List;

public class OrderController {
    public static Order bookRoom(long roomId, long userId, Date dateFrom, Date dateTo) throws Exception {
        AuthorizationService.checkUserAuthorization();

        return OrderService.bookRoom(roomId, userId, dateFrom, dateTo);
    }

    public static void cancelReservation(long orderId) throws Exception {
        AuthorizationService.checkUserAuthorization();

        OrderService.cancelReservation(orderId);
    }

    public static List<Order> getAllOrders() throws Exception {
        return OrderService.getAllOrders();
    }
}
