import bcrypt from "bcryptjs";
import mongoose from "mongoose";
import { MOBILE_LOCAL_ROUTE, ORDER_STATUS, RES_MESSAGES } from "../utils/constants.js";
import { auth, createUserWithEmailAndPassword, sendEmailVerification, sendPasswordResetEmail, signInWithEmailAndPassword, signOut, updatePassword } from "../../firebase.js";
import { convertBase64ToFile, formatDate, generateAccessToken, printData, printTrace } from "../utils/helper.js";
import { firebaseAuthErrorHandler } from "../utils/validator.js";
import User from "../models/user.js";
import Cart from "../models/cart.js";
import Order from "../models/order.js";
import UserProductFavourite from "../models/user_product_favourite.js";
import { sendOrderEmail } from "../../mail_sender.js";

const TAG = "UserController";

export const ping = async (req, res) => {
  try {
    const order = await Order.findOne({ _id: "673091d5582c1eb2493bf677", })
      .populate("user")
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
    order.discount_value = order.discount ? order.discount.value * order.amount / 100 : 0;
    order.discounted_amount = order.discount ? order.amount - (order.discount.value * order.amount / 100) : order.amount;
    await sendOrderEmail(order);


    res.status(200).send({
      from: new Date(),
      to: new Date(new Date().setDate(new Date().getDate() + 7))
    });
  } catch (error) {
    printTrace(TAG, "ping", error);
    res.status(500).send("ERROR");
  }
};

export const register = async (req, res) => {
  const user = req.body;
  console.log(user)
  try {
    const existedPhone = await User.findOne({ phone: user.phone });
    if (existedPhone) return res.status(409).send({
      code: 409,
      message: RES_MESSAGES.PHONE_EXIST,
    });

    await createUserWithEmailAndPassword(auth, user.username, user.password);
    await sendEmailVerification(auth.currentUser)

    const hashedPassword = await bcrypt.hash(user.password, 12);
    const TIME_STAMP = new Date();

    convertBase64ToFile(user.profile_image, `./STATIC/user/${TIME_STAMP.getTime()}.png`);
    user.profile_image = `${MOBILE_LOCAL_ROUTE}/user/${TIME_STAMP.getTime()}.png`;

    const newUser = new User({
      ...user,
      password: hashedPassword,
      address: "aaa",
      created_date: TIME_STAMP,
      modified_date: TIME_STAMP,
    });
    await newUser.save();

    // Create cart for user
    const newCart = new Cart({
      user: newUser._id,
      items: [],
      created_date: TIME_STAMP,
      modified_date: TIME_STAMP,
    })
    await newCart.save();

    res.status(200).json({
      code: 200,
      message: RES_MESSAGES.REGISTER_ACCOUNT_SUCCESS,
    });
  } catch (error) {
    printTrace(TAG, "register", error);
    firebaseAuthErrorHandler(error.code, res);
  }
}

// Common
export const login = async (req, res) => {
  const user = req.body;
  try {
    await signInWithEmailAndPassword(auth, user.username, user.password);

    // Sync password to DB
    const hashedPassword = await bcrypt.hash(user.password, 12);
    const existedUser = await User.findOneAndUpdate({
      username: user.username,
    }, {
      password: hashedPassword,
    }, { new: true });

    if (existedUser.banned) return res.status(403).send({
      code: 403,
      message: RES_MESSAGES.USER_BANNED,
    });

    const accessToken = generateAccessToken(existedUser._id);

    if (auth.currentUser && auth.currentUser.emailVerified)
      res.status(200)
        .json({
          code: 200,
          token: accessToken,
          user: existedUser,
          message: RES_MESSAGES.LOGIN_SUCCESS,
        });
    else res.status(401).send({
      code: 401,
      message: RES_MESSAGES.UNVERIFIED_ACCOUNT,
    });
  } catch (error) {
    printTrace(TAG, "login", error);
    firebaseAuthErrorHandler(error.code, res);
  }
}

export const updateUser = async (req, res) => {
  const user = req.body;
  try {
    if (!mongoose.Types.ObjectId.isValid(user._id)) return res.status(401).send({
      code: 401,
      message: RES_MESSAGES.AUTHENTICATION_FAILED,
    });

    await User.findByIdAndUpdate(user._id, { ...user }, { new: true });

    res.status(200).send({
      code: 200,
      message: RES_MESSAGES.UPDATE_USER_SUCCESS,
    });
  } catch (error) {
    printTrace(TAG, "updateUser", error);
    res.status(500).send({
      code: 500,
      message: RES_MESSAGES.SERVER_ERROR,
    });
  }
}

export const changePassword = async (req, res) => {
  const user = req.body;
  console.log(user)
  try {
    if (!auth.currentUser) return res.status(401).send({
      code: 401,
      message: RES_MESSAGES.OLD_PASSWORD_WRONG,
    });
    await updatePassword(auth.currentUser, user.newPassword);

    const existedUser = await User.findOne({
      _id: user.id,
    });
    const isPasswordCorrect = await bcrypt.compare(
      user.oldPassword,
      existedUser.password
    );

    if (!isPasswordCorrect) return res.status(403).send({
      code: 403,
      message: RES_MESSAGES.OLD_PASSWORD_WRONG,
    });

    const hashedPassword = await bcrypt.hash(user.newPassword, 12);
    await User.findByIdAndUpdate(
      user.id,
      { password: hashedPassword },
      { new: true }
    );

    res.status(200).send({
      code: 200,
      message: RES_MESSAGES.CHANGE_PASSWORD_SUCCESS,
    });
  } catch (error) {
    printTrace(TAG, "changePassword", error);
    res.status(500).send(
      {
        code: 500,
        message: RES_MESSAGES.SERVER_ERROR,
      });
  }
}

