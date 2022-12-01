package utils;

/**
 *
 * @author Nando
 */
public class Separator {

    public Separator() {
    }

    public static void separator(int length) {
        for (int i = 0; i < (length * 2) - 14; i++) {
            System.out.print("=");
        }
        System.out.println("");
    }
}
