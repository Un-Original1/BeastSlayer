package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.init.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityBSPainting extends EntityHanging implements IEntityAdditionalSpawnData {
 //BS LMAOOOO
    private static final DataParameter<Integer> ART =  EntityDataManager.createKey(EntityBSPainting.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> PERSIST= EntityDataManager.createKey(EntityBSPainting.class, DataSerializers.BOOLEAN);


    public EntityBSPainting(World worldIn) {
        super(worldIn);
    }
    public EntityBSPainting(World worldIn, BlockPos pos, EnumFacing facingIn) {
        super(worldIn, pos);
        this.updateFacingWithBoundingBox(facingIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(ART, 0);
        this.dataManager.register(PERSIST, false);
    }

    public void setPersist(boolean persist) {
        this.dataManager.set(PERSIST, persist);
    }
    public boolean getPersist() {
        return this.dataManager.get(PERSIST);
    }

    public void setArt(int art) {
        this.dataManager.set(ART, art);
        if (this.facingDirection != null) {
            this.updateFacingWithBoundingBox(this.facingDirection);
        }
    }
    public int getArt() {
        return this.dataManager.get(ART);
    }

    @Override
    public int getWidthPixels() {
        return this.getArtSize(this.getArt()).getFirst();
    }

    @Override
    public int getHeightPixels() {
        return this.getArtSize(this.getArt()).getSecond();
    }


    @Override
    public void onBroken(@Nullable Entity brokenEntity) {
        if (this.world.getGameRules().getBoolean("doEntityDrops"))
        {
            this.playSound(SoundEvents.ENTITY_PAINTING_BREAK, 1.0F, 1.0F);

            if (brokenEntity instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)brokenEntity;

                if (entityplayer.capabilities.isCreativeMode)
                {
                    return;
                }
            }
            ItemStack me = new ItemStack(ModItems.PAINTINGS);
            if (me.getTagCompound() == null) {
                me.setTagCompound(new NBTTagCompound());
            }
            if(this.getPersist()) {
                me.getTagCompound().setInteger("Variant", this.getArt());
            }
            this.entityDropItem(me, 0.0F);
        }
    }

    @Override
    public void playPlaceSound() {
        this.playSound(SoundEvents.ENTITY_PAINTING_PLACE, 1.0F, 1.0F);
    }
    public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch)
    {
        this.setPosition(x, y, z);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
        this.setPosition(blockpos.getX(), blockpos.getY(), blockpos.getZ());

    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Art", this.getArt());
        compound.setBoolean("Persist", this.getPersist());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setArt(compound.getInteger("Art"));
        this.setPersist(compound.getBoolean("Persist"));

    }

    @Override
    public void writeSpawnData(ByteBuf buf) {
        buf.writeLong(this.hangingPosition.toLong());
        buf.writeBoolean(this.facingDirection != null);
        if(this.facingDirection != null) {
            buf.writeInt(this.facingDirection.getIndex());
        }
    }

    @Override
    public void readSpawnData(ByteBuf buf) {
        this.hangingPosition = BlockPos.fromLong(buf.readLong());
        if(buf.readBoolean()) {
            this.facingDirection = EnumFacing.getFront(buf.readInt());
            this.updateFacingWithBoundingBox(this.facingDirection);
        }
    }

    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        if (!this.world.isRemote)
        {
            if (!this.getPersist())
            {
                Item glass = Item.getItemFromBlock(Blocks.GLASS_PANE);
                if (!itemstack.isEmpty() && itemstack.getItem() == glass)
                {
                    this.setPersist(true);
                    this.playSound(SoundEvents.BLOCK_GLASS_PLACE, 1.0F, 1.0F);
                    this.world.setEntityState(this, (byte) 4);
                    if (!player.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);
                    }
                }
            }
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if(id == 4) {
            spawnParticles();
        } else {
            super.handleStatusUpdate(id);
        }

    }

    public void spawnParticles(){
        if(this.world.isRemote) {
            for (int i = 0; i < 15; ++i) {
                this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + this.rand.nextGaussian() * 0.6D, this.posY + 0.5D + this.rand.nextGaussian() * 0.12999999523162842D, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public Tuple<Integer, Integer> getArtSize(int id){
        switch (id){
            // (width / height)
            case 0:
            default: //Enderman
                return new Tuple<>(16,48);
            case 1: //owl
            case 2://zealot
            case 4: //vessel
                return new Tuple<>(48,32);
            case 3: //sandy
                return new Tuple<>(32,32);
            case 5:
                return new Tuple<>(16,32);
            case 6: //succ! & lil v once I finish it
                  return new Tuple<>(64,64);
            case 7: //giant
                return new Tuple<>(32,48);
            case 8: //frostwalk
                return new Tuple<>(16,16);
            case 9: //mine
                return new Tuple<>(32,16);
        }
    }
}
