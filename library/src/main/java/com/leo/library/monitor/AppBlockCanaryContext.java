package com.leo.library.monitor;

import com.github.moduth.blockcanary.BlockCanaryContext;

import java.io.File;

/**
 * Created by leo on 2017/2/17.
 */

public class AppBlockCanaryContext extends BlockCanaryContext {
    @Override
    public String getQualifier() {
        return null;
    }

    @Override
    public String getUid() {
        return null;
    }

    @Override
    public String getNetworkType() {
        return null;
    }

    @Override
    public int getConfigDuration() {
        return 0;
    }

    @Override
    public int getConfigBlockThreshold() {
        return 0;
    }

    @Override
    public boolean isNeedDisplay() {
        return false;
    }

    @Override
    public String getLogPath() {
        return null;
    }

    @Override
    public boolean zipLogFile(File[] files, File file) {
        return false;
    }

    @Override
    public void uploadLogFile(File file) {

    }
}
