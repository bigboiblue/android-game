"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;

var _express = require("express");

var _flag = require("./db_models/flag");

const router = (0, _express.Router)();
router.get("/", async (request, response, next) => {
  const distinctGroups = await _flag.flags.distinct("groupName");
  response.type("application/json");
  response.send(distinctGroups);
  response.end();
});
var _default = router;
exports.default = _default;

//# sourceMappingURL=groups.js.map