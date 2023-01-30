package com.atmostadam.cats.framework.service;

import com.atmostadam.cats.api.entity.CatEntity;
import com.atmostadam.cats.api.model.Cat;
import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.framework.jpa.CatRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class CatDatabaseService {
    @Autowired
    private CatRepository repository;

    public ResponseEntity<CatResponse> queryByMicrochipNumber(String requestId, CatRequest request) {
        ResponseEntity<CatResponse> response;
        try {
            Long microchipNumber = request.getMicrochipNumbers().get(0);
            CatEntity entity = repository.querySingleRowByMicrochipNumber(microchipNumber);
            if(Objects.nonNull(entity)) {
                Cat cat = entity.newCat();
                response = new CatResponse()
                        .setMessage(String.format("Successfully retrieved row with microchip number [%s]", microchipNumber))
                        .addCat(cat)
                        .newResponseEntity(requestId, HttpStatus.OK);
            } else {
                response = new CatResponse()
                        .setMessage(String.format("Unable to find cat with microchip number [%s]", microchipNumber))
                        .newResponseEntity(requestId, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response = new CatResponse()
                    .setMessage(e.getMessage())
                    .setStackTrace(ExceptionUtils.getStackTrace(e))
                    .newResponseEntity(requestId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<CatResponse> insertSingleRow(String requestId, CatRequest request) {
        if (request.getCats().size() != 1) {
            return new CatResponse()
                    .setMessage("Client has not provided a single cat to Add! Bad Request!")
                    .newResponseEntity(requestId, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<CatResponse> response;
        Cat cat = request.getCats().get(0);
        Long microchipNumber = cat.getMicrochipNumber();
        try {
            repository.insertSingleRow(cat.newCatEntity());
            response = new CatResponse()
                    .setMessage(String.format("Successfully inserted row with microchip number [%s]", microchipNumber))
                    .addCat(cat)
                    .newResponseEntity(requestId, HttpStatus.OK);
        } catch (ConstraintViolationException cve) {
            response = new CatResponse()
                    .setMessage(String.format("Successfully inserted row with microchip number [%s]", microchipNumber))
                    .addCat(cat)
                    .newResponseEntity(requestId, HttpStatus.OK);
        } catch (Exception e) {
            response = new CatResponse()
                    .setMessage(e.getMessage())
                    .setStackTrace(ExceptionUtils.getStackTrace(e))
                    .newResponseEntity(requestId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<CatResponse> updateSingleRow(String requestId, CatRequest request) {
        if (request.getCats().size() != 1) {
            return new CatResponse()
                    .setMessage("Client has not provided a single cat to Add! Bad Request!")
                    .newResponseEntity(requestId, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<CatResponse> response;
        Cat cat = request.getCats().get(0);
        Long microchipNumber = cat.getMicrochipNumber();
        try {
            repository.updateSingleRow(cat.newCatEntity());
            response = new CatResponse()
                    .setMessage(String.format("Successfully updated row with microchip number [%s]",
                            cat.getMicrochipNumber()))
                    .addCat(cat)
                    .newResponseEntity(requestId, HttpStatus.OK);
        } catch (Exception e) {
            response = new CatResponse()
                    .setMessage(e.getMessage())
                    .setStackTrace(ExceptionUtils.getStackTrace(e))
                    .newResponseEntity(requestId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<CatResponse> deleteSingleRow(String requestId, CatRequest request) {
        ResponseEntity<CatResponse> response;
        try {
            CatEntity entity =
                    repository.querySingleRowByMicrochipNumber(request.getMicrochipNumbers().get(0));
            repository.deleteSingleRow(entity);
            response = new CatResponse()
                    .setMessage(String.format("Successfully deleted row with microchip number [%s]",
                            request.getMicrochipNumbers().get(0)))
                    .newResponseEntity(requestId, HttpStatus.OK);
        } catch (Exception e) {
            response = new CatResponse()
                    .setMessage(e.getMessage())
                    .setStackTrace(ExceptionUtils.getStackTrace(e))
                    .newResponseEntity(requestId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
