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

<h3>Requirements</h3>
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
````json
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