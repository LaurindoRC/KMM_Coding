/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jkrypto;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Rafael Penczkoski
 */
public class FormJKrypto extends javax.swing.JFrame {

   private final String alphabet = "abcdefghijklmnopqrstuvwxyz";
   private final String capsAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

   private final ArrayList<Double> frPT = new ArrayList<Double>(Arrays.asList(14.811279, 1.051562, 3.691005, 5.094306, 12.775915, 0.980317, 1.210767,
           1.463604, 5.898956, 0.371862, 0.005934, 3.058580, 4.665052, 4.909355,
           10.633738, 2.429875, 1.226371, 6.736397, 7.917757, 4.174107, 4.473234,
           1.705391, 0.005142, 0.215266, 0.027841, 0.466387)) {
      @Override
      public String toString() {
         return "Português";
      }
   };

   private final ArrayList<Double> frEN = new ArrayList<Double>(Arrays.asList(8.167, 1.492, 2.782, 4.253, 12.70, 2.228, 2.015,
           6.094, 6.966, 0.153, 0.772, 4.025, 2.406, 6.749,
           7.507, 1.929, 0.095, 5.987, 6.327, 9.056, 2.758,
           0.978, 2.360, 0.150, 1.974, 0.074)) {
      @Override
      public String toString() {
         return "Inglês";
      }
   };

   private final ArrayList<Double> frFR = new ArrayList<Double>(Arrays.asList(7.636, 0.901, 3.260, 3.669, 14.715, 1.066, 0.866,
           0.737, 7.529, 0.545, 0.049, 5.456, 2.968, 7.095,
           5.378, 3.021, 1.362, 6.553, 7.948, 7.244, 6.311,
           1.628, 0.114, 0.387, 0.308, 0.136)) {
      @Override
      public String toString() {
         return "Francês";
      }
   };

   private final ArrayList<Double> frAL = new ArrayList<Double>(Arrays.asList(6.51, 1.89, 3.06, 5.08, 17.40, 1.66, 3.01,
           4.76, 7.55, 0.27, 1.21, 3.44, 2.53, 9.78,
           2.51, 0.79, 0.02, 7.00, 7.27, 6.15, 4.35,
           0.67, 1.89, 0.03, 0.04, 1.13)) {
      @Override
      public String toString() {
         return "Alemão";
      }
   };

   private final ArrayList<Double> frES = new ArrayList<Double>(Arrays.asList(12.53, 1.42, 4.68, 5.86, 13.68, 0.69, 1.01,
           0.70, 6.25, 0.44, 0.01, 4.97, 3.15, 6.71,
           8.68, 2.51, 0.88, 6.87, 7.98, 4.63, 3.93,
           0.90, 0.02, 0.22, 0.90, 0.52)) {
      @Override
      public String toString() {
         return "Espanhol";
      }
   };

   private final ArrayList<Double> frIT = new ArrayList<Double>(Arrays.asList(11.74, 0.92, 4.5, 3.73, 11.79, 0.95, 1.64,
           1.54, 11.28, 0.01, 0.01, 6.51, 2.51, 6.88,
           9.83, 3.05, 0.51, 6.37, 4.98, 5.62, 3.01,
           2.10, 0.01, 0.01, 0.01, 0.49)) {
      @Override
      public String toString() {
         return "Italiano";
      }
   };

   private final ArrayList<ArrayList<Double>> langFrequences = new ArrayList<ArrayList<Double>>(Arrays.asList(frPT, frEN, frFR, frAL, frES, frIT));

   /**
    * Creates new form FormJKrypto
    */
   public FormJKrypto() {
      initComponents();
      configure();
   }

   private String encrypt(String mensagem, int key) {
      StringBuilder builder = new StringBuilder();
      boolean crypto;
      for (int i = 0; i < mensagem.length(); i++) {
         crypto = false;
         for (int j = 0; j < alphabet.length(); j++) {
            if (mensagem.charAt(i) == alphabet.charAt(j)) {
               builder.append(alphabet.charAt((j + key) % alphabet.length()));
               crypto = true;
               break;
            }
            if (mensagem.charAt(i) == capsAlphabet.charAt(j)) {
               builder.append(capsAlphabet.charAt((j + key) % capsAlphabet.length()));
               crypto = true;
               break;
            }
         }
         if (!crypto) {
            builder.append(mensagem.charAt(i));
         }
      }
      return builder.toString();
   }

   private String decrypt(String mensagem, int key) {
      StringBuilder builder = new StringBuilder();
      boolean decryto;
      for (int i = 0; i < mensagem.length(); i++) {
         decryto = false;
         for (int j = 0; j < alphabet.length(); j++) {
            if (mensagem.charAt(i) == alphabet.charAt(j)) {
               if ((j - key) >= 0) {
                  builder.append(alphabet.charAt((j - key) % alphabet.length()));
               } else {
                  builder.append(alphabet.charAt((j - key + alphabet.length()) % alphabet.length()));
               }
               decryto = true;
               break;
            }
            if (mensagem.charAt(i) == capsAlphabet.charAt(j)) {
               if ((j - key) >= 0) {
                  builder.append(capsAlphabet.charAt((j - key) % capsAlphabet.length()));
               } else {
                  builder.append(capsAlphabet.charAt((j - key + capsAlphabet.length()) % capsAlphabet.length()));
               }
               decryto = true;
               break;
            }
         }
         if (!decryto) {
            builder.append(mensagem.charAt(i));
         }
      }
      return builder.toString();
   }

