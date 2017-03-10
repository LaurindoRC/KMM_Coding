package challenge2;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author fabiog
 */
public class Main {

   private Timer timer;
   private TimerTask timerTask;
   private final Email email;

   public Main() {
      this.email = new Email();
   }

   public void init() {
      timer = new Timer("readMail");
      timerTask = new TimerTask() {
         @Override
         public void run() {
            email.read();
         }
      };

      timer.schedule(timerTask, 0, 5000);
   }

   public static void main(String[] args) {
      new Main().init();
   }
}
