package interfaces.postEventHandlers;

import interfaces.CreateAndShowGUI;
import models.DataHolder;

import java.util.ArrayList;

public interface TagPostEventHandler extends CreateAndShowGUI {

    DataHolder getData();

    ArrayList<String> getTagsPressed();

}
