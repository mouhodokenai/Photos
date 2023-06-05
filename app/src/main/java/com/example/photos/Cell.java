package com.example.photos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Cell implements Parcelable {
    private String title;
    private String path;

    protected Cell(Parcel in) {
        title = in.readString();
        path = in.readString();
    }

    public Cell() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(path);
    }


    public static final Creator<Cell> CREATOR = new Creator<Cell>() {
        @Override
        public Cell createFromParcel(Parcel in) {
            return new Cell(in);
        }

        @Override
        public Cell[] newArray(int size) {
            return new Cell[size];
        }
    };
}
