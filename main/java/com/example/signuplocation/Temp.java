package com.example.signuplocation;

import com.google.gson.annotations.SerializedName;



public class Temp {

    @SerializedName("main")
    Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
