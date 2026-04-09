package com.ll0k4.client.modules.gameplay;

import com.ll0k4.client.core.Module;

public class ToggleSprintModule extends Module {

    public ToggleSprintModule() {
        super("Toggle Sprint", "Sprint permanent automatique", Category.GAMEPLAY);
    }

    @Override
    public void onEnable() {
        // Le sprint effectif est géré dans ClientPlayerEntityMixin#tickMovement
    }

    @Override
    public void onDisable() {
        // Le mixin ne force plus le sprint
    }
}
