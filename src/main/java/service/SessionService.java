package service;

import controller.CookieController;
import controller.SessionController;
import model.Cookie;
import model.Session;

import java.util.HashMap;
import java.util.Map;

public class SessionService {
    private static Map<Cookie, Session> activeSessions = new HashMap<>();
    private CookieController cookieController;
    private SessionController sessionController;

    public SessionService(CookieController cookieController, SessionController sessionController) {
        this.cookieController = cookieController;
        this.sessionController = sessionController;
    }

    public void openNewSession(Integer userId, String clientIp){
        Cookie cookie = cookieController.getUserCookie(userId, clientIp);

        Session session = sessionController.createNewSession();
        activeSessions.put(cookie, session);
    }

    public void openNewSession(Cookie cookie){
        Session session = sessionController.createNewSession();
        activeSessions.put(cookie, session);
    }

    public boolean isUserSessionValid(String cookieId){

        boolean isSessionValid = false;
        Cookie cookie = cookieController.getCookie(cookieId);
        Session session = activeSessions.get(cookie);

        if(session != null){
            if(sessionController.checkIfSessionValid(session)){
                isSessionValid = true;
                extendSession(cookie);
            }else{
                deleteSession(cookie);
            }
        }
        showAllSessions();
        return isSessionValid;
    }

    private void showAllSessions(){ //test print to remove
        System.out.println("Active sessions:");
        for(Map.Entry<Cookie, Session> entry: activeSessions.entrySet()){
            System.out.println(entry.getKey() + " ---> " + entry.getValue());
        }
        System.out.println();
    }

    public void deleteSession(Cookie cookie){ // czy id usera?
        activeSessions.remove(cookie);
    }

    private void extendSession(Cookie cookie){
        Session session = activeSessions.get(cookie);
        sessionController.extendSessionExpiration(session);
    }

}
