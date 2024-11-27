package com.project2.banhangmypham.repository.address_transfer;

import com.project2.banhangmypham.model.AddressResponse;
import com.project2.banhangmypham.model.AddressTransfer;
import com.project2.banhangmypham.model.MessageResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface IAddressTransferRepository {
    Single<MessageResponse> addAddressTransfer(AddressTransfer addressTransfer);
    Single<AddressResponse> getAllAddressTransferList(String uid);
}
