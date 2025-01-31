# Build parameters
OUT?=./target
DOCKER_TMP?=$(OUT)/docker_temp/
DOCKER_PROVIDER_TAG?=harbor-b.alauda.cn/asm/dubbo-sample-provider:latest
DOCKER_CONSUMER_TAG?=harbor-b.alauda.cn/asm/dubbo-sample-consumer:latest
BINARY_NAME?=$(OUT)/dubbo-samples-basic-1.0-SNAPSHOT.jar

build:
	mvn package
	rm -rf ./consumer
	rm -rf ./provider
	mkdir consumer
	mkdir provider
	cp $(BINARY_NAME) ./consumer/
	cp $(BINARY_NAME) ./provider/
	cp ./docker/Dockerfile.provider ./provider/Dockerfile
	cp ./docker/Dockerfile.consumer ./consumer/Dockerfile

docker-build: build
	rm -rf $(DOCKER_TMP)
	mkdir $(DOCKER_TMP)
	cp $(BINARY_NAME) $(DOCKER_TMP)
	cp ./docker/Dockerfile.provider $(DOCKER_TMP)Dockerfile
	docker build -t $(DOCKER_PROVIDER_TAG) $(DOCKER_TMP) 
	cp ./docker/Dockerfile.consumer $(DOCKER_TMP)Dockerfile
	docker build -t $(DOCKER_CONSUMER_TAG) $(DOCKER_TMP) 
	rm -rf $(DOCKER_TMP)
docker-push: docker-build
	docker push $(DOCKER_PROVIDER_TAG)
	docker push $(DOCKER_CONSUMER_TAG)
clean:
	rm -rf $(OUT)

.PHONY: build docker-build docker-push clean
