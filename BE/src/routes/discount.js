import express from "express";
import { addNewDiscount, deleteDiscount, getAllDiscounts, updateDiscount } from "../controllers/discount.js";
import { adminAuthMiddleware } from "../middlewares/auth.js";

const router = express.Router();

router.get("/get-all-discounts", getAllDiscounts);

// Admin
router.post("/add-new-discount", adminAuthMiddleware, addNewDiscount);
router.put("/update-discount", adminAuthMiddleware, updateDiscount);
router.delete("/delete-discount/:discountId", adminAuthMiddleware, deleteDiscount);

export default router;
