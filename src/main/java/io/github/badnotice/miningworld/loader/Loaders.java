package io.github.badnotice.miningworld.loader;

import com.google.common.collect.Sets;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;

@Data(staticConstructor = "of")
public final class Loaders {

    public Set<Material> loadMaterialsBlocked(ConfigurationSection section) {
        Set<Material> result = Sets.newLinkedHashSet();

        for (String key : section.getStringList("materials-blocked")) {
            result.add(Material.matchMaterial(key));
        }

        return result;
    }

    public Set<PotionEffect> loadPotionEffects(ConfigurationSection section) {
        Set<PotionEffect> result = Sets.newLinkedHashSet();

        for (String key : section.getStringList("potion-effects")) {
            String[] split = key.split(";");
            result.add(PotionEffectType.getByName(split[0]).createEffect(99999999, Integer.parseInt(split[1])));
        }

        return result;
    }

}
