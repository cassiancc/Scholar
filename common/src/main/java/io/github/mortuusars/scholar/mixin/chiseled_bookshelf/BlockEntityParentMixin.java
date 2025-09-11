package io.github.mortuusars.scholar.mixin.chiseled_bookshelf;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.mortuusars.scholar.Config;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This class is used so child classes {@link ChiseledBookShelfBlockEntityMixin}
 * could modify methods in a (hopefully) more compatible with other mods way.
 */
@Mixin(BlockEntity.class)
public class BlockEntityParentMixin {
    @Shadow @Nullable protected Level level;

    @ModifyReturnValue(method = "getUpdatePacket", at = @At("RETURN"))
    protected Packet<ClientGamePacketListener> onGetUpdatePacket(Packet<ClientGamePacketListener> original) {
        var be = (BlockEntity) (Object) this;
        if (be instanceof ChiseledBookShelfBlockEntity) {
            return Config.Common.CHISELED_BOOKSHELF_TOOLTIP.get() || Config.Common.CHISELED_BOOKSHELF_COLORS.get()
                    ? ClientboundBlockEntityDataPacket.create(be)
                    : original;
        }
        return original;
    }

    @Inject(method = "saveAdditional", at = @At("RETURN"))
    protected void onGetUpdateTag(ValueOutput output, CallbackInfo ci) {
        var be = (BlockEntity) (Object) this;
        if (be instanceof ChiseledBookShelfBlockEntity chiseledBookShelfBlockEntity) {
            ContainerHelper.saveAllItems(output, ((ChiseledBookShelfBlockEntityMixin) chiseledBookShelfBlockEntity).getItems());
        }
    }
}
