# Arquitetura
A aplicação é uma aplicação RestFul Java com Spring Boot que utiliza banco em memória (H2) e faz uso de mensageria (RabbitMQ) para cadastro e noticação das alterações.

Todas as APIs podem ser utilizadas diretamente pelo Swagger.

# Componentes
*  Seridor Tomcat
    *  http://localhost:8080
*  Documentação Swagger
    *  http://localhost:8080/swagger-ui.html#/
*  Banco de dados H2
    *  http://localhost:8080/h2
*  RabbitMQ
    *  http://localhost:5672

# Estrutura da aplicação
A aplicação faz uso do Flyway para upgrade do BD
