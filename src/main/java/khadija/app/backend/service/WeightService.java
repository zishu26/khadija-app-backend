package khadija.app.backend.service;

import khadija.app.backend.dto.WeightRecordDTO;

import java.util.List;

public interface WeightService {
    WeightRecordDTO addWeightRecord(WeightRecordDTO dto);

    List<WeightRecordDTO> getAllWeightRecords();
}
