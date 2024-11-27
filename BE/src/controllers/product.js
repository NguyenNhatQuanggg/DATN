import mongoose from "mongoose";
import Product from "../models/product.js";
import Rating from "../models/rating.js";
import UserProductFavourite from "../models/user_product_favourite.js"
import { RATING_STATUS, RES_MESSAGES } from "../utils/constants.js";
import { printData, printTrace } from "../utils/helper.js";

const TAG = "ProductController";

export const getAllProducts = async (req, res) => {
    try {
        const products = await Product.find().populate("category").lean();
        let totalScore = 0;
        for (let product of products) {
            totalScore = 0;
            const ratings = await Rating.find({ product: product._id, status: RATING_STATUS.ACCEPTED });
            ratings.forEach(rating => {
                totalScore += rating.score;
            })
            product["overall_score"] = ratings.length > 0 ? totalScore / ratings.length : 5;
        }
        res.status(200).json({
            data: products,
            isSuccess: true,
            error: "",
        });
    } catch (error) {
        printTrace(TAG, "getAllProducts", error);
        res.status(500).json({
            data: [],
            isSuccess: false,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

export const addNewProduct = async (req, res) => {
    const product = req.body;
    try {
        if (!mongoose.Types.ObjectId.isValid(product.category))
            return res.status(404).send(RES_MESSAGES.CATEGORY_NOT_EXIST);

        const TIME_STAMP = new Date();
        console.log(product.category);

        const newProduct = new Product({
            ...product,
            is_famous: product.isFamous,
            is_new: product.isNew,
            created_date: TIME_STAMP,
            modified_date: TIME_STAMP,
        });

        newProduct.save();

        res.status(200).json({
            code: 200,
            error: ""
        });
    } catch (error) {
        printTrace(TAG, "addNewProduct", error);
        res.status(500).send({
            code: 200,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

export const updateProduct = async (req, res) => {
    const product = req.body;
    console.log(product)
    try {
        if (!mongoose.Types.ObjectId.isValid(product._id)) return res.status(404).send(RES_MESSAGES.PRODUCT_NOT_EXIST);

        const TIME_STAMP = new Date();
        await Product.findOneAndUpdate(
            { _id: product._id },
            { ...product, modified_date: TIME_STAMP },
        );
        return res.status(200).json({
            code: 200,
            error: ""
        });
    } catch (error) {
        printTrace(TAG, "updateProduct", error);
        res.status(500).send({
            code: 500,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

export const deleteProduct = async (req, res) => {
    const { id } = req.params;
    console.log(id);
    try {
        if (!mongoose.Types.ObjectId.isValid(id)) return res.status(404).send(RES_MESSAGES.PRODUCT_NOT_EXIST);

        await Product.findOneAndDelete({ _id: id });
        return res.status(200).send({
            code: 200,
            error: "",
        });
    } catch (error) {
        printTrace(TAG, "deleteProduct", error);
        res.status(500).send({
            code: 500,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

export const getFavouriteProductsByUser = async (req, res) => {
    const userId = req.userId;
    try {
        if (!mongoose.Types.ObjectId.isValid(userId)) return res.status(404).send(RES_MESSAGES.USER_NOT_EXIST);
        const userFavouriteProducts = await UserProductFavourite.find({ user: userId });
        const favouriteProducts = [];
        let product;
        for (let userFavouriteProduct of userFavouriteProducts) {
            product = await Product.findOne({ _id: userFavouriteProduct.product }).populate("category");
            if (product) favouriteProducts.push(product);
        }
        printData(TAG, "getFavouriteProductsByUser", favouriteProducts)
        return res.status(200).send({
            data: favouriteProducts,
            code: 200,
            error: "",
        });
    } catch (error) {
        printTrace(TAG, "getFavouriteProductsByUser", error)
        res.status(500).send({
            code: 500,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

export const isFavouriteProduct = async (req, res) => {
    const data = req.body;
    const userId = req.userId;
    try {
        if (!mongoose.Types.ObjectId.isValid(userId)) return res.status(404).send(RES_MESSAGES.USER_NOT_EXIST);
        if (!mongoose.Types.ObjectId.isValid(data.productId)) return res.status(404).send(RES_MESSAGES.PRODUCT_NOT_EXIST);

        const userProductFavourite = await UserProductFavourite.findOne({ user: data.userId, product: data.productId });
        const isFavourite = userProductFavourite ? true : false;

        return res.status(200).send({
            data: isFavourite,
            code: 200,
            error: "",
        });
    } catch (error) {
        printTrace(TAG, "isFavouriteProduct", error)
        res.status(500).send({
            code: 500,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}