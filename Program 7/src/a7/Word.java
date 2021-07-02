package a7;
/**
 * @author Christopher Onopa 
 * 11/29/2020
 * Create word objects to be stored in thesaurusLines, which will be stored in a Thesaurus
 */
public class Word extends Synonym{
	private String word;
	public Word() {}
	public Word(String s) {word = s;}
	public Word(Word w) {word = w.word;}
	public void setWord(String w) {word = w;}
	public String toString() {
		return word;
	}
	//used to check if two word objects are equal
	public boolean equals(Object other) {
		boolean synEquals = super.equals(other);
		if (!synEquals)
			return false;
		Word o = (Word) other;
		boolean strEqual = word.equals(o.word);
		return strEqual;
	}
	
	public String getWord() {
		return this.word;
	}

}
