package visualisierung.controller;

import visualisierung.basis.Konstanten.Befehl;
import visualisierung.basis.Konstanten.Richtung;

import java.util.HashSet;
import java.util.Set;

/**
 * Gemeinsame Basisklasse für alle Aktionen, die mit Richtungen zu tun haben.
 * 
 * @author Philipp Jenke
 */
public abstract class RichtungsAktion implements Aktion {

  protected Set<Befehl> richtungsBefehle = new HashSet<Befehl>();

  public RichtungsAktion() {
    richtungsBefehle.add(Befehl.UHR_0);
    richtungsBefehle.add(Befehl.UHR_2);
    richtungsBefehle.add(Befehl.UHR_4);
    richtungsBefehle.add(Befehl.UHR_6);
    richtungsBefehle.add(Befehl.UHR_8);
    richtungsBefehle.add(Befehl.UHR_10);
  }

  /**
   * Wandelt einen Richtungs-Befehl in eine Richtung um.
   */
  protected Richtung befehl2Richtung(Befehl befehl) {
    switch (befehl) {
    case UHR_0:
      return Richtung.UHR_0;
    case UHR_2:
      return Richtung.UHR_2;
    case UHR_4:
      return Richtung.UHR_4;
    case UHR_6:
      return Richtung.UHR_6;
    case UHR_8:
      return Richtung.UHR_8;
    case UHR_10:
      return Richtung.UHR_10;
    default:
      return null;
    }
  }
}
