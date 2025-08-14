# Instructions for deploying the service

> Для развертывания сервиса требуются предустановленные [Docker Dekstop](https://www.docker.com/products/docker-desktop/) & [JDK-21](https://www.oracle.com/java/technologies/downloads/#java21) & [pgAdmin v9.6](https://www.postgresql.org/ftp/pgadmin/pgadmin4/v9.6/windows/)

Шаги для развертывания сервиса:
1. Установить и распаковать в свободную директорию исходники проекта
2. Открыть командную строку от имени администратора
3. Сменить расположение на ранее созданную папку с исходниками проекта
4. Открыть pgAdmin и создать новый сервер
5. Зайти в .env-example и изменить следующие строки:
   ```
   DATASOURCE_URL=jdbc:postgresql://mg-identity-service-postgres:5432/НАЗВАНИЕ-СОЗДАННОГО-СЕРВЕРА
   DATASOURCE_DB=mg-identity-service
   DATASOURCE_USERNAME=ПОЛЬЗОВАТЕЛЬ-СЕРВЕРА
   DATASOURCE_PASSWORD=ПАРОЛЬ-ОТ-СЕРВЕРА
   ```
6. Ввести следующие команды в строгом порядке:
    ```
    gradlew build
    docker build -t mg-identity-service .
    docker-compose up
    ```
8. После **шага 7** у Вас будет доступна документация по [ссылке](http://localhost:8080/identity-mg/v3/api-docs/index.html)


> [!TIP]
> Все основные тесты проходят на ветке **dev**

> [!WARNING]
> В исходниках проекта нет актуальных чувствительных данных (Токены доступа, переменные окружения).
> Все данные, которые содержаться в репозитории носят исключительно показательный характер настройки сервиса.
> Для корректировки обратитесь к @Ingur-5967
