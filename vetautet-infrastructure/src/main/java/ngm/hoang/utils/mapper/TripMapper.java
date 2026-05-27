package ngm.hoang.utils.mapper;

import ngm.hoang.model.Trip;
import ngm.hoang.persistence.entity.TripEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface TripMapper {
    TripEntity toEntity(Trip trip);

    Trip toModel(TripEntity tripEntity);
}
