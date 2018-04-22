package controller;

import dao.CookieDao;
import model.Cookie;
import service.IdProvider;

public class CookieController {
    private CookieDao cookieDao;

    public CookieController(CookieDao cookieDao) {
        this.cookieDao = cookieDao;
    }

    public Cookie getUserCookie(Integer userId){
        return cookieDao.getUserCookie(userId);
    }

    public Cookie getCookie(String cookieId){
        return cookieDao.getCookie(cookieId);
    }

    public void createNewCookie(Integer userId){
        String cookieId = IdProvider.getId();
        cookieDao.addCookie(new Cookie(cookieId, userId, "cookieId=" + cookieId));
    }

    public void updateCookie(Cookie cookie){
        cookieDao.updateCookieSettings(cookie);
    }

    public void removeCookie(Integer userId){
        cookieDao.removeCookie(userId);
    }
}
