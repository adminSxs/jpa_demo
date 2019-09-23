package com.wizlah.es.commons;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ValidateManager {
    @Autowired
    private Validator validator;

    public boolean validate(JsonResponse result, Object obj, String... params) {
        if (validator == null) {
            validator = new Validator();
        }
        List <ConstraintViolation> fields = validator.validate(obj); // messages
        for (ConstraintViolation constraintViolation : fields) {
            for (String param : params) {
                if (Objects.equal(param, constraintViolation.getErrorCode())) {
                    if (!Strings.isNullOrEmpty(constraintViolation.getMessage())) {
                        result.getParamValidations().add(constraintViolation.getMessage());
                    }
                }
            }
        }
        if (result.getParamValidations().size() > 0) {
            result.setError(ErrorCode.PARAM_INVALID);
            return false;
        }
        return true;
    }

    public boolean isCurrentUser(String userId, long currentUserId, JsonResponse jsonResponse) {
        long id = Long.parseLong(userId);
        if (id != currentUserId) {
            jsonResponse.setError(ErrorCode.PERMISSION_DENIED);
            return false;
        }
        return true;
    }
}

