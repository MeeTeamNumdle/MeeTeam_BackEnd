package synk.meeteam.domain.portfolio.portfolio.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import synk.meeteam.domain.portfolio.portfolio.dto.request.CreatePortfolioRequestDto;
import synk.meeteam.domain.portfolio.portfolio.dto.request.UpdatePortfolioRequestDto;
import synk.meeteam.domain.portfolio.portfolio.dto.response.GetPortfolioResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.security.AuthUser;

@Tag(name = "Portfolio", description = "포트폴리오 관련 API")
public interface PortfolioApi {
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "포트폴리오 등록 성공"),
                    @ApiResponse(responseCode = "400", description = "미입력 데이터 존재"),
            }
    )
    @Operation(summary = "포트폴리오 등록 API")
    ResponseEntity<Long> postPortfolio(@RequestBody @Valid CreatePortfolioRequestDto requestDto);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "포트폴리오 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "포트폴리오가 존재하지 않습니다"),
                    @ApiResponse(responseCode = "403", description = "본인의 포트폴리오가 아닙니다"),
            }
    )
    @Operation(summary = "포트폴리오 조회 API")
    ResponseEntity<GetPortfolioResponseDto> getPortfolio(@PathVariable Long portfolioId, @AuthUser User user);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "포트폴리오 수정 성공"),
            }
    )
    @Operation(summary = "포트폴리오 수정 API")
    ResponseEntity<Long> modifyPortfolio(@AuthUser User user,
                                         @PathVariable Long portfolioId,
                                         @RequestBody @Valid UpdatePortfolioRequestDto requestDto);
}
