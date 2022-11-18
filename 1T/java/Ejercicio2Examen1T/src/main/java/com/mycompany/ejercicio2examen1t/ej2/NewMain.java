package com.mycompany.ejercicio2examen1t;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Nando
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        //No modifiques el código de la función main
        String filePath = "archivo.xml";
        Grupo grupo7J = Grupo.getFakeGroup();

        System.out.println("Grupo de alumnos para serializar:");
        System.out.println(grupo7J.toString() + "\n");

        SerializeGroup(grupo7J, filePath);

        Grupo new7J = DeserializeGroup(filePath);

        System.out.println("Grupo de alumnos deserializado:");
        System.out.println(new7J.toString() + "\n");

    }

    public static void SerializeGroup(Grupo grupo7J, String filePath) throws FileNotFoundException {
        //Completa el código de esta función
        FileOutputStream fos = null;
        File f = new File(filePath);
        XStream flujox = new XStream();
        flujox.allowTypes(new Class[]{Grupo.class, Alumno.class}); //le doy acceso a esa clase

        try {
            flujox.toXML(grupo7J, fos = new FileOutputStream(f.getPath()));
            System.out.println("(+) Exporting Group");
        } catch (IOException e1) {
            System.out.println("(-) Error trying to export group\n");
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e1) {
                System.out.println("(-) Error relatet with the File\n");
            }
        }
    }

    public static Grupo DeserializeGroup(String filePath) throws FileNotFoundException {
        //Completa el código de esta función
        Grupo new7J = null;
        File f = new File(filePath);
        XStream flujox = new XStream();
        flujox.allowTypes(new Class[]{Grupo.class, Alumno.class}); //le doy acceso a esa clase

        if (f.exists()) {
            System.out.println("(+) Importing Group");
            new7J = (Grupo) flujox.fromXML(new File(f.getPath()));
        }
        return new7J;
    }
}
