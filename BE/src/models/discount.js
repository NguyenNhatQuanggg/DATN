import mongoose from "mongoose";

const DiscountSchema = mongoose.Schema({
    title: { type: String, required: true },
    description: { type: String, required: true },
    value: { type: Number, required: true },
    condition: { type: Number, default: 0 },
    quantity: { type: Number, default: 0 },
    effective_from: { type: Date, required: true },
    effective_to: { type: Date, required: true },
    created_date: { type: Date, default: new Date(), },
    modified_date: { type: Date, default: new Date(), },
});

export default mongoose.model("Discount", DiscountSchema);
