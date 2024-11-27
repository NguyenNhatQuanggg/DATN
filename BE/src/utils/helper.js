import fs from "fs"
import { ACCESS_TOKEN, ORDER_STATUS, PAYMENT_METHOD, RATING_STATUS } from "./constants.js";
import jwt from "jsonwebtoken";

// Order
export const isValidPaymentMethod = (paymentMethod) =>
    paymentMethod === PAYMENT_METHOD.VNPAY ||
    paymentMethod === PAYMENT_METHOD.MOMO ||
    paymentMethod === PAYMENT_METHOD.PAYPAL ||
    paymentMethod === PAYMENT_METHOD.CASH

export const isValidOrderStatus = (orderStatus) =>
    orderStatus === ORDER_STATUS.WAITING ||
    orderStatus === ORDER_STATUS.SHIPPING ||
    orderStatus === ORDER_STATUS.COMPLETE ||
    orderStatus === ORDER_STATUS.CANCELED

// Rating
export const isValidRatingStatus = (ratingStatus) =>
    ratingStatus === RATING_STATUS.WAITING ||
    ratingStatus === RATING_STATUS.ACCEPTED ||
    ratingStatus === RATING_STATUS.REJECTED

// User
export const generateAccessToken = (_id) => {
    return jwt.sign({ id: _id }, ACCESS_TOKEN.SECRET_KEY, {
        expiresIn: ACCESS_TOKEN.EXPIRATION_TIME,
    });
};

// Utilities
export const convertToGMT7 = (time) => new Date(new Date(time).getTime() + (7 * 60 * 60 * 1000)).toUTCString();

export const convertBase64ToFile = (base64Str, file) => {
    const buffer = Buffer.from(base64Str, "base64");
    fs.writeFileSync(file, buffer);
}

export const printTrace = (tag, funcName, error) => console.log(`${tag}::${funcName} => ` + error);

export const printData = (tag, funcName, obj) => {
    console.log(`----------${tag}::${funcName}`)
    console.log(obj)
    console.log("---------------------------------")
}

export const formatDate = (inputDate) => {
    const date = new Date(inputDate);
    return `${date.getDate()}-${date.getMonth() + 1}-${date.getFullYear()}`
}

export const formatPrice = (x) => x.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ".");

export const generateHtmlForProduct = (item) => `
<tr> <td align="left" valign="top" style="padding: 16px 0px 8px 16px;"> <table width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation"> <tr> <td> <table width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation"> <tr> <td valign="top"> <table class="" border="0" cellpadding="0" cellspacing="0" role="presentation"> <tr> <th valign="top" style="font-weight: normal; text-align: left;"> <table width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation"> <tr> <td class="pc-w620-spacing-0-16-20-0" valign="top" style="padding: 0px 20px 0px 0px;"> <img src="https://cloudfilesdm.com/postcards/image-17315112998826.png" class="pc-w620-width-64 pc-w620-height-64 pc-w620-width-64-min" width="100" height="104" alt="" style="display: block; outline: 0; line-height: 100%; -ms-interpolation-mode: bicubic; width: 100px; height: 104px; border: 0;" /> </td> </tr> </table> </th> <th valign="top" style="font-weight: normal; text-align: left;"> <table width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation"> <tr> <td> <table width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation"> <tr> <td valign="top"> <table width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation"> <tr> <th align="left" valign="top" style="font-weight: normal; text-align: left; padding: 0px 0px 4px 0px;"> <table border="0" cellpadding="0" cellspacing="0" role="presentation" width="100%" style="border-collapse: separate; border-spacing: 0; margin-right: auto; margin-left: auto;"> <tr> <td valign="top" align="left" style="padding: 9px 0px 0px 0px;"> <div class="pc-font-alt pc-w620-fontSize-16 pc-w620-lineHeight-26" style="line-height: 140%; letter-spacing: -0.03em; font-family: 'Poppins', Arial, Helvetica, sans-serif; font-size: 16px; font-weight: 600; font-variant-ligatures: normal; color: #001942; text-align: left; text-align-last: left;"> <div> <span>${item.product.name}</span> </div> </div> </td> </tr> </table> </th> </tr> <tr> <th align="left" valign="top" style="font-weight: normal; text-align: left;"> <table border="0" cellpadding="0" cellspacing="0" role="presentation" width="100%" style="border-collapse: separate; border-spacing: 0; margin-right: auto; margin-left: auto;"> <tr> <td valign="top" align="left"> <div class="pc-font-alt" style="line-height: 140%; letter-spacing: -0.03em; font-family: 'Poppins', Arial, Helvetica, sans-serif; font-size: 14px; font-weight: normal; font-variant-ligatures: normal; color: #53627a; text-align: left; text-align-last: left;"> <div> <span>Qty: ${item.quantity}</span> </div> </div> </td> </tr> </table> </th> </tr> </table> </td> </tr> </table> </td> </tr> </table> </th> </tr> </table> </td> </tr> </table> </td> </tr> </table> </td> <td class="pc-w620-padding-28-32-24-16" align="right" valign="top" style="padding: 24px 16px 24px 16px;"> <table border="0" cellpadding="0" cellspacing="0" role="presentation" width="100%" style="border-collapse: separate; border-spacing: 0; margin-right: auto; margin-left: auto;"> <tr> <td valign="top" align="right"> <div class="pc-font-alt pc-w620-fontSize-16 pc-w620-lineHeight-20" style="line-height: 140%; letter-spacing: -0.03em; font-family: 'Poppins', Arial, Helvetica, sans-serif; font-size: 16px; font-weight: normal; font-variant-ligatures: normal; color: #001942; text-align: right; text-align-last: right;"> <div><del><span style="color: gray;">$199</span></del><br><span style="color: #001942;">${formatPrice(item.product.price)}đ</span> </div> </div> </td> </tr> </table> </td> </tr>`