# Code Challenge: Capital Gains

## Context

    "O objetivo deste exercício é implementar um programa de linha de comando (CLI) que calcula o imposto a ser pago 
    sobre lucros ou prejuízos de operações no mercado financeiro de ações." - Code_Challenge__Ganho_de_Capital_ptbr.pdf

## Dependencies

- JAVA (amazoncorretto) `21`
- Spring Boot `3.4.1`
- Maven `3.9.9`
- Lombok `1.18.36`
- Commons-lang3 `3.17.0`

## Setup

### Build project

```shell
 ./mvnw clean install
```

### Run test

```shell
./mvnw test
```

### Run application

By Maven with manual input

```shell
 ./mvnw spring-boot:run
```

By Maven with file input

```shell
 ./mvnw spring-boot:run < ./examples/input.txt
```

By .jar with manual input

```shell
 java -jar target/capital-gains-1.0.0.jar
```

By .jar with file input

```shell
 java -jar target/capital-gains-1.0.0.jar < ./examples/input.txt
```

By Docker (with docker compose) with manual input

```shell
 
```

By Docker (with docker compose) file input

```shell
 
```

Input data example

```text
[{"operation":"buy", "unit-cost":10.00, "quantity": 10000},{"operation":"sell", "unit-cost":20.00, "quantity": 5000}]
[{"operation":"buy", "unit-cost":20.00, "quantity": 10000},{"operation":"sell", "unit-cost":10.00, "quantity": 5000}]
```

> Tips: File with all data examples: [`./examples/input.txt`](./examples/input.txt)
