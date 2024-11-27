import mongoose from "mongoose";
import { MOBILE_LOCAL_ROUTE } from "../utils/constants.js";

const UserSchema = mongoose.Schema({
  username: { type: String, trim: true, required: true, unique: true },
  password: { type: String, trim: true },
  full_name: { type: String, trim: true, required: true },
  address: { type: String, trim: true, required: true },
  phone: { type: String, trim: true, unique: true },
  profile_image: {
    type: String,
    default: `${MOBILE_LOCAL_ROUTE}/product/default_profile_image.jpg`,
  },
  banned: { type: Boolean, default: false },
  is_admin: { type: Boolean, default: false },
  created_date: { type: Date, default: new Date() },
  modified_date: { type: Date, default: new Date() },
});

export default mongoose.model("User", UserSchema);
