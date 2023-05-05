package com.example.PayDay.serviceimpl;

import com.example.PayDay.constant.StringConstant;
import com.example.PayDay.entity.DailyEntry;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.responsemodel.DailyEntryResponseModel;
import com.example.PayDay.repository.DailyEntryPaginatedRepository;
import com.example.PayDay.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    DailyEntryPaginatedRepository dailyEntryRepository;

    @Override
    public ResponseEntity<Object> getAdminReports(Integer pageNumber, Integer pageSize, Date deDate, Date endDate, Long deEmployeeId) {

        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<DailyEntry> dailyEntriesResult;

        if (deDate == null && endDate == null) {
            deDate = Calendar.getInstance().getTime();
            dailyEntriesResult = dailyEntryRepository.findByDeDateEqualsAndDeEmployeeId(deDate, deEmployeeId, page);
        } else if (deDate != null && endDate != null) {
            if (endDate.before(deDate)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(JsonResponse.builder()
                                .message("Please enter valid Start & End Dates")
                                .status(HttpStatus.NOT_FOUND)
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .build());
            }
            dailyEntriesResult = dailyEntryRepository.findByDeDateLessThanEqualAndDeDateGreaterThanEqualAndDeEmployeeId(endDate, deDate, deEmployeeId, page);
        } else {
            dailyEntriesResult = dailyEntryRepository.findByDeDateEqualsAndDeEmployeeId(deDate, deEmployeeId, page);
        }
        if (dailyEntriesResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(dailyEntriesResult.getContent().stream()
                                .map((dailyEntry) -> mapToResponseModel(dailyEntry, new DailyEntryResponseModel()))
                                .collect(Collectors.toList()))
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_DAILY_ENTRY_FETCHED)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    private DailyEntryResponseModel mapToResponseModel(final DailyEntry dailyEntry, final DailyEntryResponseModel dailyEntryResponseModel) {
        dailyEntryResponseModel.setDeId(dailyEntry.getDeId());
        dailyEntryResponseModel.setDeEmployeeId(dailyEntry.getDeEmployeeId());
        dailyEntryResponseModel.setDeProductId(dailyEntry.getDeProductId());
        dailyEntryResponseModel.setDeProductQuantity(dailyEntry.getDeProductQuantity());
        dailyEntryResponseModel.setDeProductPerUnitCost(dailyEntry.getDeProductPerUnitCost());
        dailyEntryResponseModel.setDeDate(dailyEntry.getDeDate());
        dailyEntryResponseModel.setEmployee(dailyEntry.getEmployee() == null ? null : dailyEntry.getEmployee());
        dailyEntryResponseModel.setProduct(dailyEntry.getProduct() == null ? null : dailyEntry.getProduct());

        return dailyEntryResponseModel;
    }
}
