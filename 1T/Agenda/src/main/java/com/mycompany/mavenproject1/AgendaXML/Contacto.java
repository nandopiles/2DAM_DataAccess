package com.mycompany.mavenproject1.AgendaXML;

import java.io.*;
import java.util.Objects;

//@author 7J
public class Contacto implements Serializable{

    String name, surname, phone;

    public Contacto(String name, String surname, String phone) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    //hashCode complementa el método equals, compara más rápido estructuras Hash
    //En estructuras Hash se mira primero el hashCode y luego el equals
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    //creamos un equals para comparar el HashSet por el teléfono,
    //para que no hayan 2 personas con el mismo número, ya que eso no puede ser
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contacto other = (Contacto) obj;
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        return true;
    }   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return "Name: " + this.name + "\nSurname: " + this.surname + "\nPhone: " + this.phone + "\n___________________\n";
    }
}
