package controller;

import dao.CookieDao;
import model.Cookie;
import service.IdProvider;

public class CookieController {
    private CookieDao cookieDao;

    public CookieController(CookieDao cookieDao) {
        this.cookieDao = cookieDao;
    }

    public Cookie getUserCookie(Integer userId, String clientIp){
        return cookieDao.getUserCookie(userId, clientIp);
    }

    public Cookie getCookie(String cookieId){
        return cookieDao.getCookie(cookieId);
    }

    public void createNewCookie(Integer userId, String clientIp){
        String cookieId = IdProvider.getId();
        cookieDao.addCookie(new Cookie(cookieId, userId, "cookieId=" + cookieId, clientIp));
    }

    public void updateCookie(Cookie cookie){
        cookieDao.updateCookieSettings(cookie);
    }

    public void removeCookie(Integer userId, String clientIp){
        cookieDao.removeCookie(userId, clientIp);
    }
}
