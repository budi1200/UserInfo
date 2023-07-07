package si.budimir.userinfo.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public class ClientMixin {

	@Inject(at = @At(value = "RETURN"), method = "getWindowTitle", cancellable = true)
	private void getWindowTitle(CallbackInfoReturnable<String> info) {
		MinecraftClient client = (MinecraftClient) (Object) this;

		// Title
		StringBuilder builder = new StringBuilder("Minecraft");

		// Game Version
		builder.append(" ");
		builder.append(SharedConstants.getGameVersion().getName());

		// Separator
		builder.append(" - ");

		// User info
		builder.append(client.getSession().getUsername());

		// Server info
		ServerInfo server = client.getCurrentServerEntry();

		if (server != null) {
			builder.append(" on ");
			builder.append(server.address);
		}

		info.setReturnValue(builder.toString());
	}
}
