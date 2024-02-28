package synk.meeteam.domain.common.skill.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import synk.meeteam.domain.common.skill.dto.SkillDto;

@Tag(name = "Skill", description = "스킬 관련 API")
public interface SkillApi {
    @Operation(summary = "키워드 기반 스킬 목록 조회 API", description = """
            **키워드**로 시작하는 스킬 목록을 조회합니다. \s
            이때, 스킬명이 따로 없다면 전체 목록을 조회합니다. \s
            조회 시, 최대 갯수를 제한하여 받을 수 있습니다. 기본값은 **5개** 입니다. \s
            만약, 키워드로 조회 시에 데이터가 없다면 **빈 리스트**가 반환됩니다.
            """)
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "조회 성공"
                            , content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = SkillDto.class)), examples = {
                                    @ExampleObject(name = "키워드 없이 조회", value = "[{\"id\":1,\"name\":\"파이썬\"}, {\"id\":2,\"name\":\"스프링\"}]"),
                                    @ExampleObject(name = "키워드가 \"스\"일 경우", value = "[{\"id\":2,\"name\":\"스프링\"}]"),
                                    @ExampleObject(name = "키워드가 \"객체\"일 경우", value = "[]"),
                            })}),
            }
    )
    @SecurityRequirements
    ResponseEntity<List<SkillDto>> getTotalSkills(@Parameter(description = "키워드") String keyword,
                                                  @Parameter(description = "조회 갯수") long limit);
}
