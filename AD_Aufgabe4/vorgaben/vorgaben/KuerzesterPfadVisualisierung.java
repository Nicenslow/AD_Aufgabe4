package vorgaben;

import visualisierung.basis.*;
import visualisierung.darstellung.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Benutzerinterface (Fenter für das KuerzesterPfadVisualisierung):
 *
 * @author Philipp Jenke
 */
public class KuerzesterPfadVisualisierung extends Application {

    private SpielfeldRenderer spielRenderer;

    /**
     * Liste der verschiedenen Algorithmen zum Finden von kürzesten Pfaden.
     */
    private List<KuerzesterPfad> kuerzestePfade = new ArrayList<>();

    @Override
    public void start(Stage buehne) throws Exception {
        BorderPane wurzel = new BorderPane();
        Scene szene = new Scene(wurzel, Konstanten.FENSTER_BREITE,
                Konstanten.FENSTER_HOEHE);
        buehne.setTitle("AD SS2018 - Graphen");
        buehne.setScene(szene);
        spielRenderer = new SpielfeldRenderer(() -> pfadUpdate());
        reset();
        wurzel.setCenter(spielRenderer);
        buehne.show();

        // TODO: Hier die Algorithmen zur Berechnung der kürzesten Pfade einbauen
        kuerzestePfade.add(new KuerzesterPfadDijkstra());
        kuerzestePfade.add(new KuerzesterPfadTiefensuche());

        pfadUpdate();
    }

    private void pfadUpdate() {
        // Berechne kürzeste Pfade
        Graph<Zelle> spielfeldgraph = SpielZustand.getInstance().getAktuellerLevel().erzeugeGraph();
        spielRenderer.neuzeichnen();
        Knoten<Zelle> startZelle = getKnotenFuerZelle(spielfeldgraph, SpielZustand.getInstance().getStartZelle());
        Knoten<Zelle> zielZelle = getKnotenFuerZelle(spielfeldgraph, SpielZustand.getInstance().getZielZelle());
        if (startZelle == null || zielZelle == null) {
            return;
        }
        Color[] farben = {Color.DARKRED, Color.DARKBLUE, Color.DARKKHAKI, Color.DARKGREEN};
        for (int i = 0; i < kuerzestePfade.size(); i++) {
            List<Knoten<Zelle>> pfad = kuerzestePfade.get(i).berechneKuerzestenPfad(spielfeldgraph,
                    startZelle, zielZelle);
            if (pfad != null) {
                spielRenderer.zeichnePfad(pfad, farben[i], 2 * i);
            }
        }
    }

    /**
     * Liefert den Knoten der die gesuchte Zelle beinhaltet.
     */
    private Knoten<Zelle> getKnotenFuerZelle(Graph<Zelle> spielfeldgraph, Zelle startZelle) {
        for (int i = 0; i < spielfeldgraph.getAnzahlKnoten(); i++) {
            if (spielfeldgraph.getKnoten(i).getElement() == startZelle) {
                return spielfeldgraph.getKnoten(i);
            }
        }
        return null;
    }

    private boolean befehlIstValide(String kommando) {

        String richtungenRegex = "(" + Konstanten.Befehl.UHR_0 + "|"
                + Konstanten.Befehl.UHR_2 + "|" + Konstanten.Befehl.UHR_4 + "|"
                + Konstanten.Befehl.UHR_6 + "|" + Konstanten.Befehl.UHR_8 + "|"
                + Konstanten.Befehl.UHR_10 + ")";
        String geheRegex = Konstanten.Befehl.GEHE + "\\s" + richtungenRegex;
        String bekaempfeRegex = Konstanten.Befehl.BEKAEMPFE + "\\s"
                + Konstanten.Befehl.GEGNER + "\\s" + richtungenRegex;
        String retteRegex = Konstanten.Befehl.RETTE + "\\s"
                + Konstanten.Befehl.FREUND + "\\s" + richtungenRegex;
        String regex = "(" + geheRegex + "|" + bekaempfeRegex + "|" + retteRegex
                + ")";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(kommando);
        return matcher.matches();
    }

    /**
     * Öffnet ein kleines Hinweisfenster mit einem Hinweis.
     */
    private void alert(String text) {
        VBox wurzel = new VBox();
        wurzel.setPadding(new Insets(10));
        wurzel.setSpacing(10);
        wurzel.setAlignment(Pos.CENTER);

        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        wurzel.getChildren().add(label);

        Stage stage = new Stage();
        stage.setTitle("Spielende");
        stage.setScene(new Scene(wurzel, label.getPrefWidth(), 100));

        Button button = new Button("Ok!");
        button.setOnAction(ereignis -> stage.hide());
        wurzel.getChildren().add(button);

        stage.show();
    }

    private void reset() {
        // KuerzesterPfadVisualisierung setup
        Level level = LevelIO
                .levelLaden(Thread.currentThread().getContextClassLoader().getResourceAsStream("level_shortest_path.json"));
        SpielZustand.getInstance().setAktuellerLevel(level);
        SpielZustand.getInstance().setSpielStatus(Konstanten.SpielStatus.SPIELER_ZUG);
        SpielZustand.getInstance().getAktuellerLevel().alleZellenSichtbar();

        Zelle startZelle = level.getZufallsZelle();
        Zelle zielZelle = level.getZufallsZelle();
        SpielZustand.getInstance().setStartZelle(startZelle);
        SpielZustand.getInstance().setZielZelle(zielZelle);

        spielRenderer.neuzeichnen();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
