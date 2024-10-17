package com.frilledshrimpo.lettherebefire.recipe;

public class CraftingStage {
    private final String action;
    private final int requiredLoops;

    public CraftingStage(String action, int requiredLoops) {
        this.action = action;
        this.requiredLoops = requiredLoops;
    }

    public String getAction() {
        return action;
    }

    public int getRequiredLoops() {
        return requiredLoops;
    }
}
