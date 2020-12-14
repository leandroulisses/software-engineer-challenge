docker stop pg_admin
docker rm pg_admin
docker rmi pg_admin
docker stop postgres
docker rm postgres
docker rmi postgres
cd $(pwd)/batch-user-job
bash build.sh
cd ..
cd $(pwd)/user-api
bash build.sh
cd ..
docker-compose up -d