package io.github.crmodders.spacialmenu;

import finalforeach.cosmicreach.io.SaveLocation;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.VerticalAnchor;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import static io.github.crmodders.spacialmenu.SpacialMenuMod.LOGGER;

public class MenuUtils {
    public static UIElement makeButton(float x, float y, float w, float h, String text, Consumer<UIElement> onClick) {
        UIElement elm = new UIElement(x, y, w, h) {
            @Override
            public void onClick() {
                super.onClick();
                onClick.accept(this);
            }
        };
        elm.hAnchor = HorizontalAnchor.LEFT_ALIGNED;
        elm.vAnchor = VerticalAnchor.CENTERED;
        elm.setText(text);
        return elm;
    }

    public static UIElement makeButton(String text, Consumer<UIElement> onClick) {
        return makeButton(0, 0, 0, 0, text, onClick);
    }

    public static void openSaveDirectory() {
        try {
            File saveFolder = SaveLocation.getSaveFolder();
            SaveLocation.OpenFolderWithFileManager(saveFolder);
        } catch (IOException exception) {
            LOGGER.warning("Failed to open save directory. Full error:");
            LOGGER.warning(exception.getMessage());
        }
    }
}
