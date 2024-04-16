package net.swedz.intothetwilight.mixin.mi;

import aztech.modern_industrialization.machines.init.SingleBlockSpecialMachines;
import net.swedz.intothetwilight.mi.machines.MIMachineHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SingleBlockSpecialMachines.class)
public class MISingleBlockSpecialMachinesMixin
{
	@Inject(
			method = "init",
			at = @At("TAIL")
	)
	private static void init(CallbackInfo callback)
	{
		MIMachineHook.singleBlockSpecialMachines();
	}
}
