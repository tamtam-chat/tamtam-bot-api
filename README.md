### ⚠️ Consider that project is [public](https://github.com/tamtam-chat/tamtam-bot-api).
All changes should be pushed to Github through the `master-github` branch.

# TamTam Bot API Java client

## Overview
This is Java implementation of client to TamTam Bot API.

Most of source code of this project is generated based on OpenAPI specification:

- `TamTamBotAPI`
- All models in `chat.tamtam.botapi.model` package
- All queries in `chat.tamtam.botapi.queries`

If you made changes in this files they will lose during build proccess.


If you want to modify interface you HAVE TO modify [specification](https://stash.odkl.ru/projects/TAMTAM/repos/tamtam-bot-api-schema/browse) first.

If you want to modify implementation you should consider to modify [generator](https://stash.odkl.ru/projects/TAMTAM/repos/tamtam-bot-api-generator/browse) or classes in `chat.tamtam.botapi.client` package.

## Usage
As soon as you made changes in specification you can generate files using `mvn compile`.

⚠️ Pay attention that no changes in VCS is made during build process. You should manually add generated files to Git index, commit it, sync with `master-github` branch and push.