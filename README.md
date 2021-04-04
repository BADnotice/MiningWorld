# MiningWorld
Um simples sistema de <b>mineração</b> para servidores de factions.

## Comandos
<table border="1" style="border-collapse: collapse">
<tr>
<th>Comando</th> <th>Descrição</th> <th>Permissão</th>
</tr>
<tr>
<td>/mina</td> <td>ir até o mundo de mineração</td> <td>`nenhuma`</td>
</tr>
<tr>
<td>/mina criarmundo</td> <td>gerar o mundo de mineração</td> <td>`miningworld.admin`</td>
</tr>
</table>

## Configuração
```yaml

# Olá, para lhe ajudar a configurar o Plugin corretamente, lista de efeitos e materiais abaixo!
# https://helpch.at/docs/1.8/index.html?org/bukkit/PotionEffectType.html
# https://helpch.at/docs/1.8/org/bukkit/Material.html

delay-command: 25 #Intervalo de tempo para digitar novamente o comando

world:
  name: "mina" #Nome do mundo que vai ser criado
  seed: 0 #Defina a seed do mundo ou deixe 0 para gerar aleatoriamente
  radius-teleport: 150 #Teleporta para uma coordenada aleatoria entre 0 e 150
  experience: 1 #Quantidade de experiencia que será dado ao jogador
  effects:
    - 'FAST_DIGGING;1' #Defina o efeito da seguinte forma [Nome do efeito;nivel]
  materials-blocked: 
    - "STONE" #Defina o material que não irá ser dropando no mundo de mineração

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
    percentage: 20.1 #Porcentagem do item que irá ser dropado
    height:
      max: 5 #Altura máxima aonde ele dropará
      min: 1 #Altura minima aonde ele dropará
    material:
      id: 266 #Id do material que será dropado
      data: 0 #Data do material que será dropado
  2:
    percentage: 40.1
    height:
      max: 16
      min: 11
    material:
      id: 266
      data: 0
```

## Tecnologias utilizadas
O Projeto foi desenvolvido utilizando as seguintes tecnologias.

- [Google Guice](https://github.com/google/guice) - fornece suporte para injeção de dependência usando anotações.
- [command-framework]() - framework para criação e gerenciamento de comandos.
- [Pdm](https://github.com/knightzmc/pdm) - Faz o download de dependências de desenvolvimento durante o carregamento do servidor.

## Download
Você pode encontrar o plugin pronto para baixar [aqui](https://github.com/BADnotice/MiningWorld/releases), ou se você quiser, pode optar por clonar o repositório e dar build no plugin com suas alterações.

## Events
- <b>MiningDropSpawnEvent</b> - Chamado quando o jogador recebe o drop.
- <b>MiningJoinWorldEvent</b> - Chamado quando o jogador entra no mundo de mineração.
- <b>MiningLeaveWorldEvent</b> - Chamado quando o jogador deixa o mundo de mineração.
```java
  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onMiningDrop(MiningDropSpawnEvent event) {
        Player player = event.getPlayer();
        Drop drop = event.getDrop();

        player.sendMessage(new String[]{
                "",
                "§eItem: §l" + drop.getItemStack().getType().name(),
                "§ePercent: §l" + drop.getPercentage(),
                "§eXP: §l" + event.getExperience(),
                "§eAmount: §l" + event.getAmount(),
                ""
        });
    }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onMiningDrop(MiningJoinWorldEvent event) {
        Player player = event.getPlayer();
        WorldController controller = event.getController();

        player.sendMessage(new String[]{
                "",
                "§eVocê entrou no mundo de mineração",
                "§ee recebeu §7'" + event.getController().getEffects().size() + "' §eefeitos."
                ""
        });

        player.sendMessage("Lista de materias que não droparam aqui.");
        player.sendMessage("");
        for (Material material : controller.getMaterials()) {
            player.sendMessage("§e" + material.name());
        }
        player.sendMessage("");
    }

 @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onMiningDrop(MiningLeaveWorldEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("§eTodos efeitos do mundo de mineração foram rmeovidos.");
    }
```