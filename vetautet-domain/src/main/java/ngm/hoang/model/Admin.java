package ngm.hoang.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Admin extends BaseModel {

    private String username;
    private String passwordHash;

}
