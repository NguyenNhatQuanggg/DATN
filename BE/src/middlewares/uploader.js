import multer from "multer";
import { SERVER_ROUTE } from "../utils/constants.js";

const userStorage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, "STATIC/user");
    },
    filename: (req, file, cb) => {
        const fileName = req.body._id + path.extname(file.originalname);

        const REQUIRED_PATH = path.join("STATIC", "user");
        if (!existsSync(REQUIRED_PATH))
            mkdirSync(REQUIRED_PATH, { recursive: true });

        req.body.profile_image = `${SERVER_ROUTE}/user/${fileName}`;
        cb(null, fileName);
    },
});

export const userUploader = multer({ storage: userStorage });