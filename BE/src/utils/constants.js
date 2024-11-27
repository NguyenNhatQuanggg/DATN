export const PORT = 5000;
export const DB_ROUTE = "mongodb://127.0.0.1:27017/qc";
export const SERVER_ROUTE = `http://localhost:${PORT}`;
export const MOBILE_LOCAL_ROUTE = `http://10.0.2.2:${PORT}`;

export const RES_MESSAGES = {
    SERVER_ERROR: "An unexpected error occurred",

    // User
    USERNAME_EXIST: "Username already exists",
    PHONE_EXIST: "Phone number already exists",
    WRONG_USERNAME_PASSWORD: "Incorrect username or password",
    USER_BANNED: "Your account is banned, contact administrator for details",
    AUTHENTICATION_FAILED: "Please login to use this function",
    OLD_PASSWORD_WRONG: "Old password is incorrect",
    CHANGE_PASSWORD_SUCCESS: "Changed password successfully",
    UPDATE_USER_SUCCESS: "Updated user information successfully",
    UNVERIFIED_ACCOUNT: "Please verify your account before logging in",
    USERNAME_NOT_EXIST: "Username does not exist",
    INVALID_EMAIL: "Username must be a valid email",
    WEAK_PASSWORD: "Password must be at least 6 characters",
    FIND_PASSWORD_RESET_MAIL: "Please check your mailbox to find reset password mail",
    SIGN_OUT_SUCCESS: "Signed out successfully",
    REGISTER_ACCOUNT_SUCCESS: "Registered account successfully",
    LOGIN_SUCCESS: "Login successfully",
    LOGIN_SESSION_EXPIRED: "Your session has been expired, please login again",
    NO_PERMISSION: "You don't have permission to do this action",

    // Category
    CATEGORY_NAME_EXIST: "Category name already exists",
    CATEGORY_NOT_EXIST: "Category does not exist",
    INVALID_CATEGORY_NAME: "Category name is invalid",
    DELETE_CATEGORY_SUCCESSFULLY: "Deleted category successfully",

    // Product
    PRODUCT_NOT_EXIST: "Product does not exist",
    DELETE_PRODUCT_SUCCESSFULLY: "Updated product successfully",

    // Delivery Address
    USER_NOT_EXIST: "User does not exist",
    ADD_DELIVERY_ADDRESS_SUCCESS: "Added delivery address",

    // Rating
    RATING_NOT_EXIST: "Rating does not exist",
    DELETE_RATING_SUCCESS: "Deleted rating successfully",
    INVALID_RATING_STATUS: "Invalid rating status",
    UPDATED_RATING_SUCCESS: "Updated rating successfully",
    DELETED_RATING_SUCCESS: "Deleted rating successfully",
    ADD_RATING_SUCCESS: "Added rating successfully",

    // Cart
    CART_NOT_EXIST: "Cart does not exist",
    ADD_ITEM_TO_CART_SUCCESS: "Added items to cart",
    UPDATE_CART_SUCCESS: "Updated cart successfully",

    // Discount
    ADD_DISCOUNT_SUCCESS: "Added new discount",
    UPDATED_DISCOUNT_SUCCESS: "Updated discount successfully",
    DISCOUNT_NOT_EXIST: "Discount does not exist",
    DELETE_DISCOUNT_SUCCESS: "Deleted discount successfully",

    // Order
    DELIVERY_ADDRESS_NOT_EXIST: "Delivery address does not exist",
    INVALID_AMOUNT: "Amount should be greater than 0",
    INVALID_PAYMENT_METHOD: "Payment method is invalid",
    UPDATE_ORDER_STATUS_SUCCESS: "Update order status successfully",
    ORDER_NOT_EXIST: "Order does not exist",
    INVALID_ORDER_STATUS: "Order status is invalid",
    CANCEL_ORDER_SUCCESS: "Canceled order successfully",

    // User Product Like
    LIKE_PRODUCT_SUCCESS: "Added product to favourites",
    UNLIKE_PRODUCT_SUCCESS: "Removed product from favourites",
}

export const FIREBASE_AUTH_ERROR_CODES = {
    EMAIL_ALREADY_EXISTS: "auth/email-already-in-use",
    INVALID_CREDENTIAL: "auth/invalid-credential",
    INVALID_EMAIL: "auth/invalid-email",
    WEAK_PASSWORD: "auth/weak-password",
    MISSING_EMAIL: "auth/missing-email",
    MISSING_PASSWORD: "auth/missing-password",
}

export const JWT_ERROR_CODES = {
    JWT_EXPIRED: "jwt expired",
    INVALID_TOKEN: "invalid token",
    JWT_MALFORMED: "jwt malformed",
    NO_SIGNATURE: "jwt signature is required",
}

export const RATING_STATUS = {
    WAITING: 0,
    ACCEPTED: 1,
    REJECTED: 2,
}

export const ORDER_STATUS = {
    WAITING: 0, // Waiting for user payment
    SHIPPING: 1, // The order is being shipped
    COMPLETE: 2, // The order is complete
    CANCELED: 3, // User canceled the order
}

export const PAYMENT_METHOD = {
    VNPAY: 0,
    MOMO: 1,
    PAYPAL: 2,
    CASH: 3,
}

export const ACCESS_TOKEN = {
    EXPIRATION_TIME: 3600000, // ms ~ 1h
    SECRET_KEY: "orchid",
}

export const MAILER = {
    SENDER: 'Orchid Customer Service',
    SENDER_MAIL: 'orchid.service88@gmail.com',
    TRANSPORTER_SERVICE: 'gmail',
    TRANSPORTER_HOST: 'smtp.gmail.com',
    TRANSPORTER_PORT: 465,
    TRANSPORTER_SECURE: true,
    TRANSPORTER_USER: 'orchild.service88@gmail.com',
    TRANSPORTER_PASS: 'usixvconjmoheghx',
}