package ru.cool.lwjgl3.util;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {

    private static boolean[] keys = new boolean[GLFW_KEY_LAST];

    public static boolean keyPressed(long windowId, int keyId){
        return getKeyStat(windowId, keyId) && !keys[keyId];
    }

    public static boolean keyReleased(long windowId, int keyId){
        return !getKeyStat(windowId, keyId) && keys[keyId];
    }

    private static boolean getKeyStat(long windowId, int keyId){
        return glfwGetKey(windowId, keyId) == 1;
    }

    public static void handleKeyboardInput(long windowId)
    {
        for (int i = 0; i < GLFW_KEY_LAST; i++)
        {
            keys[i] = getKeyStat(windowId, i);
        }
    }


}
