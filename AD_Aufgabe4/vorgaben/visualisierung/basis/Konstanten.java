package visualisierung.basis;

import java.awt.*;

/**
 * Sammlung der Konstanten der Anwendung
 *
 * @author Philipp Jenke
 */
public abstract class Konstanten {

    /**
     * Seitenlänge der quadratischen Zeichenfläche für das Spielfeld.
     */
    public static final int CANVAS_GROESSE = 400;

    /**
     * Rand (weiss) um das Spielfeld auf der Spielfeld-Zeichenfläche
     */
    public static final int CANVAS_RAND = 5;

    /**
     * Breite des Fenster der Anwendung
     */
    public static final double FENSTER_BREITE = 600;

    /**
     * Höhe des Fenster der Anwendung
     */
    public static final double FENSTER_HOEHE = 400;

    /**
     * Seitenlänge der Zellen (Seckeck)
     */
    public static final int ZELLEN_SEITENLAENGE = 20;

    /**
     * Hilfsvariable: Höhe eines der sechs gleichseitigen Dreiecke, die zusammen
     * eine Zelle ergeben.
     */
    public static final double ZELLEN_HOEHE = Math.sqrt(3) / 2.0
            * Konstanten.ZELLEN_SEITENLAENGE;

    /**
     * Größe der Asset-Bilder.
     */
    public static final int ASSET_BILD_GROESSE = (int) (ZELLEN_SEITENLAENGE
            * 1.3);

    /**
     * Unterverzeichnis in dem die Assets (Bilder) liegen.
     */
    public static final String ASSET_PFAD = "/";

    /**
     * Zeichenfarben
     */
    public static final javafx.scene.paint.Color START_ZELLE_FARBE = javafx.scene.paint.Color.LIGHTCORAL;
    public static final javafx.scene.paint.Color ZIEL_ZELLE_FARBE = javafx.scene.paint.Color.LIGHTCYAN;

    /**
     * Richtungen im KuerzesterPfadVisualisierung (für Steuerung, Wandpositionierung, ...)
     */
    public static enum Richtung {
        UHR_0, UHR_2, UHR_4, UHR_6, UHR_8, UHR_10;

        /**
         * Liefert die Gegenüber-Richtung der aktuellen Richtung.
         *
         * @return
         */
        public Richtung getGegenueber() {
            return values()[(ordinal() + 3) % 6];
        }
    }

    /**
     * Liefert die Bildkoordinaten für Weltkoordinaten.
     */
    public static Point getBildKoordinaten(Point weltkoordinaten) {
        Point bildKoordinaten = new Point(weltkoordinaten);
        bildKoordinaten.x += Konstanten.CANVAS_RAND;
        bildKoordinaten.y += Konstanten.CANVAS_RAND;
        return bildKoordinaten;
    }

    /**
     * Liefert die Weltkoordinaten zu Bildkoordinaten.
     */
    public static Point getWeltKoordinaten(Point bildKoordinaten) {
        Point weltkoordinaten = new Point(bildKoordinaten);
        weltkoordinaten.x -= Konstanten.CANVAS_RAND;
        weltkoordinaten.y -= Konstanten.CANVAS_RAND;
        return weltkoordinaten;
    }

    /**
     * Liefert die Weltkoordinaten zu einem Index im Gitter der Zellen.
     */
    public static Point getWeltkoordinatenVonZellenIndex(int i, int j) {
        int x = (int) (i * Konstanten.ZELLEN_SEITENLAENGE * 1.5
                + Konstanten.ZELLEN_SEITENLAENGE);
        int y = (int) (j * Konstanten.ZELLEN_HOEHE * 2
                + ((i % 2 == 1) ? Konstanten.ZELLEN_HOEHE : 0)
                + Konstanten.ZELLEN_SEITENLAENGE);
        return new Point(x, y);
    }

    /**
     * Liefert die Weltkoordinaten zu einem Index im Gitter der Zellen.
     */
    public static Point getWeltkoordinatenVonZellenIndex(Point index) {
        return getWeltkoordinatenVonZellenIndex(index.x, index.y);
    }

    /**
     * Erlaubte Befehle, um mit dem KuerzesterPfadVisualisierung zu interagieren.
     */
    public static enum Befehl {
        GEHE, BEKAEMPFE, RETTE, GEGNER, FREUND, UHR_0, UHR_2, UHR_4, UHR_6, UHR_8, UHR_10
    }

    /**
     * Steuerung des Renderes
     */
    public static final int RENDER_FLAG_ALLE_ZELLEN_SICHTBAR = 1;

    /**
     * Aktueller Status im KuerzesterPfadVisualisierung
     */
    public static enum SpielStatus {
        SPIELER_ZUG, GEGNER_ZUG, GEWONNEN, VERLOREN
    }
}
