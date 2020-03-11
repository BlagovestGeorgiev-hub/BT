package interfaces.postEventHandlers;

import interfaces.CreateAndShowGUI;

public interface OrderByNamePostEventHandler extends CreateAndShowGUI {

    void setDefaultOrderByName(boolean defaultOrderByName);

    void setDefaultOrderByPrice(boolean defaultOrderByPrice);

}
