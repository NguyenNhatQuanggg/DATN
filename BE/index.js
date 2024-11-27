import express from "express";
import mongoose from "mongoose";
import { createServer } from "http";
import { existsSync, mkdirSync } from "fs";
import { DB_ROUTE, PORT, SERVER_ROUTE } from "./src/utils/constants.js";
// 
import routes from "./src/routes/index.js";
import swaggerDocs from "./swagger.js";

// express
const app = express();
const server = createServer(app);

// middlewares
app.use(express.json({ limit: "100mb" }));
app.use(express.static("STATIC"));

// routes
app.use("/", routes);

// STATIC
if (!existsSync("STATIC")) mkdirSync("STATIC");

// Connect to DB
mongoose
  .connect(DB_ROUTE)
  .then(() => {
    server.listen(PORT, () =>
      console.log(`Server is running on ${SERVER_ROUTE}`)
    )
    swaggerDocs(app)
  })
  .catch((error) => console.log("Cannot start server, error: " + error.message));