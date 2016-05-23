/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package superserial;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * I don't think I really need this but it works.
 * @author Carlton Johnson
 */
public final class FileFilters {
    private FileFilters(){}
}

class BotFilter extends FileFilter {
    /**
     * @param f file path to check
     * @return true if the file is of type bot or top
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String s = f.getName();
        int i = s.lastIndexOf('.');
        int t=i;

        if (i > 0 && i < s.length() - 1) {
            if (s.substring(i + 1).toLowerCase().equals("bot")) {
                return true;
            }
        }

        return false;
    }
    @Override
    public String getDescription() {
        return "Accepts '.bot' files";
    }
}

class TopFilter extends FileFilter {
    /**
     * @param f file path to check
     * @return true if the file is of type bot or top
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String s = f.getName();
        int i = s.lastIndexOf('.');
        int t=i;

        if (i > 0 && i < s.length() - 1) {
            if(s.substring(i + 1).toLowerCase().equals("top")){
                return true;
            }
        }

        return false;
    }
    @Override
    public String getDescription() {
        return "Accepts '.top' files";
    }
}

class TopBotFilter extends FileFilter {
    /**
     * @param f file path to check
     * @return true if the file is of type bot or top
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String s = f.getName();
        int i = s.lastIndexOf('.');
        int t=i;

        if (i > 0 && i < s.length() - 1) {
            if(s.substring(i + 1).toLowerCase().equals("top") || s.substring(i + 1).toLowerCase().equals("bot")){
                return true;
            }
        }

        return false;
    }
    @Override
    public String getDescription() {
        return "Accepts '.top' and '.bot' files";
    }
}