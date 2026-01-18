package khadija.app.backend.service;

import khadija.app.backend.dao.WeightRepository;
import khadija.app.backend.dto.WeightRecordDTO;
import khadija.app.backend.model.Weight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeightServiceImpl implements WeightService {
    private final WeightRepository weightRepository;

    @Autowired
    public WeightServiceImpl(WeightRepository weightRepository) {
        this.weightRepository = weightRepository;
    }
    @Override
    public WeightRecordDTO addWeightRecord(WeightRecordDTO dto) {

        Weight weight = convertToEntity(dto);
        return convertToDTO(weightRepository.save(weight));

    }

    @Override
    public List<WeightRecordDTO> getAllWeightRecords() {
        return weightRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    private Weight convertToEntity(WeightRecordDTO dto) {
        return Weight.builder().weight(dto.getRecordedWeight()).date(dto.getRecordedDate()).build();
    }

    private WeightRecordDTO convertToDTO(Weight weight) {
        return WeightRecordDTO.builder().recordedWeight(weight.getWeight()).recordedDate(weight.getDate()).build();
    }
}
