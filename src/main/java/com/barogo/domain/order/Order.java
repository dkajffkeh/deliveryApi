package com.barogo.domain.order;

import com.barogo.common.exception.ApiServerException;
import com.barogo.common.type.OrderStatus;
import com.barogo.domain.auth.User;
import com.barogo.domain.base.BaseEntity;
import com.barogo.domain.order.controller.parameter.AddressModifyRequestParam;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.barogo.common.code.ResultCode.RESULT_4000;
import static com.barogo.common.type.OrderStatus.ORDER_RECEIVING;

@Entity(name = "TBL_ORDER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user"))
    private User user;

    @Column
    private String menu;

    @Column
    private String storeName;

    @Column
    private String address;

    @Column
    private String addressTitle;

    @Column
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    public static final String ORDER_NOT_FOUND_MSG = "주문정보를 찾을 수 없습니다.";
    public static final String ADDRESS_NOT_MODIFIABLE_MSG = "주문이 진행중인 상태에서는 주소를 변경할 수 없습니다.";

    public void modifyAddressInfo(AddressModifyRequestParam addressModifyRequestParam) {
        if(!isAddressModifiable()) throw new ApiServerException(RESULT_4000,ADDRESS_NOT_MODIFIABLE_MSG);
        this.address = addressModifyRequestParam.getAddress();
        this.addressTitle = addressModifyRequestParam.getAddressTitle();
    }

    private boolean isAddressModifiable() {
        return this.orderStatus == ORDER_RECEIVING;
    }
}
