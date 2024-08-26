package com.example.shopbee.ui.common.dialogs;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DialogsEventBus {
    public interface Listener {
        void onDialogEvent(Object event);
    }
    final Set<Listener> mListener = Collections.newSetFromMap(
            new ConcurrentHashMap<Listener, Boolean>(1)
    );
    public final void registerListener(Listener listener) {
        mListener.add(listener);
    }
    public final void unregisterListener(Listener listener) {
        mListener.remove(listener);
    }
    public final Set<Listener> getListeners() {
        return Collections.unmodifiableSet(mListener);
    }
    public void postEvent(Object event) {
        for (Listener listener : getListeners()) {
            listener.onDialogEvent(event);
        }
    }
}
