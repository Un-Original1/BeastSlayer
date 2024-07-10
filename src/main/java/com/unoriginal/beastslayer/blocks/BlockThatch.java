package com.unoriginal.beastslayer.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockThatch extends BlockRotatedPillarBase{

    public BlockThatch(String name, Material materialIn, SoundType type, float hardness, boolean isWood, int flamability) {
        super(name, materialIn, type, hardness, isWood, flamability);
    }
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        entityIn.fall(fallDistance, 0.2F);
    }
}
