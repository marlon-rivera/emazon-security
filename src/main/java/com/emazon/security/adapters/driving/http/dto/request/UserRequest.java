package com.emazon.security.adapters.driving.http.dto.request;

import com.emazon.security.domain.model.RoleEnum;
import com.emazon.security.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {

    @NotNull(message = Constants.ID_USER_BLANK)
    private BigInteger id;
    @NotEmpty(message = Constants.NAME_USER_BLANK)
    private String name;
    @NotEmpty(message = Constants.LAST_NAME_USER_BLANK)
    private String lastName;
    @NotEmpty(message = Constants.PHONE_USER_BLANK)
    @Size(max = Constants.MAX_CHARACTERS_PHONE)
    private String phone;
    @NotNull(message = Constants.BIRTH_DATE_USER_BLANK)
    private LocalDate birthDate;
    @NotEmpty(message = Constants.EMAIL_USER_BLANK)
    @Pattern(regexp = Constants.EMAIL_VALID_REGEX, message = Constants.EMAIL_NOT_VALID)
    private String email;
    @NotEmpty(message = Constants.PASSWORD_USER_BLANK)
    private String password;

}
