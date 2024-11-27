import mongoose from "mongoose";
import Category from "../models/category.js";
import { printTrace } from "../utils/helper.js"
import { RES_MESSAGES } from "../utils/constants.js";

const TAG = "CategoryController"

export const addNewCategory = async (req, res) => {
    const category = req.body;
    try {
        const existedCategory = await Category.findOne({ name: category.name });
        if (existedCategory) return res.status(401).send(RES_MESSAGES.CATEGORY_NAME_EXIST);

        const TIME_STAMP = new Date();
        const newCategory = new Category({
            ...category,
            created_date: TIME_STAMP,
            modified_date: TIME_STAMP,
        });
        await newCategory.save();

        res.status(200).json(newCategory);
    } catch (error) {
        printTrace(TAG, "addNewCategory", error);
        res.status(500).send(RES_MESSAGES.SERVER_ERROR);
    }
}

export const getAllCategories = async (req, res) => {
    try {
        const categories = await Category.find({}, "name");
        return res.status(200).json({
            data: categories,
            isSuccess: true,
            error: "",
        });
    } catch (error) {
        printTrace(TAG, "getAllCategories", error);
        res.status(500).json({
            data: [],
            isSuccess: false,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

export const updateCategory = async (req, res) => {
    const category = req.body;
    console.log(category)
    try {
        if (!mongoose.Types.ObjectId.isValid(category._id)) return res.status(404).send(RES_MESSAGES.CATEGORY_NOT_EXIST);
        if (!category.name || category.name === "") return res.status(400).send(RES_MESSAGES.INVALID_CATEGORY_NAME);

        const TIME_STAMP = new Date();
        const newCategory = await Category.findOneAndUpdate(
            { _id: category._id },
            { ...category, modified_date: TIME_STAMP },
            { new: true },
        );
        return res.status(200).json(newCategory);
    } catch (error) {
        printTrace(TAG, "updateCategory", error);
        res.status(500).send(RES_MESSAGES.SERVER_ERROR);
    }
}

export const deleteCategory = async (req, res) => {
    const { id } = req.params;
    try {
        if (!mongoose.Types.ObjectId.isValid(id)) return res.status(404).send(RES_MESSAGES.CATEGORY_NOT_EXIST);

        await Category.findOneAndDelete({ _id: id });
        return res.status(200).send(RES_MESSAGES.DELETE_CATEGORY_SUCCESSFULLY);
    } catch (error) {
        printTrace(TAG, "deleteCategory", error);
        res.status(500).send(RES_MESSAGES.SERVER_ERROR);
    }
}
