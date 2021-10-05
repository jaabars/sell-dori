package com.example.sellservicespringproject.services.impl;

import com.example.sellservicespringproject.dao.PriceRepo;
import com.example.sellservicespringproject.mappers.PriceMapper;
import com.example.sellservicespringproject.mappers.ProductMapper;
import com.example.sellservicespringproject.models.dtos.PriceDto;
import com.example.sellservicespringproject.models.dtos.ProductDto;
import com.example.sellservicespringproject.models.entities.Price;
import com.example.sellservicespringproject.models.responses.ErrorResponse;
import com.example.sellservicespringproject.services.PriceService;
import com.example.sellservicespringproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepo priceRepo;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?> savePrice(String token, PriceDto priceDto) {

        ResponseEntity<?> responseEntity =
                userService
                        .verifyLogin(token);

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {

            return responseEntity;
        }

        List<Price> priceList =
                priceRepo
                        .findAllByProduct(
                                ProductMapper
                                        .INSTANCE
                                        .mapToProduct(
                                                priceDto
                                                        .getProduct()));

        if (Objects.nonNull(priceList) && !priceList.isEmpty()) {
            priceList
                    .stream()
                    .filter(
                            x -> x.getStartDate().before(priceDto.getStartDate())

                                    || x.getStartDate().after(priceDto.getStartDate())

                                    && x.getEndDate().before(priceDto.getEndDate())

                                    || x.getEndDate().after(priceDto.getEndDate()))
                    .forEach(x -> {

                        x.setEndDate(new Date());
                        priceRepo.save(x);
                    });
        }

        Price price =
                PriceMapper
                        .INSTANCE
                        .mapToPrice(priceDto);

        price = priceRepo.save(price);

        return ResponseEntity.ok(
                PriceMapper
                        .INSTANCE
                        .mapToPriceDto(price));
    }

    @Override
    public ResponseEntity<?> getPriceByProduct(String token, ProductDto productDto) {

        ResponseEntity<?> responseEntity =
                userService
                        .verifyLogin(token);

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {

            return responseEntity;
        }

        Price price =
                priceRepo
                        .findActualPrice(
                                ProductMapper
                                        .INSTANCE
                                        .mapToProduct(productDto)
                        );

        if (Objects.isNull(price)) {

            return new ResponseEntity<>(
                    new ErrorResponse("Вы ввели некорректные данные!", null)
                    , HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(
                PriceMapper
                        .INSTANCE
                        .mapToPriceDto(price));
    }

    @Override
    public ResponseEntity<?> getAllPrices(String token) {

        ResponseEntity<?> responseEntity =
                userService
                        .verifyLogin(token);

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {

            return responseEntity;
        }

        List<Price> priceList =
                priceRepo
                        .findAll();

        return ResponseEntity.ok(
                priceList
                        .stream()
                        .map(
                                PriceMapper
                                        .INSTANCE::mapToPriceDto)
                        .collect(
                                Collectors.toList()));
    }

    @Override
    public double findPriceByProductForOperationDetails(ProductDto productDto) {
        Price actualPrice =
                priceRepo
                        .findActualPrice(
                                ProductMapper
                                        .INSTANCE
                                        .mapToProduct(productDto));

        return actualPrice
                .getPrice();
    }
}