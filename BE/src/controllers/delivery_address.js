import mongoose from "mongoose";
import DeliveryAddress from "../models/delivery_address.js";
import { printTrace } from "../utils/helper.js"
import { RES_MESSAGES } from "../utils/constants.js";

const TAG = "DeliveryAddressController"

export const getAllDeliveryAddressesByUser = async (req, res) => {
    const { userId } = req.params;
    console.log(userId);
    try {
        if (!mongoose.Types.ObjectId.isValid(userId))
            return res.status(404).send(RES_MESSAGES.USER_NOT_EXIST);

        const deliveryAddresses = await DeliveryAddress.find({ user: userId });
        res.status(200).json({
            data: deliveryAddresses,
            isSuccess: true,
            error: "",
        });
    } catch (error) {
        printTrace(TAG, "getAllDeliveryAddressesByUser", error)
        res.status(500).json({
            data: [],
            isSuccess: false,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}

export const addNewDeliveryAddress = async (req, res) => {
    const deliveryAddress = req.body;
    console.log(deliveryAddress);
    try {
        if (!mongoose.Types.ObjectId.isValid(deliveryAddress.uid))
            return res.status(404).send(RES_MESSAGES.USER_NOT_EXIST);

        const TIME_STAMP = new Date();
        const newDeliveryAddress = new DeliveryAddress({
            user: deliveryAddress.uid,
            receiver_name: deliveryAddress.nameReceiver,
            receiver_phone: deliveryAddress.phoneReceiver,
            address: deliveryAddress.addressReceiver,
            created_date: TIME_STAMP,
            modified_date: TIME_STAMP,
        });

        newDeliveryAddress.save();

        res.status(200).json({
            code: 200,
            error: "",
            message: RES_MESSAGES.ADD_DELIVERY_ADDRESS_SUCCESS,
        });
    } catch (error) {
        printTrace(TAG, "addNewDeliveryAddress", error)
        res.status(500).send({
            code: 200,
            error: RES_MESSAGES.SERVER_ERROR,
        });
    }
}
