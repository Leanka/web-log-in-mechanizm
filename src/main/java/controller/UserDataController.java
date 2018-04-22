package controller;

import dao.UserDao;

public class UserDataController {
    private UserDao userDao;

    public UserDataController(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean validateUserData(String name, String password){
        boolean areDataValid = false;
        if(userDao.getUserId(name, password) != null){
            areDataValid = true;
        }
        return areDataValid;
    }

    public Integer getUserId(String name, String password){
        return userDao.getUserId(name, password);
    }

    public String getUserName(Integer userId){
        return userDao.getUserName(userId);
    }
}
