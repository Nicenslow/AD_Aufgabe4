package vorgaben;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementierung eines Graphen mit einer Adjazenzmatrix.
 * 
 * @author Philipp Jenke
 * 
 */
public class Graph<T> {

	/**
	 * Liste der Knoten im Graphen
	 */
	private final List<Knoten<T>> knoten = new ArrayList<Knoten<T>>();

	/**
	 * Adjazenzmatrix als zweidimensionales Array. Falls keine Kante existiert
	 * ist das Gewicht mit Double.NEGATIVE_INFINITY markiert.
	 */
	private double[][] kantenMatrix;

	/**
	 * Konstruktor
	 */
	public Graph() {
		kantenMatrix = new double[1][1];
		kantenMatrix[0][0] = Double.NEGATIVE_INFINITY;
	}

	public void addKnoten(Knoten<T> node) {

		if (kantenMatrix.length <= getAnzahlKnoten()) {

			// Neue Matrix erzeugen
			double[][] neueKantenAdjazenzMatrix = new double[kantenMatrix.length * 2][kantenMatrix.length * 2];
			for (int i = 0; i < neueKantenAdjazenzMatrix.length; i++) {
				for (int j = 0; j < neueKantenAdjazenzMatrix.length; j++) {
					neueKantenAdjazenzMatrix[i][j] = Double.NEGATIVE_INFINITY;
				}
			}

			// Alte Werte kopieren
			for (int i = 0; i < knoten.size(); i++) {
				for (int j = 0; j < knoten.size(); j++) {
					neueKantenAdjazenzMatrix[i][j] = kantenMatrix[i][j];
				}
			}

			// Neues Array übernehmen
			kantenMatrix = neueKantenAdjazenzMatrix;
		}

		knoten.add(node);
	}

	public void addKante(Knoten<T> start, Knoten<T> ende, double wert) {
		int startIndex = knoten.indexOf(start);
		int endIndex = knoten.indexOf(ende);
		if (startIndex < 0 || endIndex < 0) {
			return;
		}
		kantenMatrix[startIndex][endIndex] = wert;
		kantenMatrix[endIndex][startIndex] = wert;
	}

	public int getAnzahlKnoten() {
		return knoten.size();
	}

	public int getAnzahlKanten() {
		int anzahl = 0;
		for (int i = 0; i < getAnzahlKnoten(); i++) {
			for (int j = 0; j < i; j++) {
				if (kantenMatrix[i][j] != Double.NEGATIVE_INFINITY) {
					anzahl++;
				}
			}
		}
		return anzahl;
	}

	public Knoten<T> getKnoten(int nodeIndex) {
		return knoten.get(nodeIndex);
	}

	public List<Knoten<T>> getNachbarn(Knoten<T> node) {
		int knotenIndex = knoten.indexOf(node);
		List<Knoten<T>> nachbarn = new ArrayList<Knoten<T>>();
		for (int i = 0; i < getAnzahlKnoten(); i++) {
			if (kantenMatrix[knotenIndex][i] != Double.NEGATIVE_INFINITY) {
				nachbarn.add(knoten.get(i));
			}
		}
		return nachbarn;
	}

	public List<Integer> getNachbarIndices(int nodeIndex) {
		List<Integer> nachbarn = new ArrayList<Integer>();
		for (int i = 0; i < getAnzahlKnoten(); i++) {
			if (kantenMatrix[nodeIndex][i] != Double.NEGATIVE_INFINITY) {
				nachbarn.add(i);
			}
		}
		return nachbarn;
	}

	public double getKantenGewicht(int startIndex, int zielIndex) {
		return kantenMatrix[startIndex][zielIndex];
	}

	public double getKantenGewicht(Knoten<T> startKnoten, Knoten<T> zielKnoten) {
		int startIndex = knoten.indexOf(startKnoten);
		int zielIndex = knoten.indexOf(zielKnoten);
		return kantenMatrix[startIndex][zielIndex];
	}

	public void setzeGewicht(Knoten<T> startKnoten,
			Knoten<T> zielKnoten, double gewicht) {
		int startIndex = knoten.indexOf(startKnoten);
		int zielIndex = knoten.indexOf(zielKnoten);
		kantenMatrix[startIndex][zielIndex] = gewicht;
		kantenMatrix[zielIndex][startIndex] = gewicht;

	}

	public boolean kanteExistiert(Knoten<T> startKnoten, Knoten<T> endKnoten) {
		int startIndex = knoten.indexOf(startKnoten);
		int zielIndex = knoten.indexOf(endKnoten);
		return kantenMatrix[startIndex][zielIndex] != Double.NEGATIVE_INFINITY;
	}

}
