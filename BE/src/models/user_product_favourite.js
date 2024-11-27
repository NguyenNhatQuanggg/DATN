import mongoose from "mongoose";

const UserProductFavouriteSchema = mongoose.Schema({
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "User",
        required: true,
        unique: false,
    },
    product: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Product",
        required: true,
        unique: false,
    },
    created_date: { type: Date, default: new Date() },
});

UserProductFavouriteSchema.index({ user: 1, product: 1 }, { unique: true });

export default mongoose.model("UserProductFavourite", UserProductFavouriteSchema);
