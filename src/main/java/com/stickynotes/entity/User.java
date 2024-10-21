package com.stickynotes.entity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Builder
@Getter
@Setter
@Entity
public class User
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="password")
    private String password;

    @Column(name="confirm_password")
    private String confirmPassword;

    @Column(name="email")
    private String email;

    @Column(name="terms_accepted")
    private Boolean termsAccepted;

    @Column(name = "created_at")
    @CurrentTimestamp
    private Date createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name="role")
    private String role;


    @Column(name="updated_at")
    @UpdateTimestamp
    private Date updatedAt;

}
