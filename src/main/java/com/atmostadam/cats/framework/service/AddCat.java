package com.atmostadam.cats.framework.service;

import com.atmostadam.cats.api.model.Cat;
import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.api.service.CatService;
import com.atmostadam.cats.api.service.CatSpringBeanServiceNames;
import com.atmostadam.cats.framework.jpa.CatRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service(CatSpringBeanServiceNames.ADD_CAT)
public class AddCat implements CatService {
    @Autowired
    private CatRepository repository;

    @Override
    public ResponseEntity<CatResponse> invoke(String requestId, CatRequest request) {
        if (request.getCats().size() == 0) {
            return new CatResponse()
                    .setMessage("Client has not provided a cat to Add! Bad Request!")
                    .newResponseEntity(requestId, HttpStatus.BAD_REQUEST);
        }
        if (request.getCats().size() > 1) {
            return new CatResponse()
                    .setMessage("Client has provided multiple cats to Add! Bad Request!")
                    .addCats(request.getCats())
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
        } catch (DataIntegrityViolationException cve) {
            response = new CatResponse()
                    .setMessage("[DataIntegrityViolationException] while inserting row with microchip number [" +
                                    microchipNumber + "] [microchipNumber already exists]")
                    .setStackTrace(ExceptionUtils.getStackTrace(cve))
                    .addCat(cat)
                    .newResponseEntity(requestId, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response = new CatResponse()
                    .setMessage(e.getMessage())
                    .setStackTrace(ExceptionUtils.getStackTrace(e))
                    .addCat(cat)
                    .newResponseEntity(requestId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
