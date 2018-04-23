package service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IdProvider {
    private static SecureRandom random = new SecureRandom();
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*_+-/";

    public static String getId(){
        return generateId();
    }

    private static String generateId(){
        List<Character> characters = pickRandomCharacters();
        return characters.stream()
                        .map(character -> character.toString())
                        .collect(Collectors.joining());
    }

    private static Character getRandomCharacter(String tokens){
        int charIndex = random.nextInt(tokens.length());
        return tokens.charAt(charIndex);
    }

    private static List<Character> pickRandomCharacters(){
        List<Character> characters = new ArrayList<>();

        int loop = 2;
        do{
            characters.add(getRandomCharacter(ALPHA));
            characters.add(getRandomCharacter(ALPHA.toUpperCase()));
            characters.add(getRandomCharacter(NUMERIC));
            characters.add(getRandomCharacter(SPECIAL_CHARS));
        }while (--loop > 0);

        Collections.shuffle(characters);

        return characters;
    }

}
