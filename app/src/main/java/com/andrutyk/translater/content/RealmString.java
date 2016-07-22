package com.andrutyk.translater.content;

import io.realm.RealmObject;

/**
 * Created by admin on 22.07.2016.
 */
public class RealmString extends RealmObject{

    private String val;

    public RealmString() {
    }

    public RealmString(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String value) {
        this.val = value;
    }
}
