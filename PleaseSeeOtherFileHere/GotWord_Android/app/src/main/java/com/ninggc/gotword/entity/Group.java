package com.ninggc.gotword.entity;

/**
 * Created by Ning on 12/5/2017 0005.
 */

public class Group {
    private int id;
    private String name;
    private int count;
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (id != group.id) return false;
        if (count != group.count) return false;
        if (name != null ? !name.equals(group.name) : group.name != null) return false;
        return note != null ? note.equals(group.note) : group.note == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + count;
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }
}
