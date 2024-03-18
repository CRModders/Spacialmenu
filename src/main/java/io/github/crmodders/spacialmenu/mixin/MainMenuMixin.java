package io.github.crmodders.spacialmenu.mixin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import finalforeach.cosmicreach.RuntimeInfo;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.gamestates.WorldSelectionMenu;
import finalforeach.cosmicreach.settings.Controls;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.VerticalAnchor;
import finalforeach.cosmicreach.world.World;
import io.github.crmodders.spacialmenu.MenuUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(MainMenu.class)
public class MainMenuMixin extends GameState {
    @Unique
    private static final List<String> PROMO_TEXT = List.of(
            "Cosmic Reach (PRE ALPHA %s)".formatted(RuntimeInfo.version),
            "finalforeach.com",
            "youtube.com/@finalforeach"
    );

    @Override
    public void create() {
        super.create();
        World.worldLoader.readyToPlay = false;

        float verticalButtonGap = 10.0f;

        AtomicReference<Float>
                w = new AtomicReference<>(250F),
                h = new AtomicReference<>(50F),
                x = new AtomicReference<>(20F),
                y = new AtomicReference<>(0F);

        List<UIElement> buttons = List.of(
                MenuUtils.makeButton("Start", button -> GameState.switchToGameState(new WorldSelectionMenu())),
                MenuUtils.makeButton("Save Directory", button -> MenuUtils.openSaveDirectory()),
                MenuUtils.makeButton("Options", button -> GameState.switchToGameState(new OptionsMenu(this))),
                MenuUtils.makeButton("Quit", button -> System.exit(0))
        );

        buttons.forEach(button -> {
            button.x = x.get();
            button.y = y.get();
            button.w = w.get();
            button.h = h.get();
            button.show();
            this.uiElements.add(button);
            y.set(y.get() + h.get() + verticalButtonGap);
        });
    }

    @Override
    public void render() {
        super.render();
        ScreenUtils.clear(0.0F, 0.0F, 0.0F, 1.0F, true);
        Gdx.gl.glEnable(2929);
        Gdx.gl.glDepthFunc(513);
        Gdx.gl.glEnable(2884);
        Gdx.gl.glCullFace(1029);
        Gdx.gl.glEnable(3042);
        Gdx.gl.glBlendFunc(770, 771);
        batch.setProjectionMatrix(this.uiCamera.combined);
        batch.begin();

        float scale = 4.0F;
        float logoW = 192.0F;
        float logoH = 64.0F;
        float logoX = -scale * logoW / 2.0F;
        float logoY = -this.uiViewport.getWorldHeight() / 2.0F;
        batch.draw(MainMenu.textLogo, logoX, logoY, 0.0F, 0.0F, logoW, logoH, scale, scale, 0.0F, 0, 0, MainMenu.textLogo.getWidth(), MainMenu.textLogo.getHeight(), false, true);

        Vector2 promoTextDim = new Vector2();
        AtomicReference<Float> y = new AtomicReference<>(-8F);

        PROMO_TEXT.forEach(text -> {
            FontRenderer.getTextDimensions(this.uiViewport, text, promoTextDim);
            batch.setColor(Color.GRAY);
            FontRenderer.drawText(batch, this.uiViewport, text, -7.0F, y.get() + 1.0F, HorizontalAnchor.RIGHT_ALIGNED, VerticalAnchor.BOTTOM_ALIGNED);
            batch.setColor(Color.WHITE);
            FontRenderer.drawText(batch, this.uiViewport, text, -8.0F, y.get(), HorizontalAnchor.RIGHT_ALIGNED, VerticalAnchor.BOTTOM_ALIGNED);
            y.set(y.get() - promoTextDim.y - 4.0F);
        });

        String macWarning;
        if (Controls.controllers.size > 0) {
            macWarning = Controls.controllers.size == 1 ? "Controller" : "Controllers";
            String controllerWarning = macWarning + " detected!\nController support is incomplete now,\nand will be improved in later updates.";
            FontRenderer.drawText(batch, this.uiViewport, controllerWarning, 8.0F, y.get(), HorizontalAnchor.LEFT_ALIGNED, VerticalAnchor.BOTTOM_ALIGNED);
        }

        if (RuntimeInfo.isMac()) {
            macWarning = "WARNING: Mac is not supported at this time. The game is unlikely to work.";
            FontRenderer.drawText(batch, this.uiViewport, macWarning, 8.0F, y.get(), HorizontalAnchor.CENTERED, VerticalAnchor.CENTERED);
        }

        batch.end();
        this.drawUIElements();
    }
}
