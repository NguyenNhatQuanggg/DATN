import express from "express";
import { getAllDeliveryAddressesByUser, addNewDeliveryAddress } from "../controllers/delivery_address.js";

const router = express.Router();

router.post("/add-delivery-address", addNewDeliveryAddress);
router.get("/get-delivery-addresses-by-user/:userId", getAllDeliveryAddressesByUser)

export default router;
