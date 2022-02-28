package com.example.c196.ViewModel;


import com.example.c196.entities.EntityTerm;

public final class TermViewModel {
    public final int id;
    public final String title;

    public TermViewModel(int id, String title){
        this.id = id;
        this.title = title;
    }
    public TermViewModel(EntityTerm t){
        this.id = t.getTermID();
        this.title = t.getTermTitle();
    }
    @Override
    public String toString() {
        return  title;
    }
}
