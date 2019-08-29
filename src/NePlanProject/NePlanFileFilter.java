package NePlanProject;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class NePlanFileFilter extends FileFilter {
    @Override
    public String getDescription() {
        return "Person database files (*.neplan)";
    }

    @Override
    public boolean accept(File f) {

        if (f.isDirectory()) {
            return true;
        }
        String name = f.getName();

        String extension = Utils.getFileExtension(name);

        if (extension == null) {
            return false;
        }

        if (extension.equals("neplan")) {
            return true;
        }
        return false;
    }
}
