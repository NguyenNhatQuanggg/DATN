import express from "express";
import {
  changePassword,
  getAllUsers,
  getDashboardData,
  getTopSellingProducts,
  login,
  logout,
  ping,
  register,
  resetPassword,
  toggleFavouriteProduct,
  updateUser,
} from "../controllers/user.js";

import { adminAuthMiddleware, tokenVerificationMiddleware, userAuthMiddleware } from "../middlewares/auth.js"

const router = express.Router();

// User
router.post("/verify-token", tokenVerificationMiddleware)
router.get("/ping", ping);
router.post("/register", register);
router.post("/login", login);
router.put("/update", userAuthMiddleware, updateUser);
router.put("/changePassword", userAuthMiddleware, changePassword);
router.post("/resetPassword", resetPassword)
router.get("/logout", logout);
router.post("/toggle-favourite-product", userAuthMiddleware, toggleFavouriteProduct);

// Admin
router.get("/getAllUsers", adminAuthMiddleware, getAllUsers);
router.post("/get-dashboard-data", adminAuthMiddleware, getDashboardData);
router.post("/get-top-selling-products", adminAuthMiddleware, getTopSellingProducts)

export default router;
