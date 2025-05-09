.DEFAULT_GOAL := help
MAKEFLAGS += --silent --no-print-directory

SCRIPTS_DIR := ./scripts

.PHONY: ide
## Run the IDE. Useful for Nix shell setups.
ide:
	nohup idea-community . > /dev/null 2>&1 &

.PHONY: build
## Build the plugin.
build:
	./gradlew buildPlugin
	export FILENAME="$$(basename $$(ls ./build/distributions/*.zip) .zip)"
	cd ./build/distributions && \
	unzip -o *.zip -d content
	cp ./build/distributions/content/nobl9-intellij-platform-plugin/lib/$$FILENAME.jar .

.PHONY: check check/spell check/trailing check/markdown check/format check/gradle
## Run all checks.
check: check/spell check/trailing check/markdown check/format check/gradle

## Run gradle checks.
check/gradle:
	./gradlew check

## Check spelling, rules are defined in cspell.json.
check/spell: install/yarn
	$(call _print_step,Verifying spelling)
	yarn --silent cspell --no-progress '**/**'

## Check for trailing whitespaces in any of the projects' files.
check/trailing: install/yarn
	$(call _print_step,Looking for trailing whitespaces)
	yarn --silent trailing-whitespaces

## Check markdown files for potential issues with markdownlint.
check/markdown: install/yarn
	$(call _print_step,Verifying Markdown files)
	yarn --silent markdownlint '**/*.md' --ignore node_modules --ignore CHANGELOG.md

## Verify if the files are formatted.
## You must first commit the changes, otherwise it won't detect the diffs.
check/format:
	$(call _print_step,Checking if files are formatted)
	$(SCRIPTS_DIR)/check-formatting.sh

.PHONY: format format/cspell
## Format files.
format: format/cspell

## Format cspell config file.
format/cspell: install/yarn
	$(call _print_step,Formatting cspell.yaml configuration \(words list\))
	yarn --silent format-cspell-config

.PHONY: install/yarn
## Install JS dependencies with yarn.
install/yarn:
	$(call _print_step,Installing yarn dependencies)
	yarn --silent install

.PHONY: help
## Print this help message.
help:
	$(SCRIPTS_DIR)/makefile-help.awk $(MAKEFILE_LIST)
