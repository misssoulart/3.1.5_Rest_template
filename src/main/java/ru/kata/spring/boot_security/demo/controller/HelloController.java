package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

// Для работы предоставляется API по URL - http://94.198.50.185:7081/api/users
// Ваша задача: Последовательно выполнить следующие операции и получить код для проверки на платформе:
//
// 1. Получить список всех пользователей.
// 2. Когда вы получите ответ на свой первый запрос, вы должны сохранить свой session id, который получен через cookie.
//    Вы получите его в заголовке ответа set-cookie. Поскольку все действия происходят в рамках одной сессии,
//    все дальнейшие запросы должны использовать полученный session id (необходимо использовать заголовок в последующих запросах)
// 3. Сохранить пользователя с id = 3, name = James, lastName = Brown, age = на ваш выбор.
//    В случае успеха вы получите первую часть кода.
// 4. Изменить пользователя с id = 3. Необходимо поменять name на Thomas, а lastName на Shelby.
//    В случае успеха вы получите еще одну часть кода.
// 5. Удалить пользователя с id = 3. В случае успеха вы получите последнюю часть кода.
//
//    В результате выполненных операций вы должны получить итоговый код, сконкатенировав все его части. Количество символов в коде = 18.

@RestController
public class HelloController {
    String url = "http://94.198.50.185:7081/api/users";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping
    public String getCode () {
        String code = "";
        String url = "http://94.198.50.185:7081/api/users";

        ResponseEntity<List<User>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
        String cookies = response.getHeaders().getValuesAsList("Set-Cookie").get(0);
        String jsessionid = cookies.substring(cookies.indexOf("JSESSIONID="), cookies.indexOf(";"));
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("Cookie", jsessionid);


        // Сохранить пользователя (пункт 3)
        User newUser = new User(3L, "James", "Brown", (byte)41);
        HttpEntity<User> entity = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> response1 = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        code += response1.getBody();


        // Изменить пользователя (пункт 4)
        User user = new User(3L, "Thomas", "Shelby", (byte)41);
        HttpEntity<User> entity1 = new HttpEntity<>(user, headers);
        ResponseEntity<String> response2 = restTemplate.exchange(url, HttpMethod.PUT, entity1, String.class);
        code += response2.getBody();


        // Удалить пользователя (пункт 5)
        HttpEntity<User> entity2 = new HttpEntity<>(headers);
        ResponseEntity<String> response3 = restTemplate.exchange(url + "/3", HttpMethod.DELETE, entity2, String.class);
        code += response3.getBody();

        return code;
    }
}
