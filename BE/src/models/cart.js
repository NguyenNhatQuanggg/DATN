import mongoose from "mongoose";

const CartSchema = mongoose.Schema({
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "User",
        required: true,
        unique: false,
    },
    items: {
        type: [{
            product: {
                type: mongoose.Schema.Types.ObjectId,
                ref: "Product",
                required: true,
                unique: false,
            },
            quantity: { type: Number, required: true, },
        }],
        required: true,
    },
    created_date: { type: Date, default: new Date() },
    modified_date: { type: Date, default: new Date() },
});

export default mongoose.model("Cart", CartSchema);
