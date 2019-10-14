ENCRYPT-SERVICE

Applications:
* Docker
    * consul (Port: 8500)
    * zipkin (Port: 9411)
        * STORAGE_TYPE = mysql
        * MYSQL_HOST = mysql
    * zipkin-dependencies
        * STORAGE_TYPE = mysql
        * MYSQL_HOST = mysql
        * MYSQL_USER = zipkin
        * MYSQL_PASS = zipkin
    * zipkin-msysql (Port: 3306)
        * ZIPKIN_VERSION = 2.14.0
    * mongodb (Port: 27017)         
        
* Micronaut
    * encrypt-service (Port: -1)
    * encryption-data
    * gateway (Port: 8080)