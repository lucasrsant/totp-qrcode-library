package br.edu.fei.server;

import java.util.HashMap;

public class InMemoryRepositoryImpl<U, V> implements Repository<U, V> {

    private HashMap<U, V> data;

    public InMemoryRepositoryImpl() {
        this.data = new HashMap<>();
    }

    @Override
    public void insertOrUpdate(U uniqueIdentifier, V value) {
        data.put(uniqueIdentifier, value);
    }

    @Override
    public void remove(U uniqueIdentifier) {
        data.remove(uniqueIdentifier);
    }

    @Override
    public boolean contains(U uniqueIdentifier) {
        return data.containsKey(uniqueIdentifier);
    }

    @Override
    public V get(U uniqueIdentifier) {
        return data.get(uniqueIdentifier);
    }
}
