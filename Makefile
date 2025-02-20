.PHONY: ide
## Run the IDE.
ide:
  nohup idea-community . > /dev/null 2>&1 &
