package com.jack.helloen.bean;

public class ItemClass {
    public int cIcon;
    public String cIndex;
    public String cTitle;

    public int getcIcon() {
        return cIcon;
    }

    public void setcIcon(int cIcon) {
        this.cIcon = cIcon;
    }

    public String getcIndex() {
        return cIndex;
    }

    public void setcIndex(String cIndex) {
        this.cIndex = cIndex;
    }

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    public ItemClass(int cIcon, String cIndex, String cTitle) {
        this.cIcon = cIcon;
        this.cIndex = cIndex;
        this.cTitle = cTitle;
    }
}