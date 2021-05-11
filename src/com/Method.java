package com;

public abstract class Method {
    public KnowledgeBase kb;

    public String query;

    public Method(KnowledgeBase kb) {

    }

    abstract public StringBuilder testAsk();

    abstract public boolean checkQuery();

}