package Agenda;

import java.io.*;
import java.util.*;

//@author 7J
public class Agenda {

    static Scanner eb = new Scanner(System.in);
    static Scanner fr = new Scanner(System.in);
    static HashSet<Contacto> contacts = new HashSet<Contacto>();

    public static void main(String[] args) {
        int opc;

        importar();
        do {
            opc = menu();
            switch (opc) {
                case 1:
                    showContacts();
                    break;
                case 2:
                    addContact();
                    break;
                case 3:
                    save();
                    break;
            }
        } while (opc != 3);
    }

    public static void importar() {
        ObjectInputStream ois = null;
        File f = new File("Contacts.bin");
        Contacto aux;

        try {
            if (f.exists()) {
                System.out.println("(+) Importing Contacts...");
            }
            ois = new ObjectInputStream(new FileInputStream(f));
            aux = (Contacto) ois.readObject();
            while (aux != null) {
                contacts.add(aux); //añadimos el contacto a la lista
                aux = (Contacto) ois.readObject();
            }            
        } catch (IOException e1) {
            
        } catch (ClassNotFoundException e2) {
            System.out.println("(-) Class Not Found...\n");
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e1) {
                System.out.println("(-) Error...\n");
            }
        }
    }

    public static void save() {
        ObjectOutputStream oos = null;
        File f = new File("Contacts.bin");

        try {
            oos = new ObjectOutputStream(new FileOutputStream(f));
            for (Contacto c : contacts) {
                oos.writeObject(c);
            }
        } catch (IOException e1) {
            System.out.println("(-) Error...\n");
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e1) {
                System.out.println("(-) Error...\n");
            }
        }
    }

    public static boolean checkPhone(String phone) {
        boolean result = false;

        try {
            Integer.parseInt(phone);
            result = true;
        } catch (NumberFormatException e1) {
            result = false;
        }

        return result;
    }

    public static void addContact() {
        Contacto c;
        String name, surname, phone;
        boolean number = false;

        System.out.print("> Name: ");
        name = eb.nextLine();
        System.out.print("> Surname: ");
        surname = eb.nextLine();
        do {
            System.out.print("> Phone: ");
            phone = eb.nextLine();
            number = checkPhone(phone);
            if (phone.length() != 9 || number == false) {
                System.out.println("(-) Wrong Phone\n");
            }
        } while (phone.length() != 9 || number == false);

        c = new Contacto(name, surname, phone);
        try {
            if (contacts.add(c)) {
                System.out.println("(+) Contact Added\n");
            } else {
                System.out.println("(-) Existing Number...\n");
            }
        } catch (Exception e) {
            System.out.println("(-) Error...\n");
        }
    }

    public static void showContacts() {
        for (Contacto c : contacts) {
            System.out.println(c.toString());
        }
    }

    public static int menu() {
        int opc = 0;

        try {
            do {
                System.out.println("1. Show all contacts\n2. Add new contact\n3. Save and exit");
                opc = fr.nextInt();
                if (opc < 1 || opc > 3) {
                    System.out.println("(-) Not valid...\n");
                }
            } while (opc < 1 || opc > 3);
        } catch (Exception e) {
            System.out.println("(-) Error...\n");
            fr.nextLine(); //vaciar el Buffered para que te permita elegir una opción correctamente
        }
        return opc;
    }
}
