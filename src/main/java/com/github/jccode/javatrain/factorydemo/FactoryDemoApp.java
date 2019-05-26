package com.github.jccode.javatrain.factorydemo;

import com.github.jccode.javatrain.factorydemo.impl.AesEncryptionStrategy;
import com.github.jccode.javatrain.factorydemo.impl.BlowfishEncryptionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * FactoryDemoApp
 *
 * @author 01372461
 */
@SpringBootApplication
public class FactoryDemoApp {

    public static void main(String[] args) {
        SpringApplication.run(FactoryDemoApp.class, args);
    }

    @Autowired
    private EncryptorService encryptorService;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            // Normal strategy pattern
            EncryptionStrategy aesStrategy=new AesEncryptionStrategy();
            Encryptor aesEncryptor=new Encryptor(aesStrategy);
            aesEncryptor.encrypt("This is plain text");

            EncryptionStrategy blowfishStrategy=new BlowfishEncryptionStrategy();
            Encryptor blowfishEncryptor=new Encryptor(blowfishStrategy);
            blowfishEncryptor.encrypt("This is plain text");


            System.out.println();
            System.out.println("============================");
            System.out.println();

            // Spring strategy pattern
            encryptorService.encrypt(EncryptorService.Algorithm.AES, "This is plain text");
            encryptorService.encrypt(EncryptorService.Algorithm.BLOWFISH, "This is plain text");
        };
    }
}
