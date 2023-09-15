### Sample requests for querying and creeating Pet records on DDB

POST http://localhost:8080/api/items
Content-Type: application/json

{
"name": "Mary",
"type": "bird",
"owner": "Miles Davis",
"birth_date": "2022-01-02"
}

---

GET http://localhost:8080/api/items
