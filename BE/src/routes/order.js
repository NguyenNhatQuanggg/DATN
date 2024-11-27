import express from "express";
import { addNewOrder, cancelOrder, getAllOrdersByAdmin, getOrdersByUser, updateOrderStatus } from "../controllers/order.js";
import { adminAuthMiddleware, userAuthMiddleware } from "../middlewares/auth.js";

const router = express.Router();

router.get("/get-orders-by-user", userAuthMiddleware, getOrdersByUser)
router.post("/add-new-order", userAuthMiddleware, addNewOrder)
router.get("/get-all-orders-by-admin", adminAuthMiddleware, getAllOrdersByAdmin)
router.put("/update-order-status", adminAuthMiddleware, updateOrderStatus)
router.put("/cancel-order", userAuthMiddleware, cancelOrder)

export default router;
