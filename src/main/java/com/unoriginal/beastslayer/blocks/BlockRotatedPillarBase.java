package com.unoriginal.beastslayer.blocks;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockRotatedPillarBase extends BlockRotatedPillar {
    public boolean isWood;
    public int flamability;
    public BlockRotatedPillarBase(String name, Material materialIn, SoundType type, float hardness, boolean isWood, int flamability) {
        super(materialIn);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setSoundType(type);
        this.setHardness(hardness);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
        this.setCreativeTab(BeastSlayer.BEASTSTAB);
        this.isWood = isWood;
        this.flamability = flamability;
    }

    @Override public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos){ return isWood; }

    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return flamability;
    }
}
