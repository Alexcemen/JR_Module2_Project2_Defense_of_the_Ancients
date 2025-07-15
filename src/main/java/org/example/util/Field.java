package org.example.util;

import com.javarush.engine.cell.Color;

public class Field {
    private static final Color[][] map = new Color[][]{
            //1
            {
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKRED, Color.DARKRED, Color.DARKRED, Color.DARKRED,
                    Color.DARKRED, Color.DARKRED, Color.DARKRED, Color.DARKGRAY, Color.DARKGRAY
            },
            //2
            {
                    Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKGRAY, Color.DARKGRAY,
                    Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY,
                    Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY,
                    Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY
            },
            //3
            {
                    Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKRED, Color.DARKRED, Color.DARKRED,
                    Color.DARKRED, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKRED
            },
            //4
            {
                    Color.DARKOLIVEGREEN, Color.DARKGRAY, Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN,
                    Color.DARKCYAN, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKRED, Color.DARKRED,
                    Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKRED
            },
            //5
            {
                    Color.DARKOLIVEGREEN, Color.DARKGRAY, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKCYAN,
                    Color.DARKCYAN, Color.DARKCYAN, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY,
                    Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKRED, Color.DARKGRAY, Color.DARKRED
            },
            //6
            {
                    Color.DARKOLIVEGREEN, Color.DARKGRAY, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKGRAY, Color.DARKGRAY,
                    Color.DARKGRAY, Color.DARKRED, Color.DARKRED, Color.DARKGRAY, Color.DARKRED
            },
            //7
            {
                    Color.DARKOLIVEGREEN, Color.DARKGRAY, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY,
                    Color.DARKSLATEGRAY, Color.DARKRED, Color.DARKRED, Color.DARKGRAY, Color.DARKRED
            },
            //8
            {
                    Color.DARKOLIVEGREEN, Color.DARKGRAY, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKRED, Color.DARKGRAY, Color.DARKRED
            },
            //9
            {
                    Color.DARKOLIVEGREEN, Color.DARKGRAY, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN,
                    Color.DARKGRAY,Color.DARKGRAY,Color.DARKGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKGRAY, Color.DARKRED
            },
            //10
            {
                    Color.DARKOLIVEGREEN, Color.DARKGRAY, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKCYAN, Color.DARKCYAN,
                    Color.DARKCYAN, Color.DARKGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKGRAY, Color.DARKSLATEGRAY,
            },
            //11
            {
                    Color.DARKOLIVEGREEN, Color.DARKGRAY, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKGRAY, Color.DARKCYAN,
                    Color.DARKCYAN, Color.DARKCYAN, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKGRAY, Color.DARKSLATEGRAY
            },
            //12
            {
                    Color.DARKGREEN, Color.DARKGRAY, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY,
                    Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKGRAY, Color.DARKSLATEGRAY
            },
            //13
            {
                    Color.DARKGREEN, Color.DARKGRAY, Color.DARKGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKGRAY, Color.DARKSLATEGRAY
            },
            //14
            {
                    Color.DARKGREEN, Color.DARKGRAY, Color.DARKGREEN, Color.DARKGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKGRAY, Color.DARKSLATEGRAY
            },
            //15
            {
                    Color.DARKGREEN, Color.DARKGRAY, Color.DARKGREEN, Color.DARKGREEN, Color.DARKGRAY,
                    Color.DARKGRAY, Color.DARKGRAY, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKSLATEGRAY,
                    Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKGRAY, Color.DARKSLATEGRAY
            },
            //16
            {
                    Color.DARKGREEN, Color.DARKGRAY, Color.DARKGREEN, Color.DARKGRAY, Color.DARKGRAY,
                    Color.DARKGRAY, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN,
                    Color.DARKCYAN, Color.DARKSLATEGRAY, Color.DARKSLATEGRAY, Color.DARKGRAY, Color.DARKSLATEGRAY
            },
            //17
            {
                    Color.DARKGREEN, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY,
                    Color.DARKGREEN, Color.DARKGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKCYAN, Color.DARKCYAN,
                    Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKGRAY, Color.DARKSLATEGRAY
            },
            //18
            {
                    Color.DARKGREEN, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGREEN,
                    Color.DARKGREEN, Color.DARKGREEN, Color.DARKGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN
            },
            //19
            {
                    Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY,
                    Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY,
                    Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY,
                    Color.DARKGRAY, Color.DARKGRAY, Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN,
            },
            //20
            {
                    Color.DARKGRAY, Color.DARKGRAY, Color.DARKGREEN, Color.DARKGREEN, Color.DARKGREEN,
                    Color.DARKGREEN, Color.DARKGREEN, Color.DARKGREEN, Color.DARKGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN,
                    Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN, Color.DARKOLIVEGREEN
            }
    };

    public static Color[][] getMap() {
        return map;
    }
}
