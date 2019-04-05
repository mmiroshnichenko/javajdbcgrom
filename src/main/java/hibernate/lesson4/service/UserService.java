package hibernate.lesson4.service;

import hibernate.lesson4.DAO.UserDAO;
import hibernate.lesson4.entity.Session;
import hibernate.lesson4.entity.User;
import hibernate.lesson4.exceptions.AuthException;
import hibernate.lesson4.exceptions.BadRequestException;

public class UserService {
    private static UserDAO userDAO = new UserDAO();

    public static User registerUser(User user) throws Exception {
        validate(user);

        return userDAO.save(user);
    }

    public static User loginUser(String userName, String password) throws Exception {
        User user = userDAO.findByUserNameAndPassword(userName, password);
        if (user == null) {
            throw new AuthException("Error: incorrect userName or password");
        }

        Session.addUser(user);

        return user;
    }

    public static void logoutUser() {
        Session.clear();
    }

    private static void validate(User user) throws Exception {
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            throw new BadRequestException("Error: user Name is required");
        }

        if (userDAO.findByUserName(user.getUserName()) != null) {
            throw new BadRequestException("Error: " + user.getUserName() + " already registered");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new BadRequestException("Error: user Password is required");
        }

        if (user.getCountry() == null || user.getCountry().isEmpty()) {
            throw new BadRequestException("Error: user Country is required");
        }

        if (user.getUserType() == null) {
            throw new BadRequestException("Error: user Type is required");
        }
    }
}
