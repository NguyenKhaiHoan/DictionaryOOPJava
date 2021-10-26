package com.example.menudemo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class DictionaryController extends Dictionary implements Initializable {
    public static int check = 0;

    @FXML
    MenuBar menuBar;

    @FXML
    TextArea txtArea ;

    @FXML
    TextField txtSearch;

    @FXML
    TextField txtAddWordTarget, txtEditWordTarget, txtDeleteWordTarget;

    @FXML
    TextArea txtTranslate;

    @FXML
    TextArea txtAddWordExplain;

    @FXML
    TextArea txtEditWordExplain;

    @FXML
    Button btnSpeak;

    @FXML
    Button btnClose;

    @FXML
    Button btnAbout;

    @FXML
    Button btnTranslate;

    @FXML
    ToggleButton btnExportFile;

    @FXML
    ToggleButton btnExportDatabase;

    @FXML
    public ListView<String> viewListWord = new ListView<String>();

    @FXML
    public void aboutProgram(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("DictionaryOOPJava");
        alert.setHeaderText("Information");
        alert.setContentText("Dictionary Java\nCreated by Hoan & Hop");
        alert.show();
    }

    @FXML
    private void closeProgram(MouseEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("DictionaryOOPJava");
        alert.setHeaderText("Notification");
        alert.setContentText("Do you want to exit program?");
        ButtonType Yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType No = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(Yes, No);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == Yes) {
            SpeechUtils.speak("", true);
            Platform.exit();
            System.exit(0);
        }
    }

    @FXML
    private void exportFile(MouseEvent event) {
        check = 0;
        try {
            DictionaryManagement.insertFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(listWordTarget);
        ObservableList<String> data = FXCollections.observableArrayList(listWordTarget);
        viewListWord.setItems(data);
        btnExportFile.setDisable(true);
        btnExportDatabase.setDisable(false);
    }

    @FXML
    private void speakEnglish(MouseEvent event) throws Exception {
        String wordTarget = txtSearch.getText();
        if (wordTarget.length() == 0) {
            SpeechUtils.speak("Search bar is empty", false);
        } else if (DictionaryManagement.lookupFromGUI(wordTarget).equals("This word does not exist!")) {
            SpeechUtils.speak("This word does not exist", false);
        } else {
            try {
                SpeechUtils.speak(wordTarget, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Lookup word.
     */
    public void lookupWord(KeyEvent event) throws IOException {
        txtArea.clear();
        if(event.getCode() == KeyCode.ENTER) {
            String wordTarget = txtSearch.getText();
            txtArea.setText(DictionaryManagement.lookupFromGUI(wordTarget));
        }
    }

    /**
     * Search list word related.
     */
    public void searchListWordRelated() {
        String wordTarget = txtSearch.getText();
        List<String> searchListWord = DictionaryManagement.searchFromGUI(wordTarget);
        ObservableList<String> list = FXCollections.observableArrayList(searchListWord);
        viewListWord.setItems(list);
        Dictionary.listWordRelated.clear();
    }

    /**
     * Click word in listview to show word explain.
     */
    public void clickShowWordExplain (MouseEvent e) {
        txtSearch.setText(viewListWord.getSelectionModel().getSelectedItem());
        txtArea.setText(DictionaryManagement.lookupFromGUI(viewListWord.getSelectionModel().getSelectedItem()));
    }

    /**
     * Function add word.
     */
    public void addWordFunction(KeyEvent event) throws SQLException {
        String wordTarget = txtAddWordTarget.getText();
        String wordExplain = txtAddWordExplain.getText();
        if (event.getCode() == KeyCode.F1) {
            if (wordTarget.length() != 0 && wordExplain.length() != 0) {
                if (DictionaryManagement.insertFromGUI(wordTarget, wordExplain)) {
                    System.out.println("Insert successful!");
                    txtAddWordTarget.clear();
                    txtAddWordExplain.clear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("DictionaryOOPJava");
                    alert.setHeaderText("Warning");
                    alert.setContentText("This word is existed!");
                    alert.show();
                    return;
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("DictionaryOOPJava");
                alert.setHeaderText("Information");
                alert.setContentText("You added " + wordTarget + " and its meaning is " + wordExplain + " successful!");
                alert.show();
            }
        }
    }

    /**
     * Function edit word.
     */
    public void editWordFunction(KeyEvent event) throws SQLException {
        String wordTarget = txtEditWordTarget.getText();
        String wordExplain = txtEditWordExplain.getText();
        if (event.getCode() == KeyCode.F1) {
            if (wordTarget.length() != 0 && wordExplain.length() != 0) {
                if (DictionaryManagement.editFromGUI(wordTarget, wordExplain)) {
                    System.out.println("Edit successful!");
                    txtEditWordTarget.clear();
                    txtEditWordExplain.clear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("DictionaryOOPJava");
                    alert.setHeaderText("Warning");
                    alert.setContentText("This word does not exist!");
                    alert.show();
                    return;
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("DictionaryOOPJava");
                alert.setHeaderText("Information");
                alert.setContentText("You fixed " + wordTarget + " and its meaning is " + wordExplain + " successful!");
                alert.show();
            }
        }
    }

    /**
     * Function delete word.
     */
    public void deleteWordFunction(KeyEvent event) throws SQLException {
        String wordTarget = txtDeleteWordTarget.getText();
        if (event.getCode() == KeyCode.F1) {
            if (DictionaryManagement.lookupCheck(wordTarget)) {
                System.out.println("Delete successful!");
                txtAddWordTarget.clear();
                txtAddWordExplain.clear();
                txtSearch.clear();
                txtArea.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("DictionaryOOPJava");
                alert.setHeaderText("Warning");
                alert.setContentText("This word does not exist!");
                alert.show();
                return;
            }
            List<String> newList = DictionaryManagement.deleteFromGUI(wordTarget);
            ObservableList<String> data = FXCollections.observableArrayList(newList);
            viewListWord.setItems(data);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DictionaryOOPJava");
            alert.setHeaderText("Information");
            alert.setContentText("You deleted " + wordTarget + " successful!");
            alert.show();
        }
    }

    /**
     * Advance translate text.
     */
    @FXML
    public void googleTranslateFunction(MouseEvent event) {
        String textTranslated = txtTranslate.getText();
        if (textTranslated.length() != 0) {
            try {
                textTranslated = GoogleTranslate.translate("VI", textTranslated);
            } catch (Exception e) {
                e.printStackTrace();
            }
            txtArea.setText(textTranslated);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("DictionaryOOPJava");
            alert.setHeaderText("Warning");
            alert.setContentText("You have not entered words or text!");
            alert.show();
        }
    }

    /**
     * initialize.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Tooltip tooltiptxtSearch = new Tooltip("Nơi nhập từ cần tìm kiếm");
        txtSearch.setTooltip(tooltiptxtSearch);
        Tooltip tooltipbtnSpeak = new Tooltip("Nhấn để phát âm từ đang tìm kiếm");
        btnSpeak.setTooltip(tooltipbtnSpeak);
        Tooltip tooltiptxtArea = new Tooltip("Nơi hiểm thị nghĩa của từ đang tìm kiếm");
        txtArea.setTooltip(tooltiptxtArea);
        Tooltip tooltipmenuBar = new Tooltip("Nơi hiểm thị các chức năng của từ điển");
        menuBar.setTooltip(tooltipmenuBar);
        Tooltip tooltiplistView = new Tooltip("Nơi hiểm thị tất cả các từ và từ có liên quan");
        viewListWord.setTooltip(tooltiptxtArea);
        Tooltip tooltipbtnAbout = new Tooltip("Nhấn để hiển thị thông tin project DictionaryOOpJava");
        btnAbout.setTooltip(tooltipbtnAbout);
        Tooltip tooltipbtnClose = new Tooltip("Nhấn để thoát chương trình");
        btnClose.setTooltip(tooltipbtnAbout);

        try {
            DictionaryManagement.insertFromFile();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("DictionaryOOPJava");
            alert.setHeaderText("Warning");
            alert.setContentText("No database connection!");
            alert.show();
        }
        Collections.sort(listWordTarget);
        ObservableList<String> data = FXCollections.observableArrayList(listWordTarget);
        viewListWord.setItems(data);
    }
}