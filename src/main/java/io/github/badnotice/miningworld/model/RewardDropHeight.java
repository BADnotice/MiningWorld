package io.github.badnotice.miningworld.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public final class RewardDropHeight {

    private final int max;
    private final int min;

}
