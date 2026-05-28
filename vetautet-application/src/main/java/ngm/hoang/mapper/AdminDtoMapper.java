package ngm.hoang.mapper;

import ngm.hoang.dto.AdminDTO;
import ngm.hoang.model.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface AdminDtoMapper {
    AdminDTO toDto(Admin admin);
}
