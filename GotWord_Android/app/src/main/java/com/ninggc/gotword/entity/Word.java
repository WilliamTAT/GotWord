package com.ninggc.gotword.entity;

/**
 * Created by Ning on 12/5/2017 0005.
 */

public class Word {
    private String word;
    private String explains;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Word word1 = (Word) o;

        if (word != null ? !word.equals(word1.word) : word1.word != null) {
            return false;
        }
        return explains != null ? explains.equals(word1.explains) : word1.explains == null;
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + (explains != null ? explains.hashCode() : 0);
        return result;
    }
}
