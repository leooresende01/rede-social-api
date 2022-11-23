# API rede social


## API que possue funcionalidades de uma rede social (Baseada no [Instagram](https://www.instagram.com/))
---

## Segurança:
- Autenticação stateless via token JWT e refresh roken para re-autenticação ao expirar o JWT
- Uso do BCrypt nas senhas persistidas no banco de dados
- Criptografia da solicitação HTTP (body) usando AES, dificultando usuários que capturam o trafego acessar o dado trafegado
---
## Features:
|  Recurso  | Suporte | Descrição       |
| --------- |  -----  | --------------- |
| Buscar usuários    |   ✔️    | Buscar outros usuários cadastrados e ver seus perfis |
| Publicar/Deletar publicação    |   ✔️    | Publicar uma imagem, adicionar legenda e deletar a mesma |
| Seguir/Deixar de seguir    |   ✔️    | Seguir outros usuários |
| Curtir/Descurtir    |   ✔️    | Curtir e descurtir publicações |
| Comentar/Remover comentários  |   ✔️    | Comentar em publicações e remover comentários  |
| Editar conta  |  ✔️   |  Editar informações da conta  |
| Mensagens  |   ✔️    |  Trocar mensagens com outros usuários  |
| Deletar conta  |   ❌    |  Deletar conta criada  |
| Responder comentarios  |   ❌    |  Responder comentarios de outros usuários  |
---
## Tecnologias Usadas:
- [Java](https://docs.oracle.com/en/java/)
- [Spring MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [Spring Data](https://spring.io/projects/spring-data)
- [Spring Security](https://spring.io/projects/spring-security)
- [GSON](https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.0/com/google/gson/Gson.html)
  
---
## Extras:
- [Aplicação front-end (Source)](https://github.com/leooresende01/rede-social-frontend)
- [Aplicação Completa em produção](https://rede-social.leooresende.tk/)
- [Aplicação WebSocket (Source)](https://github.com/leooresende01/rede-social-frontend)