   private void configure() {
      jTextAreaMensagem.setLineWrap(true);
      jTextAreaMensagem.setWrapStyleWord(true);

      jButtonEncrypt.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent ae) {
            int randomKey = ThreadLocalRandom.current().nextInt(1, 26);
            jTextAreaMensagem.setText(encrypt(jTextAreaMensagem.getText(), randomKey));
            jTextAreaLog.setText("");
         }
      });

      jButtonDecrypt.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent ae) {
            jTextAreaMensagem.setText(tryDecrypt(jTextAreaMensagem.getText()));
         }
      });

      this.setTitle("Ultimate Krypto (KMM + Crypto)");
      this.setSize(new Dimension(800, 730));
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
      this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
   }

   private ArrayList<Double> getFrequence(String mensagem) {
      Integer scope = 0;
      ArrayList<Double> frequence = new ArrayList<Double>();
      ArrayList<Integer> ocurrences = new ArrayList<Integer>(1);
      for (int i = 0; i < alphabet.length(); i++) {
         ocurrences.add(i, 0);
         frequence.add(i, 0.0);
      }
      for (int i = 0; i < mensagem.length(); i++) {
         for (int j = 0; j < alphabet.length(); j++) {
            if (mensagem.charAt(i) == alphabet.charAt(j) || mensagem.charAt(i) == capsAlphabet.charAt(j)) {
               ocurrences.set(j, ocurrences.get(j) + 1);
               scope++;
            }
         }
      }
      for (int i = 0; i < alphabet.length(); i++) {
         frequence.set(i, Double.valueOf(ocurrences.get(i)) / Double.valueOf(scope));
      }
      return frequence;
   }

   private double compareFrequence(ArrayList<Double> frLanguage, ArrayList<Double> fr) {
      Double result = 0.0;
      for (int i = 0; i < alphabet.length(); i++) {
         result += Math.pow((fr.get(i) - frLanguage.get(i)), 2) / frLanguage.get(i);
      }
      return result;
   }

   private String tryDecrypt(String mensagem) {
      ArrayList<Double> keysRating = new ArrayList<Double>();
      for (int i = 0; i < alphabet.length(); i++) {
         keysRating.add(i, compareFrequence(frEN, getFrequence(decrypt(mensagem, i)))); //utiliza sempre francês pois é a mais acertiva
      }
      Double maxValue = Collections.max(keysRating);
      Integer key = 0;
      for (int i = 0; i < alphabet.length(); i++) {
         if (keysRating.get(i) <= maxValue) {
            maxValue = keysRating.get(i);
            key = i;
         }
      }

      String log
              = "Chave de criptografia utilizada: " + key;
      jTextAreaLog.setText(log);
      return decrypt(mensagem, key);
   }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jLabelMensagem = new javax.swing.JLabel();
      jScrollPaneMensagem = new javax.swing.JScrollPane();
      jTextAreaMensagem = new javax.swing.JTextArea();
      jButtonEncrypt = new javax.swing.JButton();
      jButtonDecrypt = new javax.swing.JButton();
      jScrollPaneLog = new javax.swing.JScrollPane();
      jTextAreaLog = new javax.swing.JTextArea();

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

      jLabelMensagem.setText("Mensagem:");

      jTextAreaMensagem.setColumns(20);
      jTextAreaMensagem.setRows(5);
      jScrollPaneMensagem.setViewportView(jTextAreaMensagem);

      jButtonEncrypt.setText("Criptografar!");

      jButtonDecrypt.setText("Descriptografar!");

      jTextAreaLog.setEditable(false);
      jTextAreaLog.setColumns(20);
      jTextAreaLog.setRows(2);
      jScrollPaneLog.setViewportView(jTextAreaLog);

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jScrollPaneMensagem)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(jLabelMensagem)
                  .addGap(0, 0, Short.MAX_VALUE))
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                  .addGap(0, 179, Short.MAX_VALUE)
                  .addComponent(jButtonEncrypt)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jButtonDecrypt))
               .addComponent(jScrollPaneLog, javax.swing.GroupLayout.Alignment.TRAILING))
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabelMensagem)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPaneMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPaneLog, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jButtonEncrypt)
               .addComponent(jButtonDecrypt))
            .addContainerGap())
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

   /**
    * @param args the command line arguments
    */
   public static void main(String args[]) {
      /* Set the Nimbus look and feel */
      //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
      /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
       */
      try {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
               javax.swing.UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (ClassNotFoundException ex) {
         java.util.logging.Logger.getLogger(FormJKrypto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(FormJKrypto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(FormJKrypto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(FormJKrypto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new FormJKrypto().setVisible(true);
         }
      });
   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButtonDecrypt;
   private javax.swing.JButton jButtonEncrypt;
   private javax.swing.JLabel jLabelMensagem;
   private javax.swing.JScrollPane jScrollPaneLog;
   private javax.swing.JScrollPane jScrollPaneMensagem;
   private javax.swing.JTextArea jTextAreaLog;
   private javax.swing.JTextArea jTextAreaMensagem;
   // End of variables declaration//GEN-END:variables
}
