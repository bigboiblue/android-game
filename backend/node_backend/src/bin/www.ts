import app from "../app";

const PORT = parseInt(process.env.PORT) || 28191;
process.env.PORT = PORT.toString();

app.listen(PORT, (): void => console.log(`Server running on port: ${PORT}`));

