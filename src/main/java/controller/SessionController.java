package controller;

import model.Session;
import service.IdProvider;

import java.time.LocalDateTime;


public class SessionController {

    public Session createNewSession(){
        return new Session(IdProvider.getId());
    }

    public boolean checkIfSessionValid(Session session){
        return LocalDateTime.now().isBefore(session.getExpiration());
    }

    public void extendSessionExpiration(Session session){
        session.updateSessionExpirationDate();
    }

}
