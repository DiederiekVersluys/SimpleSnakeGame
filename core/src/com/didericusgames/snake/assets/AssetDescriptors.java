package com.didericusgames.snake.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> UI_FONT = new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);

    private AssetDescriptors() {
    }
}