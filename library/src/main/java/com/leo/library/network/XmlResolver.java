package com.leo.library.network;

/**
 * Created by Leo on 2016/8/20.
 */

public class XmlResolver {
    String xmlFileName = "";
    private int LOAD_MODE = 1001;
    public static final int FROM_RES = 1001;
    public static final int FROM_ASSERT = 1002;

    public XmlResolver() {

    }

    public XmlResolver(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public XmlResolver(int loadMode, String xmlFileName) {
        LOAD_MODE = loadMode;
        this.xmlFileName = xmlFileName;
    }

}
