package com.example.PayDay.controller;

import com.example.PayDay.constant.ApiConstant;
import com.example.PayDay.constant.StringConstant;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.requestmodel.DailyEntryRequestModel;
import com.example.PayDay.model.responsemodel.DailyEntryResponseModel;
import com.example.PayDay.service.DailyEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = ApiConstant.REQUEST_MAPPING_KEY_DAILY_ENTRY)
public class DailyEntryController {

    @Autowired
    private DailyEntryService dailyEntryService;

    @GetMapping
    public ResponseEntity<Object> getAllDailyEntries() {
        List<DailyEntryResponseModel> dailyEntryResponseModelList = dailyEntryService.findAll();
        if (dailyEntryResponseModelList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(dailyEntryResponseModelList)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_DAILY_ENTRY_FETCHED)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping(ApiConstant.REQUEST_MAPPING_DAILY_ENTRY_ID)
    public ResponseEntity<Object> getDailyEntry(@PathVariable final Long deId) {
        Optional<DailyEntryResponseModel> dailyEntryResponseModel = dailyEntryService.get(deId);
        if (dailyEntryResponseModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponse.builder()
                            .data(dailyEntryResponseModel)
                            .message(StringConstant.REQUEST_SUCCESS_MESSAGE_SELECTED_DAILY_ENTRY_FETCHED + deId)
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(JsonResponse.builder()
                        .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build());
    }

    @PostMapping
    public ResponseEntity<Object> createDailyEntry(
            @RequestBody @Valid final DailyEntryRequestModel dailyEntryRequestModel) {
        DailyEntryResponseModel dailyEntryResponseModel = dailyEntryService.create(dailyEntryRequestModel);
        if (dailyEntryResponseModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JsonResponse.builder()
                        .data(dailyEntryResponseModel)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_DAILY_ENTRY_CREATED)
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

    @PutMapping(ApiConstant.REQUEST_MAPPING_DAILY_ENTRY_ID)
    public ResponseEntity<Object> updateDailyEntry(@PathVariable final Long deId,
                                                   @RequestBody @Valid final DailyEntryRequestModel dailyEntryRequestModel) {
        DailyEntryResponseModel dailyEntryResponseModel = dailyEntryService.update(deId, dailyEntryRequestModel);
        if (dailyEntryResponseModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(dailyEntryResponseModel)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_DAILY_ENTRY_UPDATED)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping(ApiConstant.REQUEST_MAPPING_DAILY_ENTRY_ID)
    public ResponseEntity<Object> deleteDailyEntry(@PathVariable final Long deId) {
        dailyEntryService.delete(deId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_DAILY_ENTRY_DELETED)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
