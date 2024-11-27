import mongoose from "mongoose";

const DeliveryAddressSchema = mongoose.Schema({
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "User",
        required: true,
        unique: false,
    },
    receiver_name: { type: String, trim: true, required: true },
    receiver_phone: { type: String, trim: true, required: true },
    address: { type: String, trim: true, required: true },
    created_date: { type: Date, default: new Date() },
    modified_date: { type: Date, default: new Date() },
});

export default mongoose.model("DeliveryAddress", DeliveryAddressSchema);
