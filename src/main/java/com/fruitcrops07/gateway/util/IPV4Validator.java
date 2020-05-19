package com.fruitcrops07.gateway.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class IPV4Validator implements AddressValidator {

    private static final String OWSAP_IPV4_REGEX = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." 
                                                   + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
                                                   + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
                                                   + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    private static final Pattern OWASP_IPV4_PATTERN = Pattern.compile(OWSAP_IPV4_REGEX);

    @Override
    public Boolean isValid(String address) {
        return OWASP_IPV4_PATTERN.matcher(address).matches();
    }

}
