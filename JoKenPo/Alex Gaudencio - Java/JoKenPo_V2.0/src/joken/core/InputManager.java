package joken.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener {

   static protected int KEY_RELEASED = 0;
   static protected int KEY_JUST_PRESSED = 1;
   static protected int KEY_PRESSED = 2;
   static private InputManager instance;
   private HashMap<Integer, Integer> keyCache;
   private ArrayList<Integer> pressedKeys;
   private ArrayList<Integer> releasedKeys;
   static protected int MOUSE_RELEASED = 0;
   static protected int MOUSE_JUST_PRESSED = 1;
   static protected int MOUSE_PRESSED = 2;
   private HashMap<Integer, Integer> mouseCache;
   private ArrayList<Integer> pressedButtons;
   private ArrayList<Integer> releasedButtons;

   private int mouseX;
   private int mouseY;
   private int mouseScroll;

   private InputManager() {
      keyCache = new HashMap<Integer, Integer>();
      pressedKeys = new ArrayList<Integer>();
      releasedKeys = new ArrayList<Integer>();
      mouseCache = new HashMap<Integer, Integer>();
      pressedButtons = new ArrayList<Integer>();
      releasedButtons = new ArrayList<Integer>();
   }

   static public InputManager getInstance() {
      if (instance == null) {
         instance = new InputManager();
      }
      return instance;
   }

   public boolean isPressed(int keyId) {
      return keyCache.containsKey(keyId)
              && !keyCache.get(keyId).equals(KEY_RELEASED);
   }

   public boolean isJustPressed(int keyId) {
      return keyCache.containsKey(keyId)
              && keyCache.get(keyId).equals(KEY_JUST_PRESSED);
   }

   public boolean isReleased(int keyId) {
      return !keyCache.containsKey(keyId)
              || keyCache.get(keyId).equals(KEY_RELEASED);
   }

   public boolean isMousePressed(int buttonId) {
      return mouseCache.containsKey(buttonId)
              && !mouseCache.get(buttonId).equals(MOUSE_RELEASED);
   }

   public boolean isMouseJustPressed(int buttonId) {
      return mouseCache.containsKey(buttonId)
              && mouseCache.get(buttonId).equals(MOUSE_JUST_PRESSED);
   }

   public boolean isMouseReleased(int buttonId) {
      return !mouseCache.containsKey(buttonId)
              || mouseCache.get(buttonId).equals(MOUSE_RELEASED);
   }

   public void update() {
      for (Integer keyCode : keyCache.keySet()) {
         if (isJustPressed(keyCode)) {
            keyCache.put(keyCode, KEY_PRESSED);
         }
      }
      for (Integer keyCode : releasedKeys) {
         keyCache.put(keyCode, KEY_RELEASED);
      }
      for (Integer keyCode : pressedKeys) {
         if (isReleased(keyCode)) {
            keyCache.put(keyCode, KEY_JUST_PRESSED);
         } else {
            keyCache.put(keyCode, KEY_PRESSED);
         }
      }
      pressedKeys.clear();
      releasedKeys.clear();

      for (Integer buttonCode : mouseCache.keySet()) {
         if (isMouseJustPressed(buttonCode)) {
            mouseCache.put(buttonCode, MOUSE_PRESSED);
         }
      }
      for (Integer buttonCode : releasedButtons) {
         mouseCache.put(buttonCode, MOUSE_RELEASED);
      }
      for (Integer buttonCode : pressedButtons) {
         if (isMouseReleased(buttonCode)) {
            mouseCache.put(buttonCode, MOUSE_JUST_PRESSED);
         } else {
            mouseCache.put(buttonCode, MOUSE_PRESSED);
         }
      }
      pressedButtons.clear();
      releasedButtons.clear();
   }

   public void keyTyped(KeyEvent e) {
      // Rotina não utilizada. Evento de tecla teclada.
   }

   public void keyPressed(KeyEvent e) {
      pressedKeys.add(e.getKeyCode());
   }

   public void keyReleased(KeyEvent e) {
      releasedKeys.add(e.getKeyCode());
   }

   public void mouseClicked(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
      // Chamado quando um botão do mouse é pressionado.
      pressedButtons.add(e.getButton());
   }

   public void mouseReleased(MouseEvent e) {
      // Chamado quando um botão do mouse é solto.
      releasedButtons.add(e.getButton());
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mouseDragged(MouseEvent e) {
      // Chamado quando o mouse é movido com o botão pressionado.
      mouseX = e.getX();
      mouseY = e.getY();
   }

   public void mouseMoved(MouseEvent e) {
      // Chamado quando o mouse é movido.
      setMouseX(e.getX());
      setMouseY(e.getY());
   }

   /**
    * @return the mouseX
    */
   public int getMouseX() {
      return mouseX;
   }

   /**
    * @param mouseX the mouseX to set
    */
   private void setMouseX(int mouseX) {
      this.mouseX = mouseX;
   }

   /**
    * @return the mouseY
    */
   public int getMouseY() {
      return mouseY;
   }

   /**
    * @param mouseY the mouseY to set
    */
   private void setMouseY(int mouseY) {
      this.mouseY = mouseY;
   }

   /**
    * @return the mouseScroll
    */
   public int getMouseScroll() {
      return mouseScroll;
   }

   /**
    * @param mouseScroll the mouseScroll to set
    */
   public void setMouseScroll(int mouseScroll) {
      this.mouseScroll = mouseScroll;
   }
}
