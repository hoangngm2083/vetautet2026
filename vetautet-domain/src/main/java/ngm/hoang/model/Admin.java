package ngm.hoang.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Admin extends BaseModel {

    public static Admin create(String username, String passwordHash){
        // validate


        return Admin.builder()
                .id(BaseModel.genId())
                .username(username)
                .passwordHash(passwordHash)
                .build();
    }

    private String username;
    private String passwordHash;

}
