package ngm.hoang.rest.mapper;

import ngm.hoang.cqrs.admin.command.CreateAdminCommand;
import ngm.hoang.cqrs.admin.query.GetAdminByIdQuery;
import ngm.hoang.dto.AdminDTO;
import ngm.hoang.rest.request.CreateAdminRequest;
import ngm.hoang.rest.request.GetAdminByIdRequest;
import ngm.hoang.rest.response.AdminResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdminPresentationMapper {
    AdminResponse toResponse(AdminDTO dto);
    CreateAdminCommand toCommand(CreateAdminRequest request);
    GetAdminByIdQuery toQuery(GetAdminByIdRequest request);
}
