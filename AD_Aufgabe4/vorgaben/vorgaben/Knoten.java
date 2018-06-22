package vorgaben;

/**
 * Knoten in einem Graphen.
 * 
 * @author Philipp Jenke
 *
 */
public class Knoten<T> {

	/**
	 * Wert des Knotens.
	 */
	private T element;

	/**
	 * Debugging index - Ebene bei einer Suchstrategie
	 */
	private int suchEbene = -1;

	/**
	 * Konstruktor
	 */
	public Knoten(T element) {
		this.element = element;
	}

	/**
	 * Getter.
	 */
	public T getElement() {
		return element;
	}

	@Override
	public String toString() {
		return element.toString();
	}

	@Override
	public boolean equals(Object anderesObjekt) {
		if (!(anderesObjekt instanceof Knoten)) {
			return false;
		}
		Knoten<?> andererKnoten = (Knoten<?>) anderesObjekt;
		return getElement().equals(andererKnoten.getElement());
	}

	public int getSuchEbene() {
		return suchEbene;
	}

	public void setSuchEbene(int suchEbene) {
		this.suchEbene = suchEbene;
	}
}
