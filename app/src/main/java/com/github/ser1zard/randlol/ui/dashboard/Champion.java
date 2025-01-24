package com.github.ser1zard.randlol.ui.dashboard;

import android.graphics.drawable.Drawable;

public class Champion {
  private final Drawable image;
  private final String name;

  public Champion(String name, Drawable image) {
    this.name = name;
    this.image = image;
  }

  public Drawable getImage() {
    return image;
  }

  public String getName() {
    return name;
  }
}
