package synk.meeteam.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Service
@ActiveProfiles("test")
public class DatabaseCleanUp implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
                .map(e -> e.getName())
                .collect(Collectors.toList());
    }

    @Transactional
    public void clear() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (String tableName : tableNames) {
            String convertedTableName = convert(tableName);

            if (convertedTableName.equals("user")) {
                convertedTableName = "users";
            }
            entityManager.createNativeQuery("TRUNCATE TABLE " + convertedTableName).executeUpdate();
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private String convert(String camelCase) {
        StringBuilder snakeCase = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char currentChar = camelCase.charAt(i);
            // 대문자인 경우
            if (Character.isUpperCase(currentChar)) {
                if (i > 0) {
                    // 첫 문자가 아니면 밑줄 추가
                    snakeCase.append("_");
                }
                // 소문자로 변환하여 추가
                snakeCase.append(Character.toLowerCase(currentChar));
            } else {
                // 그 외의 경우 그대로 추가
                snakeCase.append(currentChar);
            }
        }
        return snakeCase.toString();
    }
}
