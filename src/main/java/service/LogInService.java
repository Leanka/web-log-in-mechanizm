package service;

import controller.CookieController;
import controller.UserDataController;
import model.Cookie;

import java.util.List;

public class LogInService {
    private int nameIndex = 0;
    private int passwordIndex = 1;
    private UserDataController userDataController;
    private CookieController cookieController;
    private SessionService sessionService;

    public LogInService(UserDataController userDataController, CookieController cookieController, SessionService sessionService) {
        this.userDataController = userDataController;
        this.cookieController = cookieController;
        this.sessionService = sessionService;
    }

    public boolean validateUserData(List<String> userData){
        return userDataController.validateUserData(userData.get(nameIndex), userData.get(passwordIndex));
    }

    private String setDataBeforeFirstLogIn(Integer userId){
        Cookie cookie = cookieController.createNewCookie(userId);
        sessionService.openNewSession(cookie);
        return cookie.getId();
    }

    public String firstLogIn(List<String> userData){
        Integer userId = userDataController.getUserId(userData.get(nameIndex), userData.get(passwordIndex));
        return setDataBeforeFirstLogIn(userId);
    }

    public void nextLogIn(String cookieId){
        if(!sessionService.isUserSessionValid(cookieId)){
            Cookie cookie = cookieController.getCookie(cookieId);

            sessionService.openNewSession(cookie);
        }
    }

    public boolean isCookieValidForCurrentUser(String cookieId, List<String> userData){
        Integer userId = userDataController.getUserId(userData.get(nameIndex), userData.get(passwordIndex));
        Cookie browserCookie = cookieController.getCookie(cookieId);

        boolean isCookieValid = false;
        if(browserCookie != null) {
            isCookieValid = browserCookie.getUserId() == userId;
        }

        return  isCookieValid;
    }

    public  boolean isCookieValid(String cookieId){
        Cookie cookie = cookieController.getCookie(cookieId);
        return cookie != null;
    }

    public String getUserName(String cookieId){
        Cookie cookie = cookieController.getCookie(cookieId);
        return userDataController.getUserName(cookie.getUserId());
    }

}
