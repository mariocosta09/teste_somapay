# teste_somapay
Teste dev SomaPay

# Instruçôes
O projeto utiliza Spring Boot,spring security,Auth2.0, JWT com Java 11 e como gerenciador de dependências temos o Maven, então para que a 
mesma funcione corretamente é necessário ter o Java 11 em sua máquina ou no ambiente de workspace onde a
aplicação será executada na IDE(Eclipse, InteliJ...), e ter o Maven seja ele instalado diretamente em sua máquina ou
somente na IDE. Uma vez que o projeto tenha sido baixado e importado para sua IDE de preferência, em geral as IDEs
já baixam as dependências no momento da importação, do contrário é necessário executar um maven update para
que ele possa buscar pelas dependências do projeto. A porta que está sendo utilizada é a 9000, caso ela não esteja
disponível em sua máquina, altere para uma porta de sua preferência que esteja livre. No banco de dados temos a
aplicação utilizando o PostgresSQL 11, para que a aplicação funcione deve-se atentar às seguintes configurações no
application.properties
