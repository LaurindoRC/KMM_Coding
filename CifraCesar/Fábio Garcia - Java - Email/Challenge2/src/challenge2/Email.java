package challenge2;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author fabiog
 */
public class Email {

   private final String userName = "cryptokmm@gmail.com";
   private final String password = "kmm@2017";
   private Properties properties;
   private Session session;
   private Crypto crypto;

   public Email() {
      configProperties();
      configSession();
      this.crypto = new Crypto();
   }

   private void configProperties() {
      properties = new Properties();
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.starttls.enable", "true");
      properties.put("mail.smtp.host", "smtp.gmail.com");
      properties.put("mail.smtp.port", "587");
   }

   private void configSession() {
      this.session = Session.getDefaultInstance(properties);
   }

   public void read() {
      try {
         Store store = session.getStore("imaps");
         store.connect("smtp.gmail.com", userName, password);

         Folder inbox = store.getFolder("inbox");
         inbox.open(Folder.READ_WRITE);

         Message[] messages = inbox.getMessages();
         for (Message message : messages) {
            sendMail(message);
         }
         inbox.close(true);
         store.close();

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private void sendMail(Message m) throws Exception {
      if (m.getSubject().toLowerCase().trim().startsWith("rules")) {
         sendRules(m);
      } else if (m.getSubject().toUpperCase().trim().contains("SILVIO_SANTOS")) {
         if (m.getSubject().toUpperCase().trim().contains("[C]")) {
            sendCript(m);
         } else if (m.getSubject().toUpperCase().trim().contains("[D]")) {
            sendDecrypt(m);
         }
      }
      m.setFlag(Flags.Flag.DELETED, true);
   }

   private void sendRules(Message m) throws MessagingException {
      MimeMessage sendMessage = new MimeMessage(session);
      sendMessage.setFrom(new InternetAddress(userName));
      for (Address from : m.getFrom()) {
         sendMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(from.toString().trim()));
      }

      sendMessage.setSubject("Regras de uso");
      sendMessage.setText(ChallengeUtil.getRules());

      Transport transport = session.getTransport("smtp");
      transport.connect("smtp.gmail.com", userName, password);
      transport.sendMessage(sendMessage, sendMessage.getAllRecipients());
      transport.close();
   }

   private void sendCript(Message m) throws Exception {

      MimeMessage sendMessage = new MimeMessage(session);
      sendMessage.setFrom(new InternetAddress(userName));
      for (Address from : m.getFrom()) {
         sendMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(from.toString().trim()));
      }

      sendMessage.setSubject("Criptografado: " + m.getSubject());
      crypto.setOffset(getOffset(m));
      sendMessage.setText(crypto.encrypt(getText(m)));

      Transport transport = session.getTransport("smtp");
      transport.connect("smtp.gmail.com", userName, password);
      transport.sendMessage(sendMessage, sendMessage.getAllRecipients());
      transport.close();

   }

   private void sendDecrypt(Message m) throws Exception {
      MimeMessage sendMessage = new MimeMessage(session);
      sendMessage.setFrom(new InternetAddress(userName));
      for (Address from : m.getFrom()) {
         sendMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(from.toString().trim()));
      }

      sendMessage.setSubject("Descriptografado: " + m.getSubject());
      crypto.setOffset(getOffset(m));
      sendMessage.setText(crypto.decrypt(getText(m)));

      Transport transport = session.getTransport("smtp");
      transport.connect("smtp.gmail.com", userName, password);
      transport.sendMessage(sendMessage, sendMessage.getAllRecipients());
      transport.close();
   }

   private int getOffset(Message message) throws Exception {
      try {
         return Integer.valueOf(message.getSubject().replace("SILVIO_SANTOS", "").replace("[", "").replace("]", "").substring(1, 2));
      } catch (NumberFormatException e) {
         return 3;
      } catch (Exception e) {
         throw e;
      }
   }

   private String getText(Message message) throws Exception {
      String result = "";
      if (message.isMimeType("text/plain")) {
         result = message.getContent().toString();
      } else if (message.isMimeType("multipart/*")) {
         MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
         result = getTextFromMimeMultipart(mimeMultipart);
      }

      result = result.substring(result.indexOf("{") + 1, result.lastIndexOf("}"));
      return result.replace("}", "");
   }

   private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
      String result = "";
      int count = mimeMultipart.getCount();
      for (int i = 0; i < count; i++) {
         BodyPart bodyPart = mimeMultipart.getBodyPart(i);
         if (bodyPart.isMimeType("text/plain")) {
            result = result + "\n" + bodyPart.getContent();
            break;
         } else if (bodyPart.isMimeType("text/html")) {
            String html = (String) bodyPart.getContent();
            result = result + "\n" + html;
         } else if (bodyPart.getContent() instanceof MimeMultipart) {
            result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
         }
      }
      return result;
   }
}
