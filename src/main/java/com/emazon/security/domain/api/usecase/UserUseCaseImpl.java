package com.emazon.security.domain.api.usecase;

import com.emazon.security.domain.api.IUserServicePort;
import com.emazon.security.domain.exception.*;
import com.emazon.security.domain.model.Auth;
import com.emazon.security.domain.model.User;
import com.emazon.security.domain.spi.IEncoderPort;
import com.emazon.security.domain.spi.IUserPersistencePort;
import com.emazon.security.utils.Constants;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@RequiredArgsConstructor
public class UserUseCaseImpl implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IEncoderPort encoderPort;

    @Override
    public Auth saveUser(User user) {
        validateId(user.getId());
        validateEmail(user.getEmail());
        validatePhone(user.getPhone());
        if(userPersistencePort.getUserByEmail(user.getEmail()).isPresent()) {
            throw new UserEmailAlreadyUsedException();
        }
        if(userPersistencePort.getUserById(user.getId()).isPresent()) {
            throw new UserIdAlreadyUsedException();
        }
        if(!isAdult(user.getBirthDate())){
            throw new UserNotLegalAgeException();
        }
        user.setPassword(encoderPort.encode(user.getPassword()));
        return userPersistencePort.saveUser(user);
    }

    @Override
    public Auth loginUser(String email, String password) {
        Optional<User> userOptional = userPersistencePort.getUserByEmail(email);
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userOptional.get();
        if(!encoderPort.matches(password, user.getPassword())) {
            throw new UserIncorrectPasswordException();
        }
        return userPersistencePort.loginUser(email, password);
    }

    private void validatePhone(String phone){
        if(phone.startsWith(Constants.PHONE_START_PREFIX) && phone.length() != Constants.MAX_CHARACTERS_PHONE){
            throw new UserPhoneNotValidException();
        }else{
            phone = phone.substring(Constants.PHONE_START_PREFIX.length() - 1);
        }
        if(phone.length() < Constants.MIN_CHARACTERS_PHONE){
            throw new UserPhoneNotValidException();
        }
        for(int i = 0; i < phone.length(); i++){
            if(!Character.isDigit(phone.charAt(i))){
                throw new UserPhoneNotValidException();
            }
        }
    }

    private void validateEmail(String email){
        if(!email.matches(Constants.EMAIL_VALID_REGEX)){
            throw new UserEmailNotValidException();
        }
    }

    private void validateId(String id){
        for(int i = 0; i < id.length(); i++){
            if(!Character.isDigit(id.charAt(i))){
                throw new UserIdNotValidOnlyNumericException();
            }
        }
    }

    private boolean isAdult(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        Period age = Period.between(birthDate, today);

        return age.getYears() >= Constants.MIN_AGE;
    }

}
