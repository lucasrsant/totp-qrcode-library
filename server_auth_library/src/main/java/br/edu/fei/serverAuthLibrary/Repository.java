package br.edu.fei.serverAuthLibrary;

/***
 * Represents a data repository
 * @param <U> The type of repository identifier
 * @param <V> The type of repository data
 */
public interface Repository<U, V> {
    /***
     * This method inserts or update, if a record with the specified identifier already exists.
     * @param uniqueIdentifier The unique identifier of the record
     * @param value The data to be stored
     */
    void insertOrUpdate(U uniqueIdentifier, V value);

    /***
     * This method removes the data associated with the specified unique identifier.
     * @param uniqueIdentifier The unique identifier of the data to be removed.
     */
    void remove(U uniqueIdentifier);

    /***
     * Checks if there is some data associated with the provided identifier.
     * @param uniqueIdentifier The unique identifier
     * @return <code>true</code> if there is data associated with the provided identifier, otherwise returns <code>false</code>.
     */
    boolean contains(U uniqueIdentifier);

    /***
     * Retrieves the data with the specified identifier associated.
     * @param uniqueIdentifier The unique identifier associated to the data.
     * @return The data associated with the identifier
     */
    V get(U uniqueIdentifier);
}
