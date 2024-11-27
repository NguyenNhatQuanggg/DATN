import mongoose from "mongoose";

const OrderSchema = mongoose.Schema({
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
    delivery_address: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "DeliveryAddress",
        required: true,
        unique: false,
    },
    discount: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Discount",
        default: null,
        unique: false,
        required: false,
    },
    amount: { type: Number, required: true },
    payment_method: { type: Number, required: true },
    status: { type: Number, required: true },
    created_date: { type: Date, default: new Date() },
    modified_date: { type: Date, default: new Date() },
});

export default mongoose.model("Order", OrderSchema);
