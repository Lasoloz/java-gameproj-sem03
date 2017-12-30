package com.github.lasoloz.gameproj.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class ResourceLoader {
    public static String internalPath = "";
    public static String localPath = "local/";

    public static FileHandle loadInternalOrLocalResource(String filePath) {
        final String internalName = internalPath + filePath;
        FileHandle internalFile = Gdx.files.internal(internalName);
        if (internalFile.exists()) {
            return internalFile;
        }

        final String localName = localPath + filePath;
        FileHandle localFile = Gdx.files.local(localName);
        if (localFile.exists()) {
            return localFile;
        }

        return null;
    }
}
