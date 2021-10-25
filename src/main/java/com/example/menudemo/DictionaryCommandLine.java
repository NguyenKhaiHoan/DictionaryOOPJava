package com.example.menudemo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class DictionaryCommandLine {
    /**
     * Basic function in dictionary.
     */
    public static void dictionaryBasic(String wordTarget, String wordExplain) throws FileNotFoundException, SQLException {
        DictionaryManagement.insertFromCommandline();
        Dictionary.showAllWord();
    }

    /**
     * Advanced function in dictionary.
     */
    public static void dictionaryAdvanced(String wordTarget) throws IOException {
        DictionaryManagement.insertFromFile();
        Dictionary.showAllWord();
        DictionaryManagement.dictionaryLookup();
    }
}
