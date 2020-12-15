docker stop mongo
docker rm mongo
docker rmi mongo
cd $(pwd)/batch-user-job
bash build.sh
cd ..
cd $(pwd)/user-api
bash build.sh
cd ..
docker-compose up -d
