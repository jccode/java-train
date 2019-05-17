package com.github.jccode.fp.ch03;

import com.github.jccode.fp.common.Effect;
import com.github.jccode.fp.common.Executable;
import com.github.jccode.fp.common.Function;
import com.github.jccode.fp.common.Result;
import static com.github.jccode.fp.common.Result.*;
import static com.github.jccode.fp.common.Case.*;

import java.util.regex.Pattern;

/**
 * FunctionalEmailExample
 *
 * @author 01372461
 */
public class FunctionalEmailExample {

    static class V1 {
        final Pattern emailPattern =
                Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

        final Function<String, Result<String>> emailChecker = email -> {
            if (email == null) {
                return Result.failure("email must not be null");
            } else if (email.isEmpty()) {
                return Result.failure("email must not be empty");
            } else if (emailPattern.matcher(email).matches()) {
                return Result.success(email);
            } else {
                return Result.failure("email " + email + " is invalid.");
            }
        };

        Executable validate(String email) {
            Result<String> result = emailChecker.apply(email);
            return result instanceof Result.Success ?
                    () -> sendVerificationMail(email) :
                    () -> logError(((Result.Failure<String>) result).getErrorMessage());
        }


        void sendVerificationMail(String s) {
            System.out.println("Verification mail sent to " + s);
        }

        private static void logError(String s) {
            System.err.println("Error message logged: " + s);
        }

        public static void main(String[] args) {
            V1 foo = new V1();
            foo.validate("abc@hotmail.com").exec();
            foo.validate("abc").exec();
            foo.validate("").exec();
            foo.validate(null).exec();
        }
    }

    static class V2 {
        final Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
        final Effect<String> success = s -> System.out.println("Verification mail sent to " + s);
        final Effect<String> failure = s ->  System.err.println("Error message logged: " + s);

        final Function<String, Result<String>> emailChecker = email -> {
            if (email == null) {
                return Result.failure("email must not be null");
            } else if (email.isEmpty()) {
                return Result.failure("email must not be empty");
            } else if (emailPattern.matcher(email).matches()) {
                return Result.success(email);
            } else {
                return Result.failure("email " + email + " is invalid.");
            }
        };

        void validate(String email) {
            emailChecker.apply(email).bind(success, failure);
        }

        public static void main(String[] args) {
            V2 foo = new V2();
            foo.validate("abc@hotmail.com");
            foo.validate("abc");
            foo.validate("");
            foo.validate(null);
        }
    }

    static class V3 {
        final Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
        final Effect<String> success = s -> System.out.println("Verification mail sent to " + s);
        final Effect<String> failure = s ->  System.err.println("Error message logged: " + s);

        final Function<String, Result<String>> emailChecker = email -> match (
                mcase(() -> failure("email " + email + " is invalid.")),
                mcase(() -> email == null                        , () -> failure("email must not be null")),
                mcase(email::isEmpty                             , () -> failure("email must not be empty")),
                mcase(() -> emailPattern.matcher(email).matches(), () -> success(email))
        );

        void validate(String email) {
            emailChecker.apply(email).bind(success, failure);
        }

        public static void main(String[] args) {
            V3 foo = new V3();
            foo.validate("abc@hotmail.com");
            foo.validate("abc");
            foo.validate("");
            foo.validate(null);
        }
    }

}
