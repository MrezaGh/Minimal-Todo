package com.example.avjindersinghsekhon.minimaltodo.Utility;

import java.util.ArrayList;

public class FilterConstraints {
    private ArrayList<String> types = new ArrayList<>();
    {
        types.add("No type");
        types.add("Work");
        types.add("University");
        types.add("Recreation");
    }
    private ArrayList<String> typeConstraints = new ArrayList<>();

    private ArrayList<String> importances = new ArrayList<>();
    {
        importances.add("very important");
        importances.add("important");
        importances.add("less important");
        importances.add("not important");
    }
    private ArrayList<String> importanceConstraints = new ArrayList<>();

    public boolean hasImportanceConstraint() {
        return importanceConstraints.size() != 0;
    }

    public boolean hasTypeConstraint() {
        return typeConstraints.size() != 0;
    }

    public void addImportanceConstraint(String importanceConstraint) {
        if (!importances.contains(importanceConstraint))
            return;
        if (importanceConstraints.contains(importanceConstraint))
            return;
        importanceConstraints.add(importanceConstraint);
    }

    public void addTypeConstraint(String typeConstraint) {
        if (!types.contains(typeConstraint))
            return;
        if (typeConstraints.contains(typeConstraint))
            return;
        typeConstraints.add(typeConstraint);
    }

    public void removeImportanceConstraint(String importanceConstraint){
        importanceConstraints.remove(importanceConstraint);
    }

    public void removeTypeConstraint(String typeConstraint){
        typeConstraints.remove(typeConstraint);
    }

    public ArrayList<String> getImportanceConstraints() {
        return importanceConstraints;
    }

    public ArrayList<String> getTypeConstraints() {
        return typeConstraints;
    }

    public void clearAll(){
        typeConstraints.clear();
        importanceConstraints.clear();
    }

    public void addAllImportanceConstraint() {
        for (int i = 0; i < importances.size() ; i++) {
            this.importanceConstraints.add(importances.get(i));
        }
    }

    public void clearImportance() {
        importanceConstraints.clear();
    }

    public void clearType() {
        typeConstraints.clear();
    }

    public void addAllTypeConstraint() {
        for (int i = 0; i < types.size() ; i++) {
            this.typeConstraints.add(types.get(i));
        }
    }
}
