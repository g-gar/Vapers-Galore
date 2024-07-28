package com.ggar.vapersgalore.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EqualsAndHashCode
@ToString
public class ElectricCircuitDefinition {

    Double resistance;
    Double voltage;
    Double current;
    Double power;

}
