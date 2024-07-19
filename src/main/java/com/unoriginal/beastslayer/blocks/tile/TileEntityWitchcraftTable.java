package com.unoriginal.beastslayer.blocks.tile;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.gui.ContainerWitchcraft;
import com.unoriginal.beastslayer.recipe.WitchcraftRecipeHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;

public class TileEntityWitchcraftTable extends TileEntityLockable implements ITickable, ISidedInventory {
    private static final int[] SLOTS_INPUT = new int[] {0,1,2,3,4};
    private static final int[] SLOT_OUTPUT = new int[] {5};
    private NonNullList<ItemStack> myItemStacks = NonNullList.withSize(6, ItemStack.EMPTY);
    private int cookTime;
    private int totalCookTime;
    private String CustomName;

    public int getSizeInventory()
    {
        return this.myItemStacks.size();
    }

    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.myItemStacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    public ItemStack getStackInSlot(int index)
    {
        return this.myItemStacks.get(index);
    }

    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.myItemStacks, index, count);
    }

    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.myItemStacks, index);
    }

    public void setInventorySlotContents(int index, ItemStack stack)
    {
        ItemStack itemstack = this.myItemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.myItemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index != 5 && !flag)
        {
            this.totalCookTime = this.getCookTime();
            this.cookTime = 0;
            this.markDirty();
        }
    }

    public String getName()
    {
        return this.hasCustomName() ? this.CustomName : "container.witchcraft";
    }

    public boolean hasCustomName()
    {
        return this.CustomName != null && !this.CustomName.isEmpty();
    }

    public void setCustomInventoryName(String p_145951_1_)
    {
        this.CustomName = p_145951_1_;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.myItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.myItemStacks);
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");

        if (compound.hasKey("CustomName", 8))
        {
            this.CustomName = compound.getString("CustomName");
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("CookTime", (short)this.cookTime);
        compound.setInteger("CookTimeTotal", (short)this.totalCookTime);

        ItemStackHelper.saveAllItems(compound, this.myItemStacks);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.CustomName);
        }

        return compound;
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public void update()
    {

        boolean flag1 = false;



        if (!this.world.isRemote)
        {
            ItemStack itemstack = this.myItemStacks.get(1);

            if (!itemstack.isEmpty() && !this.myItemStacks.get(0).isEmpty())
            {

                if (this.CanCraft())
                {
                    ++this.cookTime;

                    if (this.cookTime == this.totalCookTime)
                    {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime();
                        this.TransformItem(); //DO THE THING HERE
                        flag1 = true;
                    }
                }
                else
                {
                    this.cookTime = 0;
                }
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }

    public int getCookTime()
    {
        return 60;
    }

    private boolean CanCraft()
    {
        List<ItemStack> s = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            s.add(this.getStackInSlot(i));
        }
        if (s.size() != 5)
        {
            return false;
        }
        if(s.contains(ItemStack.EMPTY)){
            return false;
        }
        else
        {
            WitchcraftRecipeHandler handler = new WitchcraftRecipeHandler(this.world);
            ItemStack itemstack = handler.getResults(s);


            if (s.size() != 5)
            {
                return false;
            }
            if(s.contains(ItemStack.EMPTY)){
                return false;
            }
            if (itemstack.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack itemstack1 = this.myItemStacks.get(5);

                if (itemstack1.isEmpty())
                {
                    return true;
                }
                else if (!itemstack1.isItemEqual(itemstack))
                {
                    return false;
                }
                else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())
                {
                    return true;
                }
                else
                {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
                }
            }
        }
    }

    public void TransformItem()
    {
        if (this.CanCraft())
        {
            List<ItemStack> s = new ArrayList<>();
            for(int i = 0; i < 5; i++) {
                s.add(this.myItemStacks.get(i));
            }
            WitchcraftRecipeHandler handler = new WitchcraftRecipeHandler(this.world);
            ItemStack itemstack1 = handler.getResults(s);

            ItemStack itemstack2 = this.myItemStacks.get(5);

            if (itemstack2.isEmpty())
            {
                this.myItemStacks.set(5, itemstack1.copy());
            }
            else if (itemstack2.getItem() == itemstack1.getItem())
            {
                itemstack2.grow(itemstack1.getCount());
            }
            for(int i = 0; i < 5; i++) {
                this.myItemStacks.get(i).shrink(1);
            }
        }
    }

    public boolean isUsableByPlayer(EntityPlayer player)
    {
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    public void openInventory(EntityPlayer player)
    {
    }

    public void closeInventory(EntityPlayer player)
    {
    }

    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return index != 5;
    }

    public int[] getSlotsForFace(EnumFacing side)
    {
        if (side == EnumFacing.DOWN)
        {
            return SLOT_OUTPUT;
        }
        else
        {
            return SLOTS_INPUT;
        }
    }

    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
       return true;
    }

    public String getGuiID()
    {
        return BeastSlayer.MODID + ":" + "witchcraft_table";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerWitchcraft(playerInventory, this );
    }

    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.cookTime;
            case 1:
                return this.totalCookTime;
            default:
                return 0;
        }
    }



    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.cookTime = value;
                break;
            case 1:
                this.totalCookTime = value;
        }
    }

    public int getFieldCount()
    {
        return 2;
    }

    public void clear()
    {
        this.myItemStacks.clear();
    }

    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);

    @SuppressWarnings("unchecked")
    @Override
    @javax.annotation.Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing)
    {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;

        return super.getCapability(capability, facing);
    }
}
