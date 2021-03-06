### Тех.задание:
### Разработайте и создайте REST приложение, которое предоставляет функциональные возможности для банковских счетов.

##### Эти возможности заключаются в следующем:
1. Реализовать возможность пользователю создавать банкоский счет (до 5 на пользователя, а также основной счет в виде полей профиля без возможности удаления).
2. Реализовать возможность пользователю пополнять баланс и снимать со счета средства.
3. Реализовать возможность пользователю переводить средства между своими счетами и на посторонний счет.
4. Реализовать возможность пользователю удалить счет (если остались средства, то перевести их на счет профиля). 

##### Предложения для начала работы:
1. Класс пользователя должен быть составлен как можно проще (идентификатор, емейл, пароль, профиль, роль(сделать в виде enum), остальные счета (не более 5)).
2. Класс профиля минимально должен содержать следующие поля: идентификатор-счет, имя, фамилия, баланс счета.
3. Класс счета минимально  должен содержать следующие поля: идентификатор-счет, баланс счета.
4. Добавьте столько дополнительных полей, сколько необходимо.

##### Условия для приложения:
1. Используйте Spring Boot с Java 8 или 11.
2. Используйте реляционную БД (MySQL, PostgreSQL) и Liquibase в качестве инструмента для миграции БД.
3. Создайте unit-тесты как для сценариев ошибок, так и для успешных.
4. Настройте приложение так, чтобы мы могли запускать его с помощью Docker.
5. Все endpoint должны потреблять и производить только JSON.
6. Код должен быть доступен в общедоступном репозитории github/gitlab для клонирования.
