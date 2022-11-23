package code;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Nando
 */
public class Email {

    /**
     * Method used for checking the syntax of the email
     *
     * @param email The email that has to be checked
     * @return If the email is Correct or not
     */
    public static Boolean checkEmail(String email) {
        Pattern pattern = null;
        Matcher matcher = null;

        pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+"); //(MinusText NO SPECIAL CHAR) @ (MinusText NO SPECIAL CHAR) . MinusText NO SPECIAL CHAR
        matcher = pattern.matcher(email);
        if (matcher.find() == false) {
            System.out.println("(-) Incorrect EMAIL Format");
            return true;
        }
        return false;
    }
}
