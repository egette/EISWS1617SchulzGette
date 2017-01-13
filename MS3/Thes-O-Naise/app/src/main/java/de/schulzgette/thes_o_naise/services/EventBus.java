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

    public interface ThesenAnsichtListner{
        public void onFirebaseUpdate();
    }
    private static List<IEventListner> listeners = new ArrayList<>();
    private static List<ThesenAnsichtListner> listeners2 = new ArrayList<>();
    public static void register(IEventListner listener) {
        listeners.add(listener);
    }
    public static void unregister(IEventListner listener) {
        listeners.remove(listener);
    }
    public static void register(ThesenAnsichtListner listener) {
        listeners2.add(listener);
    }
    public static void unregister(ThesenAnsichtListner listener) {
        listeners2.remove(listener);
    }

    public static void fireThesenUpdate(){
        for (IEventListner listener : listeners)
            listener.onThesenUpdate();
    }

    public static void fireFirebaseUpdate(){
        for (ThesenAnsichtListner listener : listeners2)
            listener.onFirebaseUpdate();
    }

}
