package caesarcipher;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Laurindo
 */
public class CaesarCipher {

   private JFrame window;
   private JScrollPane scrollPane;
   private JPanel mainPanel, buttonPanel;
   private JTextArea text;
   private JButton buttonCipher, buttonDecipher;

   public CaesarCipher() {
      initialize();
   }

   private void initialize() {

      buttonCipher = new JButton("Cipher");
      buttonCipher.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            text.setText(cipher(text.getText()));
         }
      });
      buttonDecipher = new JButton("Decipher");
      buttonDecipher.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            text.setText(decipher(text.getText()));
         }
      });

      buttonPanel = new JPanel();
      buttonPanel.add(buttonCipher);
      buttonPanel.add(buttonDecipher);

      text = new JTextArea();
      text.setPreferredSize(new Dimension(300, 200));

      text.setLineWrap(true);

      scrollPane = new JScrollPane(text);
      scrollPane.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

      mainPanel = new JPanel();
      mainPanel.setLayout(new BorderLayout());

      mainPanel.add(scrollPane, BorderLayout.CENTER);

      window = new JFrame();
      window.setLayout(new BorderLayout());
      window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      window.add(mainPanel, BorderLayout.CENTER);
      window.add(buttonPanel, BorderLayout.SOUTH);

      window.setTitle("Caesar's Cipher");

   }

   private String cipher(String input) {
      char[] cs = input.toCharArray();
      String r = "";
      for (int i = 0; i < cs.length; i++) {
         int c = (int) cs[i];
         if (c != 10 && c != 13 && c != 32) {
            c += 3;
            if ((c > 122) || (c < 97 && c > 90)) {
               c = c - 26;
            }
         }
         r += (char) c;
      }
      return r;
   }

   private String decipher(String input) {
      char[] cs = input.toCharArray();
      String r = "";
      for (int i = 0; i < cs.length; i++) {
         int c = (int) cs[i];
         if (c != 10 && c != 13 && c != 32) {
            c -= 3;
            if ((c < 65) || (c < 97 && c > 90)) {
               c = c + 26;
            }
         }
         r += (char) c;
      }
      return r;
   }

   private void run() {
      window.pack();
      window.setVisible(true);
   }

   public static void main(String[] args) {
      CaesarCipher cc = new CaesarCipher();
      cc.run();
   }

}
