package br.edu.fei.serverAuthLibrary;

public enum HashingAlgorithm {
    SHA_1("HmacSHA1"),
    SHA_256("HmacSHA256"),
    SHA_512("HmacSHA512");

    private final String value;

    HashingAlgorithm(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
