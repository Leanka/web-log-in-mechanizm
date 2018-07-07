package controller;

import model.Session;

import java.time.LocalDateTime;


public class SessionController {

    public Session createNewSession(String cookieID){
        return new Session(cookieID);
    }

    public boolean checkIfSessionValid(Session session){
        return LocalDateTime.now().isBefore(session.getExpiration());
    }

    public void extendSessionExpiration(Session session){
        session.updateSessionExpirationDate();
    }

    public String getSessionId(Session session){
        return session.getId();
    }

}
