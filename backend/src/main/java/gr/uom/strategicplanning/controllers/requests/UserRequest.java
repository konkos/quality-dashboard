package gr.uom.strategicplanning.controllers.requests;

import gr.uom.strategicplanning.models.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private Long organizationId;

    public UserRequest(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        if(user.getOrganization() != null){
            this.organizationId = user.getOrganization().getId();
        }
    }
}
