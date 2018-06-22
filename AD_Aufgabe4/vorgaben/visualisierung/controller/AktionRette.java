package visualisierung.controller;

import visualisierung.assets.Assets.AssetTyp;
import visualisierung.basis.Asset;
import visualisierung.basis.Konstanten.Befehl;
import visualisierung.basis.Konstanten.Richtung;
import visualisierung.basis.Konstanten.SpielStatus;
import visualisierung.basis.SpielZustand;
import visualisierung.basis.Spielfigur;
import vorgaben.Zelle;

import java.util.List;

public class AktionRette extends RichtungsAktion {

  @Override
  public String verarbeite(List<Befehl> befehlskette) {
    if (befehlskette.size() != 2) {
      return "Ungültige Befehlssyntax für RETTE.";
    }

    Befehl befehlGegner = befehlskette.get(0);
    if (befehlGegner != Befehl.FREUND) {
      return "Ungültige Befehlssyntax für RETTE.";
    }

    Befehl befehlRichtung = befehlskette.get(1);
    if (!richtungsBefehle.contains(befehlRichtung)) {
      return "Ungültiger Richtungsbefehl: " + befehlRichtung.toString();
    }

    /// Vorbereitung
    Richtung richtung = befehl2Richtung(befehlRichtung);
    Spielfigur spielfigur = SpielZustand.getInstance().getAktuellerLevel()
        .getSpielfigur();
    Zelle spielfigurZelle = spielfigur.getZelle();

    // Wand?
    if (spielfigurZelle.istWand(richtung)) {
      return "Eine Wand kann nicht gerettet werden!";
    }

    // Ziel-Zelle belegt?
    Zelle zielZelle = spielfigurZelle.getNachbarZelle(richtung);
    Asset asset = zielZelle.getAsset();
    if (asset == null) {
      return "Da ist niemand, der gerettet werden kann!";
    } else if (asset.getTyp() == AssetTyp.BOESEWICHT) {
      SpielZustand.getInstance().setSpielStatus(SpielStatus.VERLOREN);
      return "Keine gute Idee, einen Boesewicht zu retten - du bist tot";
    } else if (asset.getTyp() == AssetTyp.NPC) {
      SpielZustand.getInstance().setSpielStatus(SpielStatus.GEWONNEN);
      return "Prima, du hast deinen Freund gerettet - KuerzesterPfadVisualisierung gewonnen.";
    } else {
      return "Ein " + asset.getTyp().toString()
          + " kann nicht angegriffen werden.";
    }
  }

}
