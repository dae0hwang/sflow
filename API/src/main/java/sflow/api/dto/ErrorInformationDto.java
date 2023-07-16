package sflow.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorInformationDto {

    private String errorType;
    private String errorMessage;
}
