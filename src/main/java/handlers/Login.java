package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.util.*;

import controller.CookieController;
import controller.SessionController;
import controller.UserDataController;
import dao.CookieDao;
import dao.DbConnection;
import dao.UserDao;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import service.LogInService;
import service.LogOutService;
import service.SessionService;

public class Login implements HttpHandler {
    private LogInService logInService;
    private SessionService sessionService;
    private LogOutService logOutService;

    public Login() {
        Connection connection = DbConnection.getConnection();
        CookieController cookieController = new CookieController(new CookieDao(connection));
        UserDataController userDataController = new UserDataController(new UserDao(connection));
        SessionController sessionController = new SessionController();

        this.sessionService = new SessionService(cookieController, sessionController);

        this.logInService = new LogInService(userDataController, cookieController, sessionService);
        this.logOutService = new LogOutService(sessionService, cookieController);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");

        JtwigModel model = JtwigModel.newModel();
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/login.twig");

        if(method.equals("GET") && (cookieStr != null)){
            String cookieId = getCookieId(cookieStr);
            if (sessionService.isUserSessionValid(cookieId)) {
                template = JtwigTemplate.classpathTemplate("/templates/logout.twig");
                model.with("userName", logInService.getUserName(cookieId) );
            }
        }

        if (method.equals("POST")) {
            template = handleMethodPOST(httpExchange, template, model, cookieStr);
        }

        String response = template.render(model);
        sendResponse(httpExchange, response);
        System.out.println("From Local Address: " +  httpExchange.getLocalAddress()); ///ip:8007
        sessionService.showAllSessions();
        }

    private  void sendResponse(HttpExchange httpExchange, String response) throws IOException{
        byte [] byteResponse = response.getBytes("ISO-8859-2");

        httpExchange.sendResponseHeaders(200, byteResponse.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(byteResponse);
        os.close();
    }


    private JtwigTemplate handleMethodPOST(HttpExchange httpExchange, JtwigTemplate template, JtwigModel model, String cookieData) throws IOException {
        List<String> parsedFormData = getUserData(httpExchange);

        if (parsedFormData.get(0).equalsIgnoreCase("logout")) {
            logOutService.logOut(getCookieId(cookieData));

        }else if(logInService.validateUserData(parsedFormData)){ //if user login and password are valid
            template = JtwigTemplate.classpathTemplate("templates/logout.twig");
            model.with("userName", parsedFormData.get(0));
            logIn(httpExchange, parsedFormData, cookieData);
        }

        return template;
    }

    private void logIn(HttpExchange httpExchange, List<String> userData, String cookieData){
//        String clientIp = getClientIp(httpExchange);

        if(cookieData == null){ //if cookies were cleared, create new  //add handling login from new device
            String newCookieId = logInService.firstLogIn(userData);
            setResponseCookie(httpExchange, newCookieId);

        }else{
            String cookieId = getCookieId(cookieData);

            if(!logInService.isCookieValidForCurrentUser(cookieId, userData)){ //if current cookie does not belong to user, who is loggin in
                cookieId = logInService.getUsersCookieId(userData);
                setResponseCookie(httpExchange, cookieId);
            }

            logInService.nextLogIn(cookieId);
        }
    }

    private String getClientIp(HttpExchange httpExchange){
        return httpExchange.getLocalAddress().toString().replaceAll("/", "");
    }

    private void setResponseCookie(HttpExchange httpExchange, String cookieId){
        HttpCookie cookie = new HttpCookie("cookieId", cookieId);
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
    }


    private List<String> parseFormData(String formData) throws UnsupportedEncodingException{
        List<String> userData = new ArrayList<>();
        formData = URLDecoder.decode(formData, "ISO-8859-2");
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            String value = URLDecoder.decode(keyValue[1], "ISO-8859-2");
            userData.add(value.trim());
        }
        return userData;
    }

    private String getCookieId(String cookieData){
        int cookieIdIndex = 1;
        String [] data = cookieData.split("=");

        return data[cookieIdIndex].replaceAll("\"", "");
    }

    private List<String> getUserData(HttpExchange httpExchange) throws IOException{
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "ISO-8859-2");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        return parseFormData(formData);
    }

}

