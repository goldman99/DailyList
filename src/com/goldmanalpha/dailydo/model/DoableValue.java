package com.goldmanalpha.dailydo.model;

import android.content.Context;
import com.com.goldmanalpha.dailydo.db.DoableItemTableAdapter;

import java.sql.Time;
import java.util.Date;

public class DoableValue extends DoableBase{
    private String description;

    //used as start or only time
    private Time fromTime;
    private Time toTime;

    private Integer amount = 0;

    private Date appliesToDate;
    private Integer doableItemId = 0;

    public DoableValue()
    {
    }

    public DoableValue(int id)
    {
        super(id);
    }

    public Date getAppliesToDate() {
        return appliesToDate;
    }

    public void setAppliesToDate(Date appliesToDate) {
        this.appliesToDate = new Date(appliesToDate.getYear(), appliesToDate.getMonth(), appliesToDate.getDate());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Time getFromTime() {
        return fromTime;
    }

    public void setFromTime(Time fromTime) {
        this.fromTime = fromTime;
    }

    public Time getToTime() {
        return toTime;
    }

    public void setToTime(Time toTime) {
        this.toTime = toTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getDoableItemId() {
        return doableItemId;
    }

    public void setDoableItemId(Integer doableItemId) {
        this.doableItemId = doableItemId;
    }


    DoableItem item;
    public DoableItem getItem(Context context) {
        if (item == null)
        {
            item = new DoableItemTableAdapter(context).get(doableItemId);
        }

        return item;
    }

}
