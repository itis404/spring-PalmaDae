package org.palmadae.donortrack.util;

import com.ibm.icu.text.Transliterator;

public class SlugUtil {

    private static final Transliterator TO_LATIN =
            Transliterator.getInstance("Russian-Latin/BGN");

    public static String toCitySlug(String city) {
        if (city == null) return null;

        String translit = TO_LATIN.transliterate(city);

        return translit
                .toLowerCase()
                .replace(" ", "-")
                .replaceAll("[^a-z0-9-]", "");
    }
}