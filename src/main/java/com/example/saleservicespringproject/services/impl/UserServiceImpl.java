package com.example.saleservicespringproject.services.impl;

import com.example.saleservicespringproject.dao.UserRepo;
import com.example.saleservicespringproject.mappers.UserMapper;
import com.example.saleservicespringproject.models.dtos.UserDto;
import com.example.saleservicespringproject.models.entities.Code;
import com.example.saleservicespringproject.models.entities.User;
import com.example.saleservicespringproject.models.enums.CodeStatus;
import com.example.saleservicespringproject.models.responses.ErrorResponse;
import com.example.saleservicespringproject.models.responses.OkResponse;
import com.example.saleservicespringproject.models.responses.SuccessLogin;
import com.example.saleservicespringproject.services.CodeService;
import com.example.saleservicespringproject.services.SendSimpleMessage;
import com.example.saleservicespringproject.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SendSimpleMessage sendMessage;

    @Autowired
    private CodeService codeService;

    @Value("${jwtSecret}")
    private String secretKey;

    @Override
    public ResponseEntity<?> saveUser(UserDto userDto) {

        User user =
                UserMapper
                        .INSTANCE
                        .mapToUser(userDto);

        if (Objects.isNull(userRepo.findByLogin(user.getLogin()))) {
            userRepo
                    .save(user);
        } else {
            return new ResponseEntity<>(
                    new ErrorResponse("Пользователь уже существует", null)
                    , HttpStatus.OK);
        }

        return ResponseEntity.ok(
                UserMapper
                        .INSTANCE
                        .mapToUserDto(user));
    }

    @Override
    public ResponseEntity<?> sendCode(String login) {

        User user = userRepo.findByLogin(login);

        if (Objects.isNull(user)) {
            return new ResponseEntity<>(
                    new ErrorResponse("Некорректный логин!", null)
                    , HttpStatus.NOT_FOUND);
        }

        Code lastCode =
                codeService.findUserCode(user);

        if (Objects.nonNull(lastCode)) {
            lastCode.setCodeStatus(CodeStatus.CANCELLED);

            codeService.saveCode(lastCode);
        }

        int code = codeService.randomCode();
        String hashedCode =
                BCrypt
                        .hashpw(
                                Integer
                                        .toString(code)
                                , BCrypt.gensalt());

        Calendar endOfCodeAction = Calendar.getInstance();
        endOfCodeAction.add(Calendar.MINUTE, 3);

        Code saveCode = new Code();
        saveCode.setCode(hashedCode);
        saveCode.setEndDate(endOfCodeAction.getTime());
        saveCode.setCodeStatus(CodeStatus.valueOf("NEW"));
        saveCode.setUser(user);
        codeService.saveCode(saveCode);

        sendMessage
                .sendSimpleMessage(
                        user.getEmail()
                        , Integer.toString(code));

        return ResponseEntity.ok(
                new OkResponse("Письмо успешно отправлено!", null));
    }

    @Override
    public ResponseEntity<?> getToken(String login, String code) {

        User user = userRepo.findByLogin(login);

        if (Objects.isNull(user)) {
            return new ResponseEntity<>(
                    new ErrorResponse("Некорректный логин!", null)
                    , HttpStatus.NOT_FOUND);
        }

        Code checkUserCode =
                codeService
                        .findUserCode(user);

        String hashing =
                BCrypt.hashpw(
                        code
                        , BCrypt.gensalt());

        if (!BCrypt.checkpw(checkUserCode.getCode(), hashing)) {
            return new ResponseEntity<>(
                    new ErrorResponse("Авторизация не пройдена!", "Вы ввели некорректный код подтверждения!")
                    , HttpStatus.NOT_FOUND);
        } else {
            Calendar tokensTimeLive =
                    Calendar.getInstance();
            tokensTimeLive
                    .add(Calendar.MINUTE, 5);

            String token =
                    Jwts.builder()
                            .claim("login", login)
                            .setExpiration(
                                    tokensTimeLive
                                            .getTime())
                            .signWith(
                                    SignatureAlgorithm.ES256
                                    , secretKey)
                            .compact();

            SuccessLogin successLogin = new SuccessLogin("Вы успешно ввели пароль!", token);

            return ResponseEntity.ok(successLogin);
        }

    }
}

