package synk.meeteam.domain.common.field.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.common.field.repository.FieldRepository;

@Service
@RequiredArgsConstructor
public class FieldService {
    private final FieldRepository fieldRepository;

    public Field findByFieldId(Long fieldId) {
        return fieldRepository.findByIdOrElseThrowException(fieldId);
    }
}
