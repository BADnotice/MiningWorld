package com.github.badnotice.miningworld.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtils {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteDir(File dir) {
        if (!dir.exists()) return;

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : Objects.requireNonNull(children)) deleteDir(new File(dir, child));
        }
        dir.delete();
    }

}
