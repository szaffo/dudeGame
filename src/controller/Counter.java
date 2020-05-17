package controller;

import java.util.StringTokenizer;

public class Counter {
    private String text;

    public Counter(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public int countWords() {
        // return text.split("\\s+").length;
        StringTokenizer tokens = new StringTokenizer(text);
        return tokens.countTokens();
    }

    public int countLines() {
        return text.split("\n").length;
    }

    public int countLetters() {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isLetter(text.charAt(i)))
                count++;
        }
        return count;
        // return text.replaceAll("\n", "").replaceAll(" ", "").length();
    }
}