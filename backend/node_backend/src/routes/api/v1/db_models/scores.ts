import mongoose from "mongoose";

export const scoresSchema = new mongoose.Schema({
    name: { type: String, required: true },
    rounds: { type: Number, required: true },
    points: { type: Number, required: true },
    date: { type: Date, required: true, default: Date.now },
}, { versionKey: false });

export const scores = mongoose.model("scores", scoresSchema);
