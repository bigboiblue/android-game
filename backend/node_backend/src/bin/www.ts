import fs from "fs";
import https from "https";
import app from "../app";


const privateKey = fs.readFileSync("/certs/privkey.pem", "utf8");
const certificate = fs.readFileSync("/certs/cert.pem", "utf8");
const ca = fs.readFileSync("/certs/chain.pem", "utf8");

const credentials = {
    key: privateKey,
    cert: certificate,
    ca,
};

const PORT = parseInt(process.env.PORT) || 28191;
process.env.PORT = PORT.toString();

const httpsServer = https.createServer(credentials, app);

httpsServer.listen(443, () => {
    console.log(`HTTPS Server running on port ${PORT}`);
});
