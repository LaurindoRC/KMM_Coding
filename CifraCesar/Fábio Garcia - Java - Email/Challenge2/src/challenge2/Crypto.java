package challenge2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author fabiog
 */
public class Crypto {

   private HashMap mapLetters;
   private int offset;

   public Crypto() {
      initMap();
      this.offset = 3;
   }

   public void setOffset(int offset) {
      this.offset = offset;
   }

   public int getOffset() {
      return offset;
   }

   private void initMap() {
      mapLetters = new HashMap();

      for (int i = 65; i < 91; i++) {
         mapLetters.put((char) i, (i - 64));
      }
   }

   public String encrypt(String text) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < text.length(); i++) {
         if (Character.isLetter(text.charAt(i))) {
            sb.append(getNextLetter(String.valueOf(text.charAt(i))));
         } else {
            sb.append(String.valueOf(text.charAt(i)));
         }
      }
      return new String(sb);
   }

   public String decrypt(String text) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < text.length(); i++) {
         if (Character.isLetter(text.charAt(i))) {
            sb.append(getPriorLetter(String.valueOf(text.charAt(i))));
         } else {
            sb.append(String.valueOf(text.charAt(i)));
         }
      }
      return new String(sb);
   }

   private Integer getIndexLetter(String letter) {
      Iterator it = mapLetters.entrySet().iterator();
      while (it.hasNext()) {
         Map.Entry pair = (Map.Entry) it.next();
         if (pair.getKey().toString().equals(letter.toUpperCase())) {
            return (Integer) pair.getValue();
         }
      }
      return -1;
   }

   private String getLetterIndex(int index) {
      Iterator it = mapLetters.entrySet().iterator();
      while (it.hasNext()) {
         Map.Entry pair = (Map.Entry) it.next();
         if ((Integer) pair.getValue() == index) {
            return String.valueOf(pair.getKey());
         }
      }
      return "";
   }

   private String getNextLetter(String letter) {
      int index = getIndexLetter(letter) + getOffset();
      return getLetterIndex((index > 26) ? (index - 26) : (index));

   }

   private String getPriorLetter(String letter) {
      int index = getIndexLetter(letter) - getOffset();
      return getLetterIndex((index <= 0) ? (26 - (index * -1)) : (index));
   }
}
