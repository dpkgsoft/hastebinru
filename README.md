# Hastebin.ru

---
## Online demo

Will be available soon (or available now maybe :)) on [hastebin.ru](https://hastebin.ru)

## Installation

1. Download JAR executable from Releases tab or compile project from source code.
2. Copy-n-paste `config.example.json` to `config.json`
3. Configure your instance
4. Create storage directory (specified in `config.json`) and `static` directory
5. Create/download/build frontend and place it in the `static` directory

## Frontend

You can use free original Hastebin UI. You can download it [here](https://dpkgsoftcdn.com/packages/archives/hastebinui.zip).

If you want you can create your own UI using original Hastebin API

## API

#### Create document
```http request
POST /documents
Host: <your ruhaste host>
Content-Type: text/plain
Accept: application/json

<body>
```
Rate limiting is 10 requests per minute.

Answer:
```http request
HTTP/1.1 200 OK
Content-Type: application/json
X-RateLimit-Limit: 10
X-RateLimit-Remaining: 9
X-RateLimit-Reset: 299999

{"key": "xaxaxaxaxaxa"}
```

#### Get raw document
```http request
GET /raw/:id
Host: <your ruhaste host>
```

This will return document (`text/plain`) or 404 error with JSON (`application/json`)

#### Get document
```http request
GET /documents/:id
Host: <your ruhaste host>
```

Document found:
```json
{
  "key": "key from request (:id param)",
  "data": "file contents"
}
```

Document not found:
```json
{
  "message": "Document not found."
}
```

## Config

---

### listen
IP we listen to. Default: `0.0.0.0`

### port
Port we listen to. Default: `8080`

### dir
Directory we save pastes to. Default: `./data` (*Please note that you have to create this directory first*)

### keyLength
Key length for generator. Default: `10`

### maxLength
Maximum length of document. Default: `400000`

### docs
You can specify here static pastes available all time. \
Default:
```json
{
  "readme": "./README.md"
}
```
So if you go to `<your ruhaste host>/readme` you will get content of README.md file.