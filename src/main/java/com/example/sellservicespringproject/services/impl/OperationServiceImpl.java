package com.example.sellservicespringproject.services.impl;

import com.example.sellservicespringproject.dao.OperationRepo;
import com.example.sellservicespringproject.mappers.OperationMapper;
import com.example.sellservicespringproject.mappers.UserMapper;
import com.example.sellservicespringproject.models.dtos.*;
import com.example.sellservicespringproject.models.entities.Operation;
import com.example.sellservicespringproject.models.responses.ErrorResponse;
import com.example.sellservicespringproject.services.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationRepo operationRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private OperationDetailService operationDetailService;

    @Value("${jwtSecret}")
    private String secretKey;

    @Override
    public ResponseEntity<?> provideOperation(String token, List<InputDataForOperation> operationList) {

        ResponseEntity<?> responseEntity =
                userService
                        .verifyLogin(token);

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {

            return responseEntity;
        }

        ProductDto productDto;

        double price;
        double discount;
        double amount;              // for OperationDetail
        double totalAmount = 0;     // for Operation


        List<OperationDetailDto> operationDetailDtoList = new ArrayList<>();
        List<ReceiptDetailsDto> receiptDetailsDtoList = new ArrayList<>();

        // for show to the buyer receiptDetails
        ReceiptDto receiptDto = new ReceiptDto();
        ReceiptDetailsDto receiptDetailsDto = new ReceiptDetailsDto();

        // for set userId to the receipt
        UserDto userDto = new UserDto();

        for (InputDataForOperation element : operationList) {

            productDto =
                    productService
                            .findProductByBarcodeForOperationDetails(
                                    element
                                            .getBarcode()
                            );

            if (Objects.isNull(productDto)) {
                return new ResponseEntity<>(
                        new ErrorResponse("Некорректно введенные данный!"
                                , "Проверьте введенный штрихкод -> " + element.getBarcode())
                        , HttpStatus.NOT_FOUND);
            }

            OperationDetailDto operationDetailDto = new OperationDetailDto();

            operationDetailDto
                    .setProduct(productDto);

            operationDetailDto
                    .setQuantity(
                            element
                                    .getQuantity());

            price = priceService.findPriceByProductForOperationDetails(productDto);

            discount = 1 - (discountService.findDiscountByProduct(productDto) / 100);

            if (discount != 0) {

                amount = (price * discount) * element.getQuantity();
            } else {

                amount = price * element.getQuantity();
            }

            totalAmount += amount;

            operationDetailDto.setAmount(amount);

            operationDetailDtoList.add(operationDetailDto);

            receiptDetailsDto
                    .setName(
                            productDto
                                    .getName()
                    );

            receiptDetailsDto
                    .setBarcode(
                            element
                                    .getBarcode()
                    );

            receiptDetailsDto
                    .setQuantity(
                            element
                                    .getQuantity()
                    );

            receiptDetailsDto
                    .setPrice(price);

            receiptDetailsDto
                    .setDiscount(discountService.findDiscountByProduct(productDto));

            receiptDetailsDto
                    .setAmount(amount);

            receiptDetailsDtoList.add(receiptDetailsDto);

            Jws<Claims> jwt =
                    Jwts
                            .parser()
                            .setSigningKey(secretKey)
                            .parseClaimsJws(token);

            userDto =
                    UserMapper
                            .INSTANCE
                            .mapToUserDto(
                                    userService
                                            .findUserByLogin(
                                                    (String) jwt.getBody().get("login")
                                            )
                            );

            receiptDto
                    .setCashier(
                            userDto
                                    .getName()
                    );
        }

        receiptDto
                .setReceiptDetailsDto(receiptDetailsDtoList);

        receiptDto
                .setTotalAmount(totalAmount);

        Operation operation = new Operation();

        operation
                .setTotalAmount(totalAmount);

        operation
                .setUser(
                        UserMapper
                                .INSTANCE
                                .mapToUser(userDto)
                );

        operationRepo
                .save(operation);

        for (OperationDetailDto element : operationDetailDtoList) {

            element
                    .setOperation(
                            OperationMapper
                                    .INSTANCE
                                    .mapToOperationDto(operation)
                    );
        }

        operationDetailService
                .saveOperationDetails(operationDetailDtoList);

        return ResponseEntity.ok(receiptDto);
    }
}