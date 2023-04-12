package com.example.PayDay.service;

import com.example.PayDay.model.requestmodel.DailyEntryRequestModel;
import com.example.PayDay.model.responsemodel.DailyEntryResponseModel;
import java.util.List;

public interface DailyEntryService {

    List<DailyEntryResponseModel> findAll();

    DailyEntryResponseModel get(Long deId);

    DailyEntryResponseModel create(DailyEntryRequestModel dailyEntryRequestModel);

    DailyEntryResponseModel update(Long deId, DailyEntryRequestModel dailyEntryRequestModel);

    void delete(Long deId);
}
