package com.example.sellservicespringproject.services.impl;

import com.example.sellservicespringproject.dao.UserRepo;
import com.example.sellservicespringproject.mappers.UserMapper;
import com.example.sellservicespringproject.models.dtos.UserDto;
import com.example.sellservicespringproject.models.entities.Code;
import com.example.sellservicespringproject.models.entities.Request;
import com.example.sellservicespringproject.models.entities.User;
import com.example.sellservicespringproject.models.enums.CodeStatus;
import com.example.sellservicespringproject.models.responses.ErrorResponse;
import com.example.sellservicespringproject.models.responses.OkResponse;
import com.example.sellservicespringproject.models.responses.SuccessLogin;
import com.example.sellservicespringproject.services.CodeService;
import com.example.sellservicespringproject.services.RequestService;
import com.example.sellservicespringproject.services.SendSimpleMessage;
import com.example.sellservicespringproject.services.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SendSimpleMessage sendMessage;

    @Autowired
    private CodeService codeService;

    @Autowired
    private RequestService requestService;

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

        if (Objects.nonNull(user.getEndOfBlockDate())) {

            if (new Date().before(user.getEndOfBlockDate())) {

                SimpleDateFormat formatToShowEndOfBlockDate =
                        new SimpleDateFormat("hh:mm a");

                return ResponseEntity.ok(" Превышено количество попыток входа, вы заблокированы. Повторите попытку в " +
                        formatToShowEndOfBlockDate
                                .format(
                                        user.getEndOfBlockDate()));
            }
        }

        Code lastCode =
                codeService.findUserCode(user);

        if (Objects.nonNull(lastCode)) {

            lastCode.setCodeStatus(CodeStatus.CANCELLED);

            codeService.saveCode(lastCode);
        }

        int code =
                codeService.randomCode();

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
                new OkResponse("Код подтверждения успешно отправлен!", null));
    }

    @Override
    public ResponseEntity<?> getToken(String login, String code) {

        User user = userRepo.findByLogin(login);

        if (Objects.isNull(user)) {

            return new ResponseEntity<>(
                    new ErrorResponse("Некорректный логин!", null)
                    , HttpStatus.NOT_FOUND);
        }

        if (Objects.nonNull(user.getBlockDate())) {

            if (new Date().before(user.getEndOfBlockDate())) {

                SimpleDateFormat formatToShowEndOfBlockDate =
                        new SimpleDateFormat("hh:mm a");

                return ResponseEntity.ok(" Превышено количество попыток входа, вы заблокированы. Повторите попытку в " +
                        formatToShowEndOfBlockDate
                                .format(
                                        user.getEndOfBlockDate()));
            }
        }

        Code checkUserCode =
                codeService
                        .findUserCode(user);

        if (new Date().after(checkUserCode.getEndDate())) {
            return new ResponseEntity<>(
                    new ErrorResponse(
                            "Время действия кода подтверждения истек!"
                            , "Вам нужно получить код подтверждения повторно!")
                    , HttpStatus.OK
            );
        }

        if (!BCrypt.checkpw(code, checkUserCode.getCode())) {

            Request request = new Request();
            request.setCode(checkUserCode);
            request.setSuccess(false);
            requestService.saveRequest(request);

            if (requestService.countFailedAttempts(checkUserCode) >= 3) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, 1);

                user.setBlockDate(new Date());
                user.setEndOfBlockDate(calendar.getTime());
                userRepo.save(user);

                Code forSettingCodeStatus = codeService.findUserCode(user);
                forSettingCodeStatus.setCodeStatus(CodeStatus.FAILED);
                codeService.saveCode(forSettingCodeStatus);
            }

            return new ResponseEntity<>(
                    new ErrorResponse("Авторизация не пройдена!", "Вы ввели некорректный код подтверждения!")
                    , HttpStatus.NOT_FOUND);

        }

        Request request = new Request();
        request.setCode(checkUserCode);
        request.setSuccess(true);
        requestService.saveRequest(request);

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
                                SignatureAlgorithm.HS256
                                , secretKey)
                        .compact();

        Code forSetApproved =
                codeService.findUserCode(user);

        forSetApproved
                .setCodeStatus(CodeStatus.APPROVED);

        codeService.saveCode(forSetApproved);

        SuccessLogin successLogin = new SuccessLogin("Вы успешно ввели пароль!", token);

        return ResponseEntity.ok(successLogin);
    }

    @Override
    public ResponseEntity<?> verifyLogin(String token) {
        try {
            Jws<Claims> jwt = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return ResponseEntity.ok(jwt.getBody().get("login"));
        } catch (ExpiredJwtException jwtException) {
            return new ResponseEntity<>("Непрвильно ввели токен или время действия токена истек", HttpStatus.CONFLICT);
        }
    }
}