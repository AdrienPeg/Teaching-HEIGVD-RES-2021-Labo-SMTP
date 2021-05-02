/**
 *
 * @author Michael Ruckstuhl et Adrien Peguiron
 *
 * Cette classe permet d'envoyer les mails de prank avec le protocole SMTP puis de fermer la connexion SMTP. SmtpClient implémente l'interface ISmtpClient.
 */

package main.java.smtp;

import main.java.model.mail.Message;

import java.io.*;
import java.net.Socket;

public class SmtpClient implements ISmtpClient {

    private String smtpServerAddress;
    private int smtpServerPort;

    private PrintWriter writer;
    private BufferedReader reader;

    /**
     * Constructeur
     *
     * @param smtpServerAddress adresse du serveur
     * @param port              numéro de port
     */
    public SmtpClient(String smtpServerAddress, int port) {
        this.smtpServerAddress = smtpServerAddress;
        this.smtpServerPort = port;
    }

    @Override
    public void sendMessage(Message message) throws IOException {

        Socket socket = new Socket(smtpServerAddress, smtpServerPort);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

        // Lecture de connexion
        String line = reader.readLine();
        System.out.println(line);

        // Envoie du EHLO
        writer.write("EHLO localhost");
        nextLine();
        line = reader.readLine();
        System.out.println(line);

        // Lecture des 250- jusqu'à 250
        if (!line.startsWith("250")) {
            throw new IOException("error: " + line);
        }
        while (line.startsWith("250-")) {
            line = reader.readLine();
            System.out.println(line);
        }

        // Source du Mail
        writer.write("MAIL FROM: ");
        writer.write(message.getFrom());
        nextLine();

        readAndPrintLine();

        // Destinations du mail
        rcptTo(message.getTo());
        rcptTo(message.getCc());

        // Les données du mail
        writer.write("DATA");
        nextLine();

        readAndPrintLine();

        // format
        writer.write("Content-Type: text/plain; charset=\"utf-8\"");
        writer.write("\r\n");

        // source
        writer.write("From: " + message.getFrom());
        writer.write("\r\n");

        // A qui
        destination("To: ", message.getTo());

        // Copie
        destination("Cc: ", message.getCc());

        // Copie cachée

        // Sujet
        writer.write("Subject: " + message.getSubject());

        // Fin en-tête
        writer.write("\r\n");
        nextLine();

        // Message
        writer.write(message.getBody());
        writer.write("\r\n");
        writer.write(".");
        nextLine();
        readAndPrintLine();

        // QUIT
        writer.write("QUIT");
        nextLine();

        close(socket);
    }

    /**
     * Destinations dans les en-têtes
     *
     * @param s  to/cc/bcc
     * @param to tableau des destinataires
     */
    private void destination(String s, String[] to) {
        writer.write(s + to[0]);
        for (int i = 1; i < to.length; i++) {
            writer.write(", " + to[i]);
        }
        writer.write("\r\n");
    }

    /**
     * Lit la ligne suivante et l'imprime
     *
     * @throws IOException
     */
    private void readAndPrintLine() throws IOException {
        String line;
        line = reader.readLine();
        System.out.println(line);
    }

    /**
     * Ferme le writer, le reader et le socket
     *
     * @param socket le sockest à fermer
     * @throws IOException
     */
    private void close(Socket socket) throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }

    /**
     * Fait un saut de ligne et un flush
     */
    private void nextLine() {
        writer.write("\r\n");
        writer.flush();
    }

    /**
     * Lit un tableau de personne pour les envoyer comme destinataires
     *
     * @param to2 Le tableau de personne
     * @throws IOException
     */
    private void rcptTo(String[] to2) throws IOException {
        String line;
        for (String to : to2) {
            writer.write("RCPT TO: ");
            writer.write(to);
            nextLine();
            readAndPrintLine();
        }
    }
}
