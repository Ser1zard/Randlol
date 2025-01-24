package com.github.ser1zard.randlol.ui.home;

public class PreferredLane {
  private String champion;
  private String lane;

  public PreferredLane(String champion, String lane) {
    this.champion = champion;
    this.lane = lane;
  }

  public String getChampion() {
    return champion;
  }

  public String getLane() {
    return lane;
  }
}
