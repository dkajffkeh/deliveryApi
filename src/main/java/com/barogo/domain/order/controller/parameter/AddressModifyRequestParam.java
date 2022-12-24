package com.barogo.domain.order.controller.parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.barogo.domain.address.UserAddress.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class AddressModifyRequestParam {

    @NotNull
    @Size(min = MIN_ADDRESS_TITLE_LENGTH, max = MAX_ADDRESS_TITLE_LENGTH)
    private String addressTitle;

    @NotNull
    @Size(min = MIN_ADDRESS_LENGTH, max = MAX_ADDRESS_LENGTH)
    private String address;

}
