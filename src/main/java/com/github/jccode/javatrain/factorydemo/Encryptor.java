package com.github.jccode.javatrain.factorydemo;

public class Encryptor {

    private EncryptionStrategy strategy;

    public Encryptor(EncryptionStrategy strategy) {
        this.strategy = strategy;
    }

    public void encrypt(String plainText) {
        strategy.encryptData(plainText);
    }

}