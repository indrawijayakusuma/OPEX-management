package com.bni.report.validation;

import com.bni.report.repositories.MataAnggaranRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UniqueFieldValidator implements ConstraintValidator<UniqueField, String> {

    @Autowired
    private MataAnggaranRepository mataAnggaranRepository;
    @Override
    public void initialize(UniqueField constraintAnnotation) {
    }

    @Override
    public boolean isValid(String nomerRekening, ConstraintValidatorContext constraintValidatorContext) {
        if(nomerRekening == null){
            return true;
        }
        return Objects.isNull(mataAnggaranRepository.findByNomerRekening(nomerRekening));
    }
}
