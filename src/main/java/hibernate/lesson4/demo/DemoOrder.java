package hibernate.lesson4.demo;

import hibernate.lesson4.controller.OrderController;
import hibernate.lesson4.controller.UserController;
import hibernate.lesson4.entity.Order;

public class DemoOrder extends BaseDemo {
    public static void main(String[] args) {
        try {
            fillData();

            for (Order elOrder : OrderController.getAllOrders()) {
                System.out.println(elOrder);
            }

            System.out.println("Add order for not authorized user");
            UserController.logout();
            try {
                OrderController.bookRoom(room1.getId(), user1.getId(), dateFormat.parse("16-03-2019"), dateFormat.parse("20-03-2019"));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            UserController.login("User1", "789465");
            System.out.println("Add order on already booked room");
            try {
                OrderController.bookRoom(room1.getId(), user1.getId(), dateFormat.parse("10-02-2019"), dateFormat.parse("20-03-2019"));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            System.out.println("Add order on not exist room");
            try {
                OrderController.bookRoom(room24.getId() + 21345, user1.getId(), dateFormat.parse("10-02-2019"), dateFormat.parse("20-03-2019"));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            System.out.println("Add order on not exist user");
            try {
                OrderController.bookRoom(room1.getId(), user2.getId() + 13245, dateFormat.parse("21-02-2019"), dateFormat.parse("20-03-2019"));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            System.out.println("Add valid order");
            Order newOrder = OrderController.bookRoom(room1.getId(), user2.getId(), dateFormat.parse("21-02-2019"), dateFormat.parse("20-03-2019"));

            for (Order elOrder : OrderController.getAllOrders()) {
                System.out.println(elOrder);
            }
            System.out.println("Cancel order");
            OrderController.cancelReservation(newOrder.getId());
            for (Order elOrder : OrderController.getAllOrders()) {
                System.out.println(elOrder);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
