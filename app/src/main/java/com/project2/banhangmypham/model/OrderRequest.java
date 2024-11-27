package com.project2.banhangmypham.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest implements Parcelable {
    private String _id ;
    private String userId ;
    private List<CartProductRequest> items = new ArrayList<>();
    private int methodPayment ;
    private AddressTransfer addressTransfer ;
    private DiscountModel discountModel ;
    private long amount  ;
    private int status ;
    private long discounted_amount;

    protected OrderRequest(Parcel in) {
        _id = in.readString();
        userId = in.readString();
        items = in.createTypedArrayList(CartProductRequest.CREATOR);
        methodPayment = in.readInt();
        addressTransfer = in.readParcelable(AddressTransfer.class.getClassLoader());
        discountModel = in.readParcelable(DiscountModel.class.getClassLoader());
        amount = in.readLong();
        status = in.readInt();
        discounted_amount = in.readLong();
    }

    public static final Creator<OrderRequest> CREATOR = new Creator<OrderRequest>() {
        @Override
        public OrderRequest createFromParcel(Parcel in) {
            return new OrderRequest(in);
        }

        @Override
        public OrderRequest[] newArray(int size) {
            return new OrderRequest[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public long getDiscounted_amount() {
        return discounted_amount;
    }

    public OrderRequest(String userId, List<CartProductRequest> items, int methodPayment, AddressTransfer addressTransfer, DiscountModel discountModel, long amount) {
        this.userId = userId;
        this.items = items;
        this.methodPayment = methodPayment;
        this.addressTransfer = addressTransfer;
        this.discountModel = discountModel;
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartProductRequest> getItems() {
        return items;
    }

    public void setItems(List<CartProductRequest> items) {
        this.items = items;
    }

    public int getMethodPayment() {
        return methodPayment;
    }

    public void setMethodPayment(int methodPayment) {
        this.methodPayment = methodPayment;
    }

    public AddressTransfer getAddressTransfer() {
        return addressTransfer;
    }

    public void setAddressTransfer(AddressTransfer addressTransfer) {
        this.addressTransfer = addressTransfer;
    }

    public DiscountModel getDiscountModel() {
        return discountModel;
    }

    public void setDiscountModel(DiscountModel discountModel) {
        this.discountModel = discountModel;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(userId);
        dest.writeTypedList(items);
        dest.writeInt(methodPayment);
        dest.writeParcelable(addressTransfer, flags);
        dest.writeParcelable(discountModel, flags);
        dest.writeLong(amount);
        dest.writeInt(status);
        dest.writeLong(discounted_amount);
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "addressTransfer=" + addressTransfer +
                ", discountModel=" + discountModel +
                ", methodPayment=" + methodPayment +
                ", amount=" + amount +
                ", discounted_amount=" + discounted_amount +
                '}';
    }
}
