import Cart from "../models/cart.js";
import { RES_MESSAGES } from "../utils/constants.js";
import { printTrace } from "../utils/helper.js";

const TAG = "CartController"

export const addItemToCart = async (req, res) => {
    const cartReq = req.body;
    console.log("addItemToCart")
    console.log(cartReq)
    try {
        const existedCart = await Cart.findOne({ user: cartReq.userId });
        if (!existedCart) return res.status(401).send(RES_MESSAGES.CART_NOT_EXIST);

        let found = false;
        existedCart.items.every(item => {
            if (item.product.toString() === cartReq.product._id) {
                item.quantity += cartReq.quantity
                found = true;
                return false;
            }
            return true;
        })
        if (!found) existedCart.items.push({ product: cartReq.product._id, quantity: cartReq.quantity })
        existedCart.modified_date = new Date();

        await Cart.findOneAndUpdate(
            { _id: existedCart._id },
            { ...existedCart },
        );
        return res.status(200).json({
            code: 200,
            error: "",
            message: RES_MESSAGES.ADD_ITEM_TO_CART_SUCCESS,
        });
    } catch (error) {
        printTrace(TAG, "addItemToCart", error);
        res.status(500).send(RES_MESSAGES.SERVER_ERROR);
    }
}

export const updateCart = async (req, res) => {
    const cartReq = req.body;
    console.log("updateCart")
    console.log(cartReq)
    try {
        const existedCart = await Cart.findOne({ user: cartReq.userId });
        if (!existedCart) return res.status(401).send(RES_MESSAGES.CART_NOT_EXIST);

        existedCart.items = cartReq.data.filter(item => item.quantity > 0).map(item => ({ product: item.product, quantity: item.quantity }));
        existedCart.modified_date = new Date();

        await Cart.findOneAndUpdate(
            { _id: existedCart._id },
            { ...existedCart },
        );
        return res.status(200).json({
            code: 200,
            error: "",
            message: RES_MESSAGES.UPDATE_CART_SUCCESS
        });
    } catch (error) {
        printTrace(TAG, "updateCart", error);
        res.status(500).send(RES_MESSAGES.SERVER_ERROR);
    }
}

export const getCart = async (req, res) => {
    const userId = req.userId;
    console.log("getCart")
    try {
        const cart = await Cart.findOne({ user: userId }).populate({
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
        if (!cart) return res.status(401).send(RES_MESSAGES.CART_NOT_EXIST);

        let totalPrice = 0;
        cart.items.forEach(item => {
            totalPrice += (item.product.price - item.product.price * item.product.discount / 100) * item.quantity;
        })

        cart.total_price = totalPrice;

        return res.status(200).json({
            data: cart,
            isSuccess: true,
            error: "",
        });
    } catch (error) {
        printTrace(TAG, "getCart", error);
        res.status(500).json({
            data: [],
            isSuccess: false,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}