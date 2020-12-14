Nome: Leandro Ulisses dos Passos

![image info](/img/solution.JPG)

## BATCH USER JOB
Responsabilidades do serviço:
- Realiza o download do arquivo da base gzip
- Extrai as informações do arquivo gzip para um arquivo csv
- Inicia o processamento do arquivo csv inserindo os dados no mongodb indexando por name e username.

## USER API
Responsabilidades do serviço:
- Realizar consultas na base por qualquer texto digitado
- Api de autenticação

#Iniciando o projeto
Para iniciar os projetos basta rodar:
```
sh run.sh
```


## Autenticando no serviço
Para autenticar basta realizar a seguinte chamada:
```
curl --location --request POST 'http://localhost:8080/auth' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"user",
    "password":"12345"
}'
```
O response comando acima retornará um Json nesse formato:
```
{"token":"eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJBUEkgVVNFUiBQSUNQQVkgU09GVFdBUkUgRU5HSU5FRVIgQ0hBTExFTkdFIiwic3ViIjoiZWM1ZjY2YWEtNDZlNC00ZjExLTk3NTEtZjJlMmJmMjg1ZjJjIiwiaWF0IjoxNjA3OTU1Nzk5LCJleHAiOjE2MDgwNDIxOTksInJvbGVzIjpbIlVTRVIiXX0.hEoR0m3Vm1JpuuBJgkWuQ2eCAJ9fi48P_X3XhCcZA-_jYZtxtJXe7da1Pj4lH56eN75EJTGxE5wwq-dEPiX-ZQ","type":"Bearer"}
```

## Pesquisando usuários por uma keyword
```
curl --location --request GET 'http://localhost:8080/users?keyword=leandro&pageNumber=0' \
--header 'Authorization: Bearer TOKEN_GERADO_NA_REQUEST_ANTERIOR' 
```
