import mongoose from "mongoose";
import { MOBILE_LOCAL_ROUTE } from "../utils/constants.js";

const ProductSchema = mongoose.Schema({
    name: { type: String, trim: true, required: true },
    category: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Category",
        required: true,
        unique: false,
    },
    thumbnail: {
        type: String,
        default: `${MOBILE_LOCAL_ROUTE}/product/default_thumbnail.png`,
    },
    description: { type: String, trim: true, required: true },
    price: { type: Number, required: true, },
    discount: { type: Number, required: true, },
    is_famous: { type: Boolean, default: false },
    is_new: { type: Boolean, default: false },
    created_date: { type: Date, default: new Date() },
    modified_date: { type: Date, default: new Date() },
});

export default mongoose.model("Product", ProductSchema);
