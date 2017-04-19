package com.example.gjw.albumlibrary.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by guojiawei on 2017/4/14.
 */

public class SerMap implements Serializable {
    private Map<String, List<Images>> map;

    public Map<String, List<Images>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<Images>> map) {
        this.map = map;
    }
}
