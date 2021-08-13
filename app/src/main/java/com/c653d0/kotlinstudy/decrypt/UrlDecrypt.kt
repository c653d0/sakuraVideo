package com.c653d0.kotlinstudy.decrypt

import android.util.Log

class UrlDecrypt {
    companion object{
        fun jtDecrypt(ciphertext:String):String{
            val map:HashMap<String,String> = HashMap()
            map["Y0"]= "d"
            map["Qx"]= "A"
            map["Q2"]= "F"
            map["cx"]= "q"
            map["Q1"]= "E"
            map["Y4"]= "h"
            map["M5"]= "9"
            map["M2"]= "6"
            map["ZC"]= "k"
            map["M1"]= "5"
            map["RF"]= "N"
            map["Uy"]= "R"
            map["Q3"]= "G"
            map["Ux"]= "Q"
            map["RD"]= "L"
            map["c4"]= "x"
            map["cz"]= "s"
            map["JF"]= "."
            map["c0"]= "t"
            map["Yz"]= "c"
            map["ZE"]= "m"
            map["Qy"]= "B"
            map["U2"]= "V"
            map["Mw"]= "0"
            map["My"]= "2"
            map["RB"]= "J"
            map["U4"]= "X"
            map["M3"]= "7"
            map["Qz"]= "C"
            map["Y5"]= "i"
            map["dB"]= "z"
            map["U3"]= "W"
            map["Uz"]= "S"
            map["c1"]= "u"
            map["Y2"]= "f"
            map["RE"]= "M"
            map["c5"]= "y"
            map["ZB"]= "j"
            map["Q0"]= "D"
            map["RG"]= "O"
            map["VB"]= "Z"
            map["U5"]= "Y"
            map["Q4"]= "H"
            map["U1"]= "U"
            map["ZG"]= "o"
            map["cy"]= "r"
            map["Y3"]= "g"
            map["Mx"]= "1"
            map["Yx"]= "a"
            map["cw"]= "p"
            map["ZD"]= "l"
            map["M0"]= "4"
            map["ZF"]= "n"
            map["M4"]= "8"
            map["JG"]= "/"
            map["NB"]= ":"
            map["U0"]= "T"
            map["c3"]= "w"
            map["Uw"]= "P"
            map["Yy"]= "b"
            map["c2"]= "v"
            map["Y1"]= "e"
            map["RC"]= "K"
            map["Mz"]= "3"
            map["Q5"]= "I"
            map["JE"]= "-"
            map["VG"]= "_"

            val data = ciphertext.split("JT")
            val result = StringBuilder()
            //Log.d("Decrypt_init", "jtDecrypt: $result")
            //Log.d("Decrypt_init", "cipherText: $ciphertext")
            //Log.d("Decrypt_init", "Data: $data")
            for (element in data){
                if (element == ""){
                    continue
                }
                result.append(map[element])
                //Log.d("Decrypt_init", "result: $result")
            }
            return result.toString()
        }
    }
}