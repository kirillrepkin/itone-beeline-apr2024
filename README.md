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