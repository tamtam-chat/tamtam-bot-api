# ⚠️ This project is [public](https://github.com/tamtam-chat/tamtam-bot-api).
All code under the `project` directory is public. So be careful modifing it.

Actually it was added as subtree (`git subtree add --prefix=project github/master`).

To push changes to GitHub use:
```
git subtree push -P project github master
```

To generate client's code, run `mvn clean compile` in root directory. It will generate files in `project` directory.

To pull changes made in github repository run:

```
git fetch github
git subtree merge -P project github/master
```

# Release process
- Fetch all changes made in GitHub repo
- `cd project`
- `mvn release:prepare`
- Go to parent dir: `cd ..`
- Check everything is ok
- Check what are you going to push: `git diff --stat github/master HEAD:project`
- Push changes to GitHub: `git subtree push --prefix project github master`
- Push current version tag to GitHub: `git push github v0.1.2`
- `cd project && mvn -P release release:perform`
- Draft new release at https://github.com/tamtam-chat/tamtam-bot-api/releases/new

## Notice on deploy to Maven Central

The last release stage will deploy jar artifact to Maven Central through the [Sonatype Staging Repository](https://central.sonatype.org/pages/ossrh-guide.html).
You should have credentials to deploy to it.
See [this issue](https://issues.sonatype.org/browse/OSSRH-45920) for details.

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

⚠️ Pay attention that no changes in VCS is made during build process. You should manually add generated files to Git index and commit it.