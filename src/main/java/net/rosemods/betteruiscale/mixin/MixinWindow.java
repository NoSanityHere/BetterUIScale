package net.rosemods.betteruiscale.mixin;

import com.mojang.blaze3d.platform.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Window.class)
public class MixinWindow {
    @Shadow
    private int framebufferWidth;

    @Shadow
    private int framebufferHeight;

    @Shadow
	private double guiScale;

    @Shadow
	private int guiScaledWidth;

    @Shadow
	private int guiScaledHeight;

    /**
     * @author Rose
	 * @reason Modifies gui scaling
     */
    @Overwrite
    public int calculateScale(int guiScale, boolean forceUnicodeFont) {
		int i;
		for(i = 1; i != guiScale && i < this.framebufferWidth && i < this.framebufferHeight && this.framebufferWidth / (i + 1) >= 40 && this.framebufferHeight / (i + 1) >= 30; ++i) {
		}

		if (forceUnicodeFont && i % 2 != 0) {
			++i;
		}

		return i;
	}

	/**
	 * @author Rose
	 * @reason Modifies gui scaling
	 */
	@Overwrite
	public void setGuiScale(double guiScale) {
		if(guiScale > 2) guiScale = 1.5 * (0.3 + Math.log10(guiScale));
		else guiScale = 1 + (guiScale * 0.075);
		//scaleFactor = (scaleFactor + 3) / 4;
		this.guiScale = guiScale;
		int i = (int)((double)this.framebufferWidth / guiScale);
		this.guiScaledWidth = (double)this.framebufferWidth / guiScale > (double)i ? i + 1 : i;
		int j = (int)((double)this.framebufferHeight / guiScale);
		this.guiScaledHeight = (double)this.framebufferHeight / guiScale > (double)j ? j + 1 : j;
	}
}
