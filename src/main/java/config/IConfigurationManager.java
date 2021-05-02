package main.java.config;

/**
 *
 * @author Michael Ruckstuhl et Adrien Peguiron
 *
 * Interface pour ConfigurationManager
 */

import main.java.model.mail.Personne;

import java.util.List;

public interface IConfigurationManager {
    public List<Personne> getVictims();
    public List<String> getMessages();
    public int getNbGroups();
    public String getSmtpServerAddress();
    public int getSmtpServerPort();
    public List<Personne> getWitnesses();
}
