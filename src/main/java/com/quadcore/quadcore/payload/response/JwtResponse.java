package com.quadcore.quadcore.payload.response;
import com.quadcore.quadcore.Entities.User;
import com.quadcore.quadcore.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private User user;
    public JwtResponse(String accessToken, User user) {
        this.token = accessToken;
        this.user = user;

    }


}