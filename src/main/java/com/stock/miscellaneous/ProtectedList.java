package com.stock.miscellaneous;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ProtectedList<K> {
    private CopyOnWriteArrayList<K> list;
    private final List<K> unmodifiableList;
    private volatile int nrReaders = 0;
    //private final Lock  read = new ReentrantLock ();
    //private final Lock  write = new ReentrantLock ();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public ProtectedList() {
        this.list = new CopyOnWriteArrayList<K>();
        unmodifiableList =  Collections.unmodifiableList(list);
    }

    /*private void startRead() {
        //this.read.lock();
        lock.readLock().lock();
        synchronized(this) {
            ++nrReaders;
            if (nrReaders == 1) {
                lock.writeLock().lock();
                //this.write.lock();
            }
        }

        //this.read.unlock();
        lock.readLock().unlock();
    }*/

   /* private void endRead() {
        //this.read.lock();
        lock.readLock().lock();
        synchronized(this) {
            --nrReaders;
            if (nrReaders == 0) {
                lock.writeLock().lock();
               // this.write.unlock();
            }
        }
        lock.readLock().unlock();
        //this.read.unlock();
    }*/

    /*private void startWrite() {
        lock.writeLock().lock();
        //this.write.lock();
    }

    private void endWrite() {
        lock.writeLock().unlock();
        //this.write.unlock();
    }*/

    public List<K> getList() {
        //this.startRead();
        //ArrayList<K> l = new ArrayList<>(this.list);
        //this.endRead();
        return unmodifiableList;
    }

    public void add(K t) {
        //this.startWrite();
        list.add(t);
       // this.endWrite();
    }

    public boolean contains(K t) {
        //this.startRead();
        boolean answer = unmodifiableList.contains(t);
        //this.endRead();
        return answer;
    }

    public boolean remove(K t) {
        //this.startWrite();
        //boolean result = this.list.remove(t);
       // this.endWrite();
       // return result;
        for (K p : list) {
            if (p.equals(t)) list.remove(p);
            return true;
        }
        return false;
    }
}
