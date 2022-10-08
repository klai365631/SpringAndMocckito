package com.SkyPro.SpringAndMocckito.Service;

import com.SkyPro.SpringAndMocckito.exeption.IncorrectNameException;
import com.SkyPro.SpringAndMocckito.exeption.IncorrectSurnameException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {

    public String validateName(String name) {
        if (StringUtils.isAlpha(name)) {
            return StringUtils.capitalize(name.toLowerCase());
        }
        throw new IncorrectNameException();

    }
    public String validateSurname(String surname) {
        String[] surnames = surname.split("-");
        for (int i = 0; i < surnames.length; i++) {
            if (StringUtils.isAlpha(surnames[i])){
                surnames[i] = StringUtils.capitalize(surnames[i].toLowerCase());
            }else {
                throw new IncorrectSurnameException();
            }
            surnames[i] = StringUtils.capitalize(surnames[i].toLowerCase());

        }
        return String.join("-", surnames);

    }


}
