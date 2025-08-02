package com.View;

public interface ContactViewListener {
    void onAddContactRequested();
    void onDeleteContactRequested(int idContact);
    void onChangeContactRequested(int idContact);
    void onClearContactListRequested();
    void onSearchContactListRequested();
}
