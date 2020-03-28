import { Router } from "express";
import mongoose from "mongoose";
import scoresRouter from "./scores";
const router = Router();

const MONGO_IP = "mongodb"; //docker-compose adds service link as ip in hosts
const MONGO_PORT = "27017";
const DB_URL = `mongodb://${MONGO_IP}:${MONGO_PORT}`;
mongoose.connect(`${DB_URL}/last_man_standing`, { useNewUrlParser: true, useUnifiedTopology: true });


router.get("/", (request, response) => {
    response.type("text/plain");
    response.end("Please use /api/v1/scores");
});

router.use("/scores", scoresRouter);


export default router;

