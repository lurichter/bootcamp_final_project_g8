# Grupo 8 - Projeto Final MELI Bootcamp W1-BR

<h3>Introdução</h3>

Todos os Documentos com os requisitos implementados (incluindo o 6 individual)
estão na pasta `/docs` na raiz do projeto.


<h3>Setup</h3>
A aplicação necessita de uma instância do banco de dados MySQL para 
ser rodada localmente.

É necessário as seguintes variáveis de ambiente configuradas de acordo com
as configurações do banco:

`````
SCOPE=local;
LOCAL_DB_USER=root;
LOCAL_DB_PASS=123456789;
`````

Configurações como a porta e URL do banco de dados podem ser modificadas
no arquivo `application-integration_test.yml`

<h3>Dados Iniciais</h3>

Os dados iniciais DDL e DML rodam automaticamente. Tanto no perfil local quanto no
perfil de testes.

No perfil de testes a aplicação roda com um banco de dados em memória `H2`.


<h3>Documentação</h3>

Todos os serviços necessitam de autenticação. O token de autenticação pode
ser obtido pelo endpoint de login (exemplos abaixo). Ao obter o token é necessário
inserir o header `Authentication : Bearer {token}` para consumir os serviços.

<h4> Postman </h4>
Todos os endpoints necessários para os devidos testes dos requisitos (com exceção do req06) 
estão disponíveis no link abaixo, que contém os metadados em Raw text da coleção do Postman. Para importar basta clicar 
em Import no client do Postman e importar via Link ou Raw text.

https://www.getpostman.com/collections/a94387c0135d1901b6c3

O header de autenticação pode ser configurado na pasta pai "Final Project" da coleção
fazendo assim com que o token seja herdado pelos demais serviços. 

(Lembrar de salvar quando trocar o token).

<h4> Swagger </h4>
É possível ver a documentação e testar os endpoints via Swagger pelo link:

http://localhost:8082/swagger-ui.html

É possível configurar a autenticação pelo botão `Authorize`.

<h3>Requisito 06<h4>

Os detalhes do Requisito 6 (individual) pode ser encontrado no
arquivo `Req06-{nome-aluno}.pdf` em `/docs` e os exemplos para teste podem ser encontrados
no final do próximo tópico (Requirements/Examples) do README.

<h3>Requirements/Examples<h4>

First is necessary authenticate w/ email and password

[POST]
````shell
/api/v1/sign-in 
````

Eg.: 
````json
{
    "username" : "operador1@mercadolivre.com",
    "password" : "123456"
}
````

<hr>

US01 - ml-insert-batch-in-fulfillment-warehouse-01

create new inbound order [POST] 
````shell
/api/v1/fresh-products/inboundorder
````

Eg.:

````json
{
  "inboundOrder": {
    "section": {
      "sectionCode": 1,
      "warehouseCode": 1
    },
    "batchStock": [
      {
        "batchNumber": "BATCH001",
        "productId": 1,
        "currentTemperature": 15,
        "minimumTemperature": 10,
        "quantity": 2,
        "manufacturingDate": "2021-07-08",
        "manufacturingTime": "11:15:24",
        "dueDate": "2021-07-16"
      },
      {
        "batchNumber": "BATCH002",
        "productId": 1,
        "currentTemperature": 15,
        "minimumTemperature": 10,
        "quantity": 3,
        "manufacturingDate": "2021-07-08",
        "manufacturingTime": "11:15:24",
        "dueDate": "2021-07-17"
      },
      {
        "batchNumber": "BATCH003",
        "productId": 1,
        "currentTemperature": 15,
        "minimumTemperature": 10,
        "quantity": 3,
        "manufacturingDate": "2021-07-08",
        "manufacturingTime": "11:15:24",
        "dueDate": "2021-07-15"
      }
    ]
  }
}
````

update inbound order [PUT]
````shell
/api/v1/fresh-products/inboundorder
````

Eg.:

````json
{
  "inboundOrder": {
    "section": {
      "sectionCode": 6,
      "warehouseCode": 1
    },
    "batchStock": [
      {
        "batchId": 1,
        "batchNumber": "BATCH001",
        "productId": 1,
        "currentTemperature": 15,
        "minimumTemperature": 10,
        "quantity": 2,
        "manufacturingDate": "2021-07-08",
        "manufacturingTime": "11:15:24",
        "dueDate": "2021-07-16"
      },
      {
        "batchId": 2,
        "batchNumber": "BATCH002",
        "productId": 1,
        "currentTemperature": 15,
        "minimumTemperature": 10,
        "quantity": 8,
        "manufacturingDate": "2021-07-08",
        "manufacturingTime": "11:15:24",
        "dueDate": "2021-07-17"
      },
      {
        "batchId": 3,
        "batchNumber": "BATCH003",
        "productId": 1,
        "currentTemperature": 15,
        "minimumTemperature": 10,
        "quantity": 3,
        "manufacturingDate": "2021-07-08",
        "manufacturingTime": "11:15:24",
        "dueDate": "2021-07-15"
      }
    ]
  }
}
````

<hr>

US02 - ml-add-products-to-cart-01

 
List all products [GET]
```shell
/api/v1/fresh-products/
```

List products by category [GET]
```shell
/api/v1/fresh-products/list/{productCategory}
```

- > productCategory [Enum] : FS, RF or FF;

FS: Fresh
RF: Chilled
FF: Frozen

Eg.: 
````
/api/v1/fresh-products/list/FS
````

New purchase order [POST]
````shell
/api/v1/fresh-products/orders
````

Eg.:

````json
{
    "purchaseOrder": {
        "buyerId": 1,
        "orderStatus": {
            "statusCode": "OPEN"
        },
        "products": [
            {
                "productId": 1,
                "quantity": 100
            },
            {
                "productId": 2,
                "quantity": 50
            }
        ] 
    }
}
````

List specific purchase order [GET]

````shell
/api/v1/fresh-products/orders/{idOrder}
````
- > idOrder [Long] - identifier from purchase order
  
Update purchase order [PUT]

````shell
/api/v1/fresh-products/orders/{idOrder}
````

- > idOrder [Long] - identifier from purchase order

Eg.:

````json
{
    "purchaseOrder": {
        "buyerId": 1,
        "orderStatus": {
            "statusCode": "OPEN"
        },
        "products": [
            {
                "productId": 1,
                "quantity": 100
            },
            {
                "productId": 2,
                "quantity": 50
            }
        ] 
    }
}
````

<hr>

US03 - ml-check-product-location-in-warehouse-01

List product per batch [GET]
````shell
/api/v1/fresh-products/batch/list/{productId}?order
````

- > productId [Long]: identifier from a product;
- > order [String] - OPTIONAL: Query param w/ default value = dueDate_desc
- > order values: dueDate_desc or dueDate_asc
  
<hr>

US04 - ml-check-product-stock-in-warehouses-04

List all products from Warehouse [GET]
````shell
/api/v1/fresh-products/warehouse/{productId}
````

- > productId [Long]: identifier from some product
  
<hr>

US05 - ml-check-batch-stock-due-date-01

List products by due date [GET]
````shell
/api/v1/fresh-products/due-date/list/{daysQuantity}
````

- > daysQuantity [Integer] : filter by days remaining 
- > Query param: 
  - > productCategory: [Enum] : FS, RF or FF;
  - > order [String] - OPTIONAL: Query param w/ default value = dueDate_desc
  - > order values: dueDate_desc or dueDate_asc
    
<hr>

US06 -