package com.project2.banhangmypham.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderRequestAdmin implements Parcelable {
    private String _id ;
    private User user ;
    private List<CartProductRequest> items = new ArrayList<>();
    private int methodPayment ;
    @SerializedName("delivery_address")
    private AddressTransfer addressTransfer ;
    @SerializedName("discount")
    private DiscountModel discountModel ;
    private long amount  ;
    private int status ;
    private long discounted_amount;
    @SerializedName("created_date")
    private String createDate ;

    protected OrderRequestAdmin(Parcel in) {
        _id = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        items = in.createTypedArrayList(CartProductRequest.CREATOR);
        methodPayment = in.readInt();
        addressTransfer = in.readParcelable(AddressTransfer.class.getClassLoader());
        discountModel = in.readParcelable(DiscountModel.class.getClassLoader());
        amount = in.readLong();
        status = in.readInt();
        discounted_amount = in.readLong();
        createDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeParcelable(user, flags);
        dest.writeTypedList(items);
        dest.writeInt(methodPayment);
        dest.writeParcelable(addressTransfer, flags);
        dest.writeParcelable(discountModel, flags);
        dest.writeLong(amount);
        dest.writeInt(status);
        dest.writeLong(discounted_amount);
        dest.writeString(createDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderRequestAdmin> CREATOR = new Creator<OrderRequestAdmin>() {
        @Override
        public OrderRequestAdmin createFromParcel(Parcel in) {
            return new OrderRequestAdmin(in);
        }

        @Override
        public OrderRequestAdmin[] newArray(int size) {
            return new OrderRequestAdmin[size];
        }
    };

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public long getDiscounted_amount() {
        return discounted_amount;
    }

    public OrderRequestAdmin(User data, List<CartProductRequest> items, int methodPayment, AddressTransfer addressTransfer, DiscountModel discountModel, long amount) {
        this.user = data ;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
    public String toString() {
        return "OrderRequestAdmin{" +
                "items=" + items +
                ", addressTransfer=" + addressTransfer +
                ", discountModel=" + discountModel +
                ", discounted_amount=" + discounted_amount +
                ", status=" + status +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
