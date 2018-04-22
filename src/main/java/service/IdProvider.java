package service;

public class IdProvider {
    private static int counter = 0;

    public static String getId(){
        return "placeholderCounterNr" + counter++;
    }

}
