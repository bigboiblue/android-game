import { Router } from "express";
import { scores } from "./db_models/scores";
const router = Router();

// TODO: IMPORTANT Make server and frontent request code PRISTINE. Each and every project you make is going to rely on this sould project.

/** URL Format: /scores?limit=10*/
router.get("/", async (request, response, next) => {
    response.type("application/json");

    let { limit } = request.query;
    if (limit === undefined) limit = 20;

    const result = await scores.find().limit(limit);
    response.send(result);

    response.end();
});

router.put("/:id", async (request, response) => {
    await scores.updateOne({ _id: request.params.id },
        { $set: request.body });
    response.type("application/json");
    response.end("");
});

router.delete("/:id", async (request, response) => {
    response.type("application/json");
    await scores.deleteOne({ _id: request.params.id });
    response.end("");
});

router.post("/add", async (request, response) => {
    response.type("application/json");
    await scores.insertMany(request.body);
    response.end("");
});


export default router;
