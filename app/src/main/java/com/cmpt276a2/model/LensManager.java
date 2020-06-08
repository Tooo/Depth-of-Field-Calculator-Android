package com.cmpt276a2.model;

import com.cmpt276a2.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LensManager implements Iterable<Lens>{
    private List<Lens> lens = new ArrayList<>();

    // Refer to Brain Fraser video: Singleton Model: Android Programming
    private static LensManager instance;
    private LensManager() {

    }
    public static LensManager getInstance() {
        if (instance == null) {
            instance = new LensManager();
            instance.add(new Lens("Canon", 1.8, 50, R.drawable.len_blue));
            instance.add(new Lens("Tamron", 2.8, 90, R.drawable.len_green));
            instance.add(new Lens("Sigma", 2.8, 200, R.drawable.len_orange));
            instance.add(new Lens("Nikon", 4, 200, R.drawable.len_yellow));
        }
        return instance;
    }

    public void add(Lens len) {
        lens.add(len);
    }

    public void remove(Lens len) {
        lens.remove(len);
    }

    public Lens get(int index) {
        return lens.get(index);
    }

    public int size() {
        return lens.size();
    }

    public List<Lens> getLens() {
        return lens;
    }

    @Override
    public Iterator<Lens> iterator() {
        return lens.iterator();
    }
}
