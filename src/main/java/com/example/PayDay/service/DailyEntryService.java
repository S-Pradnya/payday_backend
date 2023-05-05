package com.example.PayDay.service;

import com.example.PayDay.model.requestmodel.DailyEntryRequestModel;
import com.example.PayDay.model.responsemodel.DailyEntryResponseModel;

import java.util.List;
import java.util.Optional;

public interface DailyEntryService {

    List<DailyEntryResponseModel> findAll();

    Optional<DailyEntryResponseModel> get(Long deId);

    DailyEntryResponseModel create(DailyEntryRequestModel dailyEntryRequestModel);

    DailyEntryResponseModel update(Long deId, DailyEntryRequestModel dailyEntryRequestModel);

    void delete(Long deId);
}
