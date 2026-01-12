package khadija.app.backend.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Validation {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private String path;
}
