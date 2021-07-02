package a7;
/**
 * @author Christopher Onopa 
 * 11/29/2020
 * Create a thesaurus and fill with thesaurusLines read from a file.
 * Via GUI, users can add, remove, look up synonyms, and save the thesaurus to a file
 */
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;


public class Thesaurus {
	private List<ThesaurusLine> thesaurusLines = new ArrayList<ThesaurusLine>();
	private int lineCount = 0;
	public static final int lineLimit = 100;
	
	// loads data from a file, each line contains a word and four synonyms, they are saved as a thesaurusline object
	public void loadData(String fileName) {
		try {
			java.util.Scanner inFile = new java.util.Scanner(new java.io.FileReader(fileName));
			if (fileIsValid(fileName)) {
			while (inFile.hasNextLine()) {
				java.util.StringTokenizer t = new java.util.StringTokenizer(inFile.nextLine());
				Word newWord = new Word(t.nextToken().trim());                           		               														
				Word synonymOne = new Word(t.nextToken(",").trim());                                
				Word synonymTwo = new Word(t.nextToken(",").trim());
				Word synonymThree = new Word(t.nextToken(",").trim());
				Word synonymFour = new Word(t.nextToken(",").trim());
				addLine(newWord, synonymOne, synonymTwo, synonymThree, synonymFour);
			}
			} else {
				JOptionPane.showMessageDialog(null, "Incorrect file format...");
			}
			inFile.close();
		} catch (java.io.FileNotFoundException e) {
	    	   JOptionPane.showMessageDialog(null, "Incorrect file name...");
		}
	}
	// checks to make sure each line in the file is the format"Word\t\tSynonym, Synonym, Synonym, Synonym)
	private boolean fileIsValid(String fileName) {
		try {
			java.util.Scanner inFile = new java.util.Scanner(new java.io.FileReader(fileName));
		while (inFile.hasNextLine()) {
			String lineToCheck = inFile.nextLine().trim();
			String newString = lineToCheck.substring(lineToCheck.indexOf("\t")).trim();

	        String[] stringArray = newString.split(",");
	        if (stringArray.length != 4)
	        	return false;
		}
		return true;
		}catch (java.io.FileNotFoundException e) {
	    	   JOptionPane.showMessageDialog(null, "Incorrect file name...");
	    	   return false;
		}
	}
	
	public void saveThesaurus(String fileName) throws FileNotFoundException {
        PrintWriter outFile = new PrintWriter(fileName);

        for (int i=0; i<thesaurusLines.size(); i++){//Saves the current Thesaurus to an Output file
            outFile.println(thesaurusLines.get(i)); //one line at a time
        }
        outFile.close();
    }
	// used to ensure phrases are stored properly
	private Word convertWordtoPhrase(Synonym in) {
		if (isPhrase(in)) {
			Word word = (Word) in;
			String[] sa = word.getWord().split(" ");
			Word[] wa = new Word[sa.length];
			for (int i=0; i<sa.length; i++) {
				wa[i] = new Word(sa[i]);
			}
			return new Phrase(wa);
		}
		return (Word) in;
	}
	// used to check if a synonym is a phrase and should be saved as an array of words
	private boolean isPhrase(Synonym s) {
		Word w = (Word) s;
		if (w.getWord().contains(" "))
			return true;
		return false;
	}
	
	public void addLine(Word wordToAdd, Synonym synonymOne, Synonym synonymTwo, Synonym synonymThree, Synonym synonymFour) {
		if (lineCount < lineLimit) {
			synonymOne = convertWordtoPhrase(synonymOne);
			synonymTwo = convertWordtoPhrase(synonymTwo);
			synonymThree = convertWordtoPhrase(synonymThree);
			synonymFour = convertWordtoPhrase(synonymFour);
			ThesaurusLine newLine = new ThesaurusLine(wordToAdd, synonymOne, synonymTwo, synonymThree, synonymFour);
    		thesaurusLines.add(newLine);    //adds a word and 4 synonyms to the end of the Thesaurus
		} else {}
	}
	//if user input matches a word in the thesaurus it will be removed
	public void removeLine(Word wordToRemoveFromThesaurus){
		this.bubbleSort();
		for (int i=0; i<thesaurusLines.size(); i++) {
			if (thesaurusLines.get(i).getWord().equals(wordToRemoveFromThesaurus))
	        	thesaurusLines.remove(i);
		}
        
    }
	
	//first sorts the words in the thesaurus alphabetically then searches for a word from user input
	// the synonyms for the word are returned if the word is found
	public List<Synonym> suggest(Word suggestWordFor) {
		this.bubbleSort();
		int location = binarySearch(thesaurusLines,suggestWordFor);
		if (location >= 0)
			return thesaurusLines.get(location).getSynonyms();
		return null;
	}
	
	public void bubbleSort() {
		final int length = thesaurusLines.size(); // assume locations 0-size-1 are not null!
		for (int counter = 0; counter < length-1; counter++) {
            for (int index = 0; index < length-1-counter; index++)
                if (thesaurusLines.get(index).compareTo( thesaurusLines.get(index + 1).getWord() ) > 0 ) {
                	// Swap
                	ThesaurusLine temp = thesaurusLines.get(index);
                	thesaurusLines.set(index, thesaurusLines.get(index + 1));
                	thesaurusLines.set(index + 1, temp);
                }
        }
	}
	
	public int binarySearch(List<ThesaurusLine> thesaurusLines, Word searchWord) {
		int start = 0, end = thesaurusLines.size()-1;
		while (start <= end) {
			int mid = start + (end-start) /2;
			if (thesaurusLines.get(mid).getWord().equals(searchWord))
				return mid;
			if (thesaurusLines.get(mid).compareTo(searchWord) < 0)
				start = mid+1;
			else
				end = mid-1;
		}
		return -1;
	}
	
//Takes a string array with up to 3 sentences, splits each sentence into an array of words and
//seach a thesaurs for a match, replaces the word with a random synonym if found
//reform the sentences and return an array of sentences
	public String[] synonymize(String[] sa) {
		//split each sentence to an array of one word strings
		String[] newArray = new String[3];
		Random rn = new Random();
		for (int i=0; i<3; i++) {
			if (sa[i] != null) {
				String[] words = sa[i].split(" ");
				for (int j=0; j<words.length; j++) {
					words[j] = words[j].replaceAll("\\p{Punct}", "").toLowerCase();
					
					//scan thesaurus for a match
					for (int q=0; q<thesaurusLines.size(); q++) {
						Word compareWord = new Word(words[j]);
						if (thesaurusLines.get(q).getWord().equals(compareWord))// if found replace with a random synonym
							words[j] = thesaurusLines.get(q).getSynonyms().get(rn.nextInt(4)).toString();
					}
				}
				//reconstruct the sentence with random synonyms and save it in new array
				String tempSentence = "";
				for (int w=0; w<words.length; w++) {
					tempSentence += words[w].trim() + " ";
				}//add a period and capitilize the first letter
				tempSentence = tempSentence.trim() + ".";
				tempSentence = tempSentence.substring(0, 1).toUpperCase() + tempSentence.substring(1);
				newArray[i] = tempSentence;
			}
		}
		return newArray;
	}
}
