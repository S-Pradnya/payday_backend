package com.example.PayDay.serviceimpl;

import com.example.PayDay.constant.StringConstant;
import com.example.PayDay.entity.DailyEntry;
import com.example.PayDay.exception.ResourceNotFoundException;
import com.example.PayDay.model.requestmodel.DailyEntryRequestModel;
import com.example.PayDay.model.responsemodel.DailyEntryResponseModel;
import com.example.PayDay.repository.DailyEntryRepository;
import com.example.PayDay.repository.UserRepository;
import com.example.PayDay.service.DailyEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DailyEntryServiceClass implements DailyEntryService {

    @Autowired
    private DailyEntryRepository dailyEntryRepository;

    @Autowired
    private UserRepository userRepository;

    public List<DailyEntryResponseModel> findAll() {
        final List<DailyEntry> dailyEntrys = dailyEntryRepository.findAll(Sort.by("deId"));
        return dailyEntrys.stream()
                .map((dailyEntry) -> mapToResponseModel(dailyEntry, new DailyEntryResponseModel()))
                .collect(Collectors.toList());
    }

    public Optional<DailyEntryResponseModel> get(final Long deId) {
        return dailyEntryRepository.findById(deId)
                .map(dailyEntry -> mapToResponseModel(dailyEntry, new DailyEntryResponseModel()));
    }
    public DailyEntryResponseModel create(final DailyEntryRequestModel dailyEntryRequestModel) {
        final DailyEntry dailyEntry = new DailyEntry();
        mapToEntity(dailyEntryRequestModel, dailyEntry);
        DailyEntry savedDailyEntry = dailyEntryRepository.save(dailyEntry);
        return mapToResponseModel(dailyEntry, new DailyEntryResponseModel());
    }

    public DailyEntryResponseModel update(final Long deId, final DailyEntryRequestModel dailyEntryRequestModel) {
        final DailyEntry dailyEntry = dailyEntryRepository.findById(deId)
                .orElseThrow(() -> new ResourceNotFoundException(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST));
        mapToEntity(dailyEntryRequestModel, dailyEntry);
        DailyEntry savedDailyEntry = dailyEntryRepository.save(dailyEntry);
        return mapToResponseModel(dailyEntry, new DailyEntryResponseModel());
    }
    public void delete(final Long deId) {
        dailyEntryRepository.deleteById(deId);
    }

    private DailyEntryResponseModel mapToResponseModel(final DailyEntry dailyEntry, final DailyEntryResponseModel dailyEntryResponseModel) {
        dailyEntryResponseModel.setDeId(dailyEntry.getDeId());
        dailyEntryResponseModel.setDeEmployeeId(dailyEntry.getDeEmployeeId());
        dailyEntryResponseModel.setDeProductId(dailyEntry.getDeProductId());
        dailyEntryResponseModel.setDeProductQuantity(dailyEntry.getDeProductQuantity());
        dailyEntryResponseModel.setDeProductPerUnitCost(dailyEntry.getDeProductPerUnitCost());
        dailyEntryResponseModel.setDeDate(dailyEntry.getDeDate());
        dailyEntryResponseModel.setEmployee(dailyEntry.getEmployee());
        dailyEntryResponseModel.setProduct(dailyEntry.getProduct());

        return dailyEntryResponseModel;
    }
    private DailyEntry mapToEntity(final DailyEntryRequestModel dailyEntryRequestModel, final DailyEntry dailyEntry) {
        dailyEntry.setDeEmployeeId(dailyEntryRequestModel.getDeEmployeeId());
        dailyEntry.setDeProductId(dailyEntryRequestModel.getDeProductId());
        dailyEntry.setDeProductQuantity(dailyEntryRequestModel.getDeProductQuantity());
        dailyEntry.setDeProductPerUnitCost(dailyEntryRequestModel.getDeProductPerUnitCost());
        dailyEntry.setDeDate(dailyEntryRequestModel.getDeDate());
        dailyEntry.setEmployee(dailyEntryRequestModel.getEmployee());
        dailyEntry.setProduct(dailyEntryRequestModel.getProduct());

        return dailyEntry;
    }
}
