package com.emazon.security.utils;

public class Constants {

    private Constants() {}

    public static final int MAX_CHARACTERS_PHONE = 13;
    public static final String ID_USER_BLANK = "El documento de identidad no puede estar vacio.";
    public static final String NAME_USER_BLANK = "El nombre no puede estar vacio.";
    public static final String LAST_NAME_USER_BLANK = "El apellido no puede estar vacio.";
    public static final String PHONE_USER_BLANK = "El numero de celular no puede estar vacio.";
    public static final String EMAIL_USER_BLANK = "El correo electronico no puede estar vacio.";
    public static final String BIRTH_DATE_USER_BLANK = "La fecha de nacimiento no puede estar vacia.";
    public static final String PASSWORD_USER_BLANK = "La contraseña no puede estar vacia.";
    public static final String ROLE_USER_BLANK = "El rol no puede estar vacio.";
    public static final String EMAIL_NOT_VALID = "El correo ingresado no es valido.";
    public static final String EMAIL_VALID_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String PHONE_START_PREFIX = "+57";
    public static final String ROLE_SECURITY_PREFIX = "ROLE_";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final int MIN_CHARACTERS_PHONE = 10;
    public static final int MIN_AGE = 18;

    //EXCEPTIONS
    public static final String USER_EMAIL_ALREADY_USED = "El usuario con el correo ingresado ya existe";
    public static final String USER_ID_ALREADY_USER = "El usuario con ese numero de identificación ya se encuentra regisrtrado.";
    public static final String USER_ID_NOT_VALID_ONLY_NUMERIC = "El numero de identificación debe ser numerico unicamente.";
    public static final String USER_NOT_LEGAL = "El usuario a registrar debe de ser mayor de edad.";
    public static final String USER_PHONE_NOT_VALID = "El numero de celular no sigue el formato +57 o 3xx, y debe ser unicamente numerico.";
    public static final String USER_NOT_FOUND = "No se encontró el usuario solicitado.";
}
