# MiningWorld
um simples sistema de <b>mineração</b> para servidores de factions, podendo criar quantos e qualquer item para ser dropado!

## Comandos
<table border="1" style="border-collapse: collapse">
<tr>
<th>Comando</th> <th>Descrição</th> <th>Permissão</th>
</tr>
<tr>
<td>/mina</td> <td>ir até o mundo de mineração</td> <td>Nenhuma</td>
</tr>
</table>

## Configuração
```bash

# Olá, para lhe ajudar a configurar o Plugin corretamente, lista de efeitos e materiais abaixo!
# https://helpch.at/docs/1.8/index.html?org/bukkit/PotionEffectType.html
# https://helpch.at/docs/1.8/org/bukkit/Material.html

delay-command: 25 -> Intervalo de tempo para digitar novamente o comando
delay-effects: 5 -> Intervalo de tempo para setar os efeitos

world:
  name: "mina" -> Nome do mundo que vai ser criado
  seed: 0 -> Defina a seed do mundo ou deixe 0 para gerar aleatoriamente
  radius-teleport: 150 -> Teleporta para uma coordenada aleatoria entre 0 e 150
  effects:
    - 'FAST_DIGGING;20;1' -> Defina o efeito da seguinte forma [Nome do efeito;duração;nivel]
  materials-blocked: 
    - "STONE" -> Defina o material que não irá ser dropando no mundo de mineração

messages:
  in-cooldown: "&cAguarde para digitar este comando novamente!"
  in-world: "&cVocê já está no mundo de mineração"
  inventory-full: "&cSeu inventário está lotado."
  join-world:
    - '&aSeja bem-vindo ao mundo de Mineração!'
    - ''
    - ' &eEsse mundo é resetado toda vez que o servidor reinicia'
    - ' &eNão construa bases nesse mundo, Não guarde seus itens nesse mundo'
    - ''

drops:
  1:
    percentage: 20.1 -> Porcentagem do item que irá ser dropado
    height:
      max: 5 -> Altura máxima aonde ele dropará
      min: 1 -> Altura minima aonde ele dropará
    material:
      id: 266 -> Id do material que será dropado
  2:
    percentage: 40.1
    height:
      max: 16
      min: 11
    material:
      id: 266
```

## Tecnologias utilizadas
O Projeto foi desenvolvido utilizando as seguintes tecnologias.

- [Google Guice](https://github.com/google/guice)
- [Pdm](https://github.com/knightzmc/pdm)

## Download
Você pode encontrar o plugin pronto para baixar [aqui](https://github.com/BADnotice/MiningWorld/releases), ou se você quiser, pode optar por clonar o repositório e dar build no plugin com suas alterações.

Desenvolvido por eTioCooK
