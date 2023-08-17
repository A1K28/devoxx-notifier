APP_NAME := devoxx
APP_TAG := latest

build:
	DOCKER_BUILDKIT=1 docker build -t $(APP_NAME):$(APP_TAG) .

run: ## run a detached container
	docker run -d \
-it \
-e TZ=Asia/Tbilisi \
--name=$(APP_NAME) \
$(APP_NAME)

logs: ## follow container logs
	docker logs $(APP_NAME) -f

a: ## attach to a running container
	docker attach $(APP_NAME)

stop: ## stop a running container
	docker container stop $(APP_NAME)

rm: ## remove container and image
	docker container rm $(APP_NAME); docker image rm $(APP_NAME)

deploy: build run logs

redeploy: stop rm deploy

r: redeploy
