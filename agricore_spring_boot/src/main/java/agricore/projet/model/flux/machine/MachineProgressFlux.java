package agricore.projet.model.flux.machine;

import agricore.projet.model.flux.SimulationStrategy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.Duration;

@Entity
public abstract class MachineProgressFlux extends MachineFlux {

    private static final long PROGRESS_SCALE = 1000L;

    @Column(nullable = true)
    private Double baseTransformationsPerMinute = 1.0;

    @Column(nullable = true)
    private Double speedMultiplier = 1.0;

    @Column(nullable = true)
    private Long progressUnits = 0L;

    protected MachineProgressFlux() {
    }

    protected MachineProgressFlux(
            SimulationStrategy simulationStrategy,
            int priority,
            boolean active,
            double baseTransformationsPerMinute
    ) {
        super(simulationStrategy, priority, active);
        this.baseTransformationsPerMinute = baseTransformationsPerMinute;
        this.speedMultiplier = 1.0;
        this.progressUnits = 0L;
    }

    protected void addProgress(Duration duration) {
        if (duration == null || duration.isZero() || duration.isNegative()) {
            return;
        }

        double minutes = duration.toSeconds() / 60.0;

        long gainedProgress = Math.round(
                effectiveTransformationsPerMinute() * minutes * PROGRESS_SCALE
        );

        progressUnits = safeProgressUnits() + gainedProgress;
    }

    protected int completedTransformations() {
        return Math.toIntExact(safeProgressUnits() / PROGRESS_SCALE);
    }

    protected void consumeTransformations(int transformations) {
        if (transformations <= 0) {
            return;
        }

        progressUnits = safeProgressUnits() - transformations * PROGRESS_SCALE;

        if (progressUnits < 0) {
            progressUnits = 0L;
        }
    }

    protected double effectiveTransformationsPerMinute() {
        return safeBaseTransformationsPerMinute() * safeSpeedMultiplier();
    }

    public void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    protected void resetProgress() {
        this.progressUnits = 0L;
    }

    private double safeBaseTransformationsPerMinute() {
        return baseTransformationsPerMinute == null ? 1.0 : baseTransformationsPerMinute;
    }

    private double safeSpeedMultiplier() {
        return speedMultiplier == null ? 1.0 : speedMultiplier;
    }

    private long safeProgressUnits() {
        return progressUnits == null ? 0L : progressUnits;
    }

    public Double getBaseTransformationsPerMinute() {
        return baseTransformationsPerMinute;
    }

    public void setBaseTransformationsPerMinute(Double baseTransformationsPerMinute) {
        this.baseTransformationsPerMinute = baseTransformationsPerMinute;
    }

    public Double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public Long getProgressUnits() {
        return progressUnits;
    }
}