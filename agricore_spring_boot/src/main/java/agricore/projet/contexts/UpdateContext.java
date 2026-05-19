package agricore.projet.contexts;

import java.time.Duration;
import java.time.LocalDateTime;

public class UpdateContext {

    private final LocalDateTime lastUpdate;
    private final LocalDateTime now;
    private final Duration stepDuration;

    private LocalDateTime currentStepStart;
    private LocalDateTime currentStepEnd;
    private int stepIndex;

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
}