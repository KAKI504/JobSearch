# JobSearch Project
## Настройка базы данных
Я создал базу данных PostgreSQL для проекта JobSearch:
1. Запустил PostgreSQL через терминал:
   ```bash
   psql -U postgres
   ```
2. Создал новую базу данных:
   ```sql
   CREATE DATABASE jobsearch;
   ```
3. Подключился к созданной базе данных:
   ```bash
   \c jobsearch
   ```
4. В файле `application.properties` настроил подключение:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/jobsearch
   spring.datasource.username=postgres
   spring.datasource.password=qwerty123
   
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   
   server.port=8081
   ```
5. При запуске Spring Boot приложения Hibernate автоматически создал таблицы в базе данных благодаря настройке `spring.jpa.hibernate.ddl-auto=update`.

6. Проверил созданные таблицы:
   ```
   \dt
   ```
   Результат:
   ```
                List of relations
   Schema |         Name         | Type  |  Owner   
   --------+----------------------+-------+----------
   public | categories           | table | postgres
   public | contact_types        | table | postgres
   public | contacts_info        | table | postgres
   public | education_info       | table | postgres
   public | messages             | table | postgres
   public | responded_applicants | table | postgres
   public | resumes              | table | postgres
   public | users                | table | postgres
   public | vacancies            | table | postgres
   public | work_experience_info | table | postgres
   (10 rows)
   ```

Таким образом я настроил PostgreSQL для работы с проектом JobSearch. Hibernate создает таблицы автоматически на основе моделей данных с аннотациями JPA.