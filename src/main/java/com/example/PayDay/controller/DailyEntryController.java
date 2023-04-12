package com.example.PayDay.controller;

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

@Controller
@RequestMapping(value = "/dailyEntries")
public class DailyEntryController {

    @Autowired
    private DailyEntryService dailyEntryService;

    @GetMapping
    public ResponseEntity<Object> getAllDailyEntries() {
        List<DailyEntryResponseModel> dailyEntryResponseModelList = dailyEntryService.findAll();
        if (dailyEntryResponseModelList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Daily Entry Found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());

        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(dailyEntryResponseModelList)
                        .message("Fetched Daily Entry Lists")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping("/{deId}")
    public ResponseEntity<Object> getDailyEntry(@PathVariable final Long deId) {
        DailyEntryResponseModel dailyEntryResponseModel = dailyEntryService.get(deId);
        if (dailyEntryResponseModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Daily Entry Found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());

        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(dailyEntryResponseModel)
                        .message("Fetched Daily Entry Data")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PostMapping
    public ResponseEntity<Object> createDailyEntry(
            @RequestBody @Valid final DailyEntryRequestModel dailyEntryRequestModel) {
        DailyEntryResponseModel dailyEntryResponseModel = dailyEntryService.create(dailyEntryRequestModel);
        if (dailyEntryResponseModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("Something went wrong!!!")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JsonResponse.builder()
                        .data(dailyEntryResponseModel)
                        .message("Created Daily Entry")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

    @PutMapping("/{deId}")
    public ResponseEntity<Object> updateDailyEntry(@PathVariable final Long deId,
                                                   @RequestBody @Valid final DailyEntryRequestModel dailyEntryRequestModel) {
        DailyEntryResponseModel dailyEntryResponseModel = dailyEntryService.update(deId, dailyEntryRequestModel);
        if (dailyEntryResponseModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("Something went wrong!!!")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());

        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(dailyEntryResponseModel)
                        .message("Updated Daily Entry")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping("/{deId}")
    public ResponseEntity<Object> deleteDailyEntry(@PathVariable final Long deId) {
        dailyEntryService.delete(deId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .message("Daily Entry Deleted Successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
