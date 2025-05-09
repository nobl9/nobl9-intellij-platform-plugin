# Development

This document describes the intricacies of Nobl9 Intellij plugin development workflow.
If you see anything missing, feel free to contribute :)

> ![NOTE]
> The vast majority of tools, CI and processes are enforced
> by the Intellij ecosystem.

## Running plugin

### In the project

1. Launch the project in any Intellij IDE.
2. Depending on which Intellij IDE you have the license for,
   you should set it in TODO.
3. Click in the top right corner to see _Run / Debug Configurations_.
4. Select _Run Plugin_ configuration and run it.
5. New Intellij window should be spawned with the plugin loaded inside.

> ![NOTE]
> If you're on an OS which might have problems with required, linked libraries,
> which are needed to run the spawned Intellij instance,
> there's a Nix shell and direnv integration ready to use.
> Use `make ide` to spawn an Intellij instance for this project.

### Packaging

If you run `make build` it will build the plugin and output a ready to use
JAR file at the root of the project,
e.g. `nobl9-intellij-platform-plugin-0.0.1.jar`.

Each build both on PRs and on main branch is also building artifacts,
you can download them from the GitHub job summary and
install the plugin locally.

## Pull requests

[Pull request template](../.github/pull_request_template.md)
is provided when you create new PR.
Section worth noting and getting familiar with is located under
`## Release Notes` header.

## Gradle

Gradle is the build system used in this project.

## Makefile

Makefile in this project contains only a few, basic commands.

## CI

You can inspect things like test or linter results in the GitHub job summary.
You can run similar commands to the ones in CI locally by calling `./gradlew`.

## Testing

### Intellij integration tests

The repository has integration tests with Intellij, run via
[Intellij plugin testing framework](https://plugins.jetbrains.com/docs/intellij/integration-tests-intro.html).

The tests are located under [src/test](../src/test) directory.

## Releases

Refer to [RELEASE.md](./RELEASE.md) for more information on release process.

## Dependencies

Renovate is configured to automatically merge minor and patch updates.
For major versions, which sadly includes GitHub Actions, manual approval
is required.
