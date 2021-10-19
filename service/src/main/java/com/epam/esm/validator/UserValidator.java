package com.epam.esm.validator;

import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator {
    private static final String USERNAME_PATTERN="^[^._ ](?:[\\w-]|\\.[\\w-])+[^._ ]$";
    private static final String PASSWORD_PATTERN="^[^._ ](?:[\\w-]|\\.[\\w-])+[^._ ]$";

    public boolean isValid(User user) {
        return isNotNull(user)
                && isUsernameValid(user.getUsername())
                && isPasswordValid(user.getPassword());
    }

    public boolean isUsernameValid(String username) {
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean isNotNull(User user) {
        return user != null;
    }

}
