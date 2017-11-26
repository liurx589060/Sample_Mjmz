package com.mjmz.lrx.sample_mjmz.bean;

/**
 * Created by Administrator on 2017/11/4.
 */

public class HeadsetBean {
    private boolean isInsert;
    private int volume = 0;
    private boolean isPause;

    public boolean isInsert() {
        return isInsert;
    }

    public void setInsert(boolean insert) {
        isInsert = insert;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }
}
