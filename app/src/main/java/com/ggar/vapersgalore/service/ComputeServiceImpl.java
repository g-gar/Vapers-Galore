package com.ggar.vapersgalore.service;

import com.ggar.vapersgalore.domain.ElectricCircuitDefinition;
import com.ggar.vapersgalore.domain.InvalidCircuitDefinitionException;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ComputeServiceImpl implements ComputeService {

    @Override
    public Double power(ElectricCircuitDefinition electricCircuitDefinition) throws InvalidCircuitDefinitionException {
        Function<ElectricCircuitDefinition, Double> byResistance = e -> Objects.nonNull(e.getVoltage()) && Objects.nonNull(e.getResistance()) ? Math.pow(e.getVoltage(), 2) / e.getResistance() : null;
        Function<ElectricCircuitDefinition, Double> byCurrent = e -> Objects.nonNull(e.getVoltage()) && Objects.nonNull(e.getCurrent()) ? e.getVoltage() * e.getCurrent() : null;

        return List.of(byResistance, byCurrent).stream()
            .map(e -> e.apply(electricCircuitDefinition))
            .filter(Objects::nonNull)
            .findAny()
            .orElseThrow(InvalidCircuitDefinitionException::new);
    }

    @Override
    public Double current(ElectricCircuitDefinition electricCircuitDefinition) throws InvalidCircuitDefinitionException {
        Function<ElectricCircuitDefinition, Double> byResistance = e -> Objects.nonNull(e.getVoltage()) && Objects.nonNull(e.getResistance()) ? e.getVoltage() / e.getResistance() : null;
        Function<ElectricCircuitDefinition, Double> byCurrent = e -> Objects.nonNull(e.getVoltage()) && Objects.nonNull(e.getPower()) ? e.getPower() / e.getVoltage() : null;

        return List.of(byResistance, byCurrent).stream()
                .map(e -> e.apply(electricCircuitDefinition))
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(InvalidCircuitDefinitionException::new);
    }
}
