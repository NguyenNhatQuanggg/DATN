import mongoose from "mongoose";
import Order from "../models/order.js";
import Cart from "../models/cart.js";
import { ORDER_STATUS, RES_MESSAGES } from "../utils/constants.js";
import { isValidOrderStatus, isValidPaymentMethod, printTrace } from "../utils/helper.js"
import { sendOrderEmail } from "../../mail_sender.js";

const TAG = "OrderController";

export const getOrdersByUser = async (req, res) => {
    const userId = req.userId
    try {
        const orders = await Order.find({ user: userId })
            .populate("discount")
            .populate("delivery_address")
            .populate("payment_method")
            .populate({
                path: "items",
                populate: {
                    path: "product",
                    model: "Product",
                    populate: {
                        path: "category",
                        model: "Category",
                    }
                },
            }).lean();
        for (let order of orders) {
            order.discounted_amount = order.amount;
            order.addressTransfer = order.delivery_address;
            order.discountModel = order.discount;

            if (order.discount)
                order.discounted_amount = order.amount - (order.amount * order.discount.value) / 100;
        }
        console.log("orders = " + orders);
        res.status(200).json({
            data: orders,
            isSuccess: true,
            error: "",
        });
    } catch (error) {
        printTrace(TAG, "getOrdersByUser", error);
        res.status(500).json({
            data: [],
            isSuccess: false,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

export const addNewOrder = async (req, res) => {
    const order = req.body;
    const userId = req.userId
    try {
        if (!mongoose.Types.ObjectId.isValid(userId))
            return res.status(404).send(RES_MESSAGES.USER_NOT_EXIST);

        if (order.addressTransfer && !mongoose.Types.ObjectId.isValid(order.addressTransfer._id))
            return res.status(404).send(RES_MESSAGES.DELIVERY_ADDRESS_NOT_EXIST);

        if (order.discount && !mongoose.Types.ObjectId.isValid(order.discount))
            return res.status(404).send(RES_MESSAGES.DISCOUNT_NOT_EXIST);

        if (order.amount <= 0)
            return res.status(400).send(RES_MESSAGES.INVALID_AMOUNT);

        if (!isValidPaymentMethod(order.methodPayment))
            return res.status(400).send(RES_MESSAGES.INVALID_PAYMENT_METHOD);

        const TIME_STAMP = new Date();
        let newOrder = new Order({
            user: order.userId,
            delivery_address: order.addressTransfer._id,
            discount: order.discount,
            amount: order.amount,
            payment_method: order.methodPayment,
            items: order.items,
            status: ORDER_STATUS.WAITING,
            created_date: TIME_STAMP,
            modified_date: TIME_STAMP,
        });
        await newOrder.save();


        newOrder = await Order.findOne({ _id: newOrder._id }).populate("discount")
            .populate("delivery_address")
            .populate("payment_method")
            .populate({
                path: "items",
                populate: {
                    path: "product",
                    model: "Product",
                    populate: {
                        path: "category",
                        model: "Category",
                    }
                },
            }).lean();

        await Cart.findOneAndUpdate({ user: order.userId }, { items: [] });
        newOrder.discount_value = newOrder.discount ? newOrder.discount.value * newOrder.amount / 100 : 0;
        newOrder.discounted_amount = newOrder.discount ? newOrder.amount - (newOrder.discount.value * newOrder.amount / 100) : newOrder.amount;
        await sendOrderEmail(newOrder);

        res.status(200).json({
            code: 200,
            error: "",
        });
    } catch (error) {
        printTrace(TAG, "addNewOrder", error);
        res.status(500).send({
            code: 200,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

// Admin
export const getAllOrdersByAdmin = async (req, res) => {
    try {
        const orders = await Order.find()
            .populate("user")
            .populate("discount")
            .populate("delivery_address")
            .populate({
                path: "items",
                populate: {
                    path: "product",
                    model: "Product",
                    populate: {
                        path: "category",
                        model: "Category",
                    }
                },
            }).lean();
        for (let order of orders) {
            order.discounted_amount = order.amount;
            if (order.discount)
                order.discounted_amount = order.amount - (order.amount * order.discount.value) / 100;
        }

        console.log(orders);

        res.status(200).json({
            data: orders,
            isSuccess: true,
            error: "",
        });
    } catch (error) {
        printTrace(TAG, "getAllOrdersByAdmin", error);
        res.status(500).json({
            data: [],
            isSuccess: false,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

export const updateOrderStatus = async (req, res) => {
    const order = req.body;
    console.log(order);
    try {
        if (!mongoose.Types.ObjectId.isValid(order._id))
            return res.status(404).send(RES_MESSAGES.ORDER_NOT_EXIST);

        if (!isValidOrderStatus(order.status))
            return res.status(400).send(RES_MESSAGES.INVALID_ORDER_STATUS);

        const TIMESTAMP = new Date();
        await Order.findOneAndUpdate(
            { _id: order._id },
            { status: order.status, modified_date: TIMESTAMP }
        );

        res.status(200).json({
            isSuccess: true,
            error: "",
            message: RES_MESSAGES.UPDATE_ORDER_STATUS_SUCCESS,
        });
    } catch (error) {
        printTrace(TAG, "updateOrderStatus", error);
        res.status(500).json({
            data: [],
            isSuccess: false,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

export const cancelOrder = async (req, res) => {
    const order = req.body;
    console.log(order);
    try {
        if (!mongoose.Types.ObjectId.isValid(order._id))
            return res.status(404).send(RES_MESSAGES.ORDER_NOT_EXIST);

        const TIMESTAMP = new Date();
        await Order.findOneAndUpdate(
            { _id: order._id },
            { status: ORDER_STATUS.CANCELED, modified_date: TIMESTAMP }
        );

        res.status(200).json({
            isSuccess: true,
            error: "",
            message: RES_MESSAGES.CANCEL_ORDER_SUCCESS,
        });
    } catch (error) {
        printTrace(TAG, "cancelOrder", error);
        res.status(500).json({
            data: [],
            isSuccess: false,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}
