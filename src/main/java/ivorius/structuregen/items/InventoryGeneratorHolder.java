package ivorius.structuregen.items;

import net.minecraft.item.ItemStack;

/**
 * Created by lukas on 26.05.14.
 */
public interface InventoryGeneratorHolder
{
    String inventoryKey(ItemStack stack);
}
