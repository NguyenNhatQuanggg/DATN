import jwt from "jsonwebtoken";
import User from "../models/user.js";
import { ACCESS_TOKEN, RES_MESSAGES } from "../utils/constants.js";
import { JwtErrorHandler } from "../utils/validator.js";
import { printTrace } from "../utils/helper.js";

const TAG = "AuthMiddleware"

export const userAuthMiddleware = async (req, res, next) => {
    try {
        let token = "";
        if (req.headers.authorization && req.headers.authorization.split(' ')[0] === 'Bearer')
            token = req.headers.authorization.split(' ')[1];
        else return res.status(401).send(RES_MESSAGES.AUTHENTICATION_FAILED);
        const decodedData = jwt.verify(token, ACCESS_TOKEN.SECRET_KEY);
        const user = await User.findOne({ _id: decodedData.id });
        if (user) {
            req.userId = decodedData.id;
            next();
        }
        else return res.status(401).send(RES_MESSAGES.AUTHENTICATION_FAILED);
    } catch (error) {
        printTrace(TAG, "userAuthMiddleware", error.message);
        JwtErrorHandler(error.message, res);
    }
};

export const adminAuthMiddleware = async (req, res, next) => {
    try {
        let token = "";
        if (req.headers.authorization && req.headers.authorization.split(' ')[0] === 'Bearer')
            token = req.headers.authorization.split(' ')[1];
        else return res.status(401).send(RES_MESSAGES.AUTHENTICATION_FAILED);
        const decodedData = jwt.verify(token, ACCESS_TOKEN.SECRET_KEY);
        const user = await User.findOne({ _id: decodedData.id });

        if (user) {
            if (user.is_admin) {
                req.adminId = decodedData.id;
                next();
            } else return res.status(403).send(RES_MESSAGES.NO_PERMISSION);
        }
        else return res.status(401).send(RES_MESSAGES.AUTHENTICATION_FAILED);
    } catch (error) {
        printTrace(TAG, "adminAuthMiddleware", error.message);
        JwtErrorHandler(error.message, res);
    }
};

export const tokenVerificationMiddleware = async (req, res, next) => {
    const data = req.body
    try {
        const token = data.token;
        if (!token) return res.status(401).send(RES_MESSAGES.AUTHENTICATION_FAILED);
        const decodedData = jwt.verify(token, ACCESS_TOKEN.SECRET_KEY);
        const user = await User.findOne({ _id: decodedData.id });
        if (user) {
            req.userId = decodedData.id;
            return res.status(200).json({
                data: user.is_admin,
                code: 200,
                error: "",
            });
        }
        else return res.status(401).send(RES_MESSAGES.AUTHENTICATION_FAILED);
    } catch (error) {
        printTrace(TAG, "tokenVerificationMiddleware", error.message);
        JwtErrorHandler(error.message, res);
    }
};

