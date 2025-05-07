.PHONY: ide
## Run the IDE.
ide:
	nohup idea-community . > /dev/null 2>&1 &

.PHONY: build
## Build the plugin.
build:
	./gradlew buildPlugin

.PHONY: check
## Run gradle checks.
check:
	./gradlew check
