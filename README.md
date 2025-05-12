<!-- markdownlint-disable no-inline-html -->

# Nobl9 Intellij Platform plugin

![Build](https://github.com/nobl9/nobl9-intellij-platform-plugin/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/27319-nobl9.svg)](https://plugins.jetbrains.com/plugin/27319-nobl9)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/27319-nobl9.svg)](https://plugins.jetbrains.com/plugin/27319-nobl9)

<!-- Plugin description -->
Support for Nobl9 configuration files through
[nobl9-language-server](https://github.com/nobl9/nobl9-language-server)
LSP integration.
<!-- Plugin description end -->

## Requirements

[Nobl9 language server](https://github.com/nobl9/nobl9-language-server)
executable has to be installed and either available in the path or
specified via `nobl9.languageServerPath`.

## Plugin Settings

Go to
<kbd>Settings/Preferences</kbd> >
<kbd>Tools</kbd> >
<kbd>Nobl9</kbd>

This extension contributes the following settings:

- <kbd>Language server executable path</kbd>:
  Specify a custom path to the Nobl9 Language Server executable.
- <kbd>Log level</kbd>:
  Specify log level for the Nobl9 Language Server to use.
- <kbd>Log file path</kbd>:
  Specify log file path for the Nobl9 Language Server to save the logs in.

## Known Issues

Nobl9 Language Server is currently in experimental stage,
please report any bugs encountered,
which are not directly related to the Nobl9 Intellij Platform plugin in the
[Language Server's repository](https://github.com/nobl9/nobl9-language-server).

## Installation

- Using the IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> >
  <kbd>Plugins</kbd> >
  <kbd>Marketplace</kbd> >
  <kbd>Search for "nobl9-intellij-platform-plugin"</kbd> >
  <kbd>Install</kbd>

- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/27319-nobl9)
  and install it by clicking the <kbd>Install to ...</kbd> button
  in case your IDE is running.

  You can also download the
  [latest release](https://plugins.jetbrains.com/plugin/27319-nobl9/versions)
  from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> >
  <kbd>Plugins</kbd> >
  <kbd>⚙️</kbd> >
  <kbd>Install plugin from disk...</kbd>

- Manually:

  Download the [latest release](https://github.com/nobl9/nobl9-intellij-platform-plugin/releases/latest)
  and install it manually using
  <kbd>Settings/Preferences</kbd> >
  <kbd>Plugins</kbd> >
  <kbd>⚙️</kbd> >
  <kbd>Install plugin from disk...</kbd>

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template

[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
