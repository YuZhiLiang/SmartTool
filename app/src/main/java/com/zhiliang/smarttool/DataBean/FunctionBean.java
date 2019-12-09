package com.zhiliang.smarttool.DataBean;

public class FunctionBean implements STBean {
    public static final int sProcessMode_Route = 0;
    public static final int sProcessMode_Custom = 1;

    private String mName;
    private int mProcessMode = sProcessMode_Route;
    private String mPath;
    private String mDes;

    public FunctionBean() {
    }

    public FunctionBean(String name) {
        this.mName = name;
    }

    public FunctionBean(String name, String funTag) {
        this.mName = name;
        mPath = funTag;
    }

    public String getName() {
        return mName;
    }

    public FunctionBean setName(String name) {
        this.mName = name;
        return this;
    }

    public String getPath() {
        return mPath;
    }

    public FunctionBean setPath(String path) {
        mPath = path;
        return this;
    }

    public int getProcessMode() {
        return mProcessMode;
    }

    public FunctionBean setProcessMode(int processMode) {
        mProcessMode = processMode;
        return this;
    }

    public String getDes() {
        return mDes;
    }

    public FunctionBean setDes(String des) {
        mDes = des;
        return this;
    }
}
