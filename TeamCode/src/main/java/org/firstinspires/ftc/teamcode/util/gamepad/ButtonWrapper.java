package org.firstinspires.ftc.teamcode.util.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.lang.reflect.Field;

public class ButtonWrapper {
    public static final long debounceLength = 500;

    private Field buttonField;
    private long lastUpdate = 0;

    public static ButtonWrapper wrap(Gamepad gamepad, String buttonFieldName) {
        try {
            return new ButtonWrapper(gamepad, gamepad.getClass().getField(buttonFieldName));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e); // Fatal, propagate error wrapped in RuntimeException
        }
    }

    public ButtonWrapper(Gamepad gamepad, Field buttonField) {
        this.buttonField = buttonField;
    }
}
