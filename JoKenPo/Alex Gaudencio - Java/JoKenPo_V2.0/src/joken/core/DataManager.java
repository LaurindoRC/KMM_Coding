package joken.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.filechooser.FileSystemView;

public class DataManager {

   private File file;
   Properties properties;

   public DataManager(String fileName) throws IOException {
      properties = new Properties();
      FileSystemView filesys = FileSystemView.getFileSystemView();
      filesys.getRoots();
      file = new File(filesys.getHomeDirectory() + fileName);
      if (file.exists()) {
         load();
      }
   }

   public void load() throws IOException {
      properties.load(new FileInputStream(file));
   }

   public void save() throws FileNotFoundException, IOException {
      properties.store(new FileOutputStream(file), null);
   }

   public void write(String propertyName, String value) {
      properties.setProperty(propertyName, value);
   }

   public void write(String propertyName, int value) {
      properties.setProperty(propertyName, String.valueOf(value));
   }

   public void write(String propertyName, float value) {
      properties.setProperty(propertyName, String.valueOf(value));
   }

   public void write(String propertyName, boolean value) {
      properties.setProperty(propertyName, String.valueOf(value));
   }

   public String read(String propertyName, String field) {
      return properties.getProperty(propertyName);
   }

   public int read(String propertyName, int field) {
      return Integer.valueOf(properties.getProperty(propertyName));
   }

   public float read(String propertyName, float field) {
      return Float.valueOf(properties.getProperty(propertyName));
   }

   public boolean read(String propertyName, boolean field) {
      return Boolean.valueOf(properties.getProperty(propertyName));
   }
}
