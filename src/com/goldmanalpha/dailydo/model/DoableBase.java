package com.goldmanalpha.dailydo.model;

import java.util.Date;

public abstract class DoableBase {

    int id;
    Date dateCreated;
    Date dateModified;

    public int getId() {
        return id;
    }

    public Date getDateCreated() {

        if (dateCreated == null)
            dateCreated = new Date();

        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        if (this.dateCreated != null)
            throw new IllegalArgumentException("Created date cannot be reset");

        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    DoableBase(int id) {
        this.id = id;
    }

    DoableBase() {
        this(0);
        setDateModified(new Date());
    }

    @Override
    public String toString() {

        return String.format("%d", id);
    }
}
