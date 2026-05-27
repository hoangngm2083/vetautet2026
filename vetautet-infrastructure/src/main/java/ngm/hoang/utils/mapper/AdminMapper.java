package ngm.hoang.utils.mapper;

import ngm.hoang.model.Admin;
import ngm.hoang.persistence.entity.AdminEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface AdminMapper {
    AdminEntity toEntity(Admin admin);

    Admin toModel(AdminEntity adminEntity);
}
