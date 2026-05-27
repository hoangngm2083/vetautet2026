package ngm.hoang.utils.mapper;

import ngm.hoang.model.Booking;
import ngm.hoang.persistence.entity.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface BookingMapper {
    BookingEntity toEntity(Booking booking);

    Booking toModel(BookingEntity bookingEntity);
}
