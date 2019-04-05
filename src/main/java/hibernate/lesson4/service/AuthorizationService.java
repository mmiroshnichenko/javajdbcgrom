package hibernate.lesson4.service;

import hibernate.lesson4.entity.Session;
import hibernate.lesson4.entity.UserType;
import hibernate.lesson4.exceptions.AuthException;

public class AuthorizationService {
    public static void checkAdminPermissions() throws Exception {
        checkUserAuthorization();

        if (!Session.getUser().getUserType().equals(UserType.ADMIN.toString())) {
            throw new AuthException("Error: user is not admin");
        }
    }

    public static void checkUserAuthorization() throws Exception {
        if (Session.getUser() == null) {
            throw new AuthException("Error: user is not authorized");
        }
    }
}
