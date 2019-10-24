package handleVPN;

import myJavaClasses.Disp;

import java.util.concurrent.TimeUnit;

public class ChangeIP extends HandleVPN {

    public ChangeIP(int nb_rounds, int try_nb)
    {
        Runtime runtime = Runtime.getRuntime();

        try {
            Disp.exc("Changing IP ———— round n°" + nb_rounds + " ———— try n°" + try_nb);

            runtime.exec(clickIcon);            TimeUnit.MILLISECONDS.sleep(config[1]);
            runtime.exec(pressTab);             TimeUnit.MILLISECONDS.sleep(config[1]);
            runtime.exec(pressSpace);           TimeUnit.MILLISECONDS.sleep(config[3]);
            runtime.exec(pressSpace);           TimeUnit.MILLISECONDS.sleep(config[1]); // these two
            runtime.exec(clickIcon);            TimeUnit.MILLISECONDS.sleep(config[4]); // can be reversed :)

        } catch (Exception e) { e.printStackTrace(); }
    }

}
