package com.github.poluka.kControlLibrary.enity.position

import com.github.poluka.kControlLibrary.enity.Coordinate

/**
 * Массив, который хранит позицию робота.
 * @author artem241120@gmail.com
 */
class Position(x: Double, y: Double, z: Double, o: Double, a: Double, t: Double){
    private val position = doubleArrayOf(x, y, z, o, a, t)

    constructor(): this(0.0,0.0,0.0,0.0,0.0,0.0)
    constructor(x: Int, y: Int, z: Int, o: Int, a: Int, t: Int): this(x.toDouble(),
                                                                      y.toDouble(),
                                                                      z.toDouble(),
                                                                      o.toDouble(),
                                                                      a.toDouble(),
                                                                      t.toDouble())

    /**
     *
     */
    override fun toString(): String {
        return position.joinToString(separator = ";")
    }

    /**
     * Получаем доступ через координаты.
     * @param coordinate - координата, по которой нужно олучить значения.
     */
    operator fun get(coordinate: Coordinate) =
        position[coordinate.ordinal]
//        when(coordinate){
//            Coordinate.X -> position[0]
//            Coordinate.Y -> position[1]
//            Coordinate.Z -> position[2]
//            Coordinate.DX -> position[3]
//            Coordinate.DY -> position[4]
//            Coordinate.DZ -> position[5]
//        }


    /**
     * Изменяем значения через координаты.
     * @param coordinate - координата, по которой нужно изменить значения,
     * @param value - значение, которое нужно установить.
     */
    operator fun set(coordinate: Coordinate, value: Double){
        position[coordinate.ordinal] = value
//        when(coordinate){
//            Coordinate.X -> position[0] = value
//            Coordinate.Y -> position[1] = value
//            Coordinate.Z -> position[2] = value
//            Coordinate.DX -> position[3] = value
//            Coordinate.DY -> position[4] = value
//            Coordinate.DZ -> position[5] = value
//        }
    }
}
