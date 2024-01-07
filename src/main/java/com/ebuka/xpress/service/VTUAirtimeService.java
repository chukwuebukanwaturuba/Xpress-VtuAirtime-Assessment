package com.ebuka.xpress.service;

import com.ebuka.xpress.model.dto.base.AirtimeRequestDto;
import com.ebuka.xpress.model.dto.base.BaseResponse;

public interface VTUAirtimeService {
    BaseResponse<?> requestAirtime(AirtimeRequestDto airtimeRequestDto);
}
