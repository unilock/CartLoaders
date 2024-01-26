package cc.unilock.cartloaders.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntityMixin extends Entity {
	public AbstractMinecartEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "tick()V", at = @At("HEAD"))
	private void tick(CallbackInfo info) {
		if (this.getWorld() instanceof ServerWorld sw && this.getVelocity() != Vec3d.ZERO) {
			sw.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(this.getBlockPos()), 3, this.getBlockPos());
		}
	}
}
