package stupidmod.client;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import stupidmod.entity.tile.TileEntityCentrifuge;

@OnlyIn(Dist.CLIENT)
public class SoundCentrifuge extends MovingSound {
    
    public TileEntityCentrifuge entity;
    
    public SoundCentrifuge(SoundEvent soundResource, TileEntityCentrifuge te) {
        super(soundResource, SoundCategory.BLOCKS);
        this.entity = te;
        this.repeat = true;

        BlockPos pos = te.getPos();

        this.x = pos.getX() + .5f;
        this.y = pos.getY() + .5f;
        this.z = pos.getZ() + .5f;
        this.volume = .01f;
    }
    
    @Override
    public void tick() {
        if (entity == null || entity.isRemoved()) {
            this.donePlaying = true;
            CentrifugeSoundManager.removeCentrifugeSound(new BlockPos((int)(this.x - 0.5f), (int)(this.y - 0.5f), (int)(this.z - 0.5f)));
            return;
        }
        
        if (entity.isSpinning() && volume < 1)
            volume += .02f;
        else if (!entity.isSpinning() && volume > 0)
            volume -= .02f;
        
        this.pitch = volume;
    }
    
    
}
