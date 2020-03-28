"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.flags = exports.flagsSchema = void 0;

var _mongoose = _interopRequireDefault(require("mongoose"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

const flagsSchema = new _mongoose.default.Schema({
  name: {
    type: String,
    required: true
  },
  groupName: {
    type: String,
    required: true
  },
  type: {
    type: String,
    required: true,
    default: "toggle"
  },
  isEnabled: {
    type: Boolean,
    required: true,
    default: false
  },
  dateCreated: {
    type: Date,
    required: true,
    default: Date.now
  },
  isArchived: {
    type: Boolean,
    required: true,
    default: false
  },
  dateArchived: {
    type: Date,
    required: false
  }
}, {
  versionKey: false
});
exports.flagsSchema = flagsSchema;

const flags = _mongoose.default.model("flag", flagsSchema);

exports.flags = flags;

//# sourceMappingURL=flag.js.map