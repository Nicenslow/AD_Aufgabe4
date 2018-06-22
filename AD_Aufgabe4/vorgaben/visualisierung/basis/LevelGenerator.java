package visualisierung.basis;

import vorgaben.Zelle;

import java.awt.*;
import java.util.Iterator;

/**
 * Hilfsklasse, um Level zu generieren.
 * 
 * @author Philipp Jenke
 *
 */
public class LevelGenerator {

  /**
   * Erzeugt einen rechteckiegen Level.
   */
  public static Level generiereLevel(int breite, int hoehe) {
    Level level = new Level();

    Zelle[][] zellenCache = new Zelle[breite][hoehe];

    for (int i = 0; i < breite; i++) {
      for (int j = 0; j < hoehe; j++) {
        Zelle zelle = new Zelle(new Point(i, j));
        level.zelleHinzufuegen(zelle);
        zellenCache[i][j] = zelle;
      }
    }

    for (int i = 0; i < breite; i++) {
      for (int j = 0; j < hoehe; j++) {
        if (i < breite - 1) {
          if (i % 2 == 0) {
            if (j > 0) {
              zellenCache[i][j].setzeNachbar(Konstanten.Richtung.UHR_2,
                  zellenCache[i + 1][j - 1]);
            }
            zellenCache[i][j].setzeNachbar(Konstanten.Richtung.UHR_4,
                zellenCache[i + 1][j]);

          } else {
            zellenCache[i][j].setzeNachbar(Konstanten.Richtung.UHR_2,
                zellenCache[i + 1][j]);
            if (j < hoehe - 1) {
              zellenCache[i][j].setzeNachbar(Konstanten.Richtung.UHR_4,
                  zellenCache[i + 1][j + 1]);
            }
          }
        }
        if (j < hoehe - 1) {
          zellenCache[i][j].setzeNachbar(Konstanten.Richtung.UHR_6,
              zellenCache[i][j + 1]);
        }
      }
    }

    Iterator<Zelle> zellenIterator = level.getZellenIterator();

    Spielfigur spielfigur = new Spielfigur();
    spielfigur.setZelle(zellenIterator.next());
    level.setSpielfigur(spielfigur);
    
    SpielfigurNPC npc = new SpielfigurNPC();
    npc.setZelle(zellenIterator.next());
    level.setSpielfigurNPC(npc);

    return level;
  }

}
