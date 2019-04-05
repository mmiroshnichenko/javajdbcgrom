package hibernate.lesson4.controller;

import hibernate.lesson4.entity.User;
import hibernate.lesson4.service.UserService;

public class UserController {
    public static User registerUser(User user) throws Exception{
        return UserService.registerUser(user);
    }

    public static void login(String userName, String password) throws Exception {
        UserService.loginUser(userName, password);
    }

    public static void logout() {
        UserService.logoutUser();
    }
}
