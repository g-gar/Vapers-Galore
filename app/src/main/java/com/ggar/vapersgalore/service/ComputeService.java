package com.ggar.vapersgalore.service;

import com.ggar.vapersgalore.domain.ElectricCircuitDefinition;
import com.ggar.vapersgalore.domain.InvalidCircuitDefinitionException;

public interface ComputeService {

    Double power(ElectricCircuitDefinition electricCircuitDefinition) throws InvalidCircuitDefinitionException;
    Double current(ElectricCircuitDefinition electricCircuitDefinition) throws InvalidCircuitDefinitionException;

}
