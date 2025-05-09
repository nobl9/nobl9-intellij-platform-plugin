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

.PHONY: check
## Run gradle checks.
check:
	./gradlew check
