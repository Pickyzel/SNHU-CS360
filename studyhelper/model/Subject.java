package com.zybooks.studyhelper.model;

import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class Subject {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")

    private long mId;
    private String mText;

    @ColumnInfo(name = "updated")
    private long mUpdateTime;

    public Subject(@NonNull String text) {
        mText = text;
        mUpdateTime = System.currentTimeMillis();
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getText() {
        return mText;
    }

    public void setText(String subject) {
        mText = subject;
    }

    public long getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(long updateTime) {
        mUpdateTime = updateTime;
    }
}