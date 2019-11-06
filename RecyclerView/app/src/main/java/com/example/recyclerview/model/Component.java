package com.example.recyclerview.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.recyclerview.R;

public class Component extends BaseObservable {
    private int imageId;
    private float rating;
    private boolean checked;
    private String stringData;

    Component(){}

    public Component(int imageId, int rating, boolean checked, String stringData) {
        this.imageId = imageId;
        this.rating = rating;
        this.checked = checked;
        this.stringData = stringData;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Bindable
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
        notifyPropertyChanged(R.id.rating_bar);
    }

    @Bindable
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        notifyPropertyChanged(R.id.radio_button);
    }

    @Bindable
    public String getStringData() {
        return stringData;
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
        notifyPropertyChanged(R.id.text_data);
    }
}
