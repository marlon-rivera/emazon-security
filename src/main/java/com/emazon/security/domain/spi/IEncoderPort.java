package com.emazon.security.domain.spi;

public interface IEncoderPort {

    String encode(String text);
    boolean matches(String text);

}
