package com.cmpt276a2.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LensManager implements Iterable<Lens>{
    private List<Lens> lens = new ArrayList<>();

    private static LensManager instance;
    private LensManager() {

    }
    public static LensManager getInstance() {
        if (instance == null) {
            instance = new LensManager();
        }
        return instance;
    }

    public void add(Lens len) {
        lens.add(len);
    }

    public Lens get(int index) {
        return lens.get(index);
    }

    public List<Lens> getLens() {
        return lens;
    }

    @Override
    public Iterator<Lens> iterator() {
        return lens.iterator();
    }
}
