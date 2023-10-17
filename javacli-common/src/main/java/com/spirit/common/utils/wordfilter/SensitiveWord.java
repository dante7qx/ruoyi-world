package com.spirit.common.utils.wordfilter;

public class SensitiveWord implements Comparable<SensitiveWord>{
    public char c;
    public SensitiveWordList next = null;

    public SensitiveWord(char c){
        this.c = c;
    }

    @Override
    public int compareTo(SensitiveWord word) {
        return c - word.c;
    }

    public String toString(){
        return c + "(" + (next == null ? null : next.size()) + ")";
    }
}
