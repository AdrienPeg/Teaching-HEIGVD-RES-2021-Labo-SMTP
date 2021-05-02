package main.java.model.mail;

/**
 *
 * @author Michael Ruckstuhl et Adrien Peguiron
 *
 * Cette classe représente les personnes à pranker.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Personne {

    /**
     * Retourne le prénom
     * @return  le prénom
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retourne le nom
     * @return  le nom
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retourne l'adresse
     * @return  l'adresse'
     */
    public String getAddress() {
        return address;
    }

    private String firstName;
    private String lastName;
    private String address;

    /**
     * Constructeur
     * @param address de la personne
     */
    public Personne(String address){
        this.address = address;
        Pattern pattern = Pattern.compile("(.*)\\.(.*)@");
        Matcher matcher = pattern.matcher(address);
        boolean found = matcher.find();
        if(found){
            this.firstName = matcher.group(1);
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            this.lastName = matcher.group(2);
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
        }
    }
}