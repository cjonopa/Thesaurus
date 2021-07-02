package a7;
/**
 * @author Christopher Onopa 
 * 11/29/2020
 * Create thesaurusLines from a word and four synonyms, which will be stored in a Thesaurus
 */
import java.util.ArrayList;
import java.util.List;

public class ThesaurusLine {
	
	private List<Synonym> wordAndSynonyms = new ArrayList<Synonym>();
	
	public ThesaurusLine(Word word, Synonym synonym1, Synonym synonym2, Synonym synonym3, Synonym synonym4) {
		wordAndSynonyms.add(word);
		wordAndSynonyms.add(synonym1);
		wordAndSynonyms.add(synonym2);		
		wordAndSynonyms.add(synonym3);
		wordAndSynonyms.add(synonym4);
	}
	
	public Word getWord() {
		return new Word((Word) this.wordAndSynonyms.get(0));
	}
	
	public List<Synonym> getSynonyms() {
		List<Synonym> synonyms = new ArrayList<Synonym>();
		synonyms.add(this.wordAndSynonyms.get(1));
		synonyms.add(this.wordAndSynonyms.get(2));
		synonyms.add(this.wordAndSynonyms.get(3));
		synonyms.add(this.wordAndSynonyms.get(4));
		return synonyms;
	}
	
	//compares the String value of the word in this thesaurusLine to the string value of the word in 
	//another thesaurusLine.  Allows thesaurusLines to be sorted.
	public int compareTo(Word o) {
		return this.getWord().getWord().compareToIgnoreCase(o.getWord());
	}
	
	public String toString() {
		return this.getWord() + "\t\t" + this.getSynonyms().get(0) + " " + this.getSynonyms().get(1) + " "
				 + this.getSynonyms().get(2) + " " + this.getSynonyms().get(3) + " ";
	}
}
