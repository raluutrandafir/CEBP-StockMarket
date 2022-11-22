package com.stock.miscellaneous;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProtectedList<K> {
    private ArrayList<K> list;
    private static int nrReaders = 0;
    private final Lock  read = new ReentrantLock ();
    private final Lock  write = new ReentrantLock ();

    public ProtectedList() {
        this.list = new ArrayList<>();
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

    public ArrayList<K> getList() {
        this.startRead();
        ArrayList<K> l = new ArrayList<>(this.list);
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
