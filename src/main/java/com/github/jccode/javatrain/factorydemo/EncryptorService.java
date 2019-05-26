package com.github.jccode.javatrain.factorydemo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * EncryptorService
 *
 * @author 01372461
 */
@Service
public class EncryptorService implements ApplicationContextAware {

    private ApplicationContext context;

    public void encrypt(Algorithm alg, String plainText) {
        getStrategy(alg).encryptData(plainText);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private EncryptionStrategy getStrategy(Algorithm alg) {
        return (EncryptionStrategy) context.getBean(alg.value+"EncryptionStrategy");
    }


    public enum Algorithm {
        AES("aes"), BLOWFISH("blowfish");

        private String value;

        Algorithm(String value) {
            this.value = value;
        }

    }
}
