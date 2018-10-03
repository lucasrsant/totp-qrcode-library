package br.edu.fei.server;

public interface Repository<U, V> {
    void insertOrUpdate(U uniqueIdentifier, V value);
    void remove(U uniqueIdentifier);
    boolean contains(U uniqueIdentifier);
    V get(U uniqueIdentifier);
}
