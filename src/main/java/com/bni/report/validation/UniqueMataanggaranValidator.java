package com.bni.report.validation;

import com.bni.report.repositories.MataAnggaranRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UniqueMataanggaranValidator implements ConstraintValidator<UniqueMataanggaaran, String> {

    @Autowired
    private MataAnggaranRepository mataAnggaranRepository;

    @Override
    public void initialize(UniqueMataanggaaran uniqueMataanggaaran) {
    }

    @Override
    public boolean isValid(String mataAnggaran, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.isNull(mataAnggaranRepository.findByMataAnggaran(mataAnggaran));
    }
}
