package no.knowit.backtobasics.domain;

public class IdCounter {
    int id = 0;

    public int getNextId() {
        return ++id;
    }
}
