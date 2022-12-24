package com.barogo.domain.auth;


import com.barogo.domain.address.UserAddress;
import com.barogo.domain.auth.controller.parameter.signup.SignUpParameter;
import com.barogo.common.type.Role;
import com.barogo.domain.base.BaseEntity;
import com.barogo.domain.order.Order;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.barogo.common.type.Role.ROLE_ADMIN;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity(name = "TBL_USER")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private Password password;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = LAZY, cascade = ALL)
    private List<UserAddress> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = LAZY, cascade = ALL)
    private List<Order> orders = new ArrayList<>();

    public String passwordDirectGet() {
        return password.getPassword();
    }

    public User(SignUpParameter signUpParameter, PasswordEncoder passwordEncoder) {
        this.password = new Password(signUpParameter.getPassword(),passwordEncoder);
        this.userId = signUpParameter.getUserId();
        this.username = signUpParameter.getUsername();
        // TODO 권한별 접근자 개발. 2022.12.22
        this.role = ROLE_ADMIN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public static final int MAX_USER_ID_LENGTH = 15;
    public static final int MIN_USER_ID_LENGTH = 6;
    public static final int MAX_USERNAME_LENGTH = 15;
    public static final int MIN_USERNAME_LENGTH = 1;
}
