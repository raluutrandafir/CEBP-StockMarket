package com.stock;

import java.util.ArrayList;
import java.util.List;
import sun.awt.Mutex;

public class ProtectedList<K> {
    private ArrayList<K> list = new ArrayList();
    private static int nrReaders = 0;
    private final Mutex read = new Mutex();
    private final Mutex write = new Mutex();

    public ProtectedList() {
    }

    private void startRead() {
        this.read.lock();
        synchronized(this) {
            ++nrReaders;
            if (nrReaders == 1) {
                this.write.lock();
            }
        }

        this.read.unlock();
    }

    private void endRead() {
        this.read.lock();
        synchronized(this) {
            --nrReaders;
            if (nrReaders == 0) {
                this.write.unlock();
            }
        }

        this.read.unlock();
    }

    private void startWrite() {
        this.write.lock();
    }

    private void endWrite() {
        this.write.unlock();
    }

    public List<K> getList() {
        this.startRead();
        List<K> l = new ArrayList(this.list);
        this.endRead();
        return l;
    }

    public void add(K t) {
        this.startWrite();
        this.list.add(t);
        this.endWrite();
    }

    public boolean contains(K t) {
        this.startRead();
        boolean answer = this.list.contains(t);
        this.endRead();
        return answer;
    }

    public boolean remove(K t) {
        this.startWrite();
        boolean result = this.list.remove(t);
        this.endWrite();
        return result;
    }
}
