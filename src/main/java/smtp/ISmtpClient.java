package main.java.smtp;

/**
 *
 * @author Michael Ruckstuhl et Adrien Peguiron
 * Interface smtp client
 */

import main.java.model.mail.Message;

import java.io.IOException;

public interface ISmtpClient {
    public void sendMessage(Message message) throws IOException;
}
