package Imagine;

import java.io.*;
import java.util.*;

//@author Nando
public class DinamicDictionary {

    public static void main(String[] args) {
        //declaración variables
        File directory = new File("Diccionario");
        File fich = new File("Imagine.txt");

        deleteDirectory(directory);
        makeDirectory(directory);
        readLine(fich);
        noRepetition(directory);
    }

    //método recursivo para que no se repitan las palabras
    public static void noRepetition(File carpeta) {
        File[] files = carpeta.listFiles();
        HashSet<String> list = new HashSet<String>();

        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    noRepetition(f);
                } else {
                    list = readFile(f); //método que lee el file y lo almacena en un HashSet returneado
                    writeFile(list, f); //método que escribe el HashSet en un nuevo file
                }
            }
        }
        carpeta.delete(); //borra el directorio grande
    }

    public static HashSet<String> readFile(File f) {
        BufferedReader br = null;
        HashSet<String> list = new HashSet<String>();
        String text;

        try {
            br = new BufferedReader(new FileReader(f));
            text = br.readLine();
            while (text != null) { //mintras el texto leído no sea null...
                list.add(text); //irá añadiendo las palabras al HashSet
                text = br.readLine();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch(IOException e1) {
                e1.printStackTrace();
            }
        }

        return list; //returnea el HashSet con todas las palabras del fichero, Sin Repetir
    }

    public static void writeFile(HashSet<String> list, File temp) {
        String name;
        File f;
        BufferedWriter bw = null;

        //borramos el fichero guardando su nombre
        name = temp.getName();
        temp.delete();
        //creamos un file vacío con el mismo nombre 
        f = new File("Diccionario/" + name);
        try {
            //pasamos la info del HashSet al nuevo file
            bw = new BufferedWriter(new FileWriter(f, true));
            /*Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                bw.write(it + "\n");
            }*/
            for(String word : list) {
                bw.write(word + "\n");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    //método recursivo para borrar el Directorio con su contenido
    public static void deleteDirectory(File carpeta) {
        File[] files = carpeta.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteDirectory(f);
                } else {
                    f.delete();
                }
            }
        }
        carpeta.delete(); //borra el directorio grande
    }

    public static void makeDirectory(File carpeta) {
        if (!carpeta.exists()) {
            if (carpeta.mkdirs()) {
                System.out.println("> Directory created");
            } else {
                System.out.println("> Error...");
            }
        }
    }

    public static void readLine(File fichero) {
        BufferedReader br = null;
        String text;
        String[] words;

        try {
            br = new BufferedReader(new FileReader(fichero));
            text = br.readLine();
            while (text != null) {
                words = readWord(text);
                readChar(words);
                text = br.readLine();
            }
        } catch (FileNotFoundException e1) {
            e1.getMessage();
        } catch (IOException e2) {
            e2.getMessage();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static String[] readWord(String texto) {
        String[] words = texto.split("\\s+"); //(espacio)

        return words;
    }

    public static void readChar(String[] words) {
        char c;
        String com;

        for (int i = 0; i < words.length; i++) {
            com = words[i];
            if (!com.isBlank()) { // sirve para "", " " y null
                c = words[i].charAt(0);
                checkLetter(com, c);
            }
        }
    }

    public static void checkLetter(String word, char c) {
        BufferedWriter bw = null;
        //PrintWriter pw = null;

        String car = String.valueOf(c); //convertir char en String        
        File f = new File("Diccionario/" + car.toUpperCase() + ".txt");

        try {
            bw = new BufferedWriter(new FileWriter(f, true));
            //pw = new PrintWriter(bw);
            bw.write(word + "\n");
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
