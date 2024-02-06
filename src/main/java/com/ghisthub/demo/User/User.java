package com.ghisthub.demo.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ghisthub.demo.Model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    private String id;
    @Length(min = 3, message = "user name must have at least 3 characters") @NotBlank(message ="user name is required!")
    private String name;

    @Indexed(unique = true)
    @Email(message = "please provide a valid email") @NotBlank(message ="user email is required!")
    private String email;

    @NotBlank
    @Length(max = 120)
    private String password;

    @Builder.Default
    private Role role = Role.USER;

    private String address;

    private Date passwordChangedAt;

    private Date DOB;

    @JsonIgnore
    @Builder.Default
    private boolean active = true;

    @Indexed
    @CreatedDate
    private Instant createdAt;

    @Indexed
    @LastModifiedDate
    private Instant updatedAt;
}
