package com.example.menudemo;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.sql.*;

public class DictionaryManagement extends Dictionary {

    /**
     * Insert word from GUI.
     */
    public static boolean insertFromGUI(String wordTarget,String wordExplain) throws SQLException {
        if (!lookupCheck(wordTarget)) {
            Word newWord = new Word(wordTarget, wordExplain);
            listWord.add(newWord);
            listWordTarget.add(newWord.getWord_target());
            if (DictionaryController.check == 0) {
                dictionaryExportToFile();
                return true;
            }
            else {
                dictionaryExportToDatabase();
                return true;
            }
        }
        return false;
    }

    /**
     * Delete word from GUI.
     */
    public static List<String> deleteFromGUI(String wordTarget) throws SQLException {
        for(int i = listWord.size() - 1; i >= 0; i--) {
            if (wordTarget.equalsIgnoreCase(listWord.get(i).getWord_target())) {
                listWord.remove(listWord.get(i));
                listWordTarget.remove(listWordTarget.get(i));
                if (DictionaryController.check == 0) {
                    dictionaryExportToFile();
                }
                else {
                    dictionaryExportToDatabase();
                }
            }
        }
        return listWordTarget;
    }

    /**
     * Search word from GUI.
     */
    public static List<String> searchFromGUI(String wordTarget) {
        for (int i = 0; i < listWord.size(); i++) {
            if(listWord.get(i).getWord_target().toLowerCase().startsWith((wordTarget.toLowerCase()))) {
                listWordRelated.add(listWord.get(i).getWord_target());
            }
        }
        Collections.sort(listWordRelated);
        return listWordRelated;
    }

    /**
     * Insert word from Commandline.
     */
    public static String insertFromCommandline() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of words which you want to insert: ");
        int num = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < num; i++) {
            System.out.println("Enter word target which you want to insert: ");
            String wordTarget = sc.nextLine();
            System.out.println("Enter word explain of this word target: ");
            String wordExplain = sc.nextLine();
            Word newWord = new Word(wordTarget, wordExplain);
            listWord.add(newWord);
            listWordTarget.add(newWord.getWord_target());
            if (DictionaryController.check == 0) {
                dictionaryExportToFile();
            }
            else {
                dictionaryExportToDatabase();
            }
        }
        return "Insert successful";
    }

    /**
     * Delete word from Commandline.
     */
    public static String deleteFromCommandline() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter word target which you want to delete:");
        String wordTarget = sc.nextLine();
        for(int i = listWord.size() - 1; i >= 0; i--) {
            if (wordTarget.equals(listWord.get(i).getWord_target())) {
                listWord.remove(listWord.get(i));
                if (DictionaryController.check == 0) {
                    dictionaryExportToFile();
                }
                else {
                    dictionaryExportToDatabase();
                }
                return "Delete successful!";
            }
        }
        return "This word does not exist!";
    }


    /**
     * dictionary export to file.
     */
    public static void dictionaryExportToFile() {
        FileWriter fw;
        try {
            fw = new FileWriter("src/dictionarydata/dictionaries.txt");
            for (int i = 0; i < listWord.size(); i++ ) {
                fw.write(listWord.get(i).getWord_target() + '\n');
                fw.write(listWord.get(i).getWord_explain() + '\n');
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert from file.
     */
    public static void insertFromFile() throws IOException {
        Scanner sc;
        listWord.clear();
        listWordTarget.clear();
        try {
            sc = new Scanner(new File("src/dictionarydata/dictionaries.txt"));
            while (sc.hasNext()) {
                String wordTarget = sc.nextLine().trim();
                String wordExplain = sc.nextLine().trim();
                listWord.add(new Word(wordTarget, wordExplain));
            }
            for (Word word : listWord) {
                listWordTarget.add(word.getWord_target());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Search word from commandline.
     */
    public static void searcherForCommandline() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter word target which you want to search: ");
        String wordTarget = sc.nextLine();
        listWordRelated.clear();
        for (int i = 0; i < listWord.size(); i++) {
            if(listWord.get(i).getWord_target().toLowerCase().startsWith((wordTarget.toLowerCase()))) {
                listWordRelated.add(listWord.get(i).getWord_target());
            }
        }
        Collections.sort(listWordRelated);
        Dictionary.showListWordRelated(listWordRelated);
    }
}