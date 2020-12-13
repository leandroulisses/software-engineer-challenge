cd $(pwd)/batch-user-job
bash build.sh
cd ..
cd $(pwd)/user-api
bash build.sh
cd ..
docker-compose up -d