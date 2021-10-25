package com.example.menudemo;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    public static ArrayList<Word> listWord = new ArrayList<>(); // list of words target, words explain
    public static List<String> listWordTarget = new ArrayList<>(); //  list of only words target
    public static List<String> listWordRelated = new ArrayList<>(); //  List of related words

    /**
     * Show all word.
     */
    public static void showAllWord() throws FileNotFoundException {
        System.out.println("No" + "   |English\t" + "\t\t\t\t\t|Vietnamese");
        for (int i = 0; i < listWord.size(); i++) {
            String wordTarget = listWord.get(i).getWord_target();
            String wordExplain = listWord.get(i).getWord_explain();
            System.out.printf("%d    |%s\t\t\t\t\t|%s\n", i, wordTarget, wordExplain);
        }
    }

    /**
     * Show list word related.
     */
    public static void showListWordRelated(List<String> list) {
        System.out.println("No" + "   |English\t");
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d    |%s\t\t\t\t\n", i, listWordRelated.get(i));
        }
    }
}
