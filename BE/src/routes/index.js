import express from "express";

import userRoutes from "./user.js"
import categoryRoutes from "./category.js"
import productRoutes from "./product.js"
import deliveryAddressRoutes from "./delivery_address.js"
import ratingRoutes from "./rating.js"
import cartRoutes from "./cart.js"
import discountRoutes from "./discount.js"
import orderRoutes from "./order.js"

const router = express.Router();

router.use("/user", userRoutes)
router.use("/category", categoryRoutes)
router.use("/product", productRoutes)
router.use("/delivery-address", deliveryAddressRoutes)
router.use("/rating", ratingRoutes)
router.use("/cart", cartRoutes)
router.use("/discount", discountRoutes)
router.use("/discount", discountRoutes)
router.use("/order", orderRoutes)

export default router;
