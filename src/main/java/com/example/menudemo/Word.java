package com.example.menudemo;

public class Word {
    private String word_target;
    private String word_explain;

    /**
     * Constructor Word.
     * @param wordTarget String
     * @param wordExplain String
     */
    public Word(String wordTarget, String wordExplain) {
        this.word_target = wordTarget;
        this.word_explain = wordExplain;
    }

    /**
     * Constructor Word.
     * @param newWord Word
     */
    public Word(Word newWord) {
        this.word_target = newWord.getWord_target();
        this.word_explain = newWord.getWord_explain();
    }

    public String getWord_target() {
        return word_target;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
}
