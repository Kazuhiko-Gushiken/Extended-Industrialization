package net.swedz.extended_industrialization.machines.blockentities;

import aztech.modern_industrialization.MICapabilities;
import aztech.modern_industrialization.api.energy.EnergyApi;
import aztech.modern_industrialization.inventory.MIInventory;
import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.components.OrientationComponent;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import aztech.modern_industrialization.machines.models.MachineModelClientData;
import aztech.modern_industrialization.util.Tickable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.swedz.extended_industrialization.machines.components.MachineChainerComponent;
import net.swedz.extended_industrialization.machines.guicomponents.modularmultiblock.ModularMultiblockGui;
import net.swedz.extended_industrialization.machines.guicomponents.modularmultiblock.ModularMultiblockGuiLine;
import net.swedz.extended_industrialization.text.EIText;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public final class MachineChainerMachineBlockEntity extends MachineBlockEntity implements Tickable
{
	private final MachineChainerComponent chainer;
	
	private long tick;
	
	public MachineChainerMachineBlockEntity(BEP bep)
	{
		super(
				bep,
				new MachineGuiParameters.Builder("machine_chainer", false).backgroundHeight(180).build(),
				new OrientationComponent.Params(false, false, false)
		);
		
		chainer = new MachineChainerComponent(this, 64);
		
		this.registerComponents(chainer);
		
		this.registerGuiComponent(new ModularMultiblockGui.Server(60, () ->
		{
			List<ModularMultiblockGuiLine> text = Lists.newArrayList();
			
			text.add(new ModularMultiblockGuiLine(EIText.MACHINE_CHAINER_CONNECTED_MACHINES.text(chainer.getMachineCount(), chainer.getMaxConnectedMachines())));
			
			return text;
		}));
	}
	
	@Override
	public MIInventory getInventory()
	{
		return MIInventory.EMPTY;
	}
	
	@Override
	protected MachineModelClientData getMachineModelData()
	{
		MachineModelClientData data = new MachineModelClientData();
		orientation.writeModelData(data);
		return data;
	}
	
	@Override
	public void onPlaced(LivingEntity placer, ItemStack itemStack)
	{
		super.onPlaced(placer, itemStack);
		chainer.buildLinks();
	}
	
	@Override
	public void tick()
	{
		if(!level.isClientSide())
		{
			chainer.tick();
		}
	}
	
	public static void registerCapabilities(BlockEntityType<?> bet)
	{
		MICapabilities.onEvent((event) ->
		{
			event.registerBlockEntity(
					Capabilities.ItemHandler.BLOCK, bet,
					(be, direction) -> ((MachineChainerMachineBlockEntity) be).chainer.itemHandler
			);
			event.registerBlockEntity(
					Capabilities.FluidHandler.BLOCK, bet,
					(be, direction) -> ((MachineChainerMachineBlockEntity) be).chainer.fluidHandler
			);
			event.registerBlockEntity(
					EnergyApi.SIDED, bet,
					(be, direction) -> ((MachineChainerMachineBlockEntity) be).chainer.energyHandler
			);
		});
	}
}
