package agricore.projet.contexts;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UpdateContext {

    private final LocalDateTime lastUpdate;
    private final LocalDateTime now;
    private final Duration stepDuration;

    private LocalDateTime currentStepStart;
    private LocalDateTime currentStepEnd;
    private int stepIndex;

    private final Set<Integer> disabledFluxIdsForCurrentUpdate = new HashSet<>();
    private final List<String> warnings = new ArrayList<>();

    public UpdateContext(
            LocalDateTime lastUpdate,
            LocalDateTime now,
            Duration stepDuration
    ) {
        this.lastUpdate = lastUpdate;
        this.now = now;
        this.stepDuration = stepDuration;
        this.currentStepStart = lastUpdate;
        this.currentStepEnd = lastUpdate.plus(stepDuration);
        this.stepIndex = 0;
    }

    public LocalDateTime lastUpdate() {
        return lastUpdate;
    }

    public LocalDateTime now() {
        return now;
    }

    public Duration stepDuration() {
        return stepDuration;
    }

    public LocalDateTime currentStepStart() {
        return currentStepStart;
    }

    public LocalDateTime currentStepEnd() {
        return currentStepEnd;
    }

    public int stepIndex() {
        return stepIndex;
    }

    public void nextStep() {
        this.currentStepStart = this.currentStepEnd;
        this.currentStepEnd = this.currentStepEnd.plus(stepDuration);
        this.stepIndex++;
    }

    public boolean isFluxDisabledForCurrentUpdate(Integer fluxId) {
        return disabledFluxIdsForCurrentUpdate.contains(fluxId);
    }

    public void disableFluxForCurrentUpdate(Integer fluxId, String reason) {
        disabledFluxIdsForCurrentUpdate.add(fluxId);
        warnings.add(reason);
    }

    public List<String> warnings() {
        return warnings;
    }

    public boolean canRunNextStep() {
        return !currentStepEnd.isAfter(now);
    }
}