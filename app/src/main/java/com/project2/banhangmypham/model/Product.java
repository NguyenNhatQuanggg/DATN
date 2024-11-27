package com.project2.banhangmypham.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "Product")
public class Product implements Parcelable {

    @PrimaryKey
    @NonNull
    @SerializedName("_id")
    private String _id ;
    @SerializedName("name")
    private String name;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("description")
    private String description;
    @SerializedName("category")
    private Category category ;
    @SerializedName("price")
    private Long price;
    @SerializedName("discount")
    private int discount;
    @SerializedName("is_famous")
    private boolean isFamous;
    @SerializedName("is_new")
    private boolean isNew ;
    @SerializedName("overall_score")
    private float overAllScore = 0f;

    protected Product(Parcel in) {
        _id = in.readString();
        name = in.readString();
        thumbnail = in.readString();
        description = in.readString();
        category = in.readParcelable(Category.class.getClassLoader());
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readLong();
        }
        discount = in.readInt();
        isFamous = in.readByte() != 0;
        isNew = in.readByte() != 0;
        overAllScore = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(thumbnail);
        dest.writeString(description);
        dest.writeParcelable(category, flags);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(price);
        }
        dest.writeInt(discount);
        dest.writeByte((byte) (isFamous ? 1 : 0));
        dest.writeByte((byte) (isNew ? 1 : 0));
        dest.writeFloat(overAllScore);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public float getOverAllScore() {
        return overAllScore;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Product(String name, String thumbnail, String description, Category category, Long price, int discount, boolean isFamous, boolean isNew) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.description = description;
        this.category = category;
        this.price = price;
        this.discount = discount;
        this.isFamous = isFamous;
        this.isNew = isNew;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public boolean isFamous() {
        return isFamous;
    }

    public void setFamous(boolean famous) {
        isFamous = famous;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public Product(String name, String thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category=" + category +
                ", isFamous=" + isFamous +
                ", isNew=" + isNew +
                ", discount=" + discount +
                ", price=" + price +
                '}';
    }
}
