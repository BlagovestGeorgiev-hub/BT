package interfaces.postEventHandlers;

import interfaces.CreateAndShowGUI;

public interface OrderByPricePostEventHandler extends CreateAndShowGUI {

    void setDefaultOrderByName(boolean defaultOrderByName);

    void setDefaultOrderByPrice(boolean defaultOrderByPrice);


}
