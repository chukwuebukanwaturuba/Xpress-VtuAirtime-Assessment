package com.ebuka.xpress.service.impl;

import com.ebuka.xpress.model.dto.base.AirtimePurchaseResponse;
import com.ebuka.xpress.model.dto.base.AirtimeRequestDto;
import com.ebuka.xpress.model.dto.base.AirtimeVTURequest;
import com.ebuka.xpress.model.dto.base.BaseResponse;
import com.ebuka.xpress.model.entity.User;
import com.ebuka.xpress.service.UserService;
import com.ebuka.xpress.service.VTUAirtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation for handling VTU airtime-related operations.
 * Implements VTUAirtimeService interface.
 *
 * @author ChukwuEbuka
 * @description Handles operations related to VTU airtime requests.
 */
@Service
@RequiredArgsConstructor
public class VTUAirtimeServiceImpl implements VTUAirtimeService {

    private final UserService userService;
    private final XpressThirdPartyApiCall xpressThirdPartyApiCall;

    /**
     * Handles the request for airtime purchase.
     *
     * @param airtimeRequestDto AirtimeRequestDto object containing airtime purchase details
     * @return BaseResponse with the airtime purchase status and related information
     */
    @Override
    public BaseResponse<?> requestAirtime(AirtimeRequestDto airtimeRequestDto) {
        User loggedInUser = userService.getUserFromSecurityContext();

        // Prepare the airtime request
        AirtimeVTURequest airtimeVTURequest = new AirtimeVTURequest(airtimeRequestDto);

        // Set user's phone number if not provided in the request
        if (airtimeVTURequest.getDetails().getPhoneNumber() == null) {
            airtimeVTURequest.getDetails().setPhoneNumber(loggedInUser.getPhoneNumbers());
        }

        // Make the call to the Xpress third-party API
        AirtimePurchaseResponse response = xpressThirdPartyApiCall.makeCallToXpress(airtimeVTURequest);

        // Return response as BaseResponse
        return new BaseResponse<>(200, "success", response);
    }
}
