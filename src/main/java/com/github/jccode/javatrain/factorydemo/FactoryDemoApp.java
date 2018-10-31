package com.github.jccode.javatrain.factorydemo;

import com.github.jccode.javatrain.factorydemo.impl.AesEncryptionStrategy;
import com.github.jccode.javatrain.factorydemo.impl.BlowfishEncryptionStrategy;
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

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            EncryptionStrategy aesStrategy=new AesEncryptionStrategy();
            Encryptor aesEncryptor=new Encryptor(aesStrategy);
            aesEncryptor.setPlainText("This is plain text");
            aesEncryptor.encrypt();

            EncryptionStrategy blowfishStrategy=new BlowfishEncryptionStrategy();
            Encryptor blowfishEncryptor=new Encryptor(blowfishStrategy);
            blowfishEncryptor.setPlainText("This is plain text");
            blowfishEncryptor.encrypt();
        };
    }
}
