package com.example.gjw.albumlibrary.entity;

import java.io.Serializable;

/**
 * Created by guojiawei on 2017/4/13.
 */

public class Images implements Serializable {
    private String name;
    private String path;
    private boolean isChecked = false;
    private String folderName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
