package main.java.model.prank;

import main.java.model.mail.Message;
import main.java.model.mail.Personne;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Prank {

    private Personne sender;
    private final List<Personne> victims = new ArrayList<>();
    private final List<Personne> witnesses = new ArrayList<>();
    private String message;

    public void setSender(Personne sender) {
        this.sender = sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addVictims(List<Personne> victims){
        this.victims.addAll(victims);
    }

    public void addWitnesses(List<Personne> witnesses){
        this.witnesses.addAll(witnesses);
    }

    public Message generateMailMessage(){
        Message msg = new Message();
        //on sépare le sujet du corps
        String[] content = message.split("\r\n",2);
        //on enlève le "subject:" du message
        String[] tmp = content[0].split(":", 2);
        //on sépare le sujet du body
        String[] subject = tmp[1].split("\n\n",2);
        msg.setSubject(subject[0] + "\r\n");
        //écriture du corps du message
        msg.setBody(subject[1] + "\r\n" + sender.getFirstName());
        //on destine le message aux victimes
        msg.setTo(victims.stream().map(Personne::getAddress).collect(Collectors.toList()).toArray(new String[]{}));
        //les témoins sont en cc
        msg.setCc(witnesses.stream().map(Personne::getAddress).collect(Collectors.toList()).toArray(new String[]{}));
        //on assigne l'envoyeur
        msg.setFrom(sender.getAddress());
        return msg;
    }
}
