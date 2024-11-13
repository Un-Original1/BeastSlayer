package com.unoriginal.beastslayer.items;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityGiant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import java.util.ArrayList;
import java.util.List;

public class ItemToughGlove extends ItemAbstractMultimodel {
    private final float attackDamage;
    public ItemToughGlove(String name) {
        super(name);
        this.setMaxDamage(128);
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.attackDamage = 5.0F;
    }
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        World world = attacker.getEntityWorld();
        double d0 = target.posX - attacker.posX;
        double d1 = target.posZ - attacker.posZ;
        stack.damageItem(1, attacker);
        if(attacker instanceof EntityPlayer){
            EntityPlayer p = (EntityPlayer) attacker;
            if(!p.getCooldownTracker().hasCooldown(this)){
                if(!(target instanceof EntityGiant) && !world.isRemote) {
                    target.addVelocity(d0, 0.2, d1);
                    p.getCooldownTracker().setCooldown(this, 40);
                }
            }
        }
        return true;
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(2, entityLiving);
        }

        return true;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, stack);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));

        }

        return multimap;
    }


    @Override
    public void registerModels() {

        List<ModelResourceLocation> mrls = new ArrayList<>();
        ImmutableList<ModelResourceLocation> MList = ImmutableList.of(new ModelResourceLocation("ancientbeasts:tough_glove", "inventory"),
        new ModelResourceLocation("ancientbeasts:tough_glove_model", "inventory"));
        mrls.addAll(MList);

        ModelBakery.registerItemVariants(this, mrls.toArray(new ModelResourceLocation[0]));

        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(BeastSlayer.MODID,"tough_glove_dummy"), "inventory")); //d stands for dummy

    }
}
