package ua.skillsup;

import ua.skillsup.services.Human;
import ua.skillsup.services.JsonMapper;

import java.text.ParseException;
import java.time.LocalDate;

/**
 * Created by Honey on 16.09.2015.
 */
public class App {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ParseException {

        JsonMapper gson = new JsonMapper();
        String json = gson.toJson(new Human("Nastya", "Sokirko", "Programming", LocalDate.now()));

        System.out.println(json);

        JsonMapper newClass = new JsonMapper();

        System.out.println(newClass.fromJson(json, Human.class).toString());
    }

}
