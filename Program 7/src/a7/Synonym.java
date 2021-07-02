package a7;
/**
 * @author Christopher Onopa 
 * 11/29/2020
 * Super class of Word and Phrase
 */
public abstract class Synonym {
	public abstract String toString();
	public boolean equals(Object other) {
		if (other == null)
			return false;
		else return other instanceof Synonym;
	}

}
