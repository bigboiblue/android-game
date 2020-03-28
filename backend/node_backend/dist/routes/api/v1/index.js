"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;

var _express = require("express");

var _mongoose = _interopRequireDefault(require("mongoose"));

var _scores = _interopRequireDefault(require("./scores"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

const router = (0, _express.Router)();
const MONGO_IP = "mongodb"; //docker-compose adds service link as ip in hosts

const MONGO_PORT = "27017";
const DB_URL = `mongodb://${MONGO_IP}:${MONGO_PORT}`;

_mongoose.default.connect(`${DB_URL}/last_man_standing`, {
  useNewUrlParser: true,
  useUnifiedTopology: true
});

router.get("/", (request, response) => {
  response.type("text/plain");
  response.end("Please use /api/v1/scores");
});
router.use("/scores", _scores.default);
var _default = router;
exports.default = _default;

//# sourceMappingURL=index.js.map