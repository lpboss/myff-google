/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 *
 * @author jerry
 */
public class GetPid {

    private static final String PNAME = "QQ";

    public static void main(String[] args)
            throws IOException {
        System.out.println("包含关键字 " + PNAME + " 的进程有：");
        System.out.println();
        String path = System.getProperty("user.dir");
        final File createFileName = new File(path + "\\mytempscript.vbe");
        if (createFileName.exists() ? createFileName.delete() : createFileName.createNewFile()) {
        }

        final PrintWriter pw = new PrintWriter(new FileWriter(createFileName,
                true), true);
        pw.println("for each ps in getobject(\"winmgmts:\\\\.\\root\\cimv2:win32_process\").instances_");
        pw.println("wscript.echo ps.handle&vbtab&ps.name");
        pw.println("next");
        pw.close();

        final InputStream ii = Runtime.getRuntime().exec(
                "cscript " + path + "\\mytempscript.vbe").getInputStream();

        final InputStreamReader ir = new InputStreamReader(ii);

        final BufferedReader br = new BufferedReader(ir);
        String str = null;
        String[] ss = null;
        while ((str = br.readLine()) != null) {
            if (str.indexOf(PNAME) > 0 && str.endsWith(".exe")) {
                ss = str.split("\\s");
                for (int i = 0; i < ss.length; i += 2) {
                    System.out.println("****************************");
                    System.out.println("进程名：" + ss[i + 1] + "\n进程ID：" + ss[i]);
                }
            }
        }
        System.out.println("****************************");
        ir.close();
        ii.close();
        br.close();
    }
}
