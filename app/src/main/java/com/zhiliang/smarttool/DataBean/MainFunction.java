package com.zhiliang.smarttool.DataBean;

public class MainFunction implements STBean {
    private String nName;
    private String mPath;

    public MainFunction() {
    }

    public MainFunction(String nName, String funTag) {
        this.nName = nName;
        mPath = funTag;
    }

    public String getnName() {
        return nName;
    }

    public MainFunction setnName(String nName) {
        this.nName = nName;
        return this;
    }

    public String getPath() {
        return mPath;
    }

    public MainFunction setPath(String path) {
        mPath = path;
        return this;
    }
}
