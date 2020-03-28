"use strict";

var _app = _interopRequireDefault(require("../app"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//TODO: SERVER Do 404 and error handling
const PORT = parseInt(process.env.PORT) || 8081;
process.env.PORT = PORT.toString();

_app.default.listen(PORT, () => console.log(`Server running on port: ${PORT}`)); //// Server error handling here ////
//// e.g.  app.on('error', onError); ////

//# sourceMappingURL=www.js.map