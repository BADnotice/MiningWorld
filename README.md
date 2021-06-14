# mining-world
mining-world é um plugin de mineração simples e de fácil configuração para servidores de factions.

## :pushpin: Features
  - Configuração flexível.
  - Codigo limpo e leve.
  - Permitido criar quantas recompensas quiser.

## :heavy_minus_sign: Comandos
<table border="1" style="border-collapse: collapse">
<tr>
<th>Comando</th> <th>Descrição</th> <th>Permissão</th>
</tr>
<tr>
<td>/mina</td> <td>Responsavel por ir até o mundo de mineração</td> <td>nenhuma</td>
</tr>
<tr>
<td>/mina criarmundo</td> <td>Responsavel por gerar o mundo de mineração</td> <td>miningworld.command.createworld</td>
</tr>
</table>

## :link: Links
  - [config.yml](https://github.com/BADnotice/MiningWorld/blob/master/src/main/resources/config.yml) - clique para acessar o arquivo de configuração.
  - [rewards.yml](https://github.com/BADnotice/MiningWorld/blob/master/src/main/resources/rewards.yml) - clique para acessar o arquivo de recompensas.

## :tada: Tecnologias utilizadas
O Projeto foi desenvolvido utilizando as seguintes tecnologias.

- [lombok](https://projectlombok.org/) - Gera getters, setters e outros métodos útils durante a compilação por meio de anotações.
- [pdm](https://github.com/knightzmc/pdm) - baixa as dependências de desenvolvimento assim que o plugin é ligado pela primeira vez.

### :tada: APIs e Frameworks

 - [command-framework](https://github.com/SaiintBrisson/command-framework) - framework para criação e gerenciamento de comandos.
 - [configuration-inject](https://github.com/HenryFabio/configuration-injector) - injetar valores de configurações automaticamente.
 
## :inbox_tray: Download
Você pode baixar o plugin clicando [AQUI](https://github.com/BADnotice/MiningWorld/releases) ou se preferir alterar, pode clonar o repositório.

#### :fire: Events

- <b>MiningRewardSpawnEvent</b> - Chamado quando o jogador recebe uma reward. 
- <b>MiningJoinWorldEvent</b> - Chamado quando o jogador entra no mundo de mineração. 
- <b>MiningLeaveWorldEvent</b> - Chamado quando o jogador deixa o mundo de mineração. 