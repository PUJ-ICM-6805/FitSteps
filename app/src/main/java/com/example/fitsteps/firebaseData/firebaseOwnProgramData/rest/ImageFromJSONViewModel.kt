package com.example.fitsteps.firebaseData.firebaseOwnProgramData.rest

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImageFromJSONViewModel: ViewModel() {
    private var imagesLinks = mutableListOf<String>()
    private val usedImagesMap = mutableMapOf<String, Int>()
    val alreadyRequested = mutableStateOf(false)

    init {
        imagesLinks = mutableListOf()
    }
    fun getImageLinks(): MutableList<String> {
        return imagesLinks
    }

    fun setImagesLinks(links: List<String>){
        imagesLinks = links.toMutableList()
        resetUsedImagesMap()
    }

    fun isImageUsed(image: String): Boolean{
        return usedImagesMap.containsKey(image)
    }
    fun markImageAsUsed(image: String){
        if(usedImagesMap.containsKey(image)){
            usedImagesMap[image] = usedImagesMap[image]!! + 1
        }else{
            usedImagesMap[image] = 1
        }
    }
    fun markImageAsUnused(image: String){
        if(usedImagesMap.containsKey(image)){
            if(usedImagesMap[image]!! > 1){
                usedImagesMap[image] = usedImagesMap[image]!! - 1
            }else{
                usedImagesMap.remove(image)
            }
        }
    }

    private fun resetUsedImagesMap() {
        usedImagesMap.clear()
    }

    fun getFirstUnusedImage(): String{
        var image = ""
        for (i in imagesLinks){
            if(!isImageUsed(i)){
                image = i
                break
            }
        }
        return image
    }
}