export const resetPassword = async (req, res) => {
  const user = req.body;
  console.log(user)
  try {
    const existedUser = await User.findOne({
      username: user.username,
    });
    if (!existedUser) return res.status(401).send({ code: 401, message: RES_MESSAGES.USERNAME_NOT_EXIST });

    await sendPasswordResetEmail(auth, user.username);
    res.status(200).json({ code: 200, message: RES_MESSAGES.FIND_PASSWORD_RESET_MAIL });
  } catch (error) {
    printTrace(TAG, "resetPassword", error);
    firebaseAuthErrorHandler(error.code, res);
  }
}

export const logout = async (req, res) => {
  try {
    await signOut(auth);
    res.status(200).json({ code: 200, message: RES_MESSAGES.SIGN_OUT_SUCCESS });
  } catch (error) {
    printTrace(TAG, "logout", error);
    firebaseAuthErrorHandler(error.code, res);
  }
}

// User
export const toggleFavouriteProduct = async (req, res) => {
  const data = req.body;
  printData(TAG, "toggleFavouriteProduct", data)
  try {
    let returnedMessage;

    if (data.isFavorite) {
      const existedRecord = await UserProductFavourite.findOne({ user: data.userId, product: data.productId, });
      if (existedRecord) return res.status(200).json({ code: 200, message: "" });

      const newUserProductFavourite = new UserProductFavourite({
        user: data.userId,
        product: data.productId,
        created_date: new Date(),
      });
      await newUserProductFavourite.save();
      returnedMessage = RES_MESSAGES.LIKE_PRODUCT_SUCCESS;
    } else {
      await UserProductFavourite.findOneAndDelete({ user: data.userId, product: data.productId });
      returnedMessage = RES_MESSAGES.UNLIKE_PRODUCT_SUCCESS;
    }

    res.status(200).json({ code: 200, message: returnedMessage });
  } catch (error) {
    printTrace(TAG, "toggleFavouriteProduct", error);
    res.status(500).json({
      data: [],
      isSuccess: false,
      message: RES_MESSAGES.SERVER_ERROR,
    });
  }
}

// Admin
export const getAllUsers = async (req, res) => {
  try {
    const users = await User.find({ is_admin: false });

    return res.status(200).json({
      data: users,
      isSuccess: true,
      message: "",
    });
  } catch (error) {
    printTrace(TAG, "getAllUsers", error);
    res.status(500).json({
      data: [],
      isSuccess: false,
      message: RES_MESSAGES.SERVER_ERROR,
    });
  }
}

// Thống kê doanh số bán hàng theo ngày, tháng, năm.
// Báo cáo các sản phẩm bán chạy
// Theo dõi số lượng người dùng, đơn hàng đã hoàn thành, doanh thu.
export const getDashboardData = async (req, res) => {
  const data = req.body;
  try {
    const userCount = await User.countDocuments({ is_admin: false });
    let income = 0;
    let paidOrdersInRange = [];

    if (data.all) {
      data.startDate = new Date("1970-01-01");
      data.endDate = new Date("3000-01-01")
    }

    paidOrdersInRange = await Order.find({
      status: ORDER_STATUS.COMPLETE,
      modified_date: { $gte: new Date(data.startDate), $lte: new Date(data.endDate) }
    })
      .populate("discount user delivery_address")
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

    paidOrdersInRange.forEach(order => {
      order.amount = order.discount ? order.amount - (order.amount * order.discount.value / 100) : order.amount;
      order.created_date = formatDate(order.created_date);
      income += order.amount;
    })

    console.log({
      userCount: userCount,
      income: income,
      paidOrderCount: paidOrdersInRange.length,
      orderList: paidOrdersInRange,
    })

    return res.status(200).json({
      data: {
        userCount: userCount,
        income: income,
        paidOrderCount: paidOrdersInRange.length,
        orderList: paidOrdersInRange,
      },
      isSuccess: true,
      message: "",
    });
  } catch (error) {
    printTrace(TAG, "getDashboardData", error);
    res.status(500).json({
      data: [],
      isSuccess: false,
      message: RES_MESSAGES.SERVER_ERROR,
    });
  }
}

export const getTopSellingProducts = async (req, res) => {
  const data = req.body;
  try {
    const paidOrders = await Order.find({
      status: ORDER_STATUS.COMPLETE,
      modified_date: { $gte: new Date(data.startDate), $lte: new Date(data.endDate) }
    })
      .populate("discount")
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

    const productIdMap = new Map();
    const topProducts = [];

    paidOrders.forEach(order => {
      order.items.forEach(item => {
        const productId = item.product._id.toString(); // key
        if (!productIdMap.has(productId)) {
          const returnedObj = {
            product: item.product.toObject(),
            quantity: item.quantity,
            amount: item.product.discount ?
              (item.product.price - item.product.price * item.product.discount / 100) * item.quantity : item.product.price * item.quantity,
          }
          productIdMap.set(productId, returnedObj);
          topProducts.push(returnedObj);
        } else {
          const existedObj = productIdMap.get(productId);
          existedObj.quantity += item.quantity;
          existedObj.amount = item.product.discount ?
            (existedObj.product.price - existedObj.product.price * item.product.discount / 100) * existedObj.quantity : existedObj.product.price * existedObj.quantity;
        }
      })
    })
    const returnedTopProducts = topProducts.sort((a, b) => b.quantity - a.quantity).slice(0, 10);

    return res.status(200).json({
      data: returnedTopProducts,
      isSuccess: true,
      message: "",
    });
  } catch (error) {
    printTrace(TAG, "getTopSellingProducts", error);
    res.status(500).json({
      data: [],
      isSuccess: false,
      message: RES_MESSAGES.SERVER_ERROR,
    });
  }
}



