package com.example.damien.myapplication.UI.Listener;

import android.widget.AbsListView;

/**
 * Listener permettant de gérer l'EndlessScroll sur le Listview des messages
 */
public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

    //region Attributs
    public final int limit = 15;
    public static int offset = 0;
    //endregion

    //region Constructor
    public EndlessScrollListener() {
    }
    //endregion

    //region Override
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + 10 >= offset) {
            offset = offset + limit;
            onLoadMore(offset, limit);
        }
    }
    //endregion

    //region Methods

    /**
     * Permet de charger plus d'information
     *
     * @param page            offset des messages
     * @param totalItemsCount limite des messages
     */
    public abstract void onLoadMore(int page, int totalItemsCount);
    //endregion
}
