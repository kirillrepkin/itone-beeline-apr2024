### Технологический стек

- Java (JDBC, JAX RS, Jetty, Concurrent)
- Maven
- Docker, Docker Compose
- Bash

### Запуск

Выполняется:
- подготовка необходимых данных (7 текстовых файлов + 10M+ строк данных для БД)
- сборка единого jar
- сборка docker-контейнеров (2 стадии - maven-сборка + результирующий контейнер)
- запуск docker-compose.yml

```bash
./app.sh run
```
![app_run](img/bee_run.gif)

### Просмотр логов

Оболочка для docker compose logs

```bash
./app.sh logs {task1, task2}
```

![app_run](img/bee_logs_task1.gif)
![app_run](img/bee_logs_task2.gif)

### Результаты

#### Кейс №1

Просмотр логов task1

#### Кейс №2

Выполнить запросы на добавление и удаление, как описано в примере [request.http](request.http).

### Ограничения

- не выполнялась разработка юнит-тестов
- отсутствует ФЛК для JSON
- основной упор на логику работы, так как требований к быстродействию не заявлено.