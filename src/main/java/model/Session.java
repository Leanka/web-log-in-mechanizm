package model;

import java.time.LocalDateTime;

public class Session {
    private int sessionTimeInSeconds = 60;
    private String id;
    private LocalDateTime expiration;

    public Session(String id) {
        this.id = id;
        this.expiration = setExpirationDate();
    }

    private LocalDateTime setExpirationDate(){
        return LocalDateTime.now().plusSeconds(sessionTimeInSeconds);
    }

    public void updateSessionExpirationDate(){
        this.expiration = setExpirationDate();
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Session{");
        sb.append("sessionTimeInSeconds=").append(sessionTimeInSeconds);
        sb.append(", id='").append(id).append('\'');
        sb.append(", expiration=").append(expiration);
        sb.append('}');
        return sb.toString();
    }
}
