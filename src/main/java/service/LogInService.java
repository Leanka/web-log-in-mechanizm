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

    private void setDataBeforeFirstLogIn(Integer userId){
        if(cookieController.getUserCookie(userId) != null) { //in case user cleared cookies remove old cookie from db
            cookieController.removeCookie(userId);
        }
        cookieController.createNewCookie(userId);
        sessionService.openNewSession(userId);
    }

    public String firstLogIn(List<String> userData){
        Integer userId = userDataController.getUserId(userData.get(nameIndex), userData.get(passwordIndex));
        setDataBeforeFirstLogIn(userId);
        Cookie cookie = cookieController.getUserCookie(userId);
        return cookie.getId();
    }

    public void nextLogIn(String cookieId){
        if(!sessionService.isUserSessionValid(cookieId)){
            Cookie cookie = cookieController.getCookie(cookieId);

            sessionService.openNewSession(cookie);
        }
    }

    public boolean isCookieValidForCurrentUser(String cookieId, List<String> userData){
        Integer userId = userDataController.getUserId(userData.get(0), userData.get(1));
        Cookie browserCookie = cookieController.getCookie(cookieId);
        return browserCookie.getUserId() == userId;
    }

    public String getUsersCookieId(List<String> userData){
        Integer userId = userDataController.getUserId(userData.get(0), userData.get(1));
        Cookie cookie = cookieController.getUserCookie(userId);

        if(cookie == null){
            cookieController.createNewCookie(userId);
            cookie = cookieController.getUserCookie(userId);
        }
        return cookie.getId();
    }

    public String getUserName(String cookieId){
        Cookie cookie = cookieController.getCookie(cookieId);
        return userDataController.getUserName(cookie.getUserId());
    }

}
