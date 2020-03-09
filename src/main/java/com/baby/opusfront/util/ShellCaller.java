package com.baby.opusfront.util;

import java.io.IOException;

public class ShellCaller {
    // cmd = "nohup /pmapp/liuyi/java/t.sh&";

    public static void callKsh(String cmd){
        try {
            Process p = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            System.out.println("--licg---    call ksh failed    -----");
            e.printStackTrace();
        }
    }
}
