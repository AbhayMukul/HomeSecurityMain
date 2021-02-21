package com.example.homesecuritymain.citizen.Model;

import android.content.Intent;

public class ModelRecyclerViewCitizenMainActivity {
    int Icon;
    String Name;
    String Background;
    Intent intent;

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public ModelRecyclerViewCitizenMainActivity(int icon, String name, String background, Intent intent) {
        Icon = icon;
        Name = name;
        Background = background;
        this.intent = intent;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBackground() {
        return Background;
    }

    public void setBackground(String background) {
        Background = background;
    }

    public ModelRecyclerViewCitizenMainActivity() {
    }

}
