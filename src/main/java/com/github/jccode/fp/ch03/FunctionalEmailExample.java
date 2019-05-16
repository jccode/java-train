package com.github.jccode.fp.ch03;

import com.github.jccode.fp.common.Function;
import com.github.jccode.fp.common.Result;

import java.util.regex.Pattern;

/**
 * FunctionalEmailExample
 *
 * @author 01372461
 */
public class FunctionalEmailExample {

    final Pattern emailPattern =
            Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    final Function<String, Result<String>> emailChecker = email -> {
        if (email == null) {
            return Result.failure("email must not be null");
        } else if (emailPattern.matcher(email).matches()) {
            return Result.success(email);
        } else {
            return Result.failure("email " + email + " is invalid.");
        }
    };

    void testMail(String email) {
        Result<String> result = emailChecker.apply(email);
        if (result instanceof Result.Success) {
            sendVerificationMail(email);
        } else {
            logError(((Result.Failure<String>) result).getErrorMessage());
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
        foo.testMail("abc@hotmail.com");
        foo.testMail("abc");
        foo.testMail("");
        foo.testMail(null);
    }
}
