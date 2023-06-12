package com.unoriginal.beastslayer.gui;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.init.ModItems;
import com.unoriginal.beastslayer.init.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiWiki extends GuiScreen {
    protected static final int X = 276;
    protected static final int Y = 180;
    private int currPage;
    private final int bookTotalPages = 18;
    private int cachedPage = -1;
    private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation("ancientbeasts:textures/bestiary/book_base.png");
    private static final ResourceLocation PAGE_GUI_TEXTURES = new ResourceLocation("ancientbeasts:textures/bestiary/pages.png");
    private static final ResourceLocation PORTRAITS = new ResourceLocation("ancientbeasts:textures/bestiary/entity_pictures.png");
    private static final ResourceLocation BIOMES = new ResourceLocation("ancientbeasts:textures/bestiary/biomes.png");
    private static final ResourceLocation OTHER = new ResourceLocation("ancientbeasts:textures/bestiary/other_drawings.png");
    private static final ResourceLocation ICONS = new ResourceLocation("ancientbeasts:textures/bestiary/mob_icons.png");
    private static final ResourceLocation CRAFTING = new ResourceLocation("ancientbeasts:textures/bestiary/craftables.png");
    protected ItemStack book;
    private GuiButton buttonDone;
    private NextPageButton buttonNextPage;
    private NextPageButton buttonPreviousPage;
    private CreaturePageButton pageButton1;
    private CreaturePageButton pageButton2;
    private CreaturePageButton pageButton3;
    public static GuiWiki self;

    private HintButton hintButton;
    private float time = 0F;

    public GuiWiki(ItemStack book) {
        this.book = book;
        self = this;
    }
    /** -- is moving to left ++ is moving smth to the right side of the screen **/
    public void initGui()
    {
        super.initGui();
        this.buttonDone = this.addButton(new GuiButton(0, (this.width ) / 2 - 49, 196, 98, 20, I18n.format("gui.done")));
        int i = (this.width - X) / 2;
        this.buttonNextPage = this.addButton(new NextPageButton(1, i + 240, 154, true));
        this.buttonPreviousPage = this.addButton(new NextPageButton(2, i + 12, 154, false));
        //not universalize the variable as of now because it just wont work, due to the fact that bestiary is meant to have 60 mobs while mod only offers 12 is a big downside as of now to add a universalized index registry
      /*  for(int x = 0; x < bookTotalPages - 3; x++) {
            this.pageButton = this.addButton(new CreaturePageButton(3 + x, i + (x * 10), 10 + (x * 20), x, x + 3, 0));
        }*/
        this.pageButton1 = this.addButton(new CreaturePageButton(3, 76 + i - 43, 65 + ((-1) * 21), 0, 3, 0));

        for(int x = 0; x < 7; x++) {
            boolean xflag = x % 2 == 0;
            int xpos = xflag ?  43 : 0;
                this.pageButton1 = this.addButton(new CreaturePageButton(4 + x, 207 + i - xpos, 65 + (((x / 2) - 1) * 21), x + 1, x + 4, 0));
        }


        for(int x = 0; x < 4; x++) {
            boolean pageflag = x % 2 == 0;
            boolean xflag = x % 2 == 0;

           // int extrapos = pageflag ? 76 : 164; //should invert flag if the previous page reaches an even number and reverted if odd
          //  this.pageButton2 = this.addButton(new CreaturePageButton(9 + x, extrapos + i - xpos, 65 + (((x / 2) - 1) * 21), x + 6, x + 9, 1));
            if(x < 1 ) {
                int xpos = xflag ?  43 : 0;
                this.pageButton2 = this.addButton(new CreaturePageButton(11 + x, 76 + i - xpos, 65 + (((x / 3) - 1) * 21), x + 8, x + 11, 1));
            }
            else {
                int xpos = xflag ?  0 : 43;
                this.pageButton2 = this.addButton(new CreaturePageButton(11 + x, 207 + i - xpos, 65 + ((((x - 1) / 2) - 1) * 21), x + 8, x + 11, 1));
            }
        }
        for(int x = 0; x < 3; x++) {
            boolean xflag = x % 2 == 0;
            int xpos = xflag ? 43 : 0;
            if (x < 2) {
                this.pageButton3 = this.addButton(new CreaturePageButton(15 + x, 76 + i - xpos, 65 + (((x / 3) - 1) * 21), x + 12, x + 15, 2));
            } else {
                this.pageButton3 = this.addButton(new CreaturePageButton(15 + x, 207 + i - xpos, 65 + (((x / 3) - 1) * 21), x + 12, x + 15, 2));
            }
        }
        for(int y = 0; y < 46; y++) {
          //0, ..., 14

             for (int x = 0; x < 3; x++) { //0, 1, 2
                 this.hintButton = this.addButton(new HintButton((x+70) + (y+1), (this.width - 208 + (54 * x)) / 2, 129, y, x, y + 3)); //hint = 61 and above
                 //154 100
             }
         }
     /*   }*/
        this.updateButtons();
    }

    protected void drawGuiContainerForegroundLayer(int x, int y) {

        for (GuiButton guibutton : this.buttonList) {
            if (guibutton.isMouseOver()) {
                guibutton.drawButtonForegroundLayer(x - this.width, y - this.height);
                break;
            }
        }
    }

    public void drawScreen(int mouse_x, int mouse_y, float p_ticks){
        this.drawDefaultBackground();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(BOOK_GUI_TEXTURES);
        int cornerX = (this.width - X) / 2;
        int cornerY = (this.height - Y) / 2;
        drawModalRectWithCustomSizedTexture((this.width - X) / 2, 2, 0, 0, 276, 180,  288, 224);
        this.drawPages(this.currPage);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();

        if (this.cachedPage != this.currPage) {
            //this.cachedComponents = Lists.newArrayList(textcomponentstring);
            this.cachedPage = this.currPage;
        }
        super.drawScreen(mouse_x, mouse_y, p_ticks);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(cornerX, cornerY, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        short short1 = 240;
        short short2 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, short1, short2);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        this.drawGuiContainerForegroundLayer(mouse_x, mouse_y);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();

    }

    private void updateButtons() {
        this.buttonNextPage.visible = this.currPage < this.bookTotalPages - 1;
        this.buttonPreviousPage.visible = this.currPage > 0;
        for (GuiButton button : this.buttonList) {
            if (button.id >= 3 && button.id < 11) {
                button.enabled = this.currPage == this.pageButton1.getRenderOrder();
                button.visible = this.currPage == this.pageButton1.getRenderOrder();
            } else if (button.id >= 11 && button.id < 15) {
                button.enabled = this.currPage == this.pageButton2.getRenderOrder();
                button.visible = this.currPage == this.pageButton2.getRenderOrder();
            } else if (button.id >= 15) {
                button.enabled = this.currPage == this.pageButton3.getRenderOrder();
                button.visible = this.currPage == this.pageButton3.getRenderOrder();
            }

            if(button instanceof HintButton){
                HintButton hintButton = (HintButton) button;
                button.visible = this.currPage == hintButton.getRenderOrder();
                button.enabled = this.currPage == hintButton.getRenderOrder();
            }

        }
    }

    protected void actionPerformed(GuiButton button)
    {
        if (button.enabled)
        {
            if (button.id == 0)
            {
                this.mc.displayGuiScreen(null);
            }
            else if (button.id == 1)
            {
                if (this.currPage < this.bookTotalPages - 1)
                {
                    ++this.currPage;
                }
            }
            else if (button.id == 2)
            {
                if (this.currPage > 0)
                {
                    --this.currPage;
                }
            }
            else if (button.id >= 3 && button.id < 70){
                CreaturePageButton button1 = (CreaturePageButton)button;
                this.currPage = button1.getPage();
            }
            this.updateButtons();
        }
    }

    private void drawPages (int pages){
        switch (pages){
            case 0:
            default:
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 238, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 238, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(BOOK_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 160) / 2, 18, 200, 196, 26, 12, 288, 224);
                drawModalRectWithCustomSizedTexture((this.width + 102) / 2, 18, 200, 184, 26, 12, 288, 224);
                GlStateManager.popMatrix();
                break;
            case 1:
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 238, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 238, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(BOOK_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 160) / 2, 18, 172, 196, 26, 12, 288, 224);
                drawModalRectWithCustomSizedTexture((this.width + 102) / 2, 18, 172, 184, 26, 12, 288, 224);
                GlStateManager.popMatrix();
                break;
            case 2:
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 238, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 238, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(BOOK_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 160) / 2, 18, 228, 184, 26, 12, 288, 224);
                drawModalRectWithCustomSizedTexture((this.width + 102) / 2, 18, 228, 196, 26, 12, 288, 224);
                GlStateManager.popMatrix();
                break;
            case 3: //r. Enderman
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 357, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 1, 232, 57, 58, 480, 480);
                this.drawDifficulty(2);
                this.drawElement(4);
                this.drawBiome(3);
               // this.drawEvenMoreStuff(12);
                this.drawItemType(2, 56);
                this.drawItemStack(new ItemStack(ModItems.RIFTED_PEARL), (this.width + 58 * 2) / 2,54);
                ItemStack potionRift = new ItemStack(Items.POTIONITEM);
                PotionUtils.addPotionToItemStack(potionRift, ModPotions.rifted); //why I typed poison instead of potion?, still a mystery
                this.drawItemStack(potionRift, (this.width + 85 * 2) / 2 ,99);
                ItemStack potionRift1 = new ItemStack(Items.POTIONITEM);
                PotionUtils.addPotionToItemStack(potionRift1, PotionTypes.AWKWARD);
                this.drawItemStack(potionRift1, (this.width + 57 * 2) / 2 ,126);
                this.drawItemStack(new ItemStack(ModItems.RIFTED_PEARL), (this.width + 30 * 2) / 2 ,99);
                this.drawText(1, 5, 40, "entity.Rifted_Enderman.name");
                GlStateManager.popMatrix();
                break;
            case 4: //zealot 1
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 119, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 59, 0, 57, 58, 480, 480);
                this.drawDifficulty(2);
                this.drawElement(1);
                this.drawBiome(1);
               // this.drawEvenMoreStuff(1);
                this.drawItemType(2, 0);
                this.drawItemType(2, 56);
                this.drawItemType(2, 114);
                this.drawItemStack(new ItemStack(ModItems.SHIELD_BOOK), (this.width + 30 * 2) / 2,54);
                this.drawItemStack(new ItemStack(Items.BONE), (this.width + 58 * 2) / 2 ,54);
                this.drawItemStack(new ItemStack(Items.EMERALD), (this.width + 86 * 2)/ 2 ,54);
                this.drawText(1, 0, 24, "entity.Zealot.name");
                GlStateManager.popMatrix();
                break;
            case 5: //ghost 2
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 357, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 117, 0, 57, 58, 480, 480);
                this.drawDifficulty(2);
                this.drawElement(1);
                this.drawBiome(1);
              //  this.drawEvenMoreStuff(2);
                this.drawItemType(2, 56);
                this.drawItemStack(new ItemStack(ModItems.ECTOPLASM), (this.width + 58 * 2) / 2 ,54);
                ItemStack potionIn1 = new ItemStack(Items.POTIONITEM);
                PotionUtils.addPotionToItemStack(potionIn1, ModPotions.ghostly); //why I typed poison instead of potion?, still a mystery
                this.drawItemStack(potionIn1, (this.width + 85 * 2)/ 2 ,99);
                ItemStack potionIn = new ItemStack(Items.POTIONITEM);
                PotionUtils.addPotionToItemStack(potionIn, PotionTypes.AWKWARD);
                this.drawItemStack(potionIn, (this.width + 57 * 2) / 2 ,126);
                this.drawItemStack(new ItemStack(ModItems.ECTOPLASM), (this.width + 30 * 2) / 2 ,99);
                this.drawText(1, 4, 28, "entity.Ghost.name");
                GlStateManager.popMatrix();
                break;
            case 6: //giant 3
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 119, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 1, 58, 57, 58, 480, 480);
                this.drawDifficulty(3);
                this.drawElement(1);
                this.drawBiome(3);
                //this.drawEvenMoreStuff(3);
                this.drawItemType(2, 0);
                this.drawItemType(2, 56);
                this.drawItemType(2, 114);
                this.drawItemStack(new ItemStack(ModItems.TOUGH_GLOVE), (this.width + 30 * 2) / 2,54);
                this.drawItemStack(new ItemStack(Items.ROTTEN_FLESH), (this.width + 58 * 2) / 2,54);
                this.drawItemStack(new ItemStack(Items.LEATHER), (this.width + 86 * 2)/ 2 ,54);
                this.drawText(1, 15, 180 + (int) BeastSlayerConfig.GiantHealthBonus, "entity.Giant_Zombie.name");
                GlStateManager.popMatrix();
                break;
            case 7: //vessel 4
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 119, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 1, 116, 57, 58, 480, 480);
                this.drawDifficulty(3);
                this.drawElement(1);
                this.drawBiome(1);
                //this.drawEvenMoreStuff(6);
                this.drawItemType(2, 0);
                this.drawItemType(2, 56);
                this.drawItemType(2, 114);
                this.drawItemStack(new ItemStack(ModItems.VESSEL), (this.width + 30 * 2) / 2,54);
                this.drawItemStack(new ItemStack(Items.STRING), (this.width + 58 * 2) / 2,54);
                this.drawItemStack(new ItemStack(ModItems.ECTOPLASM), (this.width + 86 * 2) / 2,54);
                this.drawText(1, 6, 60 + (int) BeastSlayerConfig.VesselHealthBonus, "entity.Vessel.name");
                GlStateManager.popMatrix();
                break;
            case 8: //damcell 5
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 119, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 117, 116, 57, 58, 480, 480);
                this.drawDifficulty(2);
                this.drawElement(1);
                this.drawBiome(4);
             //   this.drawEvenMoreStuff(8);
                this.drawItemType(2, 0);
                this.drawItemType(2, 56);
                this.drawItemType(2, 114);
                this.drawRecipe(0);
                this.drawItemStack(new ItemStack(ModItems.SPIKE), (this.width + 30 * 2)/ 2 ,54);
                this.drawItemStack(new ItemStack(Items.IRON_INGOT), (this.width + 58 * 2) / 2 ,54);
                this.drawItemStack(new ItemStack(Items.IRON_INGOT), (this.width + 86 * 2)/ 2 ,54);
                this.drawText(1, 6, 40, "entity.Damcell.name");
                GlStateManager.popMatrix();
                break;
            case 9: //Nekros
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 357, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 59, 232, 57, 58, 480, 480);
                this.drawDifficulty(2);
                this.drawElement(1);
                this.drawBiome(1);
              //  this.drawEvenMoreStuff(13);
                this.drawItemType(2, 56);
                this.drawItemStack(new ItemStack(ModItems.DARK_GOOP), (this.width + 58 * 2) / 2 ,54);
                ItemStack potionIn2 = new ItemStack(Items.POTIONITEM);
                PotionUtils.addPotionToItemStack(potionIn2, ModPotions.undead); //why I typed poison instead of potion?, still a mystery
                this.drawItemStack(potionIn2, (this.width + 85 * 2)/ 2 ,99);
                ItemStack potionIn3 = new ItemStack(Items.POTIONITEM);
                PotionUtils.addPotionToItemStack(potionIn3, PotionTypes.AWKWARD);
                this.drawItemStack(potionIn3, (this.width + 57 * 2) / 2 ,126);
                this.drawItemStack(new ItemStack(ModItems.DARK_GOOP), (this.width + 30 * 2) / 2 ,99);
                this.drawText(1, 5, 12, "entity.Nekros.name");
                GlStateManager.popMatrix();
                break;
            case 10: //Bonepile
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 119, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 117, 232, 57, 58, 480, 480);
                this.drawDifficulty(2);
                this.drawElement(1);
                this.drawBiome(3);
               // this.drawEvenMoreStuff(14);
                this.drawItemType(2, 0);
                this.drawItemType(2, 56);
                this.drawItemType(2, 114);
                this.drawItemStack(new ItemStack(ModItems.KUNAI), (this.width + 30 * 2)/ 2 ,54);
                this.drawItemStack(new ItemStack(Items.BONE), (this.width + 58 * 2) / 2 ,54);
                this.drawItemStack(new ItemStack(Items.BONE), (this.width + 86 * 2)/ 2 ,54);
                this.drawText(1, 6, 32, "entity.Bonepile.name");
                GlStateManager.popMatrix();
                break;
            case 11: //lilv 7
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 119, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 59, 116, 57, 58, 480, 480);
                this.drawDifficulty(0);
                this.drawElement(3);
                this.drawBiome(1);
                //this.drawEvenMoreStuff(7);
                this.drawItemType(0, 0);
                this.drawItemType(1, 56);
                this.drawItemType(1, 114);
                this.drawItemStack(new ItemStack(ModItems.ECTOPLASM), (this.width + 30 * 2) / 2 ,54);
                this.drawItemStack(new ItemStack(Items.GUNPOWDER), (this.width + 58 * 2) / 2 ,54);
                this.drawItemStack(new ItemStack(Items.SUGAR), (this.width + 86 * 2) / 2 ,54);
                this.drawText(1, 1, 18, "entity.Lil_Vessel.name");
                GlStateManager.popMatrix();
                break;
            case 12: //sandmonster 8
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 119, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 1, 0, 57, 58, 480, 480);
                this.drawDifficulty(3);
                this.drawElement(0);
                this.drawBiome(0);
                //this.drawEvenMoreStuff(0);
                this.drawItemType(0, 0);
                this.drawItemType(0, 56);
                this.drawItemType(2, 114);
                this.drawRecipe(1);
                this.drawItemStack(new ItemStack(Items.BEEF, BeastSlayerConfig.sandmonsterTameStackSize), (this.width + 30 * 2) / 2,54);
                this.drawItemStack(new ItemStack(Items.RABBIT, BeastSlayerConfig.sandmonsterTameStackSize), (this.width + 58 * 2) / 2 ,54);
                this.drawItemStack(new ItemStack(ModItems.SANDMONSTER_SCALE), (this.width + 86 * 2)/ 2 ,54);
                this.drawText(1, 6, 120 + (int) BeastSlayerConfig.SandyHealthBonus, "entity.SandMonster.name");
                GlStateManager.popMatrix();

                break;
            case 13: //owlstack!
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 119, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 117, 174, 57, 58, 480, 480);
                this.drawDifficulty(0);
                this.drawElement(0);
                this.drawBiome(6);
              //  this.drawEvenMoreStuff(11);
                this.drawItemType(1, 0);
                this.drawItemType(2, 56);
                this.drawItemType(2, 114);
                this.drawItemStack(new ItemStack(Items.STICK), (this.width + 30 * 2) / 2,54);
                this.drawItemStack(new ItemStack(Items.STICK), (this.width + 58 * 2) / 2 ,54);
                this.drawItemStack(new ItemStack(Items.FEATHER), (this.width + 86 * 2) / 2,54);
                this.drawText(1, 1, 8, "entity.Owlstack.name");
                GlStateManager.popMatrix();
                break;
            case 14: //bouldering zombie 6
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 119, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 59, 174, 57, 58, 480, 480);
                this.drawDifficulty(2);
                this.drawElement(0);
                this.drawBiome(4);
                //this.drawEvenMoreStuff(10);
                this.drawItemType(2, 0);
                this.drawItemType(2, 56);
                this.drawItemType(2, 114);
                this.drawItemStack(new ItemStack(Items.ROTTEN_FLESH), (this.width + 30 * 2)/ 2 ,54);
                this.drawItemStack(new ItemStack(Items.POTATO), (this.width + 58 * 2) / 2,54);
                this.drawItemStack(new ItemStack(ModItems.MINER_HELMET), (this.width + 86 * 2) / 2,54);
                this.drawText(1, 3, 20, "entity.Boulderer.name");
                GlStateManager.popMatrix();
                break;
            case 15: //frostash 9
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 119, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 59, 58, 57, 58, 480, 480);
                this.drawDifficulty(0);
                this.drawElement(2);
                this.drawBiome(2);
                //this.drawEvenMoreStuff(4);
                this.drawItemType(0, 0);
                this.drawItemType(1, 56);
                this.drawItemType(1, 114);
                this.drawItemStack(new ItemStack(Items.RABBIT), (this.width + 30 * 2)/ 2 ,54);
                this.drawItemStack(new ItemStack(ModItems.ICE_DART, 1, 0), (this.width + 58 * 2) / 2 ,54);
                this.drawItemStack(new ItemStack(ModItems.ICE_DART, 1, 1), (this.width + 86 * 2)/ 2 ,54);
                this.drawText(1, 2, 14, "entity.Frostash_fox.name");
                GlStateManager.popMatrix();
                break;
            case 16: //frostwalker 10
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 119, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 117, 58, 57, 58, 480, 480);
                this.drawDifficulty(2);
                this.drawElement(2);
                this.drawBiome(2);
             //   this.drawEvenMoreStuff(5);
                this.drawItemType(2, 0);
                this.drawItemType(2, 56);
                this.drawItemType(2, 114);
                this.drawItemStack(new ItemStack(ModItems.ICE_WAND), (this.width + 30 * 2)/ 2 ,54);
                this.drawItemStack(new ItemStack(ModItems.ICE_WAND_RED), (this.width + 58 * 2) / 2,54);
                this.drawItemStack(new ItemStack(ModItems.ICE_DART), (this.width + 86 * 2) / 2,54);
                this.drawText(1, 4, 32, "entity.Frost_walker.name");
                GlStateManager.popMatrix();
                break;
            case 17: //netherhound 11
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(PAGE_GUI_TEXTURES);
                drawModalRectWithCustomSizedTexture((this.width - 250) / 2, 11, 0, 0, 119, 158, 608, 160);
                drawModalRectWithCustomSizedTexture((this.width + 12) / 2, 11, 119, 0, 119, 158, 608, 160);
                this.mc.renderEngine.bindTexture(PORTRAITS);
                drawModalRectWithCustomSizedTexture((this.width - 150) / 2, 36, 1, 174, 57, 58, 480, 480);
                this.drawDifficulty(1);
                this.drawElement(5);
                this.drawBiome(5);
              //  this.drawEvenMoreStuff(9);
                this.drawItemType(0, 0);
                this.drawItemType(2, 56);
                this.drawItemType(2, 114);
                this.drawRecipe(2);
                this.drawItemStack(new ItemStack(Items.ROTTEN_FLESH), (this.width + 30 * 2) / 2 ,54);
                this.drawItemStack(new ItemStack(Items.BONE), (this.width + 58 * 2) / 2,54);
                this.drawItemStack(new ItemStack(ModItems.FUR), (this.width + 86 * 2) / 2 ,54);
                this.drawText(1, 5, 28, "entity.Netherhound.name");
                GlStateManager.popMatrix();
                break;
        }
    }

    private void drawDifficulty(int difficulty){
        this.mc.renderEngine.bindTexture(BOOK_GUI_TEXTURES);
        switch (difficulty) {
            case 0:
            default:
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 38, 45, 183, 26, 11, 288, 224);
                break;
            case 1:
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 38, 71, 183, 26, 11, 288, 224);
                break;
            case 2:
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 38, 45, 194, 26, 11, 288, 224);
                break;
            case 3:
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 38, 71, 194, 26, 11, 288, 224);
                break;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private void drawElement(int element){
        this.mc.renderEngine.bindTexture(BOOK_GUI_TEXTURES);
        switch (element){
            case 0: //earth
            default:
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 53, 172, 184, 26, 12, 288, 224);
                break;
            case 1: //darkness
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 53, 200, 184, 26, 12, 288, 224);
                break;
            case 2: //water
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 53, 228, 184, 26, 12, 288, 224);
                break;
            case 3: //basic
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 53, 172, 196, 26, 12, 288, 224);
                break;
            case 4: // light
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 53, 200, 196, 26, 12, 288, 224);
                break;
            case 5: //fire
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 53, 228, 196, 26, 12, 288, 224);
                break;
        }
    }

    private void drawBiome(int biomeIn){
        this.mc.renderEngine.bindTexture(BIOMES);
        switch (biomeIn){
            case 0: //desert
            default: //         haha incidental funny number here                 v
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 69, 1, 1, 26, 22, 168, 72);
                break;
            case 1: //roofed
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 69, 29, 1, 26, 22, 168, 72);
                break;
            case 2: //ice spikes
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 69, 57, 1, 26, 22, 168, 72);
                break;
            case 3: //plains
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 69, 1, 25, 26, 22, 168, 72);
                break;
            case 4: //caves
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 69, 29, 25, 26, 22, 168, 72);
                break;
            case 5: //nether
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 69, 57, 25, 26, 22, 168, 72);
                break;
            case 6: //birch
                drawModalRectWithCustomSizedTexture((this.width - 210) / 2, 69, 1, 49, 26, 22, 168, 72);
                break;

        }
    }

    private void drawEvenMoreStuff(int ctrNumIn){
        this.mc.renderEngine.bindTexture(OTHER);
        switch (ctrNumIn){
            case 0: //sandy
            default:
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 1, 1, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 26, 1, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 51, 1, 23, 13, 576, 112);
                break;
            case 1: //zealot
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 1, 16, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 26, 16, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 51, 16, 23, 13, 576, 112);
                break;
            case 2: //ghost
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 1, 31, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 26, 31, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 51, 31, 23, 13, 576, 112);
                break;
            case 3: //giant
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 1, 46, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 26, 46, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 51, 46, 23, 13, 576, 112);
                break;
            case 4: //frostash
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 1, 61, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 26, 61, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 51, 61, 23, 13, 576, 112);
                break;
            case 5: //frost walker
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 1, 76, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 26, 76, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 51, 76, 23, 13, 576, 112);
                break;
            case 6: //vessel
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 76, 1, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 101, 1, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 126, 1, 23, 13, 576, 112);
                break;
            case 7: //lil v
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 76, 16, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 101, 16, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 126, 16, 23, 13, 576, 112);
                break;
            case 8: //damcell
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 76, 31, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 101, 31, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 126, 31, 23, 13, 576, 112);
                break;
            case 9: //netherhound
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 76, 46, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 101, 46, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 126, 46, 23, 13, 576, 112);
                break;
            case 10: //b zombie
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 76, 61, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 101, 61, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 126, 61, 23, 13, 576, 112);
                break;
            case 11: //owlstack
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 76, 76, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 101, 76, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 126, 76, 23, 13, 576, 112);
                break;
            case 12: // renderman
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 151, 1, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 176, 1, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 201, 1, 23, 13, 576, 112);
                break;
            case 13: //Nekros
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 151, 16, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 176, 16, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 201, 16, 23, 13, 576, 112);
                break;
            case 14: //Bonepile
                drawModalRectWithCustomSizedTexture((this.width - 208) / 2, 129, 151, 31, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 154) / 2, 129, 176, 31, 23, 13, 576, 112);
                drawModalRectWithCustomSizedTexture((this.width - 100) / 2, 129, 201, 31, 23, 13, 576, 112);
                break;
        }
    }

    private void drawItemType(int type, int x){
        this.mc.renderEngine.bindTexture(BOOK_GUI_TEXTURES);
        switch (type){
            case 0: //tame
            default:
                drawModalRectWithCustomSizedTexture((this.width + 52 + x) / 2, 38, 32, 207, 22, 12, 288, 224);
                break;
            case 1: //interact
                drawModalRectWithCustomSizedTexture((this.width + 52 + x) / 2, 38, 54, 207, 22, 12, 288, 224);
                break;
            case 2: //kill
                drawModalRectWithCustomSizedTexture((this.width + 52 + x) / 2, 38, 76, 207, 22, 12, 288, 224);
                break;
        }
    }

    private void drawText(int pageTypeIn, int mDI, int mHI, String name){
        String dC = I18n.format("bestiary.items");
        String s2= String.valueOf(mDI * BeastSlayerConfig.GlobalDamageMultiplier);
        String s = I18n.format(name);
        int k = this.fontRenderer.getStringWidth(s) / 2;
        int i = (this.width - X) / 2;
        switch (pageTypeIn){
            case 0:
            default:
                break;
            case 1:
                GlStateManager.scale(0.8F, 0.8F, 0.0F);
                GlStateManager.translate(0.8F, 0.8F, 0F);
                //for correct placement when scaling the position must be divided by the scale using this method
                this.fontRenderer.drawString(TextFormatting.GRAY + s, Math.round((i + 70 - (k * 0.75F)) / 0.8F), Math.round(17 / 0.8F), 0);
                this.fontRenderer.drawString(TextFormatting.GRAY + dC, Math.round((i + 172) / 0.8F), Math.round(17 / 0.8F), 0);
                String s1 = String.valueOf(mHI * BeastSlayerConfig.GlobalHealthMultiplier);
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.scale(1.0F, 1.0F, 1.0F);
                this.fontRenderer.drawString(TextFormatting.GRAY + s1, i + 47 , 107, 0);
                this.fontRenderer.drawString(TextFormatting.GRAY + s2 , i + 84, 107, 0);
                break;
        }
    }

    private void drawItemStack(ItemStack stack, int x, int y) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 32.0F);
        this.zLevel = 200.0F;
        this.itemRender.zLevel = 200.0F;
        net.minecraft.client.gui.FontRenderer font = null;
        if (!stack.isEmpty()) font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRenderer;
        GlStateManager.scale((float) 1.0, (float) 1.0, (float) 1.0);
        this.itemRender.zLevel = 5;
        this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        this.zLevel = 0.0F;
        this.itemRender.zLevel = 0.0F;
        GlStateManager.disableLighting();
        this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, null);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    private void drawRecipe(int recipeIn){
        int c = (this.width - X) / 2;
        switch (recipeIn){
            case 0:
            default:
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(CRAFTING);
                drawModalRectWithCustomSizedTexture(c + 216, 86, 79, 1, 37, 56, 256, 128);
                for (int x = 0; x < 7; x++){
                    int xpos = 0;
                    if (x == 3) {
                        xpos = 20;
                    }
                    else if (x > 3){
                        xpos = 40;
                    }
                    int ybonus = 0;
                    if (x == 1 || x == 5)
                    {
                        ybonus = 20;
                    }
                    else if (x == 2 || x == 6){
                        ybonus = 40;
                    }
                    RenderHelper.enableGUIStandardItemLighting();
                    drawItemStack(new ItemStack(Blocks.COBBLESTONE), c + 154 + xpos, 86 + ybonus);
                    RenderHelper.enableStandardItemLighting();
                }
                drawItemStack(new ItemStack(ModItems.SPIKE), c + 174, 106);
                drawItemStack(new ItemStack(Items.REDSTONE), c + 174, 126);

                GlStateManager.popMatrix();
                break;
            case 1: //sandy
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(CRAFTING);
                drawModalRectWithCustomSizedTexture(c + 216, 86, 1, 1, 37, 56, 256, 128);
                if(--this.time <= 0) {
                    for (int x = 0; x < 5; x++) {
                        int xpos = 0;
                        if (x == 2) {
                            xpos = 20;
                        } else if (x > 2) {
                            xpos = 40;
                        }
                        int ybonus = 0;
                        if (x == 0 || x == 4) {
                            ybonus = 20;
                        }
                        drawItemStack(new ItemStack(ModItems.SANDMONSTER_SCALE), c + 154 + xpos, 86 + ybonus);
                    }
                    if (--this.time <= -200F) {
                        this.time = 200F;
                    }
                }
                else{
                    for (int x = 0; x < 8; x++){
                        int xpos = 0;
                        if (x > 2 && x <= 4) {
                            xpos = 20;
                        }
                        else if (x > 4){
                            xpos = 40;
                        }
                        int ybonus = 0;
                        if (x == 1 || x == 3 || x == 6)
                        {
                            ybonus = 20;
                        }
                        else if(x == 2 || x == 4 || x == 7){
                            ybonus = 40;
                        }
                        drawItemStack(new ItemStack(ModItems.SANDMONSTER_SCALE), c + 154 + xpos, 86 + ybonus);
                    }
                }
                GlStateManager.popMatrix();
                break;
            case 2: //netherhound
                GlStateManager.pushMatrix();
                this.mc.renderEngine.bindTexture(CRAFTING);
                drawModalRectWithCustomSizedTexture(c + 216, 86, 40, 1, 38, 56, 256, 128);
                for (int x = 0; x < 5; x++){
                    int xpos = 0;
                    if (x == 2) {
                        xpos = 20;
                    }
                    else if (x > 2){
                        xpos = 40;
                    }
                    int ybonus = 0;
                    if (x == 0 || x == 4)
                    {
                        ybonus = 20;
                    }
                    drawItemStack(new ItemStack(ModItems.FUR), c + 154 + xpos, 106 + ybonus);
                }
                drawItemStack(new ItemStack(Items.BONE), c + 174, 86);

                GlStateManager.popMatrix();
        }
    }


    @SideOnly(Side.CLIENT)
    static class NextPageButton extends GuiButton
    {
        private final boolean isForward;

        public NextPageButton(int buttonId, int x, int y, boolean isForwardIn)
        {
            super(buttonId, x, y, 23, 13, "");
            this.isForward = isForwardIn;
        }

        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if (this.visible)
            {
                boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
                int i = 110;
                int j = 190;

                if (flag)
                {
                    i += 23;
                }
                if (!this.isForward)
                {
                    j += 13;
                }
                drawModalRectWithCustomSizedTexture(this.x, this.y, i, j, 23, 13, 288, 224);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    static class CreaturePageButton extends GuiButton
    {
        private final int number;
        private final int page;
        private final int renderOrder;

        public CreaturePageButton(int buttonId, int x, int y, int numIn, int pageIn, int renderOrder)
        {
            super(buttonId, x, y, 23, 13, "");
            this.number = numIn;
            this.page = pageIn;
            this.renderOrder = renderOrder;
        }

        public int getPage() {
            return page;
        }

        public int getRenderOrder() {
            return renderOrder;
        }

        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if (this.visible)
            {
                boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(GuiWiki.ICONS);
                int x = 0;
                if (number > 9){
                    x = 72;
                }
                int i = x;
                int j = (number * 16);

                if (flag)
                {
                    i += 36;
                   // drawHoveringText(this.getItemToolTip(stack), x, y, font);
                }
                drawModalRectWithCustomSizedTexture(this.x, this.y, i, j, 36, 16, 360, 160);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    static class HintButton extends GuiButton {
        private final Minecraft mc = Minecraft.getMinecraft();
        private int creatureNum;
        private int num2;
        private int renderOrder;

        protected HintButton(int buttonID, int x, int y, int creatureNum, int num2, int renderOrder) {
            super(buttonID, x, y, 23, 13, "");
            this.creatureNum = creatureNum;
            this.num2 = num2;
            this.renderOrder = renderOrder;
        }
        public int getRenderOrder(){
            return this.renderOrder;
        }


        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
            if (this.visible) {

                this.drawEvenMoreStuff(this.creatureNum);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            }
        }

        @Override
        public void drawButtonForegroundLayer(int x, int y) {
            BeastSlayer.logger.debug("writing down");
            BeastSlayer.logger.debug("page" + this.creatureNum);
            List<String> list = new ArrayList<>();

            list.add(I18n.format("bestiary.hint" + this.creatureNum + "_" + num2 + "top"));
            list.add(I18n.format("bestiary.hint" + this.creatureNum + "_" + num2 + "bottom"));
            //GuiUtils.drawHoveringText(list, x, y, 276, 180, 300, mc.fontRenderer);
            GuiWiki.self.drawText(list, this.x - 130, this.y);
           // this.drawToolTip(list,x,y);
        }
      /*  public void drawToolTip(List<String> stringList, int x, int y)
        {
            GuiUtils.drawHoveringText(stringList, x, y, width, height, 300, mc.fontRenderer);
        }*/
        private void drawEvenMoreStuff(int ctrNumIn){
            this.mc.renderEngine.bindTexture(OTHER);
            switch (ctrNumIn){
                case 9: //sandy
                default:
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 1, 1, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture( this.x, this.y,26, 1, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 51, 1, 23, 13, 576, 112);
                            break;
                    }
                    break;
                case 1: //zealot
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 1, 16, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 26, 16, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 51, 16, 23, 13, 576, 112);
                            break;
                    }
                    break;
                case 2: //ghost
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 1, 16, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture(this.x, this.y,26, 31, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y,51, 31, 23, 13, 576, 112);
                            break;
                    }
                    break;
                case 3: //giant
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 1, 46, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 26, 46, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 51, 46, 23, 13, 576, 112);
                            break;
                    }
                    break;
                case 12: //frostash
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y,1, 61, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture(this.x, this.y,26, 61, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 51, 61, 23, 13, 576, 112);
                            break;
                    }
                    break;
                case 13: //frost walker
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y,1, 76, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 26, 76, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 51, 76, 23, 13, 576, 112);
                            break;
                    }
                    break;
                case 4: //vessel
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 76, 1, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 101, 1, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 126, 1, 23, 13, 576, 112);
                            break;
                    }
                    break;
                case 8: //lil v
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 76, 16, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 101, 16, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 126, 16, 23, 13, 576, 112);
                            break;
                    }
                    break;
                case 5: //damcell
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 76, 31, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 101, 31, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 126, 31, 23, 13, 576, 112);
                            break;
                    }
                    break;
                case 14: //netherhound
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 76, 46, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 101, 46, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 126, 46, 23, 13, 576, 112);
                            break;
                    }
                    break;
                case 11: //b zombie
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 76, 61, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 101, 61, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 126, 61, 23, 13, 576, 112);
                            break;
                    }
                    break;
                case 10: //owlstack
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 76, 76, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture(this.x, this.y,101, 76, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 126, 76, 23, 13, 576, 112);
                            break;
                    }
                    break;
                case 0: // renderman
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 151, 1, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 176, 1, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y,  201, 1, 23, 13, 576, 112);
                            break;
                    }
                    break;
                case 6: //Nekros
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 151, 16, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 176, 16, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 201, 16, 23, 13, 576, 112);
                            break;
                    }
                    break;
                case 7: //Bonepile
                    switch (this.num2){
                        case 0:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 151, 31, 23, 13, 576, 112);
                            break;
                        case 1:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 176, 31, 23, 13, 576, 112);
                            break;
                        case 2:
                            drawModalRectWithCustomSizedTexture(this.x, this.y, 201, 31, 23, 13, 576, 112);
                            break;
                    }
                    break;
            }
        }
    }
    public void drawText(List<String> list, int x, int y) {
        this.drawHoveringText(list, x,y);
    }
}
