package ru.itis.kpfu.games.cherry

import android.icu.text.ListFormatter.Width
import ru.itis.kpfu.games.R

object RowsGameLogic {

    private const val WIDTH=7
    private const val HEIGHT=6
    private val icons= listOf(R.drawable.blank_rows,R.drawable.cat_rows,R.drawable.devil_rows)

    private fun getIndex(i:Int, j:Int) :Int{
        return i*WIDTH+j
    }

    fun checkWin(list: List<Circle>, turn:Int) : List<Int>{
        val mark=icons[turn+1]
        var l= ArrayList<Int>()
        var v1:Int
        var v2:Int
        var v3:Int
        var v4:Int
//horizontally
//        var i=0
//        while(i<height*width) {
//            if(i%width==4){
//                i+=3
//            }
//            else{
//                if(mark==list[i].imageId && mark==list[i+1].imageId && mark==list[i+2].imageId && mark==list[i+3].imageId){
//                    return true
//                }
//                i+=1
//            }
//        }
        for (i in 0 until HEIGHT) {
            for (j in 0 until WIDTH - 3) {
                v1= getIndex(i,j)
                v2=getIndex(i,j+1)
                v3=getIndex(i,j+2)
                v4=getIndex(i,j+3)
                if (mark == list[v1].imageId && mark == list[v2].imageId
                        && mark == list[v3].imageId && mark == list[v4].imageId
                ) {
                    l.add(v1)
                    l.add(v2)
                    l.add(v3)
                    l.add(v4)
                    return l
                }
            }
        }
//vertically
//        i=0
//        var j=0
//        while(j<7){
//            if(i/width==4){
//                j+=1
//                i=0+j
//            } else{
//                if(mark==list[i].imageId && mark==list[i+width].imageId && mark==list[i+width*2].imageId && mark==list[i+width*3].imageId){
//                    return true
//                }
//                i+=7
//            }
//        }

        for (i in 0 until HEIGHT - 3) {
            for (j in 0 until WIDTH) {
                v1= getIndex(i,j)
                v2=getIndex(i+1,j)
                v3=getIndex(i+2,j)
                v4=getIndex(i+3,j)
                if (mark == list[v1].imageId && mark == list[v2].imageId
                    && mark == list[v3].imageId && mark == list[v4].imageId
                ) {
                    l.add(v1)
                    l.add(v2)
                    l.add(v3)
                    l.add(v4)
                    return l
                }
            }
        }
//diag \
        for (i in 0 until HEIGHT - 3) {
            for (j in 0 until WIDTH - 3) {
                v1= getIndex(i,j)
                v2=getIndex(i+1,j+1)
                v3=getIndex(i+2,j+2)
                v4=getIndex(i+3,j+3)
                if (mark == list[v1].imageId && mark == list[v2].imageId
                    && mark == list[v3].imageId && mark == list[v4].imageId
                ) {
                    l.add(v1)
                    l.add(v2)
                    l.add(v3)
                    l.add(v4)
                    return l
                }
            }
        }

//diag /
        for (i in 0 until HEIGHT - 3) {
            for (j in 0 until WIDTH - 3) {
                v1= getIndex(i+3,j)
                v2=getIndex(i+2,j+1)
                v3=getIndex(i+1,j+2)
                v4=getIndex(i,j+3)
                if (mark == list[v1].imageId && mark == list[v2].imageId
                    && mark == list[v3].imageId && mark == list[v4].imageId
                ) {
                    l.add(v1)
                    l.add(v2)
                    l.add(v3)
                    l.add(v4)
                    return l
                }
            }
        }
        l.add(-1)
        return l
    }

    fun addCircle(list: List<Circle>,index:Int) : Int?{
        val j=index% WIDTH
        for (i in HEIGHT-1 downTo 0) {
            val t=getIndex(i,j)
            if(list[t].imageId==icons[0]){
                return t
            }
        }
        return null
    }

    fun isFull(list: List<Circle>) : Boolean{
        for (i in 0 until HEIGHT* WIDTH) {
            if(list[i].imageId==icons[0]){
                return false
            }
        }
        return true
    }
}


