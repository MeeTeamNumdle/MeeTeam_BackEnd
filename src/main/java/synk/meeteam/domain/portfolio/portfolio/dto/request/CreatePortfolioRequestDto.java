package synk.meeteam.domain.portfolio.portfolio.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import synk.meeteam.domain.portfolio.portfolio_link.dto.PortfolioLinkDto;

public record CreatePortfolioRequestDto(
        @Schema(description = "제목", example = "Meeteam")
        @NotNull
        @Size(max = 20, min = 4)
        String title,
        @Schema(description = "부연설명", example = "대학생 맞춤형 구인 포트폴리오 서비스")
        @NotNull
        @Size(max = 20, min = 4)
        String description,
        @Schema(description = "내용", example = "밋팀(Meeteam)은 나 자신을 의미하는 Me, 팀을 의미하는 Team, 만남을 의미하는 Meet이 합쳐진 단어입니다.\n"
                + "대학생들의 보다 원활한 팀프로젝트를 위해 기획하게 되었습니다.\n"
                + "\n"
                + "\n"
                + "밋팀(Meeteam)은 나 자신을 의미하는 Me, 팀을 의미하는 Team, 만남을 의미하는 Meet이 합쳐진 단어입니다.\n"
                + "대학생들의 보다 원활한 팀프로젝트를 위해 기획하게 되었으며, 그 외에 포토폴리오로서의 기능까지 생각하고 있습니다!\n"
                + "\n"
                + "이를 위해 함께 멋진 서비스를 완성할 웹 디자이너를 찾고 있어요!\n"
                + "밋팀(Meeteam)은 나 자신을 의미하는 Me, 팀을 의미하는 Team, 만남을 의미하는 Meet이 합쳐진 단어입니다.\n"
                + "대학생들의 보다 원활한 팀프로젝트를 위해 기획하게 되었으며, 그 외에 포토폴리오로서의 기능까지 생각하고 있습니다!\n")
        @NotNull
        String content,
        @Schema(description = "분야", example = "1")
        @NotNull
        Long field,
        @Schema(description = "역할", example = "1")
        @NotNull
        Long role,
        @Schema(description = "시작일", example = "2023-11-02")
        @NotNull
        LocalDate startDate,
        @Schema(description = "종료일", example = "2024-03-15")
        @NotNull
        LocalDate endDate,
        @Schema(description = "진행방식", example = "온라인")
        @NotNull
        String proceedType,
        @NotNull
        @Pattern(regexp = "^(\\S+(\\.(?i)(jpg|png|gif|bmp|jpeg))$)")
        String mainImageFileName,
        @NotNull
        @Pattern(regexp = "^(\\S+(\\.(?i)(zip))$)")
        String zipFileName,
        @Schema(description = "zip 파일 순서", example = "[\"이미지1.png\",\"이미지2.jpg\"]")
        @NotNull
        List<String> fileOrder,
        @Schema(description = "스킬", example = "[1,2,3]")
        @NotNull
        @Size(max = 10)
        List<Long> skills,
        @NotNull
        @Size(max = 10)
        List<@Valid PortfolioLinkDto> links
) {
}
