package com.github.jccode.fp.ch03;

import java.util.regex.Pattern;

/**
 * ImperativeEmailExample
 *
 * @author 01372461
 */
public class ImperativeEmailExample {

    final Pattern emailPattern =
            Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    void testMail(String email) {
        if (emailPattern.matcher(email).matches()) {
            sendVerificationMail(email);
        } else {
            logError("email " + email + " is invalid.");
        }
    }

    void sendVerificationMail(String s) {
        System.out.println("Verification mail sent to " + s);
    }

    private static void logError(String s) {
        System.err.println("Error message logged: " + s);
    }

    public static void main(String[] args) {
        ImperativeEmailExample foo = new ImperativeEmailExample();
        foo.testMail("abc");
        foo.testMail("abc@hotmail.com");
    }
}
