import express from "express";
import { addNewProduct, deleteProduct, getAllProducts, getFavouriteProductsByUser, isFavouriteProduct, updateProduct } from "../controllers/product.js";
import { userAuthMiddleware } from "../middlewares/auth.js";

const router = express.Router();

router.post("/addNewProduct", addNewProduct);
router.get("/getAllProducts", getAllProducts);
router.put("/updateProduct", updateProduct);
router.delete("/deleteProduct/:id", deleteProduct);
router.get("/get-favourite-product-by-user", userAuthMiddleware, getFavouriteProductsByUser);
router.post("/is-favourite-product", userAuthMiddleware, isFavouriteProduct);

export default router;
