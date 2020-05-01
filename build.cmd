PARENT_NAME=$1
WORKSPACE=$HOME/Workspace

if [ -z $PARENT_NAME ]; then
    APP_NAME=toolbox.spring.oauth2-authotization-server
else
    APP_NAME=$PARENT_NAME
fi

gradle clean build \
&& mv build/libs/$(ls build/libs) build/libs/$APP_NAME.jar \
&& docker build . -t $APP_NAME \
&& docker container rm -f $APP_NAME \
&& docker container run --detach --restart always \
                        --publish 8443:8443 --link dev-mysql:dev-mysql \
                        --name $APP_NAME $APP_NAME

#if [ $? = 0 -a $MUST_RETURN = true ]; then
#   cd $HOME/Workspace
#fi

