package badnotice.miningworld.api.model.position;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public final class HeightPosition {

    private final int max;
    private final int min;

}
