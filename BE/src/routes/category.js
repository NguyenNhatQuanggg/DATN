import express from "express";
import { addNewCategory, deleteCategory, getAllCategories, updateCategory } from "../controllers/category.js";

const router = express.Router();

router.post("/addNewCategory", addNewCategory);
router.get("/getAllCategories", getAllCategories)
router.put("/updateCategory", updateCategory)
router.delete("/deleteCategory/:id", deleteCategory)

export default router;
