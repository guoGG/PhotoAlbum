package com.example.gjw.albumlibrary.interfaces;

import com.example.gjw.albumlibrary.entity.Images;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guojiawei on 2017/4/24.
 */

public class DataBus {
    private List<OnResultDatasListener> observer = new ArrayList<>();

    private DataBus() {
    }

    private volatile static DataBus instance = null;

    public static synchronized DataBus getInstance() {
        if (instance == null) {
            return instance = new DataBus();
        } else
            return instance;
    }

    public void register(OnResultDatasListener t) {
        observer.add(t);
    }

    public void post(List<Images> data) {
        for (int i = 0; i < observer.size(); i++) {
            observer.get(i).onResultDatas(data);
        }
    }

    public void unRegister(OnResultDatasListener t) {
        for (int i = 0; i < observer.size(); i++) {
            observer.remove(t);
        }
    }

}
