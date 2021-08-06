package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Anton", "Shirshov", (byte) 24);
        userService.saveUser("Oleg", "Petrov", (byte) 19);
        userService.saveUser("Vlad", "Sidorov", (byte) 25);
        userService.saveUser("Vadim", "Popov", (byte) 58);
        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
