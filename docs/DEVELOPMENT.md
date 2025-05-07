# Development

This document describes the intricacies of Nobl9 Intellij plugin development workflow.
If you see anything missing, feel free to contribute :)

## Running plugin

### In the project

1. Launch the project in any Intellij IDE.
2. Depending on which Intellij IDE you have the license for, you should set it in TODO.
3. Click on the top right corner to see _Run / Debug Configurations_.
4. Select _Run Plugin_ configuration and run it.
5. New Intellij window should be spawned with the plugin loaded inside.

> ![NOTE]
> If you're on an OS which might have problems with required, linked libraries,
> which are needed to run the spawned Intellij instance,
> there's a Nix shell and direnv integration ready to use.
> Use `make ide` to spawn an Intellij instance for this project.

### Packaging

TODO

## Pull requests

[Pull request template](../.github/pull_request_template.md)
is provided when you create new PR.
Section worth noting and getting familiar with is located under
`## Release Notes` header.

## Makefile

Run `make help` to display short description for each target.
The provided Makefile will automatically install dev dependencies if they're
missing and place them under `bin`
(this does not apply to `yarn` managed dependencies).
However, it does not detect if the binary you have is up to date with the
versions declaration located in Makefile.
If you see any discrepancies between CI and your local runs, remove the
binaries from `bin` and let Makefile reinstall them with the latest version.

## CI

Continuous integration pipelines utilize the same Makefile commands which
you run locally. This ensures consistent behavior of the executed checks
and makes local debugging easier.

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
