package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.HashMap;

/**
 * @author László Heim &lt;lasolozdev@gmail.com&rt;
 * The `AssetBundle` ensures that each texture from the path is loaded only
 * once, when it is needed.
 */
public class AssetBundle {
    private HashMap<String, Drawable> assets;
    private Drawable placeholder;


    private AssetBundle() {
        assets = new HashMap<String, Drawable>();
    }


    // Singleton pattern:
    private static AssetBundle assetBundle;
    public static AssetBundle getAssetBundle() {
        if (assetBundle == null) {
            assetBundle = new AssetBundle();
        }

        return assetBundle;
    }


    // Lazy loading of textures:
    public Drawable loadTexture(String pathToAsset) {
        if (assets.containsKey(pathToAsset)) {
            return assets.get(pathToAsset);
        }

        try {
            Drawable texture = new TextureWrapper(pathToAsset);
            assets.put(pathToAsset, texture);
            return texture;
        } catch (GdxRuntimeException ex) {
            Gdx.app.log("AssetBundle", "Failed to load texture!");
            return placeholder;
        }
    }

    // Lazy loading of animations:
    public Drawable loadAnimation(String pathToAsset) {
        if (assets.containsKey(pathToAsset)) {
            return assets.get(pathToAsset);
        }

        try {
            Drawable texture = new AnimationWrapper(pathToAsset);
            assets.put(pathToAsset, texture);
            return texture;
        } catch (GdxRuntimeException ex) {
            Gdx.app.log("AssetBundle", "Failed to load animation!");
            return placeholder;
        }
    }

    public boolean initPlaceHolder(String placeholderPath) {
        try {
            placeholder = new TextureWrapper(placeholderPath);
            return true;
        } catch (GdxRuntimeException ex) {
            Gdx.app.log("AssetBundle", "Failed to load placeholder!");
            return false;
        }
    }

    public void dispose() {
        for (Drawable d : assets.values()) {
            d.dispose();
        }

        assets.clear();
    }
}
