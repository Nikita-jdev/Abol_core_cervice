package org.core_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class KafkaEventGetImj {
    private String imjName;
    private long imjSize;
    private UserDto userDto;
}
