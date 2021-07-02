package a7;




import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * <p>Title: Assignment 1</p>
 * <p>Description: Interactive thesaurus</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: UWRF</p>
 * @author Hossein L Najafi, Anthony Varghese
 * @version 3.0
 */

public class Assign7Main {
    static final int LOAD = 1, SAVE = 2, ADD_LINE = 3, REMOVE_LINE = 4, 
    		         SUGGEST_WORD = 5, SYNONYMIZE_FILE = 6, QUIT = 7;
    static final String welcomeMessage = 
    		"This program implements an interactive thesaurus. It allows you to add\n" +
    		"new lines to a thesaurus, remove lines from the thesaurus.\n" +
            "In addition, you can load an existing thesaurus file, save the current\n" +
    		"thesaurus to a file.\n";
    static final String promptMessage = 
    		"What would you like to do?  Please eneter the number next to your selection\n"
                    + LOAD +        ": Load a thesaurus from a file\n"
                    + SAVE +        ": Save current thesaurus to a file\n"
                    + ADD_LINE +    ": Add a line to the current thesaurus\n"
                    + REMOVE_LINE + ": Remove a line from the current thesaurus\n"
                    + SUGGEST_WORD +": Suggest a synonym for a word\n" 
                    + SYNONYMIZE_FILE +": Replace words in a file with synonyms\n"
                    + QUIT +        ": Quit\n";

    public static void main(String[] args) throws FileNotFoundException {
	    JOptionPane.showMessageDialog(null, welcomeMessage );
	    Thesaurus mythesaurus = new Thesaurus();
	    
	    int userSelection = 0;
	    while (userSelection !=QUIT) { 
	    	try {
	    	userSelection = Integer.parseInt(JOptionPane.showInputDialog( promptMessage ) );
	    	processSelection( mythesaurus, userSelection );
	    	}
	    	catch (NumberFormatException nfRef) {
		    	   JOptionPane.showMessageDialog(null, "Invalid input");    	   

			}
	    }	    
	}

