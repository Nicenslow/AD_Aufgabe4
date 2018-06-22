package visualisierung.controller;

import visualisierung.basis.Konstanten;

import java.util.List;

/**
 * Knoten im Aktionsbaum.
 * 
 * @author Philipp Jenke
 */
public interface Aktion {

  /**
   * Verarbeitung des nächsten Befehls in der Befehlskette.
   * 
   * @return Liefert das Ergebnis der Verarbeitung.
   */
  public String verarbeite(List<Konstanten.Befehl> befehlskette);

}
