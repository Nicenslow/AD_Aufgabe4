package visualisierung.basis;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

/**
 * Laden und speichern von Leveln.
 * 
 * @author Philipp Jenke
 */
public class LevelIO {

  /**
   * Level in Datei speichern (eigenes JSON-Format).
   */
  public static void levelSpeichern(Level level, String pfad) {
    try (FileWriter file = new FileWriter(pfad)) {
      file.write(level.toJson(null).toJSONString());
      file.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Erfolgreich Level in " + pfad + " gespeichert.");
  }

  /**
   * Level aus Datei laden (eigenes JSON-Format)
   */
  public static Level levelLaden(String dateiname) {
    FileReader dateiReader;
    try {
      dateiReader = new FileReader(dateiname);
      return levelLaden(dateiReader);
    } catch (FileNotFoundException e1) {
      System.out.println("Fehler beim Einlesen der Level-Datei " + dateiname);
      return null;
    }
  }

  /**
   * Level aus InputStream laden (eigenes JSON-Format)
   */
  public static Level levelLaden(InputStream stream) {
    Reader dateiReader = null;
    dateiReader = new InputStreamReader(stream);
    return levelLaden(dateiReader);
  }

  /**
   * Level aus Reader laden.
   */
  public static Level levelLaden(Reader dateiReader) {
    Level level = new Level();
    JSONParser parser = new JSONParser();
    try {
      Object obj = parser.parse(dateiReader);
      JSONObject levelObject = (JSONObject) obj;
      level.fromJson(levelObject, null);
      System.out.println("Level erfolgreich aus Datei gelesen.");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (org.json.simple.parser.ParseException e) {
      e.printStackTrace();
    }

    return level;
  }
}
