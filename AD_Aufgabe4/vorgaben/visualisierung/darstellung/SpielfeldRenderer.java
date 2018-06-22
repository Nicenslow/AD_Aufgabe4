package visualisierung.darstellung;

import vorgaben.Knoten;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import visualisierung.basis.Konstanten;
import visualisierung.basis.Neuzeichenbar;
import visualisierung.basis.SpielZustand;
import vorgaben.Zelle;
import javafx.scene.shape.ArcType;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * Renderer für ein Level
 *
 * @author Philipp Jenke
 */
public class SpielfeldRenderer extends Canvas implements Neuzeichenbar {

    public interface PfadGewaehltCallback {
        public void callback();
    }

    public SpielfeldRenderer(PfadGewaehltCallback pfadGewaehltCallback) {
        super(Konstanten.CANVAS_GROESSE, Konstanten.CANVAS_GROESSE);
        AssetRenderer.ladeAlleAssets();
        setOnMouseClicked(
                ereignis -> {
                    if (ereignis.getButton().compareTo(MouseButton.PRIMARY) == 0) {
                        SpielZustand.getInstance().setStartZelle(waehleZelle(ereignis.getX(), ereignis.getY()));
                    } else {
                        SpielZustand.getInstance().setZielZelle(waehleZelle(ereignis.getX(), ereignis.getY()));
                    }
                    pfadGewaehltCallback.callback();
                });
    }

    /**
     * Wähle aktuelle Zelle durch Mausklick.
     */
    private Zelle waehleZelle(double x, double y) {
        Point weltPunkt = Konstanten
                .getWeltKoordinaten(new Point((int) x, (int) y));
        return getAusgwaehlteZelle(weltPunkt);
    }

    /**
     * Setze als aktuelle Zelle die, deren Mittelpunkt dem Punkt am nächsten
     * liegt.
     */
    private Zelle getAusgwaehlteZelle(Point weltPunkt) {
        double besteEntfernung = Double.POSITIVE_INFINITY;
        Zelle gewaehlteZelle = null;
        for (Iterator<Zelle> it = SpielZustand.getInstance().getAktuellerLevel()
                .getZellenIterator(); it.hasNext(); ) {
            Zelle zelle = it.next();
            Point zellenMittelpunkt = Konstanten
                    .getWeltkoordinatenVonZellenIndex(zelle.getIndex());
            double entfernung = zellenMittelpunkt.distanceSq(weltPunkt);
            if (entfernung < besteEntfernung) {
                gewaehlteZelle = zelle;
                besteEntfernung = entfernung;
            }
        }
        return gewaehlteZelle;
    }

    /**
     * Zeichnet das gesamte Spielfeld
     */
    public void neuzeichnen() {
        GraphicsContext gc = getGraphicsContext2D();
        double w = getWidth();
        double h = getHeight();
        gc.clearRect(0, 0, w, h);
        gc.setFill(Color.WHITE);
        zeichneZellen(gc);
        zeichneBeschriftung(gc);
    }

    /**
     * Legende zeichnen
     */
    private void zeichneBeschriftung(GraphicsContext gc) {
        gc.setFill(Konstanten.START_ZELLE_FARBE);
        gc.fillRect(getWidth() - 150, getHeight() - 50, 150, 25);
        gc.setFill(Konstanten.ZIEL_ZELLE_FARBE);
        gc.fillRect(getWidth() - 150, getHeight() - 25, 150, 25);

        gc.setStroke(Color.BLACK);
        gc.strokeText("Startzelle (Linksklick)", getWidth() - 140, getHeight() - 33);
        gc.strokeText("Zielzelle (Rechtsklick)", getWidth() - 140, getHeight() - 8);
    }

    /**
     * Zeichnet alle Zellen des Spielfelds. Zentrierung auf der Zeichenfläche.
     */
    protected void zeichneZellen(GraphicsContext gc) {
        for (Iterator<Zelle> it = SpielZustand.getInstance().getAktuellerLevel().getZellenIterator(); it.hasNext(); ) {
            Zelle zelle = it.next();
            ZellenRenderer.zeichneZelle(zelle, gc);
        }
    }

    /**
     * Zeichnet den Pfad als Linienzug.
     */
    public void zeichnePfad(List<Knoten<Zelle>> pfad, Color farbe, int offset) {
        GraphicsContext gc = getGraphicsContext2D();

        double[] xPoints = new double[pfad.size()];
        double[] yPoints = new double[pfad.size()];
        gc.setFill(farbe);
        gc.setStroke(farbe);
        for (int i = 0; i < pfad.size(); i++) {
            Zelle zelle1 = pfad.get(i).getElement();
            Point mittelpunktZelle = Konstanten.getBildKoordinaten(Konstanten
                    .getWeltkoordinatenVonZellenIndex(zelle1.getIndex()));
            xPoints[i] = mittelpunktZelle.x + offset;
            yPoints[i] = mittelpunktZelle.y + offset;
            double pointRadius = Konstanten.ZELLEN_HOEHE / 4;
            gc.fillArc(mittelpunktZelle.x - pointRadius + offset, mittelpunktZelle.y - pointRadius + offset,
                    pointRadius * 2, pointRadius * 2, 0, 360, ArcType.ROUND);
        }
        gc.strokePolyline(xPoints, yPoints, pfad.size());
    }
}
