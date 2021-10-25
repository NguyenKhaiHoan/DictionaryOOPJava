package com.example.menudemo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    /**
     * Main function.
     */
    public static void main(String[] args) throws IOException, SQLException {
        Scanner sc = new Scanner(System.in);
        int functionID = 0;
        boolean flag = true;
        DictionaryManagement.insertFromDB();

        do {
            showMenu();

            try {
                functionID = sc.nextInt();
                sc.nextLine();

                switch (functionID) {
                    case 1:
                        System.out.println(DictionaryManagement.insertFromCommandline());
                        break;
                    case 2:
                        System.out.println(DictionaryManagement.editFromCommandline());
                        break;
                    case 3:
                        System.out.println(DictionaryManagement.deleteFromCommandline());
                        break;
                    case 4:
                        Dictionary.showAllWord();
                        break;
                    case 5:
                        System.out.println(DictionaryManagement.dictionaryLookup());
                        break;
                    case 6:
                        DictionaryCommandLine.dictionarySearcher();
                        break;
                    case 7:
                        googleTranslate();
                        break;
                    case 8:
                        speakEnglish();
                        break;
                    case 9:
                        DictionaryApplication.runApplication();
                        break;
                    case 10:
                    default:
                        flag = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error! Please try again!");
                sc.nextLine();
            }
        } while (flag);

        sc.close();
    }

    /**
     * Show menu function.
     */
    public static void showMenu() {
        System.out.println("===================== Dictionary Java =====================");
        System.out.println("1. Insert word");
        System.out.println("2. Edit word");
        System.out.println("3. Delete word");
        System.out.println("4. All word");
        System.out.println("5. Lookup word");
        System.out.println("6. Search word");
        System.out.println("7. Translate word or text by google translate");
        System.out.println("8. Speak word or text ");
        System.out.println("9. Run application");
        System.out.println("10. Exit");
        System.out.println("Your choice [1 - 10]: ");
    }

    /**
     * googleTranslate.
     */
    public static void googleTranslate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your word or text:");
        String textTranslated = sc.nextLine();
        if (textTranslated.length() != 0) {
            try {
                textTranslated = GoogleTranslate.translate("VI", textTranslated);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Translate: " + textTranslated);
        } else {
            System.out.println("You have not entered words or text!");
        }
    }

    /**
     * speakEnglish.
     */
    public static void speakEnglish() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your word or text:");
        String wordTarget = sc.nextLine();
        if (wordTarget.length() == 0) {
            System.out.println("Word or text is empty");
        } else {
            try {
                SpeechUtils.speak(wordTarget, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
