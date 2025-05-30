package org.agent.hexstruction.misc;

import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class TimedBlockDisplay extends Display.BlockDisplay {
    public final long startTime;
    public final long lifeTime;

    public TimedBlockDisplay(EntityType<?> entityType, Level level, long startTime, long lifeTime) {
        super(entityType, level);
        this.startTime = startTime;
        this.lifeTime = lifeTime;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().getGameTime() - this.startTime >= this.lifeTime) {
            this.kill();
        }
    }
}