	private static void processSelection(Thesaurus mythesaurus, int userSelection) throws FileNotFoundException {
	    switch(userSelection){
	    
	       case LOAD:
	    	   // Remember that you can have text files in your Eclipse Project
	    	   // If this file is in src/a1, you could read the synonyms.txt file in your a1 package
	    	   // using the "fileName"  src/a1/synonyms.txt
	    	   String fileName = JOptionPane.showInputDialog("Please enter the name of the thesaurus file to load");
	    	   mythesaurus.loadData( fileName );
	    	   break;
	       case SAVE:
	    	   fileName = JOptionPane.showInputDialog("Please enter the name of the thesaurus file to save");
	    	   mythesaurus.saveThesaurus(fileName);
	    	   break;
	       case ADD_LINE:
	    	   String wordToAddtoThesaurus = JOptionPane.showInputDialog("Please enter the word you want to add to thesaurus ");
	    	   String synonym1 = JOptionPane.showInputDialog("Please enter the first synonym for " + wordToAddtoThesaurus);
	    	   String synonym2 = JOptionPane.showInputDialog("Please enter the second synonym for " + wordToAddtoThesaurus);
	    	   String synonym3 = JOptionPane.showInputDialog("Please enter the third synonym for " + wordToAddtoThesaurus);
	    	   String synonym4 = JOptionPane.showInputDialog("Please enter the fourth synonym for " + wordToAddtoThesaurus);
	    	   Word newWord = new Word(wordToAddtoThesaurus);
	    	   Synonym s1 = new Word(synonym1);
	    	   Synonym s2 = new Word(synonym2);
	    	   Synonym s3 = new Word(synonym3);
	    	   Synonym s4 = new Word(synonym4);
	    	   mythesaurus.addLine(newWord, s1, s2, s3, s4 );
	    	   break;
	       case REMOVE_LINE:
	    	   String wordToRemoveFromThesaurus = JOptionPane.showInputDialog("Please enter the word whose line you want to remove from thesaurus");
	    	   Word wordToRemove = new Word(wordToRemoveFromThesaurus);
	    	   mythesaurus.removeLine(wordToRemove);    	   
	    	   break;
	       case SUGGEST_WORD:
	    	   String suggestWordsFor = JOptionPane.showInputDialog("Please enter the word for which you want a synonym.");
	    	   Word suggestedWord = new Word(suggestWordsFor);
	    	   if (mythesaurus.suggest(suggestedWord) != null) {
	    		   String result = "";
	    		   result += mythesaurus.suggest(suggestedWord).get(0)+ " ";
	    		   result += mythesaurus.suggest(suggestedWord).get(1)+ " ";
	    		   result += mythesaurus.suggest(suggestedWord).get(2)+ " ";
	    		   result += mythesaurus.suggest(suggestedWord).get(3)+ " ";
		    	   JOptionPane.showMessageDialog(null, result);    	   

	    	   } 
	    	   else {
	    		   JOptionPane.showMessageDialog(null, "Word not found");    
	    	   }
	    	   break;
	    	   //prompt the user to select a text file and thesaurus
	    	   //allow the user to replace words in the text file with synonyms and save the file
	       case SYNONYMIZE_FILE:
	    	   //create file chooser instance to load text and thesaurus files
	    	   JFileChooser fc = new JFileChooser(".");
	    	   File file;
	    	   Scanner fileIn;
	    	   int response;
	    	   String [] fileArray = new String[3];
	    	   
	    	   
	    	   fc.setDialogTitle("Select a text file with MAX 3 Sentences");
	    	   fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    	   response = fc.showOpenDialog(null);
	    	   
	    	   //read text file into string array, must be a valid txt file
	    	   if (response == JFileChooser.APPROVE_OPTION) {
	    		   file = fc.getSelectedFile();
	    		   if (file.getName().toLowerCase().endsWith(".txt")) {
	    			   if (file.isFile()) {
	    				   try {
	    					   fileIn = new Scanner(file);
	    					   int counter = 0;
	    					   while (fileIn.hasNextLine()) {
	    						   fileArray[counter] = fileIn.nextLine();
	    						   counter++;
	    					   }
	    					   fileIn.close();
	    				   } catch (ArrayIndexOutOfBoundsException ai) {
	    					   JOptionPane.showMessageDialog(null, "Too many lines in the file.\n Only the first 3 will be used");
	    				   }
	    				   catch (Exception e) {
	    					   System.out.println(e);
	    				   }
	    			   
	    			   }
	    		   } else {
					   JOptionPane.showMessageDialog(null, "Invalid File Type");
					   break;
	    		   }
	    	   	}
	    	   
	    	   //load a selected thesaurus, must be a valid txt file
	    	   fc.setDialogTitle("Select a Thesaurus");
	    	   response = fc.showOpenDialog(null);
	    	   if (response == JFileChooser.APPROVE_OPTION) {
	    		   file = fc.getSelectedFile();
	    		   if (file.isFile()) {
	    			   if (file.getName().toLowerCase().endsWith(".txt")) {
	    				   mythesaurus.loadData(file.getName());
	    			   } else {
	    				   JOptionPane.showMessageDialog(null, "Invalid File Type");
						   break;
	    			   }
	    		   }
	    	   }
	    	   
	    	   //ask user if they would like to substitute synonyms in the selected file
	    	   //if yes, share the results with the user
	    	   int choice = JOptionPane.showConfirmDialog(null, "Would you like to substitue synonyms in the file?");
	    	   if (choice == 0) {
	    		   String[] newStringArray = mythesaurus.synonymize(fileArray);
	    		   String output = "";
	    		   for (int i=0; i<newStringArray.length; i++) {
	    			   if (newStringArray[i] != null)
	    				   output += newStringArray[i] + "\n";
	    		   }
		    	   JOptionPane.showMessageDialog(null, output);
		    	   fc.setDialogTitle("Save the Synonymized File?");

		    	response = fc.showSaveDialog(null);
		    	if (response == JFileChooser.APPROVE_OPTION) {
		    		try {
		    	         FileWriter fw = new FileWriter(fc.getSelectedFile());
		    	         fw.write(output);
		    	         fw.close();
		    	     } catch (Exception ex) {
		    	         ex.printStackTrace();
		    	     }
		    		}
	    	   }
	    	   
	    	   break;
	       case QUIT:
	    	   JOptionPane.showMessageDialog(null, "Have a nice day");
	    	   break;
	       default:
	    	   JOptionPane.showMessageDialog(null, "Invalid Input");
	    }
		}
	    
	}
