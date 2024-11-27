import express from "express";
import { addNewRating, getAllRatingsByProduct, getAllRatingsByUser, deleteRating, updateRatingStatus, getAllRatingsByAdmin } from "../controllers/rating.js";
import { adminAuthMiddleware, userAuthMiddleware } from "../middlewares/auth.js";

const router = express.Router();

router.post("/add-rating", userAuthMiddleware, addNewRating);
router.get("/get-all-ratings-by-product/:productId", getAllRatingsByProduct)
router.get("/get-all-ratings-by-user", userAuthMiddleware, getAllRatingsByUser)
router.delete("/delete-rating/:ratingId", userAuthMiddleware, deleteRating)

// Admin
router.put("/update-rating-status", adminAuthMiddleware, updateRatingStatus)
router.get("/get-all-ratings-by-admin", adminAuthMiddleware, getAllRatingsByAdmin)

export default router;
