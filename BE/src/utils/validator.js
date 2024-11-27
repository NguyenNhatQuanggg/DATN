import { FIREBASE_AUTH_ERROR_CODES, JWT_ERROR_CODES, RES_MESSAGES } from "./constants.js"

export const firebaseAuthErrorHandler = (code, res) => {
    switch (code) {
        case FIREBASE_AUTH_ERROR_CODES.EMAIL_ALREADY_EXISTS:
            return res.status(409).send({
                code: 409,
                data: null,
                isSuccess: false,
                message: RES_MESSAGES.USERNAME_EXIST,
            });

        case FIREBASE_AUTH_ERROR_CODES.INVALID_CREDENTIAL:
            return res.status(401).send({
                code: 401,
                data: null,
                isSuccess: false,
                message: RES_MESSAGES.WRONG_USERNAME_PASSWORD,
            });

        case FIREBASE_AUTH_ERROR_CODES.INVALID_EMAIL:
            return res.status(400).send({
                code: 400,
                data: null,
                isSuccess: false,
                message: RES_MESSAGES.INVALID_EMAIL,
            });

        case FIREBASE_AUTH_ERROR_CODES.WEAK_PASSWORD:
            return res.status(400).send({
                code: 400,
                data: null,
                isSuccess: false,
                message: RES_MESSAGES.WEAK_PASSWORD,
            });

        case FIREBASE_AUTH_ERROR_CODES.MISSING_EMAIL:
            return res.status(400).send({
                code: 400,
                data: null,
                isSuccess: false,
                message: RES_MESSAGES.INVALID_EMAIL,
            });

        case FIREBASE_AUTH_ERROR_CODES.MISSING_PASSWORD:
            return res.status(400).send({
                code: 400,
                data: null,
                isSuccess: false,
                message: RES_MESSAGES.WEAK_PASSWORD,
            });

        default:
            res.status(500).send({
                code: 500,
                data: null,
                isSuccess: false,
                message: RES_MESSAGES.SERVER_ERROR,
            });
    }
}

export const JwtErrorHandler = (code, res) => {
    switch (code) {
        case JWT_ERROR_CODES.JWT_EXPIRED:
            return res.status(401).send({
                code: 401,
                data: null,
                isSuccess: false,
                message: RES_MESSAGES.LOGIN_SESSION_EXPIRED,
            });
        case JWT_ERROR_CODES.NO_SIGNATURE:
        case JWT_ERROR_CODES.JWT_MALFORMED:
        case JWT_ERROR_CODES.INVALID_TOKEN:
            return res.status(401).send({
                code: 401,
                data: null,
                isSuccess: false,
                message: RES_MESSAGES.AUTHENTICATION_FAILED,
            });
        default:
            res.status(500).send({
                code: 500,
                data: null,
                isSuccess: false,
                message: RES_MESSAGES.SERVER_ERROR,
            });
    }
}