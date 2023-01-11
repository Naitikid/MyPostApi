package com.example.mypostapi.Model

import android.os.Parcelable
import android.provider.ContactsContract.RawContacts.Data
import kotlinx.android.parcel.Parcelize

@Parcelize

data class DefaultResponse (val status:Boolean,val message:String):Parcelable


