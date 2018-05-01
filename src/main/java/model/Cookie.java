package model;

import java.util.Objects;

public class Cookie {
    private String id;
    private Integer userId;
    private String cookieCustomSettings;

    public Cookie(String id, Integer userId, String cookieCustomSettings) {
        this.id = id;
        this.userId = userId;
        this.cookieCustomSettings = cookieCustomSettings;
    }

    public String getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getCookieCustomSettings() {
        return cookieCustomSettings;
    }

    public void setCookieCustomSettings(String cookieCustomSettings) {
        this.cookieCustomSettings = cookieCustomSettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cookie)) return false;
        Cookie cookie = (Cookie) o;
        return Objects.equals(getId(), cookie.getId()) &&
                Objects.equals(getUserId(), cookie.getUserId()) &&
                Objects.equals(getCookieCustomSettings(), cookie.getCookieCustomSettings());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getUserId(), getCookieCustomSettings());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cookie: ");
        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        return sb.toString();
    }
}
