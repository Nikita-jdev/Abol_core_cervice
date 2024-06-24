package org.core_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KafkaEventAddImj {
    private String bucketName;
    private long imjSize;
    private UserDto userDto;
}
