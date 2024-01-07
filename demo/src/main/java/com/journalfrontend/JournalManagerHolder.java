package com.journalfrontend;

import model.JournalManager;

public final class JournalManagerHolder {
    private JournalManager manager;
    private static JournalManagerHolder instance = new JournalManagerHolder();

    private JournalManagerHolder() {
        manager = JournalManager.getManagerInstance();
    }

    public static JournalManagerHolder getInstance() {
        return instance;
    }

    public void setJournalManager(JournalManager jm) {
        if (jm != null)
            this.manager = jm;
    }

    public JournalManager getJournalManager() {
        return this.manager;
    }

    protected void closeJournal() {
        setJournalManager(JournalManager.getManagerInstance());
    }
}
