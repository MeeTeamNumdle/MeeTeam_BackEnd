package synk.meeteam.domain.portfolio.portfolio.dto.command;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import synk.meeteam.domain.portfolio.portfolio.dto.request.CreatePortfolioRequestDto;
import synk.meeteam.domain.portfolio.portfolio.dto.request.UpdatePortfolioRequestDto;
import synk.meeteam.global.entity.ProceedType;

@Mapper(componentModel = "spring")
public interface PortfolioCommandMapper {

    @Named("toProceedType")
    static ProceedType toProceedType(String proceedType) {
        return ProceedType.findByName(proceedType);
    }

    @Mapping(source = "field", target = "fieldId")
    @Mapping(source = "role", target = "roleId")
    @Mapping(source = "startDate", target = "proceedStart")
    @Mapping(source = "endDate", target = "proceedEnd")
    @Mapping(source = "proceedType", target = "proceedType", qualifiedByName = "toProceedType")
    CreatePortfolioCommand toCreatePortfolioCommand(CreatePortfolioRequestDto requestDto);

    @Mapping(source = "field", target = "fieldId")
    @Mapping(source = "role", target = "roleId")
    @Mapping(source = "startDate", target = "proceedStart")
    @Mapping(source = "endDate", target = "proceedEnd")
    @Mapping(source = "proceedType", target = "proceedType", qualifiedByName = "toProceedType")
    UpdatePortfolioCommand toUpdatePortfolioCommand(UpdatePortfolioRequestDto requestDto);

}
