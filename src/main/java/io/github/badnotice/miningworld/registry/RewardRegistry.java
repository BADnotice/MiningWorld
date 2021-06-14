package io.github.badnotice.miningworld.registry;

import com.google.common.collect.Lists;
import io.github.badnotice.miningworld.configuration.RewardValue;
import io.github.badnotice.miningworld.model.Reward;
import io.github.badnotice.miningworld.parsers.RewardParser;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Random;

public final class RewardRegistry {

    private final List<Reward> rewards = Lists.newLinkedList();

    public void init() {
        ConfigurationSection rewardSection = RewardValue.get(RewardValue::rewards);

        for (String key : rewardSection.getKeys(false)) {
            register(RewardParser.of().reward(rewardSection.getConfigurationSection(key)));
        }
    }

    public void register(Reward reward) {
        this.rewards.add(reward);
    }

    public Reward getRewardRandom() {
        int index = new Random().nextInt(this.rewards.size());
        return this.rewards.get(index);
    }

}