package com.project2.banhangmypham.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

//[
//        {
//_id: new ObjectId('6725a52fc3e09d6a380d17d8'),
//title: 'Mừng xuân 2024',
//description: 'Tưng bừng khuyến mãi mừng xuân 2024 giảm 50% mọi sản phẩm',
//value: 50,
//condition: 0,
//quantity: 10,
//effective_from: 2024-11-02T04:05:33.354Z,
//effective_to: 2024-11-09T04:05:33.354Z,
//created_date: 2024-11-02T04:06:07.753Z,
//modified_date: 2024-11-02T04:06:07.753Z,
//__v: 0,
//valid: true
//        }
//        ]

public class DiscountModel implements Parcelable {
    private String _id ;
    @SerializedName("title")
    private String titleDiscount;
    @SerializedName("description")
    private String subTitleDiscount;
    private int value ;
    private int condition;
    private int quantity ;
    boolean valid ;

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public String getTitleDiscount() {
        return titleDiscount;
    }

    public String getSubTitleDiscount() {
        return subTitleDiscount;
    }

    public int getValue() {
        return value;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCondition() {
        return condition;
    }

    public DiscountModel(String titleDiscount, String subTitleDiscount, int value) {
        this.titleDiscount = titleDiscount;
        this.subTitleDiscount = subTitleDiscount;
        this.value = value;
    }

    public DiscountModel(String titleDiscount, String subTitleDiscount, int value, int condition, int quantity) {
        this.titleDiscount = titleDiscount;
        this.subTitleDiscount = subTitleDiscount;
        this.value = value;
        this.condition = condition;
        this.quantity = quantity;
    }

    public boolean isValid() {
        return valid;
    }

    protected DiscountModel(Parcel in) {
        _id = in.readString();
        titleDiscount = in.readString();
        subTitleDiscount = in.readString();
        value = in.readInt();
        condition = in.readInt();
        quantity = in.readInt();
        valid = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(titleDiscount);
        dest.writeString(subTitleDiscount);
        dest.writeInt(value);
        dest.writeInt(condition);
        dest.writeInt(quantity);
        dest.writeByte((byte) (valid ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DiscountModel> CREATOR = new Creator<DiscountModel>() {
        @Override
        public DiscountModel createFromParcel(Parcel in) {
            return new DiscountModel(in);
        }

        @Override
        public DiscountModel[] newArray(int size) {
            return new DiscountModel[size];
        }
    };
}
