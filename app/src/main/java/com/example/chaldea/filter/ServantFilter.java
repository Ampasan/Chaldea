package com.example.chaldea.filter;

import com.example.chaldea.model.Servant;
import java.util.ArrayList;
import java.util.List;

public class ServantFilter {
    public static ArrayList<Servant> filterByClass(List<Servant> list, String servantClass) {
        ArrayList<Servant> filteredList = new ArrayList<>();
        if (servantClass == null) {
            filteredList.addAll(list);
        } else {
            for (Servant s : list) {
                if (s.getServantClass().equalsIgnoreCase(servantClass)) {
                    filteredList.add(s);
                }
            }
        }
        return filteredList;
    }
}
