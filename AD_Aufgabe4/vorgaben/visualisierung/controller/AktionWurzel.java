package visualisierung.controller;

import visualisierung.basis.Konstanten.Befehl;

import java.util.List;

/**
 * Startpunkt des Entscheidungsbaumes für die Aktionen des Spielers.
 * 
 * @author Philipp Jenke
 */
public class AktionWurzel implements Aktion {

  /**
   * Diese Aktion wird bei GEHE verwendet.
   */
  private AktionGehe aktionGehe = new AktionGehe();

  /**
   * Diese Aktion wird bei BEKAEMPFE verwendet.
   */
  private AktionBekaempfe aktionBekamepfe = new AktionBekaempfe();

  /**
   * Diese Aktion wird bei RETTE verwendet.
   */
  private AktionRette aktionRette = new AktionRette();

  @Override
  public String verarbeite(List<Befehl> befehlskette) {
    if (befehlskette.size() == 0) {
      return "Ungültige Befehlskette.";
    }

    switch (befehlskette.get(0)) {
    case GEHE:
      befehlskette.remove(0);
      return aktionGehe.verarbeite(befehlskette);
    case BEKAEMPFE:
      befehlskette.remove(0);
      return aktionBekamepfe.verarbeite(befehlskette);
    case RETTE:
      befehlskette.remove(0);
      return aktionRette.verarbeite(befehlskette);
    default:
      return "Ungültiges Kommando an dieser Stelle: " + befehlskette.get(0);
    }
  }

}
