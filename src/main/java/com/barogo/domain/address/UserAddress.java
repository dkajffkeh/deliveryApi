package com.barogo.domain.address;

import com.barogo.domain.auth.User;
import com.barogo.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "TBL_USER_ADDRESS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UserAddress extends BaseEntity {
    public static final int MIN_ADDRESS_LENGTH = 10;
    public static final int MAX_ADDRESS_LENGTH = 100;

    public static final int MAX_ADDRESS_TITLE_LENGTH = 10;

    public static final int MIN_ADDRESS_TITLE_LENGTH = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    private String addressTitle;

    @Column(length = 500)
    private String address;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    private User user;


}
