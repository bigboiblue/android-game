"use strict";

var _fs = _interopRequireDefault(require("fs"));

var _https = _interopRequireDefault(require("https"));

var _app = _interopRequireDefault(require("../app"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

const privateKey = _fs.default.readFileSync("/etc/letsencrypt/live/www.bitdev.bar/privkey.pem", "utf8");

const certificate = _fs.default.readFileSync("/etc/letsencrypt/live/www.bitdev.bar/cert.pem", "utf8");

const ca = _fs.default.readFileSync("/etc/letsencrypt/live/www.bitdev.bar/chain.pem", "utf8");

const credentials = {
  key: privateKey,
  cert: certificate,
  ca
};
const PORT = parseInt(process.env.PORT) || 28191;
process.env.PORT = PORT.toString();

const httpsServer = _https.default.createServer(credentials, _app.default);

httpsServer.listen(443, () => {
  console.log(`HTTPS Server running on port ${PORT}`);
});

//# sourceMappingURL=www.js.map