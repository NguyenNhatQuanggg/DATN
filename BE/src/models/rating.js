import mongoose from "mongoose";
import { RATING_STATUS } from "../utils/constants.js";

const RatingSchema = mongoose.Schema({
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
    score: { type: Number, required: true },
    message: { type: String, trim: true, required: true },
    status: { type: Number, default: RATING_STATUS.ACCEPTED },
    created_date: { type: Date, default: new Date() },
    modified_date: { type: Date, default: new Date() },
});

RatingSchema.index({ user: 1, product: 1 }, { unique: true });

export default mongoose.model("Rating", RatingSchema);
