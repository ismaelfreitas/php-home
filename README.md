# Automatização de uma casa utilizando Raspberry Pi e Arduino #

![casa.png](https://bitbucket.org/repo/LkBGKL/images/3043635937-casa.png)

## Objetivo ##
Automatizar o controle de uma residência, permitindo executar ações como acender e apagar luzes, abrir portão, obter temperatura, umidade e ter controle de presença de ambientes, através de um dispositivo móvel com sistema operacional Android.


## Arquitetura de comunicação ##
O Arduino será responsável pelo acionamento de todos os componentes da casa.


Através do aplicativo Android será responsável por apresentar o status de todos os componentes da casa, podendo ou não acioná-los.


Para padronizar toda a comunicação entre o Android e o Arduino e permitir o acesso remoto dos dados, vamos utilizar um Web Service REST PHP instalado no Raspberry Pi.


**Exemplo de tráfego de dados:

Estamos na sala e eu preciso apagar a luz, abro o aplicativo e encontro a opção para apagar a luz da sala, ao clicar no botão o Android enviará uma requisição REST ao Web Service instalado no Raspberry Pi, este interpretará se a requição é válida localizando o comando recebido, em seguida enviará através de comunicação serial o mesmo comando para o Arduino que apagará a luz. 


Após o Arduino apagar a luz, será enviado sucesso ou erro para o Raspberry Pi pela comunicação serial, este retornará para o aplicativo em formato json o sucesso ou erro na ação, o aplicativo processará este retorno e atualizará a interface gráfica com o resultado.


## Componentes ##
* Arduino Mega
* Raspberry Pi B+
* Jumpers
* LEDs
* Resistores 250 ohms
* Servo Motor
* Sensor de presença
* Buzzer
* DHT-22
* Protoboard
* Fonte 5v 2A



**Todo backend (webservice e arduino) está no arquivo Backend.tar.gz**