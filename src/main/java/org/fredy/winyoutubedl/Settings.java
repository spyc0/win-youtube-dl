/* 
 * Copyright 2013 Fredy Wijaya
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.fredy.winyoutubedl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * This class stores and loads the user settings.
 * 
 * @author Fredy
 */
public class Settings {
    private Settings() {
        // prevent instantiation
    }
    
    private static Properties props;
    private static final String DIRECTORY_PROP = "directory";
    private static final String TO_MP3_PROP = "tomp3";
    private static final File PROP_FILE = new File("win-youtube-dl.properties");
    
    static {
        if (!PROP_FILE.exists()) {
            create();
        } else {
            open();
        }
    }
    
    private static void open() {
        props = new Properties();
        try {
            props.load(new FileReader(PROP_FILE));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static void create() {
        props = new Properties();
        StringBuilder downloadDir = new StringBuilder();
        downloadDir.append(System.getProperty("user.home"));
        downloadDir.append(File.separator);
        downloadDir.append("Downloads");
        File downloadFile = new File(downloadDir.toString());
        if (!downloadFile.exists()) {
            downloadFile.mkdirs();
        }
        props.put(DIRECTORY_PROP, downloadFile.getAbsolutePath());
        props.put(TO_MP3_PROP, Boolean.toString(false));
        save();
    }
    
    private static void save() {
        try {
            props.store(new FileWriter(PROP_FILE), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Sets the directory.
     * @param directory
     */
    public static void setDirectory(File directory) {
        props.put(DIRECTORY_PROP, directory.getAbsolutePath());
        save();
    }
    
    /**
     * Sets to MP3.
     * @param toMP3 
     */
    public static void setToMP3(boolean toMP3) {
        props.put(TO_MP3_PROP, Boolean.toString(toMP3));
        save();
    }
    
    /**
     * Gets the download directory.
     * @return the download directory
     */
    public static File getDirectory() {
        return new File((String) props.get(DIRECTORY_PROP));
    }
    
    /**
     * Is to MP3 selected?
     * @return true if to MP3 is selected; false otherwise
     */
    public static boolean toMP3() {
        return Boolean.parseBoolean((String) props.get(TO_MP3_PROP));
    }
}
