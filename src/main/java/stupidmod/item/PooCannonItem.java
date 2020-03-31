package stupidmod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.UseAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import stupidmod.StupidModSounds;
import stupidmod.StupidMod;
import stupidmod.entity.PooExplosiveEntity;

public class PooCannonItem extends Item {
    
    public PooCannonItem(String name) {
        super(new Properties().group(StupidMod.GROUP));
    
        this.setRegistryName(name);
    }
    
    private ItemStack findAmmo(PlayerEntity player) {
        if (player.getHeldItem(Hand.OFF_HAND).getItem() instanceof PooBrickItem) {
            return player.getHeldItem(Hand.OFF_HAND);
        }
        else if (player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof PooBrickItem) {
            return player.getHeldItem(Hand.MAIN_HAND);
        } else {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);
                
                if (itemstack.getItem() instanceof PooBrickItem) {
                    return itemstack;
                }
            }
            
            return ItemStack.EMPTY;
        }
    }
    
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity m, int timeLeft) {
        PlayerEntity player = (PlayerEntity)m;
        ItemStack ammoStack = null;
        
        if (!player.isCreative()) {
            ammoStack = this.findAmmo(player);
            if (ammoStack.isEmpty()) return;
        }
        
        player.playSound(StupidModSounds.POO_CANNON, 1, 1);
        if (!world.isRemote) {
            if(!player.isCreative())
                ammoStack.setCount(ammoStack.getCount() - 1);
            
            
            PooExplosiveEntity tst = new PooExplosiveEntity(world, 0, 0, 0, 4);
            tst.setPosition(MathHelper.cos(tst.rotationYaw / 180.0F * (float)Math.PI) * 0.16F, 0.1, MathHelper.sin(tst.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
            tst.setLocationAndAngles(player.posX,player.posY+player.getEyeHeight(),player.posZ, player.rotationYaw, player.rotationPitch);
            tst.setPosition(tst.posX, tst.posY, tst.posZ);
            double motx = -MathHelper.sin(tst.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(tst.rotationPitch / 180.0F * (float)Math.PI);
            double motz = MathHelper.cos(tst.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(tst.rotationPitch / 180.0F * (float)Math.PI);
            double moty = -MathHelper.sin(tst.rotationPitch / 180.0F * (float)Math.PI);
            tst.setMotion(motx * 2, moty * 2, motz * 2);
            world.addEntity(tst);
        }
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
        ActionResult<ItemStack> event = net.minecraftforge.event.ForgeEventFactory.onArrowNock(player.getHeldItem(hand), world, player, hand, true);
        if (event != null) return event;
        
        player.setActiveHand(hand);
        return new ActionResult<ItemStack>(ActionResultType.PASS, player.getHeldItem(hand));
    }
    
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
    
    @Override
    public int getUseDuration(ItemStack stack) {
        return 10000000;
    }
    
}