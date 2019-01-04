package br.edu.fei.serverAuthLibrary;

/***
 * This class represents an encrypted amount of data with its content and session key
 */
public class EncryptedPayload {
    /***
     * The content of payload. It's encrypted with the <code>sessionKey</code> and encoded in Base64 format.
     */
    /*package*/ String content;

    /***
     * The key used to encrypt the content. It's an AES-256 key encoded in Base64 format.
     */
    /*package*/ String sessionKey;
}
