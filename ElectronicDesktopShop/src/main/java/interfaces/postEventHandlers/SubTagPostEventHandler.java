package interfaces.postEventHandlers;

import interfaces.CreateAndShowGUI;

public interface SubTagPostEventHandler extends CreateAndShowGUI {

    void setTagPressed(String tagPressed);

    void setSubTagPressed(String subTagPressed);

    void setSubTagPressed(boolean subTagPressed);

}
