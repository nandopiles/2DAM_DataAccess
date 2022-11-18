package Imagine;

import java.io.*;

//@author Nando

public class Dictionary {

    public static void main(String[] args) {
        //declaración variables
        File directory = new File("Diccionario");
        File fich = new File("Imagine.txt");

        deleteDirectory(directory);
        makeDirectory(directory);
        readLine(fich);
    }
    
    //método recursivo para borrar el Directorio con su contenido
    public static void deleteDirectory(File carpeta) {
        File[] files = carpeta.listFiles();
        
        if(files != null) {
            for(File f: files) {
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
                checkLetter(words[i], c);
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
