/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

//Java
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 *
 * @author Nameless
 */
public class FileManager {

    public static void copy(String source, String destiny) {
        File sourceFile = new File(source);
        File destinyFile = new File(destiny);
        copyFile(sourceFile, destinyFile);
    }

    private static void copyFile(File sourceFile, File destinyFile) {

        if (cannotCopy(sourceFile, destinyFile)) {
            return;
        }
        try (FileChannel srcChannel = new FileInputStream(sourceFile).getChannel();
                FileChannel sinkChanel = new FileOutputStream(destinyFile).getChannel()) {
            srcChannel.transferTo(0, srcChannel.size(), sinkChanel);
        } catch (IOException e) {
            System.out.println("Issue Channel:" + e);
        }
    }

    public static boolean cannotCopy(File sourceFile, File destinyFile) {
        return (!sourceFile.exists() || destinyFile.exists());
    }
}
