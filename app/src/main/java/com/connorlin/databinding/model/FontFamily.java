package com.connorlin.databinding.model;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * ClassName: FontFamily
 * Description:
 * Author: connorlin
 * Date: Created on 2016-7-4.
 */
public class FontFamily {
    HashMap<String, String> mFontMap = new HashMap<>();
    HashMap<String, Typeface> mFontCache = new HashMap<>();

    public FontFamily() {
        addFont("zhuanti", "zhuanti.ttf");
    }

    public void addFont(String alias, String fontName) {
        mFontMap.put(alias, fontName);
    }

    public static FontFamily getInstance() {
        return Singleton.instance;
    }

    public Typeface getFont(String alias, Context context) {
        String fontFilename = mFontMap.get(alias);
        if (fontFilename == null) {
            return null;
        }

        if (mFontCache.containsKey(alias)) {
            return mFontCache.get(alias);
        } else {
            Typeface typeface =
                    Typeface.createFromAsset(context.getApplicationContext().getAssets(),
                            "fonts/" + fontFilename);
            mFontCache.put(fontFilename, typeface);
            return typeface;
        }
    }

    private static class Singleton {
        private static FontFamily instance = new FontFamily();
    }
}
