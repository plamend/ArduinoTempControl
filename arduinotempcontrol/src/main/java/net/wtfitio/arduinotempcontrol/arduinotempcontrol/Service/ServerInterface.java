package net.wtfitio.arduinotempcontrol.arduinotempcontrol.Service;

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes.feedObject;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes.inputObject;

import java.util.List;

/**
 * Created by plamend on 2/20/14.
 */
public interface ServerInterface {
    void getInputList(InputListcallback callback);
    void getFeedsList(FeedsListcallback callback);
    void getFeedValue(FeedValumecallback callback);

    public interface InputListcallback {
        void onSuccess(List<inputObject> input);
        void onFailure(String message, Throwable cause);
    }

    public interface FeedsListcallback {
        void onSuccess(List<feedObject> feed);
        void onFailure(String message, Throwable cause);
    }

    public interface FeedValumecallback {
        void onSuccess(String value);
        void onFailure(String message, Throwable cause);
    }

}
