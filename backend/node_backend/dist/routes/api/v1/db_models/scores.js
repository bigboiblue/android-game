"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.scores = exports.scoresSchema = void 0;

var _mongoose = _interopRequireDefault(require("mongoose"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

const scoresSchema = new _mongoose.default.Schema({
  name: {
    type: String,
    required: true
  },
  rounds: {
    type: Number,
    required: true
  },
  points: {
    type: Number,
    required: true
  },
  date: {
    type: Date,
    required: true,
    default: Date.now
  }
}, {
  versionKey: false
});
exports.scoresSchema = scoresSchema;

const scores = _mongoose.default.model("scores", scoresSchema);

exports.scores = scores;

//# sourceMappingURL=scores.js.map