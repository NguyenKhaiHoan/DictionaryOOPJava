package com.example.menudemo;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.sql.*;

public class DictionaryManagement extends Dictionary {
    /**
     * Lookup word from GUI.
     */
    public static String lookupFromGUI(String wordTarget) {
        for (Word word : listWord) {
            if (wordTarget.equalsIgnoreCase(word.getWord_target())) {
                return word.getWord_explain();
            }
        }
        return "This word does not exist!";
    }

    /**
     * Lookup word check.
     */
    public static boolean lookupCheck(String wordTarget) {
        for (Word word : listWord) {
            if (wordTarget.equalsIgnoreCase(word.getWord_target())) {
                return true;
            }
        }
        return false;
    }

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
                dictionaryExportToDatabase(wordTarget, wordExplain, 0);
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
                    dictionaryExportToDatabase(wordTarget, "", 2);
                }
            }
        }
        return listWordTarget;
    }

    /**
     * Edit word from GUI.
     */
    public static boolean editFromGUI(String wordTarget, String wordExplain) throws SQLException {
        for (int i = 0; i < listWord.size(); i++) {
            if (wordTarget.equalsIgnoreCase(listWord.get(i).getWord_target())) {
                listWord.set(i, new Word(wordTarget, wordExplain));
                if (DictionaryController.check == 0) {
                    dictionaryExportToFile();
                }
                else {
                    dictionaryExportToDatabase(wordTarget, wordExplain, 1);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Edit word from GUI.
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
     * Lookup word from Commandline.
     */
    public static String dictionaryLookup() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter word target which you want to lookup: ");
        String wordTarget = sc.nextLine();
        for (Word word : listWord) {
            if (wordTarget.equalsIgnoreCase(word.getWord_target())) {
                System.out.println("Show word explain of thí word target: ");
                return word.getWord_explain();
            }
        }
        return "This word does not exist!";
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
                dictionaryExportToDatabase(wordTarget, wordExplain, 0);
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
                    dictionaryExportToDatabase(wordTarget, "", 2);
                }
                return "Delete successful!";
            }
        }
        return "This word does not exist!";
    }

    /**
     * Edit word from Commandline.
     */
    public static String editFromCommandline() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter word target which you want to edit: ");
        String wordTarget = sc.nextLine();
        for (int i = 0; i < listWord.size(); i++) {
            if (wordTarget.equalsIgnoreCase(listWord.get(i).getWord_target())) {
                System.out.println("Enter word explain which you want to edit:  ");
                String wordExplain = sc.nextLine();
                listWord.set(i, new Word(wordTarget, wordExplain));
                if (DictionaryController.check == 0) {
                    dictionaryExportToFile();
                }
                else {
                    dictionaryExportToDatabase(wordTarget, wordExplain, 1);
                }
                return "Edit successful!";
            }
        }
        return "This word does not exist!";
    }

    private static String DB_URL = "jdbc:mysql://localhost:3306/dictionarydatabase";
    private static String USER_NAME = "root";
    private static String PASSWORD = "";

    /**
     * insert from database.
     */
    public static void insertFromDB() throws SQLException {
        listWord.clear();
        listWordTarget.clear();
        Connection conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from `tbl_edict`");
        while (rs.next()) {
            String wordTarget = rs.getString(2);
            String wordExplain = rs.getString(3);
            listWord.add(new Word(wordTarget, wordExplain));
        }
        for (Word word : listWord) {
            listWordTarget.add(word.getWord_target());
        }
        conn.close();
    }

    /**
     * dictionary export to database.
     */
    public static void dictionaryExportToDatabase(String wordTarget, String wordExplain, int checkFunction) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        Statement stmt = conn.createStatement();
        int n = listWord.size() - 1;
        if (checkFunction == 0) {
            String sql = "INSERT INTO `tbl_edict`(idx, word, detail) " + "VALUE(?, ?, ?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, n + 1);
                ps.setString(2, wordTarget);
                ps.setString(3, wordExplain);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (checkFunction == 1) {
            ResultSet rs = stmt.executeQuery("delete from `tbl_edict` where word = " + wordTarget + ");");
            String sql1 = "INSERT INTO `tbl_edict`(idx, word, detail) " + "VALUE(?, ?, ?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql1);
                ps.setInt(1, getIndex(wordTarget));
                ps.setString(2, wordTarget);
                ps.setString(3, wordExplain);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ResultSet rs = stmt.executeQuery("delete from `tbl_edict` where word = " + wordTarget + ");");
        }
        conn.close();
    }

    /**
     * get index word target.
     * @return index
     */
    private static int getIndex(String wordTarget) {
        for (int i = 0; i < listWord.size(); i++) {
            if (wordTarget.equalsIgnoreCase(listWord.get(i).getWord_target())) {
                return i;
            }
        }
        return -1;
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