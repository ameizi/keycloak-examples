# keycloak-spring-boot-example

spring boot应用集成keycloak

## 获取token

```http request
POST http://192.168.201.188:8080/auth/realms/my-realm/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded

client_id=spring-boot-client&username=sfeng&password=123456&grant_type=password
```

## 刷新token

```http request
POST http://192.168.201.188:8080/auth/realms/my-realm/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded

client_id=springboot-client&grant_type=refresh_token&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlYWE2MThhMC05Y2UzLTQxZWMtOTZjYy04MGQ5ODVkZjJjMTIifQ.eyJleHAiOjE2MjU3NjI4ODYsImlhdCI6MTYyNTc2MTA4NiwianRpIjoiZjc2MjVmZmEtZWU3YS00MjZmLWIwYmQtOTM3MmZiM2Q4NDA5IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDExL2F1dGgvcmVhbG1zL2ZlbG9yZC5jbiIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODAxMS9hdXRoL3JlYWxtcy9mZWxvcmQuY24iLCJzdWIiOiI0YzFmNWRiNS04MjU0LTQ4ZDMtYTRkYS0wY2FhZTMyOTk0OTAiLCJ0eXAiOiJSZWZyZXNoIiwiYXpwIjoic3ByaW5nYm9vdC1jbGllbnQiLCJzZXNzaW9uX3N0YXRlIjoiZDU2NmU0ODMtYzc5MS00OTliLTg2M2ItODczY2YyNjMwYWFmIiwic2NvcGUiOiJwcm9maWxlIGVtYWlsIn0.P4vWwyfGubSt182P-vcyMdKvJfvwKYr1nUlOYBWzQks
```

## 访问应用

```http request
http://localhost:8080/hello
```

浏览器会重定向到登陆界面