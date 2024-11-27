import mongoose from "mongoose";

const CategorySchema = mongoose.Schema({
  name: { type: String, trim: true, required: true, unique: true },
  created_date: { type: Date, default: new Date() },
  modified_date: { type: Date, default: new Date() },
});

export default mongoose.model("Category", CategorySchema);
