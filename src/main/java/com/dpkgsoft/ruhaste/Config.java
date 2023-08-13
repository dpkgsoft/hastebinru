package com.dpkgsoft.ruhaste;

import java.util.Map;

public class Config {
    private String listen;
    private int port;
    private String dir;
    private int keyLength;
    private long maxLength;
    private String sentryDSN;
    private Map<String, String> docs;

    public String getListen() {
        return listen;
    }

    public void setListen(String listen) {
        this.listen = listen;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getKeyLength() {
        return keyLength;
    }

    public void setKeyLength(int keyLength) {
        this.keyLength = keyLength;
    }

    public long getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(long maxLength) {
        this.maxLength = maxLength;
    }

    public Map<String, String> getDocs() {
        return docs;
    }

    public void setDocs(Map<String, String> docs) {
        this.docs = docs;
    }

    public String getSentryDSN() {
        return sentryDSN;
    }

    public void setSentryDSN(String sentryDSN) {
        this.sentryDSN = sentryDSN;
    }
}
