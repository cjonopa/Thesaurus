package a7;
/**
 * @author Christopher Onopa 
 * 11/29/2020
 * Create phrase objects to be stored in thesaurusLines, which will be stored in a Thesaurus
 */
public class Phrase extends Word{
	public static final int WORD_LIMIT = 3;
	private Word[] wordArray;
	// construtor, creates an array to store a phrase 
	public Phrase() {
		wordArray = new Word[WORD_LIMIT];
	}
	// takes multiple word objects and saves them as a phrase in a word object array
	public Phrase(Word[] wa) {
		if (wa.length<= WORD_LIMIT) {
			wordArray = new Word[wa.length];
			for (int i=0; i<wa.length; i++) {
				wordArray[i] = wa[i];
			}
		} else {}
		
	}
	
	public String toString() {
		String phrase = "";
		for (int i = 0; i<wordArray.length; i++) {
			phrase += wordArray[i] + " ";
		}
		return phrase;
	}
}
