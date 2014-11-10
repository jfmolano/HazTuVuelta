/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidadesHTV;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author ArquiDalgos
 */
public class SendEmail extends Thread {

    private final String correo;
    private final String esperaEstimada;
    private final int turnosFaltantes;

    public SendEmail(String correo, String esperaEstimada, int turnosFaltantes) {

        this.correo = correo;
        this.esperaEstimada = esperaEstimada;
        this.turnosFaltantes = turnosFaltantes;
    }

    @Override
    public void run() {
        final String user = "arquidalgos@hotmail.com";//change accordingly  
        final String password = "AlanTuring";//change accordingly  

        String sDirectorioTrabajo = System.getProperty("user.dir");
        System.out.println("--------------------------------------------------");
        System.out.println("El directorio de trabajo es " + sDirectorioTrabajo);
        //String to="jose_suarez94@hotmail.com";//change accordingly  

        //Get the session object  
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.live.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session;
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        //Compose the message  
        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress("jf.molano1587@uniandes.edu.co"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("jf.calderon1377@uniandes.edu.co"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("jm.suarez201@uniandes.edu.co"));

            MimeMultipart content = new MimeMultipart();
            MimeBodyPart bodyPart = new MimeBodyPart();
            if ( turnosFaltantes == -2){
                bodyPart.setText(esperaEstimada);
                
            }
            else if (turnosFaltantes == -1) {
                bodyPart.setText("Hola,\n Haz Tu Vuelta te avisa que faltan aproximadamente " + esperaEstimada + " "
                        + "para que sea tu turno.");
            } else {
                bodyPart.setText("Hola,\n Haz Tu Vuelta te avisa que faltan aproximadamente " + esperaEstimada + " "
                        + "para que sea tu turno. Faltan " + turnosFaltantes + " para atenderte. ");
            }
            content.addBodyPart(bodyPart);

            message.setContent(content);
            message.setSubject("Haz Tu Vuelta");

            //send the message  
            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
