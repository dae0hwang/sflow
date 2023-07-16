package sflow.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class DefaultExtensionDto {
    private Long id;
    private String extensionName;
    private boolean defaultCheck;
    private boolean activeCheck;
}
