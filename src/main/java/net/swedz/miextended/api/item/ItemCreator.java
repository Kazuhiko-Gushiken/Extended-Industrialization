package net.swedz.miextended.api.item;

import net.minecraft.world.item.Item;
import net.swedz.miextended.api.Creator;

public interface ItemCreator<I extends Item, P extends Item.Properties> extends Creator<I, P>
{
}
