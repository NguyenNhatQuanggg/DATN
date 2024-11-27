import mongoose from "mongoose";
import Rating from "../models/rating.js";
import { RATING_STATUS, RES_MESSAGES } from "../utils/constants.js";
import { convertToGMT7, isValidRatingStatus, printTrace } from "../utils/helper.js";

const TAG = "RatingController"

export const getAllRatingsByProduct = async (req, res) => {
    const { productId } = req.params;
    console.log(productId)
    try {
        if (!mongoose.Types.ObjectId.isValid(productId))
            return res.status(404).send(RES_MESSAGES.PRODUCT_NOT_EXIST);

        const ratings = await Rating.find({ product: productId, status: RATING_STATUS.ACCEPTED })
            .populate("user")
            .populate({
                path: "product",
                model: "Product",
                populate: {
                    path: "category",
                    model: "Category",
                }
            }).lean();
        ratings.forEach(item => {
            item.created_date = convertToGMT7(item.created_date);
            item.rating = item.score;
        })
        console.log(ratings);
        res.status(200).json({
            data: ratings,
            isSuccess: true,
            error: "",
        });
    } catch (error) {
        printTrace(TAG, "getAllRatingsByProduct", error);
        res.status(500).json({
            data: [],
            isSuccess: false,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

export const getAllRatingsByUser = async (req, res) => {
    const userId = req.userId;
    try {
        if (!mongoose.Types.ObjectId.isValid(userId))
            return res.status(404).send(RES_MESSAGES.USER_NOT_EXIST);

        const ratings = await Rating.find({ user: userId }).populate("product user");
        res.status(200).json({
            data: ratings,
            isSuccess: true,
            error: "",
        });
    } catch (error) {
        printTrace(TAG, "getAllRatingsByUser", error)
        res.status(500).json({
            data: [],
            isSuccess: false,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

export const addNewRating = async (req, res) => {
    const rating = req.body;
    console.log(rating);
    try {
        if (!mongoose.Types.ObjectId.isValid(rating.product._id))
            return res.status(404).send(RES_MESSAGES.PRODUCT_NOT_EXIST);

        if (!mongoose.Types.ObjectId.isValid(rating.userId))
            return res.status(404).send(RES_MESSAGES.USER_NOT_EXIST);

        const TIME_STAMP = new Date();

        const existedRating = await Rating.findOne({ user: rating.userId, product: rating.product._id });
        if (existedRating) { // Modify rating
            await Rating.findOneAndUpdate(
                { _id: existedRating._id },
                { score: rating.rating, message: rating.message, status: RATING_STATUS.WAITING },
                { new: true }
            );
            return res.status(200).json({
                code: 200,
                error: RES_MESSAGES.UPDATED_RATING_SUCCESS,
            });
        }

        // Add new rating
        const newRating = new Rating({
            ...rating,
            user: rating.userId,
            product: rating.product._id,
            score: rating.rating,
            status: RATING_STATUS.WAITING,
            created_date: TIME_STAMP,
            modified_date: TIME_STAMP,
        });

        newRating.save();

        res.status(200).json({
            code: 200,
            error: RES_MESSAGES.ADD_RATING_SUCCESS
        });
    } catch (error) {
        printTrace(TAG, "addNewRating", error)
        res.status(500).send({
            code: 200,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

export const deleteRating = async (req, res) => {
    const { ratingId } = req.params;
    console.log(ratingId);
    try {
        if (!mongoose.Types.ObjectId.isValid(ratingId))
            return res.status(404).send(RES_MESSAGES.RATING_NOT_EXIST);

        await Rating.findOneAndDelete({ _id: ratingId });
        return res.status(200).send({
            code: 200,
            error: "",
            message: RES_MESSAGES.DELETE_RATING_SUCCESS,
        });
    } catch (error) {
        printTrace(TAG, "deleteRating", error)
        res.status(500).send({
            code: 500,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

// Admin
export const getAllRatingsByAdmin = async (req, res) => {
    try {
        const ratings = await Rating.find()
            .populate({
                path: "product",
                model: "Product",
                populate: {
                    path: "category",
                    model: "Category",
                }
            }).populate("user");
        res.status(200).json({
            data: ratings,
            isSuccess: true,
            error: "",
        });
    } catch (error) {
        printTrace(TAG, "getAllRatingsByAdmin", error)
        res.status(500).json({
            data: [],
            isSuccess: false,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

export const updateRatingStatus = async (req, res) => {
    const rating = req.body;
    try {
        if (!mongoose.Types.ObjectId.isValid(rating._id))
            return res.status(404).send(RES_MESSAGES.RATING_NOT_EXIST);

        if (!isValidRatingStatus(rating.status))
            return res.status(400).send(RES_MESSAGES.INVALID_RATING_STATUS);

        // If new rating status is REJECTED then delete the rating
        if (rating.status === RATING_STATUS.REJECTED) {
            await Rating.findOneAndDelete({ _id: rating._id });
            return res.status(200).json({
                code: 200,
                error: RES_MESSAGES.DELETED_RATING_SUCCESS,
                message: RES_MESSAGES.DELETED_RATING_SUCCESS,
            });
        }

        const TIME_STAMP = new Date();
        await Rating.findOneAndUpdate(
            { _id: rating._id },
            { status: rating.status, modified_date: TIME_STAMP, },
            { new: true }
        );

        res.status(200).json({
            code: 200,
            error: RES_MESSAGES.UPDATED_RATING_SUCCESS,
            message: RES_MESSAGES.UPDATED_RATING_SUCCESS,
        });
    } catch (error) {
        printTrace(TAG, "updateRatingStatus", error)
        res.status(500).send(RES_MESSAGES.SERVER_ERROR);
    }
}