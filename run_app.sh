function build_image() {
    echo "Check $1 image..."
    if docker images | grep -q $1
    then
        echo "Image $1 is created. Do you want to delete it and create a new one? (y/n)"
        read REPLY
        if [[ $REPLY =~ ^[Yy]$ ]]
        then
           docker image rm $1 -f
           cd $1
           docker build -t $1 .
           cd ..
        fi
    else
        echo "No $1 existing image. Creating a new one"
        cd $1
        docker build -t $1 .
        cd ..
    fi
}

function build_container() {
   if docker ps -a | grep -q "$1"
   then
       echo "Container $1 is running. Do you want to delete it and create a new one? (y/n)"
       read REPLY
       if [[ $REPLY =~ ^[Yy]$ ]]
       then
          docker rm "$1" -f
          docker container run -d --name "$1" --network=parceldelivery -p "$2":"$2" "$1":latest
          sleep 5
      fi
   else
       echo "Container $1 is not running! Creating a new one..."
       docker container run -d --name "$1" --network=parceldelivery -p "$2":"$2" "$1":latest
       sleep 5
   fi
}

./gradlew clean build

NETWORK_NAME=parceldelivery
if [ -z $(docker network ls --filter name=^${NETWORK_NAME}$ --format="{{ .Name }}") ] ; then
     docker network create ${NETWORK_NAME} ;
fi


docker-compose -f docker-compose.yml up -d

build_image "eureka"
build_container "eureka" "8761"
build_image "gateway"
build_container "gateway" "9090"
build_image "auth"
build_container "auth" "8081"
build_image "courier"
build_container "courier" "8082"
build_image "parcel-delivery"
build_container "parcel-delivery" "8083"
exit