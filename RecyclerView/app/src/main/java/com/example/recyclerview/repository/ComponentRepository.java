package com.example.recyclerview.repository;

import com.example.recyclerview.R;
import com.example.recyclerview.model.Component;

import java.util.ArrayList;
import java.util.List;

public class ComponentRepository {

    public static ComponentRepository sInstance;
    private List<Component> componentList = new ArrayList<>();

    public static ComponentRepository getInstance(){
        if(sInstance == null){
            sInstance = new ComponentRepository();
        }
        return sInstance;
    }

    public List<Component> getComponentList() {
        createComponentList();
        return componentList;
    }

    private void createComponentList(){
        componentList.add(new Component(R.mipmap.ic_launcher, 5, false, "component one"));
        componentList.add(new Component(R.mipmap.ic_launcher, 4, false, "component two"));
        componentList.add(new Component(R.mipmap.ic_launcher, 3, false, "component three"));
        componentList.add(new Component(R.mipmap.ic_launcher, 2, false, "component four"));
        componentList.add(new Component(R.mipmap.ic_launcher, 1, false, "component five"));
        componentList.add(new Component(R.mipmap.ic_launcher, 1, false, "component six"));
        componentList.add(new Component(R.mipmap.ic_launcher, 2, false, "component seven"));
        componentList.add(new Component(R.mipmap.ic_launcher, 3, false, "component eight"));
        componentList.add(new Component(R.mipmap.ic_launcher, 4, false, "component nine"));
        componentList.add(new Component(R.mipmap.ic_launcher, 5, false, "component ten"));
    }
}
