"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;

var _express = require("express");

var _scores = require("./db_models/scores");

const router = (0, _express.Router)(); // TODO: IMPORTANT Make server and frontent request code PRISTINE. Each and every project you make is going to rely on this sould project.

/** URL Format: /scores?limit=10*/

router.get("/", async (request, response, next) => {
  response.type("application/json");
  let {
    limit
  } = request.query;
  if (limit === undefined) limit = 20;
  const result = await _scores.scores.find().limit(limit);
  response.send(result);
  response.end();
});
router.put("/:id", async (request, response) => {
  await _scores.scores.updateOne({
    _id: request.params.id
  }, {
    $set: request.body
  });
  response.type("application/json");
  response.end("");
});
router.delete("/:id", async (request, response) => {
  response.type("application/json");
  await _scores.scores.deleteOne({
    _id: request.params.id
  });
  response.end("");
});
router.post("/add", async (request, response) => {
  response.type("application/json");
  await _scores.scores.insertMany(request.body);
  response.end("");
});
var _default = router;
exports.default = _default;

//# sourceMappingURL=scores.js.map