package filter;

import java.util.ArrayList;

public class IdsValidator {
    private ArrayList<Integer> ids;

    public IdsValidator(ArrayList<Integer> ids) {
        this.ids = ids;
    }

    public boolean validate(){
        for(int i = 0; i < ids.size(); i++){
            if(ids.get(i) <= 0){
                return false;
            }
        }
        return true;
    }
}
