DOCKER_IMAGE_NAME=$1
USER=$2
USER_ID=$4
GROUP_ID=$5

START=$(date +%s)
docker build -t $DOCKER_IMAGE_NAME ./
END=$(date +%s)
DIFF=$(( $END - $START ))
echo "Docker image $DOCKER_IMAGE_NAME built in $DIFF seconds"