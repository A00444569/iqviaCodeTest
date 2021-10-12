package ca.smu.a00444569.iqviacodetest.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private List<String> details;
}


