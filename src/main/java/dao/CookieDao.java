package dao;

import model.Cookie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CookieDao {
    private Connection connection;

    public CookieDao(Connection connection) {

        this.connection = connection;
    }

    public Cookie getUserCookie (int userId, String clientIp){
        Cookie cookie = null;
        String query = "SELECT * FROM cookies WHERE user_id = ? AND client_ip=?;";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, clientIp);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()) {
                String cookieId = resultSet.getString("id");
                String customSettings = resultSet.getString("cookie");

                cookie = new Cookie(cookieId, userId, customSettings, clientIp);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cookie;
    }

    public Cookie getCookie (String cookieId){
        Cookie cookie = null;
        String query = "SELECT * FROM cookies WHERE id = ?;";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cookieId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()) {
                Integer userId = resultSet.getInt("user_id");
                String customSettings = resultSet.getString("cookie");
                String clientIp = resultSet.getString("client_ip");

                cookie = new Cookie(cookieId, userId, customSettings, clientIp);
            }else {
                System.out.println("resultSet closed for id: " + cookieId);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cookie;
    }

    public void addCookie(Cookie cookie){
        String query = "INSERT INTO cookies VALUES(?, ?, ?, ?);";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, cookie.getId());
            preparedStatement.setInt(2, cookie.getUserId());
            preparedStatement.setString(3, cookie.getCookieCustomSettings());
            preparedStatement.setString(4, cookie.getClientIp());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCookieSettings(Cookie cookie){
        String query = "UPDATE cookies SET cookie=? WHERE id=?;";

        try{

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, cookie.getCookieCustomSettings());
            preparedStatement.setString(2, cookie.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeCookie(Integer userId, String clientIp){
        String query = "DELETE FROM cookies WHERE user_id=? AND client_ip=?;";

        try{

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, clientIp);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

