package com.mail.mailSender.validation.imageValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    private static final String[] ALLOWED_TYPES = { "image/jpeg", "image/png", "image/webp", "image/gif", "image/svg+xml" };

    @Override
    public void initialize(ValidImage constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null) {
            return true; // Null files should be handled by @NotNull if needed
        }

        String contentType = file.getContentType();
        System.out.println("Content Type: " + contentType);

        for (String allowedType : ALLOWED_TYPES) {
            if (allowedType.equals(contentType)) {
                return true;
            }
        }
        return false;
    }
}
