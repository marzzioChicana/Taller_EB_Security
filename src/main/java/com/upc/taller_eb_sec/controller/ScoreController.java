package com.upc.taller_eb_sec.controller;

import com.upc.taller_eb_sec.dto.ScoreDto;
import com.upc.taller_eb_sec.exception.ValidationException;
import com.upc.taller_eb_sec.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    //EndPoint: localhost:8080/api/v1/drivers/{driverId}/scores
    //Method: GET
    @Transactional
    @GetMapping("/drivers/{driverId}/scores")
    public Float getScoresByDriverId(@PathVariable Long driverId, @RequestParam(required = false) Integer scope){
        if(scope == null) {
            throw new ValidationException("Scope value not specified");
        }

        switch (scope) {
            case 0:
                return scoreService.getAverageScoreByDriverId(driverId);
            case 1:
                return scoreService.getMaxScoreByDriverId(driverId);
            default:
                throw new ValidationException("Scope value not valid");
        }


        /* return switch (scope) {
            case 0 -> scoreService.getAverageScoreByDriverId(driverId);
            case 1 -> scoreService.getMaxScoreByDriverId(driverId);
            default -> throw new ValidationException("Scope value not valid");
        }; */

    }

    //EndPoint: localhost:8080/api/v1/drivers
    //Method: POST
    @Transactional
    @PostMapping("/drivers")
    public ResponseEntity<ScoreDto> createDriver(@RequestBody ScoreDto scoreDto){
        return new ResponseEntity<>(scoreService.createScore(scoreDto), HttpStatus.CREATED);
    }
}