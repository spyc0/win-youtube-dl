package org.fredy.winyoutubedl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class acts as a utility class for any functionalities pertaining
 * to youtube-dl script.
 * 
 * @author fredy
 */
public class YouTubeDownloader {
    private YouTubeDownloader() {
        // prevent instantiation
    }
    
    private static class OutputEater extends Thread {
        private InputStream stdout;
        private InputStream stderr;
        
        public OutputEater(InputStream stdout, InputStream stderr) {
            this.stdout = stdout;
            this.stderr = stderr;
        }
        
        @SuppressWarnings("unused")
        @Override
        public void run() {
            byte[] buffer = new byte[4096];
            int b;
            try {
                while ((b = stdout.read(buffer)) != -1) {
                    // ignore the output
                }
                
                while ((b = stderr.read(buffer)) != -1) {
                    // ignore the output
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    /**
     * Downloads the video.
     * 
     * @param directory the directory to output the video
     * @param url the video URL
     * @param toMP3 true if the video is to be converted to MP3; false otherwise
     */
    public static void download(String directory, String url, boolean toMP3) {
        List<String> cmd = new ArrayList<>();
        cmd.add("cmd");
        cmd.add("/c");
        File youtubedl = new File(".", "youtube-dl.exe");
        StringBuilder sb = new StringBuilder();
        sb.append("start cmd /c ");
        sb.append(youtubedl.getAbsolutePath());
        sb.append(" --title --restrict-filenames ");
        if (toMP3) {
            sb.append("--extract-audio --audio-format=mp3 ");
        }
        sb.append(url);
        sb.append("|| pause");
        cmd.add(sb.toString());
        
        try {
            Process p = new ProcessBuilder(cmd)
                .directory(new File(directory))
                .start();
            Thread t = new OutputEater(p.getInputStream(), p.getErrorStream());
            t.start();

            int exitValue = p.waitFor();
            if (exitValue != 0) {
                throw new RuntimeException("Non-zero exit value");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Performs an update of youtube-dl script.
     */
    public static void update() {
        List<String> cmd = new ArrayList<>();
        cmd.add("cmd");
        cmd.add("/c");
        File youtubedl = new File(".", "youtube-dl.exe");
        StringBuilder sb = new StringBuilder();
        sb.append("start cmd /c ");
        sb.append(youtubedl.getAbsolutePath());
        sb.append(" --update");
        sb.append("|| pause");
        cmd.add(sb.toString());
        
        try {
            Process p = new ProcessBuilder(cmd)
                .start();
            Thread t = new OutputEater(p.getInputStream(), p.getErrorStream());
            t.start();

            int exitValue = p.waitFor();
            if (exitValue != 0) {
                throw new RuntimeException("Non-zero exit value");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
