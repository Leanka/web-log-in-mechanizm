package service;

import controller.CookieController;
import model.Cookie;

public class LogOutService {
    private SessionService sessionService;
    private CookieController cookieController;

    public LogOutService(SessionService sessionService, CookieController cookieController) {
        this.sessionService = sessionService;
        this.cookieController = cookieController;
    }

    public void logOut(String cookieId){
        Cookie cookie = cookieController.getCookie(cookieId);
        sessionService.deleteSession(cookie);
    }
}
