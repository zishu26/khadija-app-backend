package khadija.app.backend.controller;


import jakarta.validation.Valid;
import khadija.app.backend.dto.WeightRecordDTO;
import khadija.app.backend.service.WeightService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("khadija/api/weight")
@RequiredArgsConstructor
public class WeightController {

    private final WeightService weightService;
    @PostMapping("/add/weightrecord")
    public ResponseEntity<WeightRecordDTO> addWeightRecord(@Valid @RequestBody WeightRecordDTO dto) {
        return ResponseEntity.ok(weightService.addWeightRecord(dto));
    }

    @GetMapping("/get/weightrecord/all")
    public ResponseEntity<List<WeightRecordDTO>> getAllWeightRecords() {
        return ResponseEntity.ok(weightService.getAllWeightRecords());

    }


}
