package hibernate.lesson4.demo;

import hibernate.lesson4.controller.UserController;
import hibernate.lesson4.entity.Session;
import hibernate.lesson4.entity.User;
import hibernate.lesson4.entity.UserType;

public class DemoUser extends BaseDemo {
    public static void main(String[] args) {
        try {
            fillData();

            try {
                System.out.println("User name already exist");
                User existUser = new User();
                existUser.setUserName("User2");
                existUser.setPassword("46464645");
                existUser.setCountry("Ukraine");
                existUser.setUserType(UserType.USER);
                UserController.registerUser(existUser);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            User user = new User();
            user.setUserName("User3");
            user.setPassword("46464645");
            user.setCountry("Ukraine");
            user.setUserType(UserType.USER);
            UserController.registerUser(user);

            try {
                System.out.println("Login not existing user");
                UserController.login("User4", "454646rrsdf");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            UserController.login("User3", "46464645");

            System.out.println(Session.getUser());

            UserController.logout();

            System.out.println(Session.getUser());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
