package com.example.todolist;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_tasks")
public class Task implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    @ColumnInfo(name = "completed")
    private boolean isCompleted;

    private String last_update;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeByte(this.isCompleted ? (byte) 1 : (byte) 0);
        dest.writeString(this.last_update);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.title = source.readString();
        this.isCompleted = source.readByte() != 0;
        this.last_update = source.readString();
    }

    public Task() {
    }

    protected Task(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.isCompleted = in.readByte() != 0;
        this.last_update = in.readString();
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
