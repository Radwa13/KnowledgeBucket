package com.example.radwa.knowledgebucket.model;

public class Fact {
    public Fact(int id, String fact) {
        this.id = id;
        this.fact = fact;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public Fact() {
    }

    private String fact;
}
