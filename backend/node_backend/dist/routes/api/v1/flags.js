"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;

var _express = require("express");

var _flag = require("./db_models/flag");

const router = (0, _express.Router)(); // TODO: IMPORTANT Make server and frontent request code PRISTINE. Each and every project you make is going to rely on this sould project.

/** URL Format: /flags?<groupName>=x&filter=y&limit=z */

router.get("/", async (request, response, next) => {
  response.type("application/json");
  let {
    groupName,
    isArchived,
    limit
  } = request.query;
  isArchived = isArchived === "true";

  if (!groupName) {
    next();
    return;
  }

  const distinctGroups = await _flag.flags.distinct("groupName");

  if (!distinctGroups.includes(groupName)) {
    response.send({
      error: {
        isError: true,
        message: `Group: "${groupName}" does not exist...`
      }
    });
  } else {
    const result = await _flag.flags.find({
      groupName,
      isArchived
    }).limit(limit);
    response.send(result);
  }

  response.end();
});
router.put("/:id", async (request, response) => {
  await _flag.flags.updateOne({
    _id: request.params.id
  }, {
    $set: request.body
  });
  response.type("application/json");
  response.end("");
});
router.put("/archive/:id", async (request, response) => {
  response.type("application/json"); //If going to be archived, then set date, else dont (and remove date)

  if (request.body.isArchived) {
    await _flag.flags.updateOne({
      _id: request.params.id
    }, {
      $set: { ...request.body,
        dateArchived: new Date()
      }
    });
  } else {
    await _flag.flags.updateOne({
      _id: request.params.id
    }, {
      $set: request.body,
      $unset: {
        dateArchived: ""
      }
    });
  }

  response.end("");
});
router.delete("/:id", async (request, response) => {
  response.type("application/json");
  await _flag.flags.deleteOne({
    _id: request.params.id
  });
  response.end("");
});
router.post("/add", async (request, response) => {
  response.type("application/json");
  await _flag.flags.insertMany(request.body);
  response.end("");
});
var _default = router;
exports.default = _default;

//# sourceMappingURL=flags.js.map