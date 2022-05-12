# keycloak-springsecurity-example

### 获取token

```http request
POST http://192.168.201.188:8080/auth/realms/my-realm/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded

client_id=spring-boot-client&username=sfeng&password=123456&grant_type=password
```

### 刷新token

```http request
POST http://192.168.201.188:8080/auth/realms/my-realm/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded

client_id=spring-boot-client&grant_type=refresh_token&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlZWE3Y2I3My0yZTlmLTRhMzItOTA2OC1iOGRiOWUzZDQ4NDgifQ.eyJleHAiOjE2NTIxNTE1OTgsImlhdCI6MTY1MjE0OTc5OCwianRpIjoiODc1NmI0YWEtOTkyMC00OTQ5LWI0MGUtYzZkZTA0NTE2Yzc1IiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMjAxLjE4ODo4MDgwL2F1dGgvcmVhbG1zL215LXJlYWxtIiwiYXVkIjoiaHR0cDovLzE5Mi4xNjguMjAxLjE4ODo4MDgwL2F1dGgvcmVhbG1zL215LXJlYWxtIiwic3ViIjoiZGFjNzAyMGYtNGQ0OS00YzVjLWEzN2YtZWY4YTcyM2U5ZjEyIiwidHlwIjoiUmVmcmVzaCIsImF6cCI6InNwcmluZy1ib290LWNsaWVudCIsInNlc3Npb25fc3RhdGUiOiJjMDg5YzdiMy1kMWFkLTRjMGMtYWY4YS0wNDkzMjNlMjhjMDQiLCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJzaWQiOiJjMDg5YzdiMy1kMWFkLTRjMGMtYWY4YS0wNDkzMjNlMjhjMDQifQ.D1rXlVLV2BCMiWtIC3gWfNWWWyp9HDhsAaBoa32_OHU
```

-----------------------------------------bearer-------------------------------------------------------------------------

### 获取token

```http request
POST http://192.168.201.188:8080/auth/realms/my-realm/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded

client_id=spring-boot-security-bearer&client_secret=2ae3a35b-983a-42ff-bf6d-06a7268ca940&grant_type=password&username=sfeng&password=123456
```

### 访问应用接口

```http request
#GET http://172.24.107.84:8080/user
#GET http://172.24.107.84:8080/admin
GET http://172.24.107.84:8080/principal
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ6N0ZnRkxWZ0FNNlJ4dkw3bTR4REVSd2s0bkdyY0xMX0pBMXctamdPRHBVIn0.eyJleHAiOjE2NTIyNDY5NDIsImlhdCI6MTY1MjI0NjY0MiwianRpIjoiYmIwZThjYWYtZTUwZC00NTU1LThhMmMtYTNkNjY5NzIwZTA0IiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMjAxLjE4ODo4MDgwL2F1dGgvcmVhbG1zL215LXJlYWxtIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjMzYzRhMTY4LWViNzYtNDhkZS05MmFhLWYxZWM2Y2Q4MTAyYiIsInR5cCI6IkJlYXJlciIsImF6cCI6InNwcmluZy1ib290LXNlY3VyaXR5LWJlYXJlciIsInNlc3Npb25fc3RhdGUiOiIwMjkxMWNiZi1kNzFhLTRmMjktYjIzMC00MzJjNzYzODFhNzIiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly8xNzIuMjQuMTA3Ljg0OjgwODAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiYWRtaW4iLCJ1bWFfYXV0aG9yaXphdGlvbiIsInVzZXIiLCJkZWZhdWx0LXJvbGVzLW15LXJlYWxtIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwic2lkIjoiMDI5MTFjYmYtZDcxYS00ZjI5LWIyMzAtNDMyYzc2MzgxYTcyIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZmVuZyJ9.Z8xeqcjJ3scuC8r6q3EfsVby5rNh8FHRT2xAJsK9E_1EK5PDEdHsFSW9wkMU12ca3fNy3LskEdPdDp2yLO7ezLR4OVlYerUTarbYJ1KxkDN0JNpNNe2DSclCeNdMBY65xRGbigYjVcn1YOD0i1abv7DiXhLAYnC3WhbOd8aixVj7zXs-jxHxdveKqd8CAlglkQ6o570SnJwybvgXaS2Rw94B_Trn0wavnHWsD6d3U4DyjZv10ZciOIV-jgTrEe47aI8yZMnfIdHh-g6m118q7evwdU1_nNTpEjxfUddEiC2dk6NF-usl_T9DcuR5nL1RWQ8vWnpsr7JynTJCceQARQ
```
