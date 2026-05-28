package ngm.hoang.mapper;

import ngm.hoang.dto.AdminDTO;
import ngm.hoang.model.Admin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdminDtoMapperTest {

    private final AdminDtoMapper mapper = Mappers.getMapper(AdminDtoMapper.class);

    @Test
    @DisplayName("toDto: maps id and username, excludes passwordHash")
    void toDto_mapsPublicFieldsOnly() {
        UUID id = UUID.randomUUID();
        Admin admin = Admin.builder()
                .id(id)
                .username("adminUser")
                .passwordHash("secretHash")
                .build();

        AdminDTO dto = mapper.toDto(admin);

        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals("adminUser", dto.username());
    }
}
