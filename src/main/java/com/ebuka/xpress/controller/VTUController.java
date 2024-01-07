package com.ebuka.xpress.controller;

import com.ebuka.xpress.model.dto.base.AirtimeRequestDto;
import com.ebuka.xpress.model.dto.base.BaseResponse;
import com.ebuka.xpress.service.VTUAirtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controller handling airtime-related endpoints for purchasing airtime.
 * Maps requests to '/api/v1/airtime' endpoints for airtime operations.
 *
 * @author ChukwuEbuka
 * @description Handles endpoints related to airtime purchase.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/airtime")
public class VTUController {
    private final VTUAirtimeService vtuAirtimeService;

    /**
     * Endpoint for purchasing airtime.
     *
     * @param airtimeRequestDto AirtimeRequestDto object containing airtime purchase details
     * @return ResponseEntity containing the response to the airtime purchase request
     */
    @PostMapping("/purchase-airtime")
    public ResponseEntity<BaseResponse<?>> purchaseAirtime(@RequestBody AirtimeRequestDto airtimeRequestDto){
        System.out.println("I am here ======>>");
        return ResponseEntity.ok(vtuAirtimeService.requestAirtime(airtimeRequestDto));
    }
}
