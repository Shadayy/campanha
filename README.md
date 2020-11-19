# Arquitetura
A aplicação é uma aplicação RestFul Java com Spring Boot que utiliza banco em memória (H2) e faz uso de mensageria (RabbitMQ) para cadastro e noticação das alterações.

Todas as APIs podem ser utilizadas diretamente pelo Swagger.

# Componentes
*  Servidor Tomcat
    *  http://localhost:8080
*  Documentação Swagger
    *  http://localhost:8080/swagger-ui.html#/
*  Banco de dados H2
    *  http://localhost:8080/h2
*  RabbitMQ
    *  http://localhost:5672

# Estrutura da aplicação
*  A aplicação faz uso do Flyway para upgrade do BD
*  A aplicação faz envio de mensagens para duas filas uma para criação e uma para notificar atualização das campanhas
*  A aplicação consome a fila de criação de campanhas com apenas 1 consumidor para garantir a não sobreposição das datas de termino de vigência

# Estrutura do projeto

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── victor
│   │   │           └── campanha
│   │   │               ├── amqp -> Abstrações que serão utilizadas para envio a filas`
│   │   │               │   └── impl -> Implementações das abstrações`
│   │   │               ├── business -> Abstrações de regra de negócio para mensagens recebidas das fila`
│   │   │               │   └── impl -> Implementações das abstrações`
│   │   │               ├── config -> Classes auxiliares para configurações do Spring. Anotadas com @Configuration e @ControllerAdvice`
│   │   │               ├── controller -> Declaração das APIs disponíveis para consumo`
│   │   │               ├── dto -> Declarão de entidades utilizadas para envio e recebimento de dados das APIs`
│   │   │               ├── entity -> Entidades de persistência utilizadas para armazenamento das informações`
│   │   │               ├── exception -> Declaração de exceptions utilizadas`
│   │   │               ├── repository -> Abstração utilizada para acesso aos dados persistidos`
│   │   │               ├── service -> Abstrações de regra de négocio para as APIs`
│   │   │               │   └── impl -> Implementações das abstrações`
│   │   │               └── util -> Classes utilitárias para manipulação e formatação dos dados`
│   │   └── resources
│   │       ├── application.properties -> Conjunto de parametrizações necessárias para funcionamento da aplicação`
│   │       └── db -> Pacote com scripts SQL`
│   │           └── migration -> Arquivos SQL para atualização do BD`

```
