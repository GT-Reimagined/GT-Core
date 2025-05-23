package org.gtreimagined.gtcore.proxy;

import net.minecraft.core.NonNullList;
import org.gtreimagined.gtcore.GTCore;

import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static org.gtreimagined.gtcore.data.GTCoreData.SupporterListGold;
import static org.gtreimagined.gtcore.data.GTCoreData.SupporterListSilver;

public class CommonHandler {
    public static void setup(){
        new Thread(() -> {

            List<String>
                    tTextFile = downloadTextFile("updates.gregtech.mechaenetia.com/com/gregoriust/gregtech/supporterlist.txt", false);
            if (tTextFile != null && tTextFile.size() > 3) {
                SupporterListSilver.addAll(tTextFile);
            } else try {
                Scanner tScanner = new Scanner(GTCore.class.getResourceAsStream("/supporterlist.txt"));
                while (tScanner.hasNextLine()) SupporterListSilver.add(tScanner.nextLine().toLowerCase());
                tScanner.close();
                GTCore.LOGGER.warn("GT_DL_Thread: Failed downloading Silver Supporter List, using interal List!");
            } catch(Throwable exc) {exc.printStackTrace();}

            tTextFile = downloadTextFile("updates.gregtech.mechaenetia.com/com/gregoriust/gregtech/supporterlistgold.txt", false);
            if (tTextFile != null && tTextFile.size() > 3) {
                SupporterListGold.addAll(tTextFile);
            } else try {
                Scanner tScanner = new Scanner(GTCore.class.getResourceAsStream("/supporterlistgold.txt"));
                while (tScanner.hasNextLine()) SupporterListGold.add(tScanner.nextLine().toLowerCase());
                tScanner.close();
                GTCore.LOGGER.warn("GT_DL_Thread: Failed downloading Gold Supporter List, using interal List!");
            } catch(Throwable exc) {exc.printStackTrace();}

            SupporterListSilver.removeAll(SupporterListGold);

        }).start();
    }

    protected static List<String> downloadTextFile(String aURL, boolean aLowercase) {
        List<String> rList = NonNullList.create();
        try {
            Scanner tScanner = new Scanner(new URL(aURL.startsWith("http")?aURL:"https://"+aURL).openStream());
            while (tScanner.hasNextLine()) rList.add(aLowercase ? tScanner.nextLine().toLowerCase() : tScanner.nextLine());
            tScanner.close();
            for (String tLine : rList) if (tLine.contains("a href")) {
                GTCore.LOGGER.error("GT_DL_Thread: Your Internet Connection has Issues, you should probably go check that your ISP or Network don't do stupid Stuff.");
                return NonNullList.create();
            }
            return rList;
        } catch(Throwable f) {
            GTCore.LOGGER.error("GT_DL_Thread: Failed to Connect.");
        }
        return null;
    }
}
