package de.schulzgette.thes_o_naise.services;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enrico on 05.11.2016.
 */

public class EventBus {

    public interface IEventListner{
        public void onThesenUpdate();
    }

    private static List<IEventListner> listeners = new ArrayList<>();
    public static void register(IEventListner listener) {
        listeners.add(listener);
    }
    public static void unregister(IEventListner listener) {
        listeners.remove(listener);
    }

    public static void fireThesenUpdate(){
        for (IEventListner listener : listeners)
            listener.onThesenUpdate();
    }

}
