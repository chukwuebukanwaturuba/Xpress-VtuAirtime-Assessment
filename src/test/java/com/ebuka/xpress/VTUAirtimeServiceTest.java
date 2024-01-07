package com.ebuka.xpress;

import com.ebuka.xpress.model.dto.base.AirtimePurchaseResponse;
import com.ebuka.xpress.model.dto.base.AirtimeRequestDto;
import com.ebuka.xpress.model.dto.base.AirtimeVTURequest;
import com.ebuka.xpress.model.dto.base.BaseResponse;
import com.ebuka.xpress.model.entity.User;
import com.ebuka.xpress.model.enums.Biller;
import com.ebuka.xpress.model.enums.UserType;
import com.ebuka.xpress.service.UserService;
import com.ebuka.xpress.service.impl.VTUAirtimeServiceImpl;
import com.ebuka.xpress.service.impl.XpressThirdPartyApiCall;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VTUAirtimeServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private XpressThirdPartyApiCall xpressThirdPartyApiCall;
    private AutoCloseable autoCloseable;
    @InjectMocks
    private VTUAirtimeServiceImpl vtuAirtimeService;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    public void setAutoCloseable() throws Exception {
        autoCloseable.close();
    }
    @Test
    public void shouldTestAirTimeVTURequest(){
        User user = new User();
        user.setEmail("chukwu@email.com");
        user.setUserType(UserType.CUSTOMER);
        user.setPassword("Chuza1@ok");

        // Creating an instance of AirtimeVTURequest
        AirtimeVTURequest airtimeVTURequest = new AirtimeVTURequest();
        airtimeVTURequest.setRequestId("123456");
        airtimeVTURequest.setUniqueCode("MTN_24207");

        // Creating an instance of Details
        AirtimeVTURequest.Details details = new AirtimeVTURequest.Details();
        details.setPhoneNumber("09132058051");
        details.setAmount(100L);

        // Setting the Details instance in AirtimeVTURequest
        airtimeVTURequest.setDetails(details);

        // Creating an instance of ApiResponse
        AirtimePurchaseResponse apiResponse = new AirtimePurchaseResponse();
        apiResponse.setRequestId(123456);
        apiResponse.setReferenceId("MATT14539722120213053702634214");
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Successful");
        apiResponse.setData(null);

        //create instance of airtimeRequestDto
        AirtimeRequestDto requestDto = new AirtimeRequestDto();
        requestDto.setAmount(100L);
        requestDto.setPhoneNumber("12345678901");
        requestDto.setBiller(Biller.MTN);

        when(userService.getUserFromSecurityContext()).thenReturn(user);
        when(xpressThirdPartyApiCall.makeCallToXpress(any(AirtimeVTURequest.class))).thenReturn(apiResponse);

        BaseResponse<?> response = vtuAirtimeService.requestAirtime(requestDto);
        assertNotNull(response);
        assertEquals(response.getPayload(), apiResponse);
    }
}
