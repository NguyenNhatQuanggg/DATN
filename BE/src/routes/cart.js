import express from "express";
import { getCart, addItemToCart, updateCart } from "../controllers/cart.js";
import { userAuthMiddleware } from "../middlewares/auth.js";

const router = express.Router();

router.get("/get-cart", userAuthMiddleware, getCart);
router.put("/add-item-to-cart", userAuthMiddleware, addItemToCart)
router.put("/update-cart", userAuthMiddleware, updateCart)

export default router;
