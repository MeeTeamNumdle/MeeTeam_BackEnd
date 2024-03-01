package synk.meeteam.domain.common.field.repository;

import static synk.meeteam.domain.common.field.exception.FieldExceptionType.INVALID_FIELD_ID;

import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.common.field.exception.FieldException;

public interface FieldRepository extends JpaRepository<Field, Long> {

    default Field findByIdOrElseThrowException(Long fieldId) {
        return findById(fieldId).orElseThrow(() -> new FieldException(INVALID_FIELD_ID));
    }
}
