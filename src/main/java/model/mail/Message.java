package main.java.model.mail;

/**
 *
 * @author Michael Ruckstuhl et Adrien Peguiron
 *
 * Cette classe permets de modifier ou récupérer certaines parties spécifiques d'un mail. Ces parties sont le sujet, le corps, l'envoyeur, les destinataire et les copies carbones.
 */

public class Message {

    private String from;
    private String[] to = new String[0];
    private String[] cc = new String[0];
    private String subject;
    private String body;

    /**
     *
     * @return la source
     */
    public String getFrom() {
        return from;
    }

    /**
     *
     * @return les destinataires
     */
    public String[] getTo() {
        return to;
    }

    /**
     *
     * @return les copies
     */
    public String[] getCc() {
        return cc;
    }

    /**
     * Set la source
     * @param from la source
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Set les destinataires
     * @param to les destinataires
     */
    public void setTo(String[] to) {
        this.to = to;
    }

    /**
     * set les copies
     * @param cc les copies
     */
    public void setCc(String[] cc) {
        this.cc = cc;
    }

    /**
     *
     * @return le sujet
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set le sujet
     * @param subject le sujet
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     *
     * @return le corp du message
     */
    public String getBody() {
        return body;
    }

    /**
     * Set le corp du message
     * @param body le corp du message
     */
    public void setBody(String body) {
        this.body = body;
    }
}
