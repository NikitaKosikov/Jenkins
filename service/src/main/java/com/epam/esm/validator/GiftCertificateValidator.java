package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.InvalidDuration;
import com.epam.esm.exception.InvalidNameOfCertificate;
import com.epam.esm.exception.InvalidPrice;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GiftCertificateValidator {

    private static final String CERTIFICATE_ERROR_CODE = "999";
    private static final String CERTIFICATE_NAME_IS_EMPTY = "giftCertificate.name.empty";
    private static final String CERTIFICATE_PRICE_IS_EMPTY = "giftCertificate.price.empty";
    private static final String CERTIFICATE_DURATION_IS_EMPTY = "giftCertificate.duration.empty";
    private static final String CERTIFICATE_PRICE_WRONG_FORMAT = "giftCertificate.price.incorrectFormat";
    private static final String CERTIFICATE_DURATION_WRONG_FORMAT = "giftCertificate.duration.incorrect.numberFormat";


    public void validate(GiftCertificate giftCertificate){
        isValidName(giftCertificate.getName());
        isValidPrice(giftCertificate.getPrice());
        isValidDuration(giftCertificate.getDuration());
    }

    public void isValidName(String certificateName){
        if ("".equals(certificateName) || certificateName == null){
            throw new InvalidNameOfCertificate(CERTIFICATE_NAME_IS_EMPTY, CERTIFICATE_ERROR_CODE);
        }
    }

    public void isValidPrice(BigDecimal price){
        if (price == null || "".equals(price.toString())){
            throw new InvalidPrice(CERTIFICATE_PRICE_IS_EMPTY, CERTIFICATE_ERROR_CODE);
        }

        Pattern pricePattern = Pattern.compile("[\\d]+.[\\d]{2}");
        Matcher priceMatcher = pricePattern.matcher(price.toString());

        if (priceMatcher.find()){
            int length = priceMatcher.end() - priceMatcher.start();

            if (price.toString().length()!=length){
                throw new InvalidPrice( CERTIFICATE_PRICE_WRONG_FORMAT, CERTIFICATE_ERROR_CODE);
            }
        }

    }

    public void isValidDuration(int duration){
        if ("".equals(String.valueOf(duration))){
            throw new InvalidDuration(CERTIFICATE_DURATION_IS_EMPTY, CERTIFICATE_ERROR_CODE);
        }

        Pattern durationPattern = Pattern.compile("\\d+");
        Matcher durationMatcher = durationPattern.matcher(String.valueOf(duration));



        if (durationMatcher.find()){
            int length = durationMatcher.end() - durationMatcher.start();

            if (String.valueOf(duration).length()!=length){
                throw new InvalidDuration(CERTIFICATE_DURATION_WRONG_FORMAT,CERTIFICATE_ERROR_CODE);
            }
        }

    }
}
