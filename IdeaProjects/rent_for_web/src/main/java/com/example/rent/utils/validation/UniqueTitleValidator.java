package com.example.rent.utils.validation;

import com.example.rent.repositories.EquipmentRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueTitleValidator implements ConstraintValidator<UniqueTitle, String> {
    private final EquipmentRepository equipmentRepository;

    public UniqueTitleValidator(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return equipmentRepository.findByTitle(value).isEmpty();
    }
}